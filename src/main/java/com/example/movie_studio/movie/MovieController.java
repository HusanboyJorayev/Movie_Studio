package com.example.movie_studio.movie;

import com.example.movie_studio.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/movie/")
public class MovieController implements MovieService<Integer, MovieDto> {
    private final MovieServiceImpl movieServiceImpl;

    @Override
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<MovieDto>> create(@RequestBody @Valid MovieDto dto) {
        return this.movieServiceImpl.create(dto);
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<MovieDto>> get(@PathVariable("id") Integer id) {
        return this.movieServiceImpl.get(id);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<MovieDto>> update(@RequestBody @Valid MovieDto dto,
                                                        @PathVariable("id") Integer id) {
        return this.movieServiceImpl.update(dto, id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<MovieDto>> delete(@PathVariable("id") Integer id) {
        return this.movieServiceImpl.delete(id);
    }

    @Override
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<MovieDto>>> getAll() {
        return this.movieServiceImpl.getAll();
    }

    @Override
    @GetMapping("/getPage/{page}/{size}")
    public ResponseEntity<ApiResponse<Page<MovieDto>>> getPage(@PathVariable("page") Integer page,
                                                               @PathVariable("size") Integer size) {
        return this.movieServiceImpl.getPage(page, size);
    }

    @Override
    @GetMapping("/getMovieSomeIds/{ids}")
    public ResponseEntity<ApiResponse<List<MovieDto>>> getMovieSomeIds(@PathVariable("ids") List<Integer> list) {
        return this.movieServiceImpl.getMovieSomeIds(list);
    }
}
