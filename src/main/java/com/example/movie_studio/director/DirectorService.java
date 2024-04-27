package com.example.movie_studio.director;

import com.example.movie_studio.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DirectorService<K, V> {

    ResponseEntity<ApiResponse<V>> create(V dto);

    ResponseEntity<ApiResponse<V>> get(K id);
    ResponseEntity<ApiResponse<V>> getWithMovie(K id);

    ResponseEntity<ApiResponse<V>> update(V dto, K id);

    ResponseEntity<ApiResponse<V>> delete(K id);

    ResponseEntity<ApiResponse<List<V>>> getAll();

    ResponseEntity<ApiResponse<Page<DirectorDto>>>getPages(Integer page, Integer size);

    ResponseEntity<ApiResponse<List<V>>> getBySomeIds(List<Integer> list);
}
