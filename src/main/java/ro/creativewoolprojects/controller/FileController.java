package ro.creativewoolprojects.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.creativewoolprojects.entity.File;
import ro.creativewoolprojects.service.FileService;

import java.io.IOException;

@RestController
@RequestMapping("file")

public class FileController {

    private final FileService fileService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String createFile(@RequestBody File newFile) {

        return fileService.save(newFile).getId();
    }

    @PostMapping(
            value = "{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String uploadFile(@PathVariable String id,
                             @RequestParam("content") MultipartFile fileContent) throws IOException {

        return fileService.setData(id, fileContent.getBytes()).getId();
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public File getFile(@PathVariable String id) {
       return fileService.findById(id);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String id) {
        final File file = fileService.findById(id);

        return createDownloadResponse(file);
    }

    private ResponseEntity<ByteArrayResource> createDownloadResponse(File file) {
        return ResponseEntity
                .ok()
                .contentType( MediaType.APPLICATION_OCTET_STREAM)
                .header( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource (file.getData()));
    }

}
