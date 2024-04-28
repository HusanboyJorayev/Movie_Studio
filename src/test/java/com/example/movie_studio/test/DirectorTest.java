package com.example.movie_studio.test;

import com.example.movie_studio.director.*;
import com.example.movie_studio.dto.ApiResponse;
import com.example.movie_studio.movie.Movie;
import com.example.movie_studio.movie.MovieDto;
import com.example.movie_studio.movie.MovieRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class DirectorTest {
    @Mock
    private DirectorMapper directorMapper;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private DirectorRepository directorRepository;

    @InjectMocks
    private DirectorServiceImpl directorServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        Director director = Director.builder()
                .id(1)
                .placeBirth("place")
                .yearBirth(2000)
                .name("name")
                .gender("Male")
                .country("country")
                .createdAt(LocalDateTime.now())
                .build();

        DirectorDto dto = DirectorDto.builder()
                .id(1)
                .placeBirth("place")
                .yearBirth(2000)
                .name("name")
                .gender("Male")
                .country("country")
                .createdAt(LocalDateTime.now())
                .build();

        when(this.directorMapper.toEntity(dto))
                .thenReturn(director);
        when(this.directorMapper.toDto(director))
                .thenReturn(dto);

        var create = this.directorServiceImpl.create(dto);
        ApiResponse<DirectorDto> directorDto = ApiResponse.<DirectorDto>builder()
                .success(true)
                .message("Ok")
                .data(dto)
                .build();

        Assertions.assertEquals(directorDto.getData(), dto);
        Assertions.assertTrue(directorDto.isSuccess());
        Assertions.assertEquals(directorDto.getCode(), 0);

        verify(this.directorMapper, times(1)).toEntity(dto);
        verify(this.directorRepository, times(1)).save(director);
    }

    @Test
    void getPositive() {
        Director director = Director.builder()
                .id(1)
                .placeBirth("place")
                .yearBirth(2000)
                .name("name")
                .gender("Male")
                .country("country")
                .createdAt(LocalDateTime.now())
                .build();

        DirectorDto dto = DirectorDto.builder()
                .id(1)
                .placeBirth("place")
                .yearBirth(2000)
                .name("name")
                .gender("Male")
                .country("country")
                .createdAt(LocalDateTime.now())
                .build();

        when(this.directorMapper.toDto(director))
                .thenReturn(dto);
        when(this.directorRepository.getDirectorById(director.getId()))
                .thenReturn(Optional.of(director));

        var get = this.directorServiceImpl.get(director.getId());
        ApiResponse<DirectorDto> directorDto = ApiResponse.<DirectorDto>builder()
                .success(true)
                .message("Ok")
                .data(dto)
                .build();

        Assertions.assertEquals(directorDto.getData(), dto);
        Assertions.assertTrue(directorDto.isSuccess());
        Assertions.assertEquals(directorDto.getCode(), 0);

        verify(this.directorMapper, times(1)).toDto(director);
        verify(this.directorRepository, times(1)).getDirectorById(director.getId());
    }

    @Test
    void getNegative() {
        Director director = Director.builder()
                .build();
        when(this.directorRepository.getDirectorById(director.getId()))
                .thenReturn(Optional.of(director));

        var get = this.directorServiceImpl.get(director.getId());
        ApiResponse<DirectorDto> directorDto = ApiResponse.<DirectorDto>builder()
                .code(-1)
                .message("Director is not found")
                .build();

        Assertions.assertNull(directorDto.getData());
        Assertions.assertFalse(directorDto.isSuccess());
        Assertions.assertEquals(directorDto.getCode(), -1);

        verify(this.directorRepository, times(1)).getDirectorById(director.getId());
    }

    @Test
    void updatePositive() {
        Director director = Director.builder()
                .id(1)
                .placeBirth("place")
                .yearBirth(2000)
                .name("name")
                .gender("Male")
                .country("country")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        DirectorDto dto = DirectorDto.builder()
                .id(1)
                .placeBirth("place")
                .yearBirth(2000)
                .name("name")
                .gender("Male")
                .country("country")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(this.directorMapper.toDto(director))
                .thenReturn(dto);
        when(this.directorRepository.getDirectorById(director.getId()))
                .thenReturn(Optional.of(director));
        when(this.directorRepository.save(director))
                .thenReturn(director);

        var update = this.directorServiceImpl.update(dto, director.getId());
        ApiResponse<DirectorDto> directorDto = ApiResponse.<DirectorDto>builder()
                .success(true)
                .message("Ok")
                .data(dto)
                .build();

        Assertions.assertEquals(directorDto.getData(), dto);
        Assertions.assertTrue(directorDto.isSuccess());
        Assertions.assertEquals(directorDto.getCode(), 0);

        verify(this.directorRepository, times(1)).save(director);
        verify(this.directorMapper, times(1)).toDto(director);
        verify(this.directorRepository, times(1)).getDirectorById(director.getId());
    }

    @Test
    void updateNegative() {
        Director director = Director.builder()
                .build();

        DirectorDto dto = DirectorDto.builder()
                .build();
        when(this.directorRepository.getDirectorById(director.getId()))
                .thenReturn(Optional.of(director));

        var update = this.directorServiceImpl.update(dto, director.getId());
        ApiResponse<DirectorDto> directorDto = ApiResponse.<DirectorDto>builder()
                .code(-1)
                .message("Director is not found")
                .build();

        Assertions.assertEquals(directorDto.getCode(), -1);
        Assertions.assertNull(directorDto.getData());
        Assertions.assertFalse(directorDto.isSuccess());

        verify(this.directorRepository, times(1)).getDirectorById(director.getId());
    }

    @Test
    void deletePositive() {
        Director director = Director.builder()
                .id(1)
                .placeBirth("place")
                .yearBirth(2000)
                .name("name")
                .gender("Male")
                .country("country")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        DirectorDto dto = DirectorDto.builder()
                .id(1)
                .placeBirth("place")
                .yearBirth(2000)
                .name("name")
                .gender("Male")
                .country("country")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        when(this.directorMapper.toDto(director))
                .thenReturn(dto);
        when(this.directorRepository.getDirectorById(director.getId()))
                .thenReturn(Optional.of(director));

        var delete = this.directorServiceImpl.delete(director.getId());
        ApiResponse<DirectorDto> directorDto = ApiResponse.<DirectorDto>builder()
                .success(true)
                .message("Ok")
                .data(dto)
                .build();

        Assertions.assertEquals(directorDto.getData(), dto);
        Assertions.assertTrue(directorDto.isSuccess());
        Assertions.assertEquals(directorDto.getCode(), 0);

        verify(this.directorRepository, times(1)).delete(director);
        verify(this.directorMapper, times(1)).toDto(director);
        verify(this.directorRepository, times(1)).getDirectorById(director.getId());

    }

    @Test
    void deleteNegative() {
        Director director = Director.builder()
                .build();

        DirectorDto dto = DirectorDto.builder()
                .build();
        when(this.directorRepository.getDirectorById(director.getId()))
                .thenReturn(Optional.of(director));

        var delete = this.directorServiceImpl.delete(director.getId());
        ApiResponse<DirectorDto> directorDto = ApiResponse.<DirectorDto>builder()
                .code(-1)
                .message("Director is not found")
                .build();

        Assertions.assertEquals(directorDto.getCode(), -1);
        Assertions.assertNull(directorDto.getData());
        Assertions.assertFalse(directorDto.isSuccess());

        verify(this.directorRepository, times(1)).getDirectorById(director.getId());
        verify(this.directorRepository, times(1)).delete(director);
    }

    @Test
    void getAllPositive() {
        Director director = Director.builder()
                .id(1)
                .placeBirth("place")
                .yearBirth(2000)
                .name("name")
                .gender("Male")
                .country("country")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        DirectorDto dto = DirectorDto.builder()
                .id(1)
                .placeBirth("place")
                .yearBirth(2000)
                .name("name")
                .gender("Male")
                .country("country")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        when(this.directorMapper.toDto(director))
                .thenReturn(dto);
        when(this.directorRepository.getAllDirectors())
                .thenReturn(List.of(director));

        var delete = this.directorServiceImpl.getAll();
        ApiResponse<List<DirectorDto>> directorDto = ApiResponse.<List<DirectorDto>>builder()
                .success(true)
                .message("Ok")
                .data(List.of(dto))
                .build();

        Assertions.assertEquals(directorDto.getData().size(), 1);
        Assertions.assertTrue(directorDto.isSuccess());
        Assertions.assertEquals(directorDto.getCode(), 0);

        verify(this.directorMapper, times(1)).toDto(director);
        verify(this.directorRepository, times(1)).getAllDirectors();
    }

    @Test
    void getAllNegative() {
        Director director = Director.builder()
                .build();

        when(this.directorRepository.getAllDirectors())
                .thenReturn(List.of(director));

        var delete = this.directorServiceImpl.getAll();
        ApiResponse<List<DirectorDto>> directorDto = ApiResponse.<List<DirectorDto>>builder()
                .code(-1)
                .message("Directors are not found")
                .build();

        Assertions.assertFalse(directorDto.isSuccess());
        Assertions.assertEquals(directorDto.getCode(), -1);
        Assertions.assertNull(directorDto.getData());

        verify(this.directorRepository, times(1)).getAllDirectors();
    }

    @Test
    void getBySomeIdsPositive() {
        Director director = Director.builder()
                .id(1)
                .placeBirth("place")
                .yearBirth(2000)
                .name("name")
                .gender("Male")
                .country("country")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        DirectorDto dto = DirectorDto.builder()
                .id(1)
                .placeBirth("place")
                .yearBirth(2000)
                .name("name")
                .gender("Male")
                .country("country")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();
        when(this.directorRepository.getDirectorsBySomeIds(List.of(1)))
                .thenReturn(List.of(director));

        var getSomeIds = this.directorRepository.getDirectorsBySomeIds(List.of(1));
        ApiResponse<List<DirectorDto>> listDirectors = ApiResponse.<List<DirectorDto>>builder()
                .success(true)
                .message("Ok")
                .data(List.of(dto))
                .build();

        Assertions.assertNotNull(listDirectors.getData());
        Assertions.assertTrue(listDirectors.isSuccess());
        Assertions.assertEquals(listDirectors.getCode(), 0);

        verify(this.directorRepository, times(1)).getDirectorsBySomeIds(List.of(1));
    }

    @Test
    void getBySomeIdsNegative() {
        when(this.directorRepository.getDirectorsBySomeIds(List.of()))
                .thenReturn(List.of());

        var getSomeIds = this.directorRepository.getDirectorsBySomeIds(List.of());
        ApiResponse<List<DirectorDto>> listDirectors = ApiResponse.<List<DirectorDto>>builder()
                .code(-1)
                .message("Directors are not found")
                .build();
        Assertions.assertEquals(listDirectors.getCode(), -1);
        Assertions.assertFalse(listDirectors.isSuccess());
        Assertions.assertNull(listDirectors.getData());

        verify(this.directorRepository, times(1)).getDirectorsBySomeIds(List.of());
    }

    @Test
    void getWithMoviePositive() {

        Movie movie = Movie.builder()
                .id(1)
                .directorId(1)
                .yearOfRelease(1234)
                .name("name")
                .language("uzbek")
                .filmingLocation("uzbekistan")
                .studioId(1)
                .countryOfRelease("release")
                .category("category")
                .build();

        MovieDto movieDto = MovieDto.builder()
                .id(1)
                .directorId(1)
                .yearOfRelease(1234)
                .name("name")
                .language("uzbek")
                .filmingLocation("uzbekistan")
                .studioId(1)
                .countryOfRelease("release")
                .category("category")
                .build();

        Director director = Director.builder()
                .id(1)
                .movies(List.of(movie))
                .placeBirth("place")
                .yearBirth(2000)
                .name("name")
                .gender("Male")
                .country("country")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        DirectorDto dto = DirectorDto.builder()
                .id(1)
                .movies(List.of(movieDto))
                .placeBirth("place")
                .yearBirth(2000)
                .name("name")
                .gender("Male")
                .country("country")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        when(this.directorRepository.getDirectorById(director.getId()))
                .thenReturn(Optional.of(director));
        when(this.movieRepository.findAllMoviesByDirectorId(director.getId()))
                .thenReturn(List.of(movie));
        when(this.directorMapper.toDtoWithMovie(director))
                .thenReturn(dto);

        var directorWithMovie = this.directorServiceImpl.getWithMovie(director.getId());
        ApiResponse<DirectorDto> directorDto = ApiResponse.<DirectorDto>builder()
                .success(true)
                .message("Ok")
                .data(dto)
                .build();

        Assertions.assertEquals(directorDto.getCode(), 0);
        Assertions.assertTrue(directorDto.isSuccess());
        Assertions.assertNotNull(directorDto.getData());

        verify(this.directorRepository, times(1)).getDirectorById(director.getId());
        verify(this.movieRepository, times(1)).findAllMoviesByDirectorId(director.getId());
        verify(this.directorMapper, times(1)).toDtoWithMovie(director);
    }

    @Test
    void getWithMovieNegative() {

        Director director = Director.builder()
                .build();

        DirectorDto dto = DirectorDto.builder()
                .build();

        when(this.directorRepository.getDirectorById(director.getId()))
                .thenReturn(Optional.empty());
        when(this.movieRepository.findAllMoviesByDirectorId(director.getId()))
                .thenReturn(List.of());
        when(this.directorMapper.toDtoWithMovie(director))
                .thenReturn(dto);

        var directorWithMovie = this.directorServiceImpl.getWithMovie(director.getId());
        ApiResponse<DirectorDto> directorDto = ApiResponse.<DirectorDto>builder()
                .code(-1)
                .message("Director is not found")
                .build();

        Assertions.assertFalse(directorDto.isSuccess());
        Assertions.assertNull(directorDto.getData());
        Assertions.assertEquals(directorDto.getCode(), -1);

        verify(this.directorRepository, times(1)).getDirectorById(director.getId());
    }

   /* @Test
    void getPagesPositive() {

        Page<DirectorDto> dtoPage = isNotNull();

        int page = 0, size = 10;
        Page<Director> getPage = this.directorRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size,
                Sort.by(Sort.Direction.ASC, "id")));
        when(this.directorRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size,
                Sort.by(Sort.Direction.ASC, "id"))))
                .thenReturn(getPage);
        ResponseEntity<ApiResponse<Page<DirectorDto>>> pages = this.directorServiceImpl.getPages(0, 1);
        ApiResponse<Page<DirectorDto>> pageDto = ApiResponse.<Page<DirectorDto>>builder()
                .success(true)
                .message("OK")
                .data(dtoPage)
                .build();

        Assertions.assertEquals(pageDto.getCode(), 0);
        Assertions.assertTrue(pageDto.isSuccess());
        Assertions.assertNotNull(pageDto.getData());

        verify(this.directorRepository, times(1))
                .findAllByDeletedAtIsNull(PageRequest.of(page, size));
    }*/
}
