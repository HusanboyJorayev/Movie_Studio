package com.example.movie_studio.actor;

import com.example.movie_studio.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService<Long, ActorDto> {
    private final ActorMapper actorMapper;
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
        var actorDto = this.actorRepository.findActorByIdAndDeletedAtIsNull(id)
                .map(this.actorMapper::toDto)
                .orElse(null);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.<ActorDto>builder()
                        .success(true)
                        .message("Ok")
                        .data(actorDto)
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse<ActorDto>> update(ActorDto dto, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<ActorDto>> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<List<ActorDto>>> getAll() {
        return null;
    }
}
