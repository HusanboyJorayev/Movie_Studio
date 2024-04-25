package com.example.movie_studio.actor;

import com.example.movie_studio.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/actor/")
@RequiredArgsConstructor
public class ActorController implements ActorService<Long, ActorDto> {
    private final ActorServiceImpl actorServiceImpl;

    @Override
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ActorDto>> create(@RequestBody ActorDto dto) {
        return this.actorServiceImpl.create(dto);
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<ActorDto>> get(@PathVariable("id") Long id) {
        return this.actorServiceImpl.get(id);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ActorDto>> update(@RequestBody ActorDto dto, @PathVariable("id") Long id) {
        return this.actorServiceImpl.update(dto, id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<ActorDto>> delete(@PathVariable("id") Long id) {
        return this.actorServiceImpl.delete(id);
    }

    @Override
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ActorDto>>> getAll() {
        return this.actorServiceImpl.getAll();
    }
}
