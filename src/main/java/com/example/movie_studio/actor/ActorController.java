package com.example.movie_studio.actor;

import com.example.movie_studio.dto.ApiResponse;
import com.example.movie_studio.filter.ActorAndCastsFilter;
import com.example.movie_studio.filter.ReadActorFromExcelFile;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/actor/")
@RequiredArgsConstructor
public class ActorController implements ActorService<Long, ActorDto> {
    private final ActorServiceImpl actorServiceImpl;

    @Override
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ActorDto>> create(@Valid @RequestBody ActorDto dto) {
        return this.actorServiceImpl.create(dto);
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<ActorDto>> get(@PathVariable("id") Long id) {
        return this.actorServiceImpl.get(id);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ActorDto>> update(@Valid @RequestBody ActorDto dto, @PathVariable("id") Long id) {
        return this.actorServiceImpl.update(dto, id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<ActorDto>> delete(@PathVariable("id") Long id) {
        return this.actorServiceImpl.delete(id);
    }

    @Override
    @DeleteMapping("/deleteByCode/{code}")
    public Integer deleteActorByQueryByCode(@PathVariable("code") Integer code) {
        return this.actorServiceImpl.deleteActorByQueryByCode(code);
    }

    @Override
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ActorDto>>> getAll() {
        return this.actorServiceImpl.getAll();
    }

    @Override
    @GetMapping("/deleteAll")
    public ResponseEntity<ApiResponse<String>> deleteAll() {
        return this.actorServiceImpl.deleteAll();
    }

    @Override
    @GetMapping("/someActorFields")
    public ResponseEntity<List<SomeActorFields>> someActorFields() {
        return this.actorServiceImpl.someActorFields();
    }

    @Override
    @GetMapping("/someActorFieldsByQuery")
    public ResponseEntity<List<SomeActorFields>> someActorFieldsByQuery() {
        return this.actorServiceImpl.someActorFieldsByQuery();
    }

    @GetMapping("/getActorWithCastsById/{id}")
    public ResponseEntity<ApiResponse<ActorDto>> getActorWithCastsById(@PathVariable("id") Long id) {
        return this.actorServiceImpl.getActorWithCastsById(id);
    }

    @Override
    @GetMapping("/filters")
    public ResponseEntity<ApiResponse<List<ActorDto>>> actorFilters(@RequestParam(value = "id", required = false) Long id,
                                                                    @RequestParam(value = "name", required = false) String name,
                                                                    @RequestParam(value = "codes", required = false) Integer codes,
                                                                    @RequestParam(value = "gender", required = false) String gender,
                                                                    @RequestParam(value = "nationality", required = false) String nationality,
                                                                    @RequestParam(value = "year of birth", required = false) Integer yearOfBirth,
                                                                    HttpServletResponse response) {
        return this.actorServiceImpl.actorFilters(id, name, codes, gender, nationality, yearOfBirth, response);
    }

    @Override
    @GetMapping("/getManyActorsById")
    public ResponseEntity<ApiResponse<List<ActorDto>>> getManyActorsById(@RequestParam(required = false) Set<Long> id) {
        return this.actorServiceImpl.getManyActorsById(id);
    }

    @Override
    @GetMapping("/exportToExcel")
    public void exportToExcel(HttpServletResponse response) {
        this.actorServiceImpl.exportToExcel(response);

    }

    @Override
    @GetMapping("/advoncadSearch")
    public ResponseEntity<ApiResponse<List<ActorFilter>>> AdvoncadSearch(@RequestParam(value = "id", required = false) Long id,
                                                                         @RequestParam(value = "name", required = false) String name,
                                                                         @RequestParam(value = "codes", required = false) Integer codes,
                                                                         @RequestParam(value = "gender", required = false) String gender,
                                                                         @RequestParam(value = "nationality", required = false) String nationality,
                                                                         @RequestParam(value = "year of birth", required = false) Integer yearOfBirth) {
        return this.actorServiceImpl.AdvoncadSearch(id, name, codes, gender, nationality, yearOfBirth);
    }

    @GetMapping("/filterActor")
    public List<ActorDto> filterActor(//@RequestParam(value = "id", required = false) Long id,
                                      @RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "codes", required = false) Integer codes,
                                      @RequestParam(value = "gender", required = false) String gender,
                                      @RequestParam(value = "nationality", required = false) String nationality,
                                      @RequestParam(value = "year of birth", required = false) Integer yearOfBirth,
                                      //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd-MM-yyyy") LocalDate now,
                                      @RequestParam(value = "list of id", required = false) Set<Long> ids,
                                      HttpServletResponse response) {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=actors.xlsx";
        response.setHeader(headerKey, headerValue);

        return this.actorServiceImpl.filterActor(/*id,*/ name, codes, gender, nationality, yearOfBirth, ids, response);
    }

    @Override
    @GetMapping("/generateExcel")
    public void generateExcel(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=actors.xlsx";
        response.setHeader(headerKey, headerValue);
        this.actorServiceImpl.generateExcel(response);
    }

    @GetMapping("/actorAndCastsFilter")
    public List<ActorDto> filterActorAndCasts(@RequestParam(value = "id", required = false) Long id,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "codes", required = false) Integer codes,
                                              @RequestParam(value = "gender", required = false) String gender,
                                              @RequestParam(value = "nationality", required = false) String nationality,
                                              @RequestParam(value = "year of birth", required = false) Integer yearOfBirth) {

        return this.actorServiceImpl.filterActorAndCasts(id, name, codes, gender, nationality, yearOfBirth);
    }

    @GetMapping("/getActorAndCasts")
    public List<ActorAndCastsFilter> actorAndCastsFilters(@RequestParam(value = "id", required = false) Long id,
                                                          @RequestParam(value = "name", required = false) String name,
                                                          @RequestParam(value = "codes", required = false) Integer codes,
                                                          @RequestParam(value = "gender", required = false) String gender,
                                                          @RequestParam(value = "nationality", required = false) String nationality,
                                                          @RequestParam(value = "year of birth", required = false) Integer yearOfBirth,
                                                          @RequestParam(value = "actorId", required = false) Long actorId,
                                                          @RequestParam(value = "roleType", required = false) String rolType,
                                                          @RequestParam(value = "actor created at", required = false) LocalDateTime actorCreatedAt,
                                                          @RequestParam(value = "casts created at", required = false) LocalDateTime castsCreatedAt) {
        return this.actorServiceImpl.actorAndCastsFilters(id, name, codes, gender, nationality, yearOfBirth, actorId, rolType, actorCreatedAt, castsCreatedAt);
    }

    @PostMapping("/readFromExcelFile")
    public List<ReadActorFromExcelFile> readActorFromExcelFile(@RequestParam("file") MultipartFile file) {
        return this.actorServiceImpl.readActorFromExcelFile(file);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "Select the CSV file", dataType = "file", paramType = "form", required = true)
    })
    @PostMapping("/readCSVFile")
    public List<ReadActorFromExcelFile> redActorFromCSVFile(@RequestParam("file") MultipartFile file) {
        try {
            return this.actorServiceImpl.redActorFromCSVFile(file);
        } catch (Exception e) {
            throw new RuntimeException("error - > " + e.getMessage());
        }
    }

    @PostMapping("/uploadCsvFile")
    public List<ReadActorFromExcelFile> uploadCSVFile(@RequestParam("file") MultipartFile file) {
        return this.actorServiceImpl.uploadCSVFile(file);
    }
}
