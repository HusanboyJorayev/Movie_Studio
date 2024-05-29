package com.example.movie_studio.studio;

import com.example.movie_studio.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/studio/")
public class StudioController implements StudioService<Integer, StudioDto> {
    private final StudentServiceImpl studentServiceImpl;

    @Override
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<StudioDto>> create(@RequestBody @Valid StudioDto dto) {
        return this.studentServiceImpl.create(dto);
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<StudioDto>> get(@PathVariable("id") Integer id) {
        return this.studentServiceImpl.get(id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<StudioDto>> delete(@PathVariable("id") Integer id) {
        return this.studentServiceImpl.delete(id);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<StudioDto>> update(@RequestBody @Valid StudioDto dto,
                                                         @PathVariable("id") Integer id) {
        return this.studentServiceImpl.update(dto, id);
    }

    @Override
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<StudioDto>>> getAll() {
        return this.studentServiceImpl.getAll();
    }

    @Override
    @GetMapping("/getPages/{page}/{size}")
    public ResponseEntity<ApiResponse<Page<StudioDto>>> getPages(@PathVariable("page") Integer page,
                                                                 @PathVariable("size") Integer size) {
        return this.studentServiceImpl.getPages(page, size);
    }

    @Override
    @GetMapping("/getSomeStudiosByIds/{ids}")
    public ResponseEntity<ApiResponse<List<StudioDto>>> getSomeStudiosByIds(@PathVariable("ids") List<Integer> list) {
        return this.studentServiceImpl.getSomeStudiosByIds(list);
    }

    @Override
    @GetMapping("/getStudioPagesByList/{page}/{size}")
    public ResponseEntity<ApiResponse<List<StudioDto>>> getStudioPagesByList(@PathVariable("page") Integer page,
                                                                             @PathVariable("size") Integer size) {
        return this.studentServiceImpl.getStudioPagesByList(page, size);
    }

    @Override
    @GetMapping("/getWithMovies/{id}")
    public ResponseEntity<ApiResponse<StudioDto>> getWithMovie(@PathVariable("id") Integer id) {
        return this.studentServiceImpl.getWithMovie(id);
    }

    @GetMapping("/filter_studio")
    public ResponseEntity<ApiResponse<List<StudioDto>>> filterStudio(@RequestParam(value = "company_name", required = false) String companyName,
                                                                     @RequestParam(value = "city", required = false) String city,
                                                                     @RequestParam(value = "founded", required = false) Integer founded,
                                                                     @RequestParam(value = "company_type", required = false) String companyType) {
        return this.studentServiceImpl.filterStudio(companyName, city, founded, companyType);
    }
}
