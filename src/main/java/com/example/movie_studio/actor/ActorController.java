package com.example.movie_studio.actor;

import com.example.movie_studio.dto.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/actor/")
@RequiredArgsConstructor
public class ActorController implements ActorService<Long, ActorDto> {
    private final ActorServiceImpl actorServiceImpl;

    @Override
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ActorDto>> create(@Valid @RequestBody ActorDto dto) {
        return this.actorServiceImpl.create(dto);
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<ActorDto>> get(@PathVariable("id") Long id) {
        return this.actorServiceImpl.get(id);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ActorDto>> update(@Valid @RequestBody ActorDto dto, @PathVariable("id") Long id) {
        return this.actorServiceImpl.update(dto, id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<ActorDto>> delete(@PathVariable("id") Long id) {
        return this.actorServiceImpl.delete(id);
    }

    @Override
    @DeleteMapping("/deleteByCode/{code}")
    public Integer deleteActorByQueryByCode(@PathVariable("code") Integer code) {
        return this.actorServiceImpl.deleteActorByQueryByCode(code);
    }

    @Override
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ActorDto>>> getAll() {
        return this.actorServiceImpl.getAll();
    }

    @Override
    @GetMapping("/deleteAll")
    public ResponseEntity<ApiResponse<String>> deleteAll() {
        return this.actorServiceImpl.deleteAll();
    }

    @Override
    @GetMapping("/someActorFields")
    public ResponseEntity<List<SomeActorFields>> someActorFields() {
        return this.actorServiceImpl.someActorFields();
    }

    @Override
    @GetMapping("/someActorFieldsByQuery")
    public ResponseEntity<List<SomeActorFields>> someActorFieldsByQuery() {
        return this.actorServiceImpl.someActorFieldsByQuery();
    }

    @GetMapping("/getActorWithCastsById/{id}")
    public ResponseEntity<ApiResponse<ActorDto>> getActorWithCastsById(@PathVariable("id") Long id) {
        return this.actorServiceImpl.getActorWithCastsById(id);
    }

    @Override
    @GetMapping("/filters")
    public ResponseEntity<ApiResponse<List<ActorDto>>> actorFilters(@RequestParam(value = "id", required = false) Long id,
                                                                    @RequestParam(value = "name", required = false) String name,
                                                                    @RequestParam(value = "codes", required = false) Integer codes,
                                                                    @RequestParam(value = "gender", required = false) String gender,
                                                                    @RequestParam(value = "nationality", required = false) String nationality,
                                                                    @RequestParam(value = "year of birth", required = false) Integer yearOfBirth) {
        return this.actorServiceImpl.actorFilters(id, name, codes, gender, nationality, yearOfBirth);
    }

    @Override
    @GetMapping("/getManyActorsById")
    public ResponseEntity<ApiResponse<List<ActorDto>>> getManyActorsById(@RequestParam(required = false) Set<Long> id) {
        return this.actorServiceImpl.getManyActorsById(id);
    }

    @Override
    @GetMapping("/exportToExcel")
    public void exportToExcel(HttpServletResponse response) {
        this.actorServiceImpl.exportToExcel(response);

    }
}
