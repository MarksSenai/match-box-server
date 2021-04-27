package com.develop.machinecomm.Machinecomm.controller;


import com.develop.machinecomm.Machinecomm.dto.UploadFileDTO;
import com.develop.machinecomm.Machinecomm.service.FileStorageService;
import com.develop.machinecomm.Machinecomm.service.ImportFileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/portal")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private FileStorageService fileStorageService;
    private ImportFileStorageService importFileStorageService;

    @Autowired
    public FileController(FileStorageService fileStorageService,
                          ImportFileStorageService importFileStorageService){
        this.fileStorageService = fileStorageService;
        this.importFileStorageService = importFileStorageService;
    }


    @PostMapping("/upload/{rec}")
    public UploadFileDTO uploadImageFile(@PathVariable(value = "rec") String rec,
                                         @RequestParam("file") MultipartFile file) {
        return fileStorageService.fileStorage(file, rec);
    }

    @PostMapping("/uploadUsersFile/{fileCode}")
    public UploadFileDTO uploadUsersImportFile(@PathVariable(value = "fileCode") String fileCode,
                                               @RequestParam("file") MultipartFile file) {
        return importFileStorageService.fileStorage(file, fileCode);
    }

    @PostMapping("/uploadCompanyImage")
    public UploadFileDTO uploadCompanyImage(@RequestParam("file") MultipartFile file) {
        return fileStorageService.companyImageStorage(file);
    }

    @GetMapping("/downloadFile/{fileName:.+}/{rec}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(value = "fileName") String fileName,
                                                 @PathVariable(value = "rec") String rec,
                                                 HttpServletRequest request
                                                 ) {
        if (rec == null){
            rec = "";
        }
        Resource resource = fileStorageService.loadFileAsResource(fileName, rec);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        ResponseEntity response =  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);


        return response;
    }

    @GetMapping("/companyLogo/{companyLogo:.+}")
    public ResponseEntity<Resource> getCompanyLogo(@PathVariable(value = "companyLogo") String companyLogo,
                                                   HttpServletRequest request) {
        try {
            Resource resource = fileStorageService.getCompanyLogo();
            String contentType = null;
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            if(contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
            return (ResponseEntity<Resource>) ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
    }
}