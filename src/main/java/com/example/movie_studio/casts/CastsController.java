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

    @GetMapping("/castsFilter")
    public ResponseEntity<List<CastsDto>> filterCasts(@RequestParam(value = "id", required = false) Integer id,
                                                      @RequestParam(value = "moveId", required = false) Integer moveId,
                                                      @RequestParam(value = "actorId", required = false) Long actorId,
                                                      @RequestParam(value = "role_type", required = false) String roleType,
                                                      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return this.castsServiceImpl.filterCasts(id, moveId, actorId, roleType, page, size);
    }

    @GetMapping("/get_some_fields")
    public ResponseEntity<List<CastsDto.SubCasts>> someCastsFields() {
        return this.castsServiceImpl.someCastsFields();
    }
}
