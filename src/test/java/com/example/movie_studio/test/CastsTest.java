package com.example.movie_studio.test;

import com.example.movie_studio.casts.*;
import com.example.movie_studio.dto.ApiResponse;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CastsTest {

    @InjectMocks
    private CastsServiceImpl castsServiceImpl;
    @Mock
    private CastsMapper castsMapper;
    @Mock
    private CastsRepository castsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        Casts casts = Casts.builder()
                .id(1)
                .movieId(1)
                .actorId(1L)
                .roleType("role")
                .createdAt(LocalDateTime.now())
                .build();

        CastsDto dto = CastsDto.builder()
                .id(1)
                .movieId(1)
                .actorId(1L)
                .roleType("role")
                .createdAt(LocalDateTime.now())
                .build();

        when(this.castsMapper.toEntity(dto))
                .thenReturn(casts);
        when(this.castsMapper.toDto(casts))
                .thenReturn(dto);

        var create = this.castsServiceImpl.create(dto);
        ApiResponse<CastsDto> castsDto = ApiResponse.<CastsDto>builder()
                .success(true)
                .message("OK")
                .data(dto)
                .build();

        Assertions.assertEquals(dto, castsDto.getData());
        Assertions.assertTrue(castsDto.isSuccess());
        Assertions.assertEquals(castsDto.getCode(), 0);

        verify(this.castsMapper, times(1)).toDto(casts);
        verify(this.castsMapper, times(1)).toEntity(dto);
        verify(this.castsRepository, times(1)).save(casts);
    }

    @Test
    void getPositive() {
        Casts casts = Casts.builder()
                .id(1)
                .movieId(1)
                .actorId(1L)
                .roleType("role")
                .createdAt(LocalDateTime.now())
                .build();

        CastsDto dto = CastsDto.builder()
                .id(1)
                .movieId(1)
                .actorId(1L)
                .roleType("role")
                .createdAt(LocalDateTime.now())
                .build();

        when(this.castsRepository.findByCastsById(casts.getId()))
                .thenReturn(Optional.of(casts));
        when(this.castsMapper.toDto(casts))
                .thenReturn(dto);

        var get = this.castsServiceImpl.get(casts.getId());
        ApiResponse<CastsDto> castsDto = ApiResponse.<CastsDto>builder()
                .success(true)
                .message("Ok")
                .data(dto)
                .build();

        verify(this.castsRepository, times(1)).findByCastsById(casts.getId());
        verify(this.castsMapper, times(1)).toDto(casts);
    }

    @Test
    void getNegative() {
        Casts casts = Casts.builder()
                .build();

        when(this.castsRepository.findByCastsById(casts.getId()))
                .thenReturn(Optional.of(casts));

        var get = this.castsServiceImpl.get(casts.getId());
        ApiResponse<CastsDto> castsDto = ApiResponse.<CastsDto>builder()
                .message("Cast is not found")
                .build();
        Assertions.assertFalse(castsDto.isSuccess());
        Assertions.assertNull(castsDto.getData());
        Assertions.assertEquals(castsDto.getMessage(), "Cast is not found");

        verify(this.castsRepository, times(1)).findByCastsById(casts.getId());
    }

    @Test
    void updatePositive() {
        Casts casts = Casts.builder()
                .id(1)
                .movieId(1)
                .actorId(1L)
                .roleType("role")
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        CastsDto dto = CastsDto.builder()
                .id(1)
                .movieId(1)
                .actorId(1L)
                .roleType("role")
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        when(this.castsRepository.findByCastsById(casts.getId()))
                .thenReturn(Optional.of(casts));
        when(this.castsMapper.toDto(casts))
                .thenReturn(dto);

        var update = this.castsServiceImpl.update(dto, casts.getId());
        ApiResponse<CastsDto> castsDto = ApiResponse.<CastsDto>builder()
                .success(true)
                .message("Ok")
                .data(dto)
                .build();

        Assertions.assertEquals(castsDto.getData(), dto);
        Assertions.assertTrue(castsDto.isSuccess());
        Assertions.assertEquals(castsDto.getCode(), 0);
        Assertions.assertEquals(castsDto.getData().getRoleType(), dto.getRoleType());


        verify(this.castsRepository, times(1)).save(casts);
        verify(this.castsMapper, times(1)).update(casts, dto);
        verify(this.castsRepository, times(1)).findByCastsById(casts.getId());
    }

    @Test
    void updateNegative() {
        Casts casts = Casts.builder()
                .build();

        CastsDto dto = CastsDto.builder()
                .build();

        when(this.castsRepository.findByCastsById(casts.getId()))
                .thenReturn(Optional.of(casts));

        var update = this.castsServiceImpl.update(dto, casts.getId());
        ApiResponse<CastsDto> castsDto = ApiResponse.<CastsDto>builder()
                .code(-1)
                .message("Cast is not found")
                .build();

        Assertions.assertNull(castsDto.getData());
        Assertions.assertFalse(castsDto.isSuccess());
        Assertions.assertEquals(castsDto.getCode(), -1);
        Assertions.assertFalse(castsDto.isSuccess());


        verify(this.castsMapper, times(1)).update(casts, dto);
        verify(this.castsRepository, times(1)).findByCastsById(casts.getId());
    }

    @Test
    void deletePositive() {
        Casts casts = Casts.builder()
                .id(1)
                .movieId(1)
                .actorId(1L)
                .roleType("role")
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        CastsDto dto = CastsDto.builder()
                .id(1)
                .movieId(1)
                .actorId(1L)
                .roleType("role")
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        when(this.castsRepository.findByCastsById(casts.getId()))
                .thenReturn(Optional.of(casts));
        when(this.castsMapper.toDto(casts))
                .thenReturn(dto);

        var delete = this.castsServiceImpl.delete(casts.getId());
        ApiResponse<CastsDto> castsDto = ApiResponse.<CastsDto>builder()
                .success(true)
                .message("Ok")
                .data(dto)
                .build();

        Assertions.assertEquals(castsDto.getData(), dto);
        Assertions.assertTrue(castsDto.isSuccess());
        Assertions.assertEquals(castsDto.getCode(), 0);
        Assertions.assertEquals(castsDto.getData().getRoleType(), dto.getRoleType());


        verify(this.castsRepository, times(1)).delete(casts);
        verify(this.castsRepository, times(1)).findByCastsById(casts.getId());
    }

    @Test
    void deleteNegative() {
        Casts casts = Casts.builder()
                .build();

        when(this.castsRepository.findByCastsById(casts.getId()))
                .thenReturn(Optional.of(casts));

        var update = this.castsServiceImpl.delete(casts.getId());
        ApiResponse<CastsDto> castsDto = ApiResponse.<CastsDto>builder()
                .code(-1)
                .message("Cast is not found")
                .build();

        Assertions.assertNull(castsDto.getData());
        Assertions.assertFalse(castsDto.isSuccess());
        Assertions.assertEquals(castsDto.getCode(), -1);
        Assertions.assertFalse(castsDto.isSuccess());


        verify(this.castsRepository, times(1)).findByCastsById(casts.getId());
    }

    @Test
    void getAllPositive() {
        Casts casts = Casts.builder()
                .id(1)
                .movieId(1)
                .actorId(1L)
                .roleType("role")
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        CastsDto dto = CastsDto.builder()
                .id(1)
                .movieId(1)
                .actorId(1L)
                .roleType("role")
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        when(this.castsRepository.findAllCasts())
                .thenReturn(List.of(casts));

        var getAll = this.castsServiceImpl.getAll();
        ApiResponse<List<CastsDto>> castList = ApiResponse.<List<CastsDto>>builder()
                .success(true)
                .message("Ok")
                .data(List.of(dto))
                .build();

        Assertions.assertEquals(castList.getData().size(), 1);
        Assertions.assertTrue(castList.isSuccess());
        Assertions.assertEquals(castList.getCode(), 0);

        verify(this.castsRepository, times(1)).findAllCasts();
    }

    @Test
    void getAllNegative() {
        Casts casts = Casts.builder()
                .build();
        when(this.castsRepository.findAllCasts())
                .thenReturn(List.of());

        var getAll = this.castsServiceImpl.getAll();
        ApiResponse<List<CastsDto>> castList = ApiResponse.<List<CastsDto>>builder()
                .message("Ok")
                .code(-1)
                .build();

        Assertions.assertNull(castList.getData());
        Assertions.assertFalse(castList.isSuccess());
        Assertions.assertEquals(castList.getCode(), -1);

        verify(this.castsRepository, times(1)).findAllCasts();

    }

    @Test
    void getAllCastsByActorIdPositive() {
        Casts casts = Casts.builder()
                .id(1)
                .movieId(1)
                .actorId(1L)
                .roleType("role")
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        CastsDto dto = CastsDto.builder()
                .id(1)
                .movieId(1)
                .actorId(1L)
                .roleType("role")
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        when(this.castsRepository.findByCastsByActorId(casts.getActorId()))
                .thenReturn(List.of(casts));

        var getAllCastsByActorID = this.castsServiceImpl.getAllCastsByActorId(casts.getActorId());
        ApiResponse<List<CastsDto>> castList = ApiResponse.<List<CastsDto>>builder()
                .success(true)
                .message("Ok")
                .data(List.of(dto))
                .build();

        Assertions.assertEquals(castList.getData().size(), 1);
        Assertions.assertTrue(castList.isSuccess());
        Assertions.assertEquals(castList.getCode(), 0);

        verify(this.castsRepository, times(1)).findByCastsByActorId(casts.getActorId());

    }

    @Test
    void getAllCastsByActorIdNegative() {
        Casts casts = Casts.builder()
                .build();
        when(this.castsRepository.findByCastsByActorId(casts.getActorId()))
                .thenReturn(List.of());

        var getAllCastsByActorId = this.castsServiceImpl.getAllCastsByActorId(casts.getActorId());
        ApiResponse<List<CastsDto>> castList = ApiResponse.<List<CastsDto>>builder()
                .message("Ok")
                .code(-1)
                .build();

        Assertions.assertNull(castList.getData());
        Assertions.assertFalse(castList.isSuccess());
        Assertions.assertEquals(castList.getCode(), -1);

        verify(this.castsRepository, times(1)).findByCastsByActorId(casts.getActorId());
    }
}
