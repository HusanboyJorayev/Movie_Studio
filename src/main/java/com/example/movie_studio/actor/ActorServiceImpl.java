package com.example.movie_studio.actor;

import com.example.movie_studio.casts.Casts;
import com.example.movie_studio.casts.CastsMapper;
import com.example.movie_studio.casts.CastsRepository;
import com.example.movie_studio.dto.ApiResponse;
import com.example.movie_studio.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService<Long, ActorDto> {
    private final ActorMapper actorMapper;
    private final CastsRepository castsRepository;
    private final ActorRepository actorRepository;

    @Override
    public ResponseEntity<ApiResponse<ActorDto>> create(ActorDto dto) {
        var actor = this.actorMapper.toEntity(dto);
        actor.setCreatedAt(LocalDateTime.now());
        this.actorRepository.save(actor);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ActorDto>builder()
                        .success(true)
                        .message("Successfully created")
                        .data(this.actorMapper.toDto(actor))
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse<ActorDto>> get(Long id) {
        return this.actorRepository.findActorByIdAndDeletedAtIsNull(id)
                .map(actor -> ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(ApiResponse.<ActorDto>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.actorMapper.toDto(actor))
                                .build()))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<ActorDto>builder()
                                .code(-1)
                                .message(String.format("Actor is not found this id : %d", id))
                                .build()));

    }

    @Override
    public ResponseEntity<ApiResponse<ActorDto>> update(ActorDto dto, Long id) {

        return this.actorRepository.findActorByIdAndDeletedAtIsNull(id)
                .map(actor -> {
                    actor.setUpdatedAt(LocalDateTime.now());
                    this.actorMapper.update(actor, dto);
                    var updateActor = this.actorRepository.save(actor);
                    return ResponseEntity.status(HttpStatus.ACCEPTED)
                            .body(ApiResponse.<ActorDto>builder()
                                    .success(true)
                                    .message("Ok")
                                    .data(this.actorMapper.toDto(updateActor))
                                    .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<ActorDto>builder()
                                .code(-1)
                                .message(String.format("Actor is not found this id : %d", id))
                                .build()));
    }

    @Override
    public ResponseEntity<ApiResponse<ActorDto>> delete(Long id) {
        return this.actorRepository.findActorByIdAndDeletedAtIsNull(id)
                .map(actor -> {
                    actor.setDeletedAt(LocalDateTime.now());
                    this.actorRepository.delete(actor);
                    return ResponseEntity.status(HttpStatus.ACCEPTED)
                            .body(ApiResponse.<ActorDto>builder()
                                    .success(true)
                                    .message("Ok")
                                    .data(this.actorMapper.toDto(actor))
                                    .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(ApiResponse.<ActorDto>builder()
                                .code(-1)
                                .message(String.format("Actor is not found this id : %d", id))
                                .build()));
    }

    @Override
    public Integer deleteActorByQueryByCode(Integer code) {
        return this.actorRepository.deleteActorByQueryByCode(code)
                .intValue();
    }

    @Override
    public ResponseEntity<ApiResponse<List<ActorDto>>> getAll() {
        List<Actor> allActors = this.actorRepository.findAllActors();
        if (allActors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<List<ActorDto>>builder()
                            .code(-1)
                            .message("Actor table is empty")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.<List<ActorDto>>builder()
                        .success(true)
                        .message("Ok")
                        .data(allActors.stream().map(this.actorMapper::toDto).toList())
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deleteAll() {
        this.actorRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.<String>builder()
                        .success(true)
                        .message("Ok")
                        .build());
    }

    public ResponseEntity<ApiResponse<ActorDto>> getActorWithCastsById(Long id) {
        return this.actorRepository.findActorByIdAndDeletedAtIsNull(id)
                .map(actor -> {
                    var casts = this.castsRepository.findByCastsByActorId(id);
                    actor.setCasts(casts);
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(ApiResponse.<ActorDto>builder()
                                    .success(true)
                                    .message("Ok")
                                    .data(this.actorMapper.toDtoWithCasts(actor))
                                    .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<ActorDto>builder()
                                .code(-1)
                                .message("Actor cannot be found")
                                .build()));
    }

    @Override
    public ResponseEntity<List<SomeActorFields>> someActorFields() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.actorMapper.toActor(this.actorRepository.findAllActors()));
    }

    @Override
    public ResponseEntity<ApiResponse<List<ActorDto>>> getManyActorsById(Set<Long> id) {
        List<Actor> manyActorsById = this.actorRepository.getManyActorsById(id);
        if (manyActorsById.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<List<ActorDto>>builder().build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<List<ActorDto>>builder()
                .success(true)
                .message("OK")
                .data(manyActorsById.stream().map(this.actorMapper::toDto).toList())
                .build());
    }

    @Override
    public ResponseEntity<List<SomeActorFields>> someActorFieldsByQuery() {
        return ResponseEntity.status(HttpStatus.OK).body(this.actorRepository.someActorFields());
    }

    @Override
    public ResponseEntity<ApiResponse<List<ActorDto>>> actorFilters(Long id, String name, Integer codes,
                                                                    String gender, String nationality, Integer yearOfBirth) {
        List<Actor> allFilterActors = this.actorRepository.getAllFilterActors(id, name, codes, gender, nationality, yearOfBirth);
        if (allFilterActors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<List<ActorDto>>builder()
                    .code(-1)
                    .message("Actor filters cannot be found")
                    .build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<List<ActorDto>>builder()
                .success(true)
                .message("Ok")
                .data(allFilterActors.stream().map(this.actorMapper::toDto).toList())
                .build());
    }
}
