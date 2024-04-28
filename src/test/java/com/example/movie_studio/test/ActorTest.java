package com.example.movie_studio.test;

import com.example.movie_studio.actor.*;
import com.example.movie_studio.casts.Casts;
import com.example.movie_studio.casts.CastsDto;
import com.example.movie_studio.casts.CastsRepository;
import com.example.movie_studio.dto.ApiResponse;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ActorTest {

    @InjectMocks
    private ActorServiceImpl actorServiceImpl;

    @Mock
    private ActorMapper actorMapper;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private CastsRepository castsRepository;


    @BeforeEach
    void initMethod() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPositive() {
        Actor actor = Actor.builder()
                .id(1L)
                .codes(101)
                .yearOfBirth(1990)
                .nationality("Uzbek")
                .name("Name")
                .gender("Female")
                .createdAt(LocalDateTime.now())
                .build();

        ActorDto dto = ActorDto.builder()
                .id(1L)
                .codes(101)
                .yearOfBirth(1990)
                .nationality("Uzbek")
                .name("Name")
                .gender("Female")
                .createdAt(LocalDateTime.now())
                .build();

        when(this.actorMapper.toDto(actor))
                .thenReturn(dto);
        when(this.actorMapper.toEntity(dto))
                .thenReturn(actor);
        when(this.actorRepository.save(actor))
                .thenReturn(actor);

        var apiResponseResponseEntity = this.actorServiceImpl.create(dto);
        ApiResponse<ActorDto> actorDto = ApiResponse.<ActorDto>builder()
                .success(true)
                .message("Ok")
                .data(dto)
                .build();
        Assertions.assertEquals(0, actorDto.getCode());
        Assertions.assertEquals(dto.getCodes(), actorDto.getData().getCodes());
        Assertions.assertTrue(actorDto.isSuccess());
        Assertions.assertEquals(dto.getYearOfBirth(), actorDto.getData().getYearOfBirth());

        verify(actorRepository, times(1)).save(actor);
        verify(actorMapper, times(1)).toDto(actor);
        verify(actorMapper, times(1)).toEntity(dto);
    }

    @Test
    void getPositive() {

        Actor actor = Actor.builder()
                .id(1L)
                .codes(101)
                .yearOfBirth(1990)
                .nationality("Uzbek")
                .name("Name")
                .gender("Female")
                .createdAt(LocalDateTime.now())
                .build();

        ActorDto dto = ActorDto.builder()
                .id(1L)
                .codes(101)
                .yearOfBirth(1990)
                .nationality("Uzbek")
                .name("Name")
                .gender("Female")
                .createdAt(LocalDateTime.now())
                .build();

        when(this.actorMapper.toDto(actor))
                .thenReturn(dto);
        when(this.actorRepository.findActorByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(actor));

        var getActor = this.actorServiceImpl.get(1L);
        ApiResponse<ActorDto> actorDto = ApiResponse.<ActorDto>builder()
                .success(true)
                .message("Ok")
                .data(dto)
                .build();

        Assertions.assertEquals(1L, actorDto.getData().getId());
        Assertions.assertTrue(actorDto.isSuccess());
        Assertions.assertEquals(actorDto.getMessage(), "Ok");
        Assertions.assertEquals(0, actorDto.getCode());

        verify(this.actorMapper, times(1)).toDto(actor);
        verify(this.actorRepository, times(1)).findActorByIdAndDeletedAtIsNull(1L);
    }

    @Test
    void getNegative() {
        Actor actor = Actor.builder().build();
        ActorDto dto = ActorDto.builder().build();
        when(this.actorMapper.toDto(actor))
                .thenReturn(dto);
        when(this.actorRepository.findActorByIdAndDeletedAtIsNull(actor.getId()))
                .thenReturn(Optional.of(actor));

        var getActor = this.actorServiceImpl.get(actor.getId());
        ApiResponse<ActorDto> actorDto = ApiResponse.<ActorDto>builder()
                .message("Actor is not found")
                .build();
        Assertions.assertFalse(actorDto.isSuccess());
        Assertions.assertEquals(actorDto.getMessage(), "Actor is not found");
        Assertions.assertNull(actorDto.getData());

        verify(this.actorRepository, times(1)).findActorByIdAndDeletedAtIsNull(actor.getId());
    }

    @Test
    void updatePositive() {
        Actor actor = Actor.builder()
                .id(1L)
                .codes(101)
                .yearOfBirth(1990)
                .nationality("Uzbek")
                .name("Name")
                .gender("Female")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ActorDto dto = ActorDto.builder()
                .id(1L)
                .codes(101)
                .yearOfBirth(1990)
                .nationality("Uzbek")
                .name("Name")
                .gender("Female")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(this.actorRepository.findActorByIdAndDeletedAtIsNull(actor.getId()))
                .thenReturn(Optional.of(actor));
        when(this.actorMapper.toDto(actor))
                .thenReturn(dto);
        when(this.actorRepository.save(actor))
                .thenReturn(actor);

        var updateActor = this.actorServiceImpl.update(dto, actor.getId());
        ApiResponse<ActorDto> actorDto = ApiResponse.<ActorDto>builder()
                .success(true)
                .message("OK")
                .data(dto)
                .build();

        Assertions.assertEquals(1L, actorDto.getData().getId());
        Assertions.assertTrue(actorDto.isSuccess());
        Assertions.assertEquals(actorDto.getMessage(), "OK");
        Assertions.assertEquals(0, actorDto.getCode());

        verify(this.actorMapper, times(1)).toDto(actor);
        verify(this.actorRepository, times(1)).save(actor);
        verify(this.actorRepository, times(1)).findActorByIdAndDeletedAtIsNull(actor.getId());

    }

    @Test
    void UpdateNegative() {
        Actor actor = Actor.builder().build();
        ActorDto dto = ActorDto.builder().build();
        when(this.actorMapper.toDto(any()))
                .thenReturn(dto);
        when(this.actorRepository.findActorByIdAndDeletedAtIsNull(actor.getId()))
                .thenReturn(Optional.empty());

        var updateActor = this.actorServiceImpl.update(dto, actor.getId());
        ApiResponse<ActorDto> actorDto = ApiResponse.<ActorDto>builder()
                .message("Actor is not found")
                .build();
        Assertions.assertFalse(actorDto.isSuccess());
        Assertions.assertEquals(actorDto.getMessage(), "Actor is not found");
        Assertions.assertNull(actorDto.getData());

        verify(this.actorRepository, times(1)).findActorByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deletePositive() {
        Actor actor = Actor.builder()
                .id(1L)
                .codes(101)
                .yearOfBirth(1990)
                .nationality("Uzbek")
                .name("Name")
                .gender("Female")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        ActorDto dto = ActorDto.builder()
                .id(1L)
                .codes(101)
                .yearOfBirth(1990)
                .nationality("Uzbek")
                .name("Name")
                .gender("Female")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        when(this.actorRepository.findActorByIdAndDeletedAtIsNull(actor.getId()))
                .thenReturn(Optional.of(actor));
        when(this.actorMapper.toDto(actor))
                .thenReturn(dto);

        var deleteActor = this.actorServiceImpl.delete(actor.getId());
        ApiResponse<ActorDto> actorDto = ApiResponse.<ActorDto>builder()
                .success(true)
                .message("OK")
                .data(dto)
                .build();

        Assertions.assertEquals(1L, actorDto.getData().getId());
        Assertions.assertTrue(actorDto.isSuccess());
        Assertions.assertEquals(actorDto.getMessage(), "OK");
        Assertions.assertEquals(0, actorDto.getCode());

        verify(this.actorMapper, times(1)).toDto(actor);
        verify(this.actorRepository, times(1)).delete(actor);
        verify(this.actorRepository, times(1)).findActorByIdAndDeletedAtIsNull(actor.getId());
    }

    @Test
    void deleteNegative() {
        Actor actor = Actor.builder().build();

        when(this.actorRepository.findActorByIdAndDeletedAtIsNull(actor.getId()))
                .thenReturn(Optional.empty());

        var deleteActor = this.actorServiceImpl.delete(actor.getId());
        ApiResponse<ActorDto> actorDto = ApiResponse.<ActorDto>builder()
                .message("Actor is not found")
                .build();
        Assertions.assertFalse(actorDto.isSuccess());
        Assertions.assertEquals(actorDto.getMessage(), "Actor is not found");
        Assertions.assertNull(actorDto.getData());

        verify(this.actorRepository, times(1)).findActorByIdAndDeletedAtIsNull(actor.getId());
    }

    @Test
    void deleteActorByCode() {
        int code = 0;
        when(this.actorRepository.deleteActorByQueryByCode(code))
                .thenReturn(code);
        Integer i = this.actorServiceImpl.deleteActorByQueryByCode(code);
        Assertions.assertEquals(code, i);

        verify(this.actorRepository, times(1)).deleteActorByQueryByCode(code);
    }

    @Test
    void getAllPositive() {
        Actor actor = Actor.builder()
                .id(1L)
                .codes(101)
                .yearOfBirth(1990)
                .nationality("Uzbek")
                .name("Name")
                .gender("Female")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        ActorDto dto = ActorDto.builder()
                .id(1L)
                .codes(101)
                .yearOfBirth(1990)
                .nationality("Uzbek")
                .name("Name")
                .gender("Female")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        when(this.actorRepository.findAllActors())
                .thenReturn(List.of(actor));
        when(this.actorMapper.toDto(actor))
                .thenReturn(dto);

        var getAllActors = this.actorServiceImpl.getAll();
        ApiResponse<List<ActorDto>> actorDto = ApiResponse.<List<ActorDto>>builder()
                .success(true)
                .message("OK")
                .data(List.of(dto))
                .build();

        Assertions.assertTrue(actorDto.isSuccess());
        Assertions.assertEquals(actorDto.getMessage(), "OK");
        Assertions.assertEquals(0, actorDto.getCode());

        verify(this.actorMapper, times(1)).toDto(actor);
        verify(this.actorRepository, times(1)).findAllActors();
    }

    @Test
    void getAllNegative() {
        Actor actor = Actor.builder().build();
        when(this.actorRepository.findAllActors())
                .thenReturn(List.of(actor));

        var getAll = this.actorServiceImpl.getAll();
        ApiResponse<List<ActorDto>> actorDto = ApiResponse.<List<ActorDto>>builder()
                .message("Actors are not found")
                .build();
        Assertions.assertFalse(actorDto.isSuccess());
        Assertions.assertEquals(actorDto.getMessage(), "Actors are not found");
        Assertions.assertNull(actorDto.getData());

        verify(this.actorRepository, times(1)).findAllActors();
    }

    @Test
    void deleteAllPositive() {
        String data = "Deleted successfully";

        var deleteAll = this.actorServiceImpl.deleteAll();
        ApiResponse<String> delete = ApiResponse.<String>builder()
                .success(true)
                .message("Ok")
                .data(data)
                .build();

        Assertions.assertTrue(delete.isSuccess());
        Assertions.assertEquals(delete.getData(), data);
        Assertions.assertEquals(delete.getMessage(), "Ok");

        verify(this.actorRepository, times(1)).deleteAll();
    }

    @Test
    void deleteAllNegative() {
        String data = "no Deleted";

        var deleteAll = this.actorServiceImpl.deleteAll();
        ApiResponse<String> delete = ApiResponse.<String>builder()
                .message("Something error")
                .data(data)
                .build();

        Assertions.assertFalse(delete.isSuccess());
        Assertions.assertEquals(delete.getData(), data);
        Assertions.assertEquals(delete.getMessage(), "Something error");

        verify(this.actorRepository, times(1)).deleteAll();
    }

    @Test
    void getActorWithCastsPositive() {
        Casts casts = Casts.builder()
                .id(1)
                .actorId(1L)
                .roleType("Actor")
                .movieId(1)
                .createdAt(LocalDateTime.now())
                .build();
        CastsDto castsDto = CastsDto.builder()
                .id(1)
                .actorId(1L)
                .roleType("Actor")
                .movieId(1)
                .createdAt(LocalDateTime.now())
                .build();

        Actor actor = Actor.builder()
                .id(1L)
                .codes(101)
                .yearOfBirth(1990)
                .nationality("Uzbek")
                .name("Name")
                .gender("Female")
                .casts(List.of(casts))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        ActorDto dto = ActorDto.builder()
                .id(1L)
                .codes(101)
                .yearOfBirth(1990)
                .nationality("Uzbek")
                .name("Name")
                .gender("Female")
                .casts(List.of(castsDto))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();


        when(this.castsRepository.findByCastsByActorId(actor.getId()))
                .thenReturn(List.of(casts));
        when(this.actorRepository.findActorByIdAndDeletedAtIsNull(actor.getId()))
                .thenReturn(Optional.of(actor));
        when(this.actorMapper.toDtoWithCasts(actor))
                .thenReturn(dto);

        var actorGetWithCasts = this.actorServiceImpl.getActorWithCastsById(actor.getId());
        ApiResponse<ActorDto> actorDto = ApiResponse.<ActorDto>builder()
                .success(true)
                .message("OK")
                .data(dto)
                .build();

        Assertions.assertTrue(actorDto.isSuccess());
        Assertions.assertEquals(actorDto.getMessage(), "OK");
        Assertions.assertEquals(0, actorDto.getCode());
        Assertions.assertEquals(actorDto.getData().getCasts().get(0).getActorId(), actor.getId());

        verify(this.actorMapper, times(1)).toDtoWithCasts(actor);
        verify(this.castsRepository, times(1)).findByCastsByActorId(castsDto.getActorId());
        verify(this.actorRepository, times(1)).findActorByIdAndDeletedAtIsNull(actor.getId());
    }

    @Test
    void getActorWithCastsNegative() {
        Actor actor = Actor.builder().build();
        Casts casts = Casts.builder().build();
        when(this.castsRepository.findByCastsByActorId(actor.getId()))
                .thenReturn(List.of());
        when(this.actorRepository.findActorByIdAndDeletedAtIsNull(actor.getId()))
                .thenReturn(Optional.empty());

        var actorGetWithCasts = this.actorServiceImpl.getActorWithCastsById(actor.getId());
        ApiResponse<ActorDto> actorDto = ApiResponse.<ActorDto>builder()
                .code(-1)
                .message("ERROR")
                .build();

        Assertions.assertFalse(actorDto.isSuccess());
        Assertions.assertEquals(actorDto.getMessage(), "ERROR");
        Assertions.assertEquals(-1, actorDto.getCode());
        Assertions.assertNull(actorDto.getData());

        verify(this.actorRepository, times(1)).findActorByIdAndDeletedAtIsNull(actor.getId());
    }

    @Test
    void someActorFieldsPositive() {
        SomeActorFields actorFields = SomeActorFields.builder()
                .codes(12)
                .gender("Male")
                .name("name")
                .nationality("nation")
                .yearOfBirth(1990)
                .build();
        Actor actor = Actor.builder()
                .id(1L)
                .codes(12)
                .yearOfBirth(1990)
                .nationality("nation")
                .name("name")
                .gender("Male")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        when(this.actorMapper.toActor(List.of(actor)))
                .thenReturn(List.of(actorFields));

        var listActorSomeFields = this.actorServiceImpl.someActorFields();

        List<SomeActorFields> someFields = this.actorRepository.someActorFields();
        someFields.add(actorFields);
        Assertions.assertEquals(someFields.size(), 1);
        Assertions.assertEquals(someFields.get(0).getGender(), "Male");

        verify(this.actorRepository, times(1)).someActorFields();
    }

    @Test
    void someActorFieldsNegative() {
        SomeActorFields actorFields = SomeActorFields.builder().build();
        Actor actor = Actor.builder().build();
        when(this.actorMapper.toActor(List.of(actor)))
                .thenReturn(List.of(actorFields));

        var listActorSomeFields = this.actorServiceImpl.someActorFields();

        List<SomeActorFields> someFields = this.actorRepository.someActorFields();
        Assertions.assertEquals(someFields.size(), 0);
    }
}
