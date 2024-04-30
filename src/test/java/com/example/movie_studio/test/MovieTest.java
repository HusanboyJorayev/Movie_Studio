package com.example.movie_studio.test;

import com.example.movie_studio.movie.MovieMapper;
import com.example.movie_studio.movie.MovieRepository;
import com.example.movie_studio.movie.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MovieTest {

    @Mock
    private MovieMapper mapper;
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieServiceImpl movieServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
