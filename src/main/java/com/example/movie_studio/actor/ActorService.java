package com.example.movie_studio.actor;

import com.example.movie_studio.dto.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface ActorService<K, V> {
    void exportToExcel(HttpServletResponse response);

    ResponseEntity<ApiResponse<V>> create(V dto);

    ResponseEntity<ApiResponse<V>> get(K id);

    ResponseEntity<ApiResponse<V>> update(V dto, K id);

    ResponseEntity<ApiResponse<V>> delete(K id);

    ResponseEntity<ApiResponse<String>> deleteAll();

    Integer deleteActorByQueryByCode(Integer code);

    ResponseEntity<ApiResponse<List<V>>> getAll();

    ResponseEntity<ApiResponse<List<V>>> getManyActorsById(Set<K> id);

    ResponseEntity<List<SomeActorFields>> someActorFields();

    ResponseEntity<List<SomeActorFields>> someActorFieldsByQuery();

    ResponseEntity<ApiResponse<List<V>>> actorFilters(Long id, String name, Integer codes,
                                                      String gender, String nationality, Integer yearOfBirth);
}
