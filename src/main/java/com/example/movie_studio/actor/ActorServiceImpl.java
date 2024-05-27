package com.example.movie_studio.actor;

import com.example.movie_studio.casts.CastsRepository;
import com.example.movie_studio.dto.ApiResponse;
import com.example.movie_studio.filter.ActorAndCastsFilter;
import com.example.movie_studio.filter.ActorAndCastsSpecification;
import com.example.movie_studio.filter.ReadActorFromExcelFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import static java.awt.Color.red;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService<Long, ActorDto> {
    private final ActorMapper actorMapper;
    private final CastsRepository castsRepository;
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
        return this.actorRepository.findActorByIdAndDeletedAtIsNull(id)
                .map(actor -> ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(ApiResponse.<ActorDto>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.actorMapper.toDto(actor))
                                .build()))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<ActorDto>builder()
                                .code(-1)
                                .message(String.format("Actor is not found this id : %d", id))
                                .build()));

    }

    @Override
    public ResponseEntity<ApiResponse<ActorDto>> update(ActorDto dto, Long id) {

        return this.actorRepository.findActorByIdAndDeletedAtIsNull(id)
                .map(actor -> {
                    actor.setUpdatedAt(LocalDateTime.now());
                    this.actorMapper.update(actor, dto);
                    var updateActor = this.actorRepository.save(actor);
                    return ResponseEntity.status(HttpStatus.ACCEPTED)
                            .body(ApiResponse.<ActorDto>builder()
                                    .success(true)
                                    .message("Ok")
                                    .data(this.actorMapper.toDto(updateActor))
                                    .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<ActorDto>builder()
                                .code(-1)
                                .message(String.format("Actor is not found this id : %d", id))
                                .build()));
    }

    @Override
    public ResponseEntity<ApiResponse<ActorDto>> delete(Long id) {
        return this.actorRepository.findActorByIdAndDeletedAtIsNull(id)
                .map(actor -> {
                    actor.setDeletedAt(LocalDateTime.now());
                    this.actorRepository.delete(actor);
                    return ResponseEntity.status(HttpStatus.ACCEPTED)
                            .body(ApiResponse.<ActorDto>builder()
                                    .success(true)
                                    .message("Ok")
                                    .data(this.actorMapper.toDto(actor))
                                    .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(ApiResponse.<ActorDto>builder()
                                .code(-1)
                                .message(String.format("Actor is not found this id : %d", id))
                                .build()));
    }

    @Override
    public Integer deleteActorByQueryByCode(Integer code) {
        return this.actorRepository.deleteActorByQueryByCode(code)
                .intValue();
    }

    @Override
    public ResponseEntity<ApiResponse<List<ActorDto>>> getAll() {
        List<Actor> allActors = this.actorRepository.findAllActors();
        if (allActors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<List<ActorDto>>builder()
                            .code(-1)
                            .message("Actor table is empty")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.<List<ActorDto>>builder()
                        .success(true)
                        .message("Ok")
                        .data(allActors.stream().map(this.actorMapper::toDto).toList())
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deleteAll() {
        this.actorRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.<String>builder()
                        .success(true)
                        .message("Ok")
                        .build());
    }

    public ResponseEntity<ApiResponse<ActorDto>> getActorWithCastsById(Long id) {
        return this.actorRepository.findActorByIdAndDeletedAtIsNull(id)
                .map(actor -> {
                    var casts = this.castsRepository.findByCastsByActorId(id);
                    actor.setCasts(casts);
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(ApiResponse.<ActorDto>builder()
                                    .success(true)
                                    .message("Ok")
                                    .data(this.actorMapper.toDtoWithCasts(actor))
                                    .build());
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<ActorDto>builder()
                                .code(-1)
                                .message("Actor cannot be found")
                                .build()));
    }

    @Override
    public ResponseEntity<List<SomeActorFields>> someActorFields() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.actorMapper.toActor(this.actorRepository.findAllActors()));
    }

    @Override
    public ResponseEntity<ApiResponse<List<ActorDto>>> getManyActorsById(Set<Long> id) {
        List<Actor> manyActorsById = this.actorRepository.getManyActorsById(id);
        if (manyActorsById.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<List<ActorDto>>builder().build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<List<ActorDto>>builder()
                .success(true)
                .message("OK")
                .data(manyActorsById.stream().map(this.actorMapper::toDto).toList())
                .build());
    }

    @Override
    public ResponseEntity<List<SomeActorFields>> someActorFieldsByQuery() {
        return ResponseEntity.status(HttpStatus.OK).body(this.actorRepository.someActorFields());
    }

    @Override
    public ResponseEntity<ApiResponse<List<ActorDto>>> actorFilters(Long id, String name, Integer codes,
                                                                    String gender, String nationality, Integer yearOfBirth, HttpServletResponse response) {
        List<Actor> allFilterActors = this.actorRepository.getAllFilterActors(id, name, codes, gender, nationality, yearOfBirth);
        if (allFilterActors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<List<ActorDto>>builder()
                    .code(-1)
                    .message("Actor filters cannot be found")
                    .build());
        }

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<List<ActorDto>>builder()
                .success(true)
                .message("Ok")
                .data(allFilterActors.stream().map(this.actorMapper::toDto).toList())
                .build());
    }

    @Override
    public void exportToExcel(HttpServletResponse response) {
        List<Actor> allActors = this.actorRepository.findAllActors();

        XSSFWorkbook workbook = new XSSFWorkbook();
        ExcelExportUtil excelExporter = new ExcelExportUtil(workbook);
        excelExporter.writeHeaderLine("Actor", List.of("name", "gender", "codes", "nationality", "yearOfBirth"));

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        int rowCount = 0;
        for (Actor allActor : allActors) {
            Row row = excelExporter.getSheet().createRow(rowCount++ + 2);
            int columnCount = 0;

            excelExporter.createCell(row, columnCount++, allActor.getName(), style);
            excelExporter.createCell(row, columnCount++, allActor.getGender(), style);
            excelExporter.createCell(row, columnCount++, allActor.getCodes(), style);
            excelExporter.createCell(row, columnCount++, allActor.getNationality(), style);
            excelExporter.createCell(row, columnCount++, allActor.getYearOfBirth(), style);
        }
        excelExporter.export(response, "actors");
    }

    @Override
    public ResponseEntity<ApiResponse<List<ActorFilter>>> AdvoncadSearch(Long id, String name, Integer codes,
                                                                         String gender, String nationality,
                                                                         Integer yearOfBirth) {
        List<ActorFilter> list = this.actorRepository.advoncadSearch(id, name, codes, gender, nationality, yearOfBirth)
                .stream().map(t ->
                        new ActorFilter(
                                t.get(0, Long.class),
                                t.get(1, String.class),
                                t.get(2, Integer.class),
                                t.get(3, String.class),
                                t.get(4, String.class),
                                t.get(5, Integer.class)
                        )).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<List<ActorFilter>>builder()
                .success(true)
                .message("OK")
                .data(list)
                .build());
    }

    public List<ActorDto> filterActor(/*Long id,*/ String name, Integer codes,
                                                   String gender, String nationality,
                                                   Integer yearOfBirth, Set<Long> ids, HttpServletResponse response) {
        Specification<Actor> actorSpecification = new ActorSpecification(/*id,*/ name, codes, gender, nationality, yearOfBirth, ids);
        List<Actor> all = this.actorRepository.findAll(actorSpecification);


        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Actors Info");
            HSSFRow row = sheet.createRow(0);

            // row.createCell(0).setCellValue("ID");
            row.createCell(0).setCellValue("NAME");
            row.createCell(1).setCellValue("CODES");
            row.createCell(2).setCellValue("GENDER");
            row.createCell(3).setCellValue("NATIONALITY");
            row.createCell(4).setCellValue("YEAR OF BIRTH");
            //row.createCell(5).setCellValue("CREATED AT");

            int dataRowIndex = 2;
            List<ActorDto> actorDtoList = all.stream().map(this.actorMapper::toDto).toList();
            for (ActorDto actorDto : actorDtoList) {
                HSSFRow rowIndex = sheet.createRow(dataRowIndex);
                // rowIndex.createCell(0).setCellValue(actorDto.getId());
                rowIndex.createCell(0).setCellValue(actorDto.getName());
                rowIndex.createCell(1).setCellValue(actorDto.getCodes());
                rowIndex.createCell(2).setCellValue(actorDto.getGender());
                rowIndex.createCell(3).setCellValue(actorDto.getNationality());
                rowIndex.createCell(4).setCellValue(actorDto.getYearOfBirth());
                //rowIndex.createCell(5).setCellValue(actorDto.getCreatedAt());
                dataRowIndex++;
            }
            ServletOutputStream outputStream = response.getOutputStream();


            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            return all.stream().map(this.actorMapper::toDto).toList();
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    public void generateExcel(HttpServletResponse response) {
        List<Actor> actorList = this.actorRepository.findAll();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Actors Info");
            HSSFRow row = sheet.createRow(0);

            row.createCell(0).setCellValue("ID");
            sheet.setColumnWidth(0, 4000);

            row.createCell(1).setCellValue("NAME");
            sheet.setColumnWidth(1, 4000);


            row.createCell(2).setCellValue("CODES");
            sheet.setColumnWidth(2, 4000);

            row.createCell(3).setCellValue("GENDER");
            sheet.setColumnWidth(3, 4000);

            row.createCell(4).setCellValue("NATIONALITY");
            sheet.setColumnWidth(4, 4000);

            row.createCell(5).setCellValue("YEAR_OF_BIRTH");
            sheet.setColumnWidth(5, 4000);

            row.createCell(6).setCellValue("CREATED_AT");
            sheet.setColumnWidth(6, 4000);

            int dataRowIndex = 2;
            List<ActorDto> actorDtoList = actorList.stream().map(this.actorMapper::toDto).toList();
            for (ActorDto actorDto : actorDtoList) {
                HSSFRow rowIndex = sheet.createRow(dataRowIndex);


                rowIndex.createCell(0).setCellValue(actorDto.getId());
                rowIndex.createCell(1).setCellValue(actorDto.getName());
                rowIndex.createCell(2).setCellValue(actorDto.getCodes());
                rowIndex.createCell(3).setCellValue(actorDto.getGender());
                rowIndex.createCell(4).setCellValue(actorDto.getNationality());
                rowIndex.createCell(5).setCellValue(actorDto.getYearOfBirth());
                rowIndex.createCell(6).setCellValue(actorDto.getCreatedAt());
                dataRowIndex++;
            }
            ServletOutputStream outputStream = response.getOutputStream();

            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception ignored) {
        }
    }

    public List<ActorDto> filterActorAndCasts(Long id, String name, Integer codes,
                                              String gender, String nationality, Integer yearOfBirth) {

        Specification<Actor> actorAndCastsSpecification = new ActorAndCastsSpecification(id, name, codes, gender,
                nationality, yearOfBirth);
        List<Actor> actors = this.actorRepository.findAll(actorAndCastsSpecification);
        return actors.stream().map(this.actorMapper::toDto).toList();
    }

    public List<ActorAndCastsFilter> actorAndCastsFilters(Long id, String name, Integer codes,
                                                          String gender, String nationality, Integer yearOfBirth,
                                                          Long actorId, String rolType, LocalDateTime actorCreatedAt, LocalDateTime castsCreatedAt) {

        return this.actorRepository.actorAndCastsFilter(id, name, codes, gender, nationality, yearOfBirth, actorId, rolType, actorCreatedAt, castsCreatedAt)
                .stream().map(t -> new ActorAndCastsFilter(
                        t.get(0, Long.class),
                        t.get(1, String.class),
                        t.get(2, Integer.class),
                        t.get(3, String.class),
                        t.get(4, String.class),
                        t.get(5, Integer.class),
                        t.get(6, Long.class),
                        t.get(7, String.class),
                        t.get(8, LocalDateTime.class),
                        t.get(9, LocalDateTime.class)
                )).toList();
    }

    public List<ReadActorFromExcelFile> readActorFromExcelFile(MultipartFile file) {
        try (//FileInputStream inputStream = new FileInputStream(String.valueOf("C:\\Online\\Movie_Studio\\actors (15).xlsx"));
             Workbook workbook = new HSSFWorkbook(file.getInputStream())) {
            List<ReadActorFromExcelFile> red = new ArrayList<>();

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                ReadActorFromExcelFile readActorFromExcelFile = new ReadActorFromExcelFile();

                double id = row.getCell(0).getNumericCellValue();
                readActorFromExcelFile.setID((int) id);

                String name = row.getCell(1).getStringCellValue();
                readActorFromExcelFile.setNAME(name);

                double codes = row.getCell(2).getNumericCellValue();
                readActorFromExcelFile.setCODES((int) codes);

                String gender = row.getCell(3).getStringCellValue();
                readActorFromExcelFile.setGENDER(gender);

                String nationality = row.getCell(4).getStringCellValue();
                readActorFromExcelFile.setNATIONALITY(nationality);

                double numericCellValue = row.getCell(5).getNumericCellValue();
                readActorFromExcelFile.setYEAR_OF_BIRTH((int) numericCellValue);

                String created_at = String.valueOf(row.getCell(6).getNumericCellValue());
                readActorFromExcelFile.setCREATED_AT(created_at);

                red.add(readActorFromExcelFile);
            }
            return red;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ReadActorFromExcelFile> redActorFromCSVFile(MultipartFile file) {
        try (CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader()
                .parse(new InputStreamReader(file.getInputStream()))) {
            List<ReadActorFromExcelFile> red = new ArrayList<>();
            ReadActorFromExcelFile actor;
            for (CSVRecord csvRecord : csvParser) {
                String createdAt = csvRecord.get("CREATED_AT");
                actor = ReadActorFromExcelFile.builder()
                        //.ID(Integer.parseInt(csvRecord.get("ID")))
                        .NAME(csvRecord.get("NAME"))
                        .CODES(Integer.parseInt(csvRecord.get("CODES")))
                        .GENDER(csvRecord.get("GENDER"))
                        .NATIONALITY(csvRecord.get("NATIONALITY"))
                        .YEAR_OF_BIRTH(Integer.parseInt(csvRecord.get("YEAR_OF_BIRTH")))
                        .CREATED_AT(csvRecord.get(createdAt))
                        .build();
                if (csvRecord.get("ID") != null) {
                    actor.setID(Integer.parseInt(csvRecord.get("ID")));
                } else actor.setID(0);
                red.add(actor);
            }
            return red;
        } catch (Exception e) {
            return null;
        }
    }

    public List<ReadActorFromExcelFile> uploadCSVFile(MultipartFile file) {
        List<ReadActorFromExcelFile> actor = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            CsvParserSettings p = new CsvParserSettings();
            p.setHeaderExtractionEnabled(true);
            CsvParser csvParser = new CsvParser(p);
            List<Record> records = csvParser.parseAllRecords(inputStream);
            records.forEach(r -> {
                ReadActorFromExcelFile red = new ReadActorFromExcelFile();
                if (r.getString("ID") != null)
                    red.setID(Integer.parseInt(r.getString("ID")));
                else red.setID(0);
                if (r.getString("NAME") != null)
                    red.setNAME(r.getString("NAME"));
                if (r.getString("CODES") != null)
                    red.setCODES(Integer.parseInt(r.getString("CODES")));
                if (r.getString("GENDER") != null)
                    red.setGENDER(r.getString("GENDER"));
                if (r.getString("NATIONALITY") != null)
                    red.setNATIONALITY(r.getString("NATIONALITY"));
                if (r.getString("YEAR_OF_BIRTH") != null)
                    red.setYEAR_OF_BIRTH(Integer.parseInt(r.getString("YEAR_OF_BIRTH")));
                if (r.getString("CREATED_AT") != null)
                    red.setCREATED_AT(r.getString("CREATED_AT"));
                actor.add(red);
            });
            return actor;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
