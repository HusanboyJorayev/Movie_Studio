package com.example.movie_studio.director;

import com.example.movie_studio.dto.ApiResponse;
import com.example.movie_studio.movie.Movie;
import com.example.movie_studio.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService<Integer, DirectorDto> {
    private final DirectorMapper directorMapper;
    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;

    @Override
    public ResponseEntity<ApiResponse<DirectorDto>> create(DirectorDto dto) {
        var director = this.directorMapper.toEntity(dto);
        director.setCreatedAt(LocalDateTime.now());
        var savedDirector = this.directorRepository.save(director);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<DirectorDto>builder()
                        .success(true)
                        .message("Created successfully")
                        .data(this.directorMapper.toDto(savedDirector))
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse<DirectorDto>> get(Integer id) {
        return this.directorRepository.getDirectorById(id)
                .map(director -> ResponseEntity.status(HttpStatus.OK)
                        .body(ApiResponse.<DirectorDto>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.directorMapper.toDto(director))
                                .build()))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<DirectorDto>builder()
                                .code(-1)
                                .message("Director is not found")
                                .build()));

    }

    @Override
    public ResponseEntity<ApiResponse<DirectorDto>> update(DirectorDto dto, Integer id) {
        return this.directorRepository.getDirectorById(id)
                .map(director -> {
                    director.setUpdatedAt(LocalDateTime.now());
                    this.directorMapper.update(director, dto);
                    var updateDirector = this.directorRepository.save(director);
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(ApiResponse.<DirectorDto>builder()
                                    .success(true)
                                    .message("Ok")
                                    .data(this.directorMapper.toDto(updateDirector))
                                    .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<DirectorDto>builder()
                                .code(-1)
                                .message("Director is not found")
                                .build()));
    }

    @Override
    public ResponseEntity<ApiResponse<DirectorDto>> delete(Integer id) {
        return this.directorRepository.getDirectorById(id)
                .map(director -> {
                    director.setDeletedAt(LocalDateTime.now());
                    this.directorRepository.delete(director);
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(ApiResponse.<DirectorDto>builder()
                                    .success(true)
                                    .message("Ok")
                                    .data(this.directorMapper.toDto(director))
                                    .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<DirectorDto>builder()
                                .code(-1)
                                .message(String.format("Director is not found this id : %d", id))
                                .build()));
    }

    @Override
    public ResponseEntity<ApiResponse<List<DirectorDto>>> getAll() {
        var allDirectors = this.directorRepository.getAllDirectors();
        if (allDirectors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<List<DirectorDto>>builder()
                            .code(-1)
                            .message("allDirectors are not found")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DirectorDto>>builder()
                        .success(true)
                        .message("Ok")
                        .data(allDirectors.stream().map(this.directorMapper::toDto).toList())
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse<List<DirectorDto>>> getBySomeIds(List<Integer> list) {
        var directorsBySomeIds = this.directorRepository.getDirectorsBySomeIds(list);
        if (directorsBySomeIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<List<DirectorDto>>builder()
                            .code(-1)
                            .message("Directories are not found")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DirectorDto>>builder()
                        .success(true)
                        .message("Ok")
                        .data(directorsBySomeIds.stream().map(this.directorMapper::toDto).toList())
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse<Page<DirectorDto>>> getPages(Integer page, Integer size) {
        var getPage = this.directorRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size,
                Sort.by(Sort.Direction.ASC, "id")));
        if (getPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<Page<DirectorDto>>builder()
                            .code(-1)
                            .message("Directories are not found")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Page<DirectorDto>>builder()
                        .success(true)
                        .message("Ok")
                        .data(getPage.map(this.directorMapper::toDto))
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse<DirectorDto>> getWithMovie(Integer id) {
        return this.directorRepository.getDirectorById(id)
                .map(director -> {
                    var allMoviesByDirectorId = this.movieRepository.findAllMoviesByDirectorId(id);
                    if (allMoviesByDirectorId.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ApiResponse.<DirectorDto>builder()
                                        .code(-1)
                                        .message("Director is not found movie")
                                        .build());
                    }
                    director.setMovies(allMoviesByDirectorId);
                    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<DirectorDto>builder()
                            .success(true)
                            .message("Ok")
                            .data(this.directorMapper.toDtoWithMovie(director))
                            .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<DirectorDto>builder()
                        .code(-1)
                        .message("Director is not found")
                        .build()));
    }
}
