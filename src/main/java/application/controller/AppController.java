package application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Tag(name = "File service", description = "service to upload and download files")
@RestController
@RequestMapping("/file")
public class AppController {
    private String fileDirectory = "D:\\Java\\HW34+(spring)\\HW_Spring\\files";

    @Operation(summary = "download file")
    @GetMapping("download/{file:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("file") String filename) throws MalformedURLException, FileNotFoundException {
        Path file = Paths.get(fileDirectory).resolve(filename);
        Resource resource = new UrlResource(file.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw new FileNotFoundException("file not found");
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    @Operation(summary = "upload file")
    @PostMapping("/upload")
    public ResponseEntity<HttpStatus> uploadFile(@RequestParam("file") @Parameter(description = "file name")  MultipartFile multipartFile) throws IOException {
        Files.copy(multipartFile.getInputStream(), Paths.get(fileDirectory).resolve(multipartFile.getOriginalFilename()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleIOException(IOException e) {
        return new ResponseEntity<>("IOException", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<String> handleMalformedURLException(MalformedURLException e) {
        return new ResponseEntity<>("MalformedURLException", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<String> handleFileNotFoundException(FileNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
