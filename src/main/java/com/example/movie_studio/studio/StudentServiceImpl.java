package com.example.movie_studio.studio;

import com.example.movie_studio.dto.ApiResponse;
import com.example.movie_studio.movie.Movie;
import com.example.movie_studio.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudioService<Integer, StudioDto> {
    private final StudioMapper studioMapper;
    private final MovieRepository movieRepository;
    private final StudioRepository studioRepository;

    @Override
    public ResponseEntity<ApiResponse<StudioDto>> create(StudioDto dto) {
        var studio = this.studioMapper.toEntity(dto);
        studio.setCreatedAt(LocalDateTime.now());
        var createStudio = this.studioRepository.save(studio);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<StudioDto>builder()
                .success(true)
                .message("Ok")
                .data(this.studioMapper.toDto(createStudio))
                .build());
    }

    @Override
    public ResponseEntity<ApiResponse<StudioDto>> get(Integer id) {
        return this.studioRepository.findStudioById(id)
                .map(studio -> ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<StudioDto>builder()
                        .success(true)
                        .message("Ok")
                        .data(this.studioMapper.toDto(studio))
                        .build()))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<StudioDto>builder()
                        .code(-1)
                        .message("Studio is not found")
                        .build()));

    }

    @Override
    public ResponseEntity<ApiResponse<StudioDto>> delete(Integer id) {
        return this.studioRepository.findStudioById(id)
                .map(studio -> {
                    studio.setDeletedAt(LocalDateTime.now());
                    this.studioRepository.delete(studio);
                    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<StudioDto>builder()
                            .success(true)
                            .message("Ok")
                            .data(this.studioMapper.toDto(studio))
                            .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<StudioDto>builder()
                        .code(-1)
                        .message("Studio is not found")
                        .build()));
    }

    @Override
    public ResponseEntity<ApiResponse<StudioDto>> update(StudioDto dto, Integer id) {
        return this.studioRepository.findStudioById(id)
                .map(studio -> {
                    studio.setUpdatedAt(LocalDateTime.now());
                    this.studioMapper.update(studio, dto);
                    var updateStudio = this.studioRepository.save(studio);
                    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<StudioDto>builder()
                            .success(true)
                            .message("OK")
                            .data(this.studioMapper.toDto(updateStudio))
                            .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<StudioDto>builder()
                        .code(-1)
                        .message("Studio is not found")
                        .build()));
    }

    @Override
    public ResponseEntity<ApiResponse<List<StudioDto>>> getAll() {
        var allStudios = this.studioRepository.findAllStudios();
        if (allStudios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<List<StudioDto>>builder()
                    .code(-1)
                    .message("Studios are not found ")
                    .build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<List<StudioDto>>builder()
                .success(true)
                .message("Ok")
                .data(allStudios.stream().map(this.studioMapper::toDto).toList())
                .build());
    }

    @Override
    public ResponseEntity<ApiResponse<Page<StudioDto>>> getPages(Integer page, Integer size) {
        Page<Studio> studioPage = this.studioRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (studioPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<Page<StudioDto>>builder()
                    .code(-1)
                    .message("Studios are not found")
                    .build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<Page<StudioDto>>builder()
                .success(true)
                .message("Ok")
                .data(studioPage.map(this.studioMapper::toDto))
                .build());
    }

    @Override
    public ResponseEntity<ApiResponse<List<StudioDto>>> getSomeStudiosByIds(List<Integer> list) {
        List<Studio> studiosSomeIds = this.studioRepository.getStudiosSomeIds(list);
        if (studiosSomeIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<List<StudioDto>>builder()
                    .code(-1)
                    .message("OK")
                    .build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<List<StudioDto>>builder()
                .success(true)
                .message("OK")
                .data(studiosSomeIds.stream().map(this.studioMapper::toDto).toList())
                .build());
    }

    @Override
    public ResponseEntity<ApiResponse<List<StudioDto>>> getStudioPagesByList(Integer page, Integer size) {
        List<Studio> pagesByList = this.studioRepository.getStudioPagesByList(page, size);
        if (pagesByList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<List<StudioDto>>builder()
                    .code(-1)
                    .message("Studios are not found")
                    .build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<List<StudioDto>>builder()
                .success(true)
                .message("OK")
                .data(pagesByList.stream().map(this.studioMapper::toDto).toList())
                .build());
    }

    @Override
    public ResponseEntity<ApiResponse<StudioDto>> getWithMovie(Integer id) {
        return this.studioRepository.findStudioById(id)
                .map(studio -> {
                    var allMoviesByStudioId = this.movieRepository.findAllMoviesByStudioId(id);
                    if (allMoviesByStudioId.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<StudioDto>builder()
                                .code(-1)
                                .message("Studio is not found movie table")
                                .build());
                    }
                    studio.setMovies(allMoviesByStudioId);
                    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<StudioDto>builder()
                            .success(true)
                            .message("OK")
                            .data(this.studioMapper.toDtoWithMovie(studio))
                            .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<StudioDto>builder()
                        .code(-1)
                        .message("Studio is not found")
                        .build()));
    }

    public ResponseEntity<ApiResponse<List<StudioDto>>> filterStudio(String companyName, String city, Integer founded, String companyType) {
        Specification<Studio> specification = new StudioFilter(companyName, city, founded, companyType);
        List<Studio> all = this.studioRepository.findAll(specification);
        if (!all.isEmpty())
            return ResponseEntity.ok(ApiResponse.<List<StudioDto>>builder()
                    .success(true)
                    .message("Ok")
                    .data(all.stream().map(this.studioMapper::toDto).toList())
                    .build());
        return ResponseEntity.ok(ApiResponse.<List<StudioDto>>builder().build());
    }
}
