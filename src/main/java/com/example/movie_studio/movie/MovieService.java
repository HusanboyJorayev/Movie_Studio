package com.example.movie_studio.movie;

import com.example.movie_studio.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MovieService<K, V> {
    ResponseEntity<ApiResponse<V>> create(V dto);

    ResponseEntity<ApiResponse<V>> get(K id);

    ResponseEntity<ApiResponse<V>> update(V dto, K id);

    ResponseEntity<ApiResponse<V>> delete(K id);

    ResponseEntity<ApiResponse<List<V>>> getAll();

    ResponseEntity<ApiResponse<Page<V>>> getPage(K page, K size);

    ResponseEntity<ApiResponse<List<V>>> getMovieSomeIds(List<K> list);
}
