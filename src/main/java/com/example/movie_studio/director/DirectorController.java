package com.example.movie_studio.director;

import com.example.movie_studio.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/director/")
public class DirectorController implements DirectorService<Integer, DirectorDto> {
    private final DirectorServiceImpl directorServiceImpl;

    @Override
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DirectorDto>> create(@RequestBody @Valid DirectorDto dto) {
        return this.directorServiceImpl.create(dto);
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<DirectorDto>> get(@PathVariable("id") Integer id) {
        return this.directorServiceImpl.get(id);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<DirectorDto>> update(@RequestBody @Valid DirectorDto dto,
                                                           @PathVariable("id") Integer id) {
        return this.directorServiceImpl.update(dto, id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<DirectorDto>> delete(@PathVariable("id") Integer id) {
        return this.directorServiceImpl.delete(id);
    }

    @Override
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<DirectorDto>>> getAll() {
        return this.directorServiceImpl.getAll();
    }

    @Override
    @GetMapping("/getSomeIds/{ids}")
    public ResponseEntity<ApiResponse<List<DirectorDto>>> getBySomeIds(@PathVariable("ids") List<Integer> list) {
        return this.directorServiceImpl.getBySomeIds(list);
    }

    @Override
    @GetMapping("/getPages/{page}/{size}")
    public ResponseEntity<ApiResponse<Page<DirectorDto>>> getPages(@PathVariable("page") Integer page,
                                                                   @PathVariable("size") Integer size) {
        return this.directorServiceImpl.getPages(page, size);
    }

    @Override
    @GetMapping("/getWithMovie/{id}")
    public ResponseEntity<ApiResponse<DirectorDto>> getWithMovie(@PathVariable("id") Integer id) {
        return this.directorServiceImpl.getWithMovie(id);
    }
}
