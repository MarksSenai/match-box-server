package com.develop.machinecomm.Machinecomm.service;

import com.develop.machinecomm.Machinecomm.dto.FileStorageDTO;
import com.develop.machinecomm.Machinecomm.dto.UploadFileDTO;
import com.develop.machinecomm.Machinecomm.exception.FileStorageException;
import com.develop.machinecomm.Machinecomm.interfaces.ManageFiles;
import com.develop.machinecomm.Machinecomm.model.UserImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImportFileStorageService implements ManageFiles {

    private Path fileStorageLocation;
    private FileStorageDTO fileStorageDTO;
    private ConfigurationService configurationService;
    private ImportService importService;

    @Autowired
    public ImportFileStorageService(FileStorageDTO fileStorageDTO,
                                    ConfigurationService configurationService,
                                    ImportService importService) {
        this.configurationService = configurationService;
        this.fileStorageDTO = fileStorageDTO;
        this.fileStorageLocation = Paths.get(fileStorageDTO.getUploadDir())
                .toAbsolutePath().normalize();
        this.importService = importService;
    }

    @Override
    public UploadFileDTO fileStorage(MultipartFile file, String fileCode) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        UserImporter userImporter = new UserImporter();

        try {
            this.createDirectory(fileCode);
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            userImporter.setUsersFilePath(targetLocation.toString());
            importService.saveImportFile(userImporter);

            String path = this.configurationService.getConfig().getVolume();
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(path)
                    .path(fileName)
                    .toUriString();

            return new UploadFileDTO(fileName, fileDownloadUri,
                    file.getContentType(), file.getSize());
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    private void createDirectory(String fileCode) {
        try {
            this.fileStorageDTO.setUploadDir(this.configurationService.getConfig()
                    .getVolume()+File.separator+fileCode);
            this.fileStorageLocation = Paths.get(this.fileStorageDTO.getUploadDir())
                    .toAbsolutePath().normalize();
            Files.createDirectories(this.fileStorageLocation);

        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
}
