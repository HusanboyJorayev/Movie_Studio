package com.example.movie_studio.casts;

import com.example.movie_studio.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/casts/")
public class CastsController implements CastsService<Integer, CastsDto> {
    private final CastsServiceImpl castsServiceImpl;

    @Override
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CastsDto>> create(@RequestBody @Valid CastsDto dto) {
        return this.castsServiceImpl.create(dto);
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<CastsDto>> get(@PathVariable("id") Integer id) {
        return this.castsServiceImpl.get(id);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CastsDto>> update(@RequestBody @Valid CastsDto dto,
                                                        @PathVariable("id") Integer id) {
        return this.castsServiceImpl.update(dto, id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<CastsDto>> delete(@PathVariable("id") Integer id) {
        return this.castsServiceImpl.delete(id);
    }

    @Override
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<CastsDto>>> getAll() {
        return this.castsServiceImpl.getAll();
    }

    @Override
    @GetMapping("/getAllByActorId/{id}")
    public ResponseEntity<ApiResponse<List<CastsDto>>> getAllCastsByActorId(@PathVariable("id") Long id) {
        return this.castsServiceImpl.getAllCastsByActorId(id);
    }
}
