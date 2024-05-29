package com.example.movie_studio.movie;

import com.example.movie_studio.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService<Integer, MovieDto> {
    private final MovieMapper mapper;
    private final MovieRepository movieRepository;

    @Override
    public ResponseEntity<ApiResponse<MovieDto>> create(MovieDto dto) {
        var movie = this.mapper.toEntity(dto);
        movie.setCreatedAt(LocalDateTime.now());
        var createMovie = this.movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MovieDto>builder()
                        .success(true)
                        .message("Create successfully")
                        .data(this.mapper.toDto(createMovie))
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse<MovieDto>> get(Integer id) {
        return this.movieRepository.findByIdAndDeletedAtNull(id)
                .map(movie -> ResponseEntity.status(HttpStatus.OK)
                        .body(ApiResponse.<MovieDto>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.mapper.toDto(movie))
                                .build()))
                .orElse(ResponseEntity.status(HttpStatus.OK)
                        .body(ApiResponse.<MovieDto>builder()
                                .code(-1)
                                .message("Movie is not found")
                                .build()));
    }

    @Override
    public ResponseEntity<ApiResponse<MovieDto>> update(MovieDto dto, Integer id) {
        return this.movieRepository.findByIdAndDeletedAtNull(id)
                .map(movie -> {
                    movie.setUpdatedAt(LocalDateTime.now());
                    this.mapper.update(movie, dto);
                    var updateMovie = this.movieRepository.save(movie);
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(ApiResponse.<MovieDto>builder()
                                    .success(true)
                                    .message("Ok")
                                    .data(this.mapper.toDto(movie))
                                    .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<MovieDto>builder()
                                .code(-1)
                                .message("Movie is not found")
                                .build()));
    }

    @Override
    public ResponseEntity<ApiResponse<MovieDto>> delete(Integer id) {
        return this.movieRepository.findByIdAndDeletedAtNull(id)
                .map(movie -> {
                    movie.setDeletedAt(LocalDateTime.now());
                    this.movieRepository.delete(movie);
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(ApiResponse.<MovieDto>builder()
                                    .success(true)
                                    .message("Ok")
                                    .data(this.mapper.toDto(movie))
                                    .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<MovieDto>builder()
                                .code(-1)
                                .message("Movie is not found")
                                .build()));
    }

    @Override
    public ResponseEntity<ApiResponse<List<MovieDto>>> getAll() {
        var allMovies = this.movieRepository.findAllMovies();
        if (allMovies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.<List<MovieDto>>builder()
                            .code(-1)
                            .message("Movies are not found")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MovieDto>>builder()
                        .success(true)
                        .message("OK")
                        .data(allMovies.stream().map(this.mapper::toDto).toList())
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse<Page<MovieDto>>> getPage(Integer page, Integer size) {

        var pages = this.movieRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (pages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<Page<MovieDto>>builder()
                            .code(-1)
                            .message("Movies are not found")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Page<MovieDto>>builder()
                        .success(true)
                        .message("Ok")
                        .data(pages.map(this.mapper::toDto))
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse<List<MovieDto>>> getMovieSomeIds(List<Integer> list) {
        var movies = this.movieRepository.getMoviesBySomeIds(list);
        if (movies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<List<MovieDto>>builder()
                            .code(-1)
                            .message("movies are not found")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MovieDto>>builder()
                        .success(true)
                        .message("Ok")
                        .data(movies.stream().map(this.mapper::toDto).toList())
                        .build());
    }

    public ResponseEntity<ApiResponse<List<MovieDto>>> filterMovie(Set<Integer> ids,
                                                                   Integer directorId,
                                                                   Integer studioId,
                                                                   String name,
                                                                   String countryOfRelease,
                                                                   String language,
                                                                   String filmingLocation,
                                                                   Integer yearOfRelease,
                                                                   String category) {
        Specification<Movie> movieSpecification = new MovieFilter(ids, directorId, studioId, name, countryOfRelease,
                language, filmingLocation, yearOfRelease, category);
        List<Movie> all = this.movieRepository.findAll(movieSpecification);
        return ResponseEntity.ok(ApiResponse.<List<MovieDto>>builder()
                .success(true)
                .message("Ok")
                .data(all.stream().map(this.mapper::toDto).toList())
                .build());
    }
}
