package com.example.movie_studio.casts;

import com.example.movie_studio.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CastsServiceImpl implements CastsService<Integer, CastsDto> {
    private final CastsRepository castsRepository;
    private final CastsMapper castsMapper;

    @Override
    public ResponseEntity<ApiResponse<CastsDto>> create(CastsDto dto) {
        var casts = this.castsMapper.toEntity(dto);
        casts.setUpdatedAt(LocalDateTime.now());
        this.castsRepository.save(casts);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CastsDto>builder()
                        .success(true)
                        .message("Created successfully")
                        .data(this.castsMapper.toDto(casts))
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse<CastsDto>> get(Integer id) {
        return this.castsRepository.findByCastsById(id)
                .map(casts -> ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(ApiResponse.<CastsDto>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.castsMapper.toDto(casts))
                                .build()))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<CastsDto>builder()
                                .code(-1)
                                .message("casts cannot be found")
                                .build()));
    }

    @Override
    public ResponseEntity<ApiResponse<CastsDto>> update(CastsDto dto, Integer id) {
        return this.castsRepository.findByCastsById(id)
                .map(casts -> {
                    casts.setUpdatedAt(LocalDateTime.now());
                    this.castsMapper.update(casts, dto);
                    var updateCasts = this.castsRepository.save(casts);
                    return ResponseEntity.status(HttpStatus.ACCEPTED)
                            .body(ApiResponse.<CastsDto>builder()
                                    .success(true)
                                    .message("Ok")
                                    .data(this.castsMapper.toDto(updateCasts))
                                    .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<CastsDto>builder()
                                .code(-1)
                                .message("casts cannot be found")
                                .build()));
    }

    @Override
    public ResponseEntity<ApiResponse<CastsDto>> delete(Integer id) {
        return this.castsRepository.findByCastsById(id)
                .map(casts -> {
                    casts.setDeletedAt(LocalDateTime.now());
                    this.castsRepository.delete(casts);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ApiResponse.<CastsDto>builder()
                                    .success(true)
                                    .message("Deleted Successfully")
                                    .data(this.castsMapper.toDto(casts))
                                    .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<CastsDto>builder()
                                .code(-1)
                                .message("Casts cannot be foumd")
                                .build()));
    }

    @Override
    public ResponseEntity<ApiResponse<List<CastsDto>>> getAll() {
        var allCasts = this.castsRepository.findAllCasts();
        if (allCasts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<List<CastsDto>>builder()
                            .code(-1)
                            .message("Casts are not found")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.<List<CastsDto>>builder()
                        .success(true)
                        .message("Ok")
                        .data(allCasts.stream().map(this.castsMapper::toDto).toList())
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse<List<CastsDto>>> getAllCastsByActorId(Long id) {
        var casts = this.castsRepository.findByCastsByActorId(id);
        if (casts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<List<CastsDto>>builder()
                            .code(-1)
                            .message("Casts not found ")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<CastsDto>>builder()
                        .success(true)
                        .message("Ok")
                        .data(casts.stream().map(this.castsMapper::toDto).toList())
                        .build());
    }

    public ResponseEntity<List<CastsDto>> filterCasts(Integer id, Integer moveId, Long actorId, String roleType,Integer page,Integer size) {
        Specification<Casts> castsFilter = new CastsFilter(id, moveId, actorId, roleType);
        Page<Casts> all = this.castsRepository.findAll(castsFilter, PageRequest.of(page, size));
        if (all.isEmpty()) {
            return ResponseEntity.ok().body(List.of());
        }
        return ResponseEntity.ok().body(all.stream().map(this.castsMapper::toDto).toList());
    }
}
