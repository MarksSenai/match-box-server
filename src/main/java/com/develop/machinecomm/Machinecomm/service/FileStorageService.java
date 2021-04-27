package com.develop.machinecomm.Machinecomm.service;


import com.develop.machinecomm.Machinecomm.dto.FileStorageDTO;
import com.develop.machinecomm.Machinecomm.dto.UploadFileDTO;
import com.develop.machinecomm.Machinecomm.exception.FileStorageException;
import com.develop.machinecomm.Machinecomm.exception.MyFileNotFoundException;
import com.develop.machinecomm.Machinecomm.interfaces.ManageFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService implements ManageFiles {

    private Path fileStorageLocation;
    private FileStorageDTO fileStorageDTO;
    private UserService userService;
    private ConfigurationService configurationService;
    @Autowired
    public FileStorageService(FileStorageDTO fileStorageDTO,
                              UserService userService,
                              ConfigurationService configurationService) {
        this.userService = userService;
        this.configurationService = configurationService;
        this.fileStorageDTO = fileStorageDTO;
        this.fileStorageLocation = Paths.get(fileStorageDTO.getUploadDir())
                .toAbsolutePath().normalize();
    }

    @Override
    public UploadFileDTO fileStorage(MultipartFile file, String fileCode) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            createDirectory(fileCode);
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            this.userService.updateUserImage(fileCode, fileName);

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

    public UploadFileDTO companyImageStorage(MultipartFile file) {
        String company = "companyFiles";
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            createDirectory(company);
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            this.configurationService.updateCompanyImage(fileName);

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

    public Resource loadFileAsResource(String fileName, String rec) {
        Resource resource = null;
        Path folderStorageLocation;
        Path filePath;
        this.fileStorageDTO.setUploadDir(this.configurationService.getConfig().getVolume());

        this.fileStorageLocation = Paths.get(this.fileStorageDTO.getUploadDir()).toAbsolutePath().normalize();
        try {
            if(fileName.equals("excelfilereport.xlsx")) {
                folderStorageLocation = Paths.get(this.fileStorageLocation +File.separator +"ExcelFile");
                filePath = folderStorageLocation.resolve(fileName).normalize();
                resource = new UrlResource(filePath.toUri());
            } else if(!fileName.equals("excelfilereport.xlsx")) {
                folderStorageLocation = Paths.get(this.fileStorageLocation + File.separator +rec);
                filePath = folderStorageLocation.resolve(fileName).normalize();
                resource = new UrlResource(filePath.toUri());
            }

            if(resource != null) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public Resource getCompanyLogo() throws IOException {
        String companyLogo = this.configurationService.getConfig().getCompanypicture();
        this.fileStorageDTO.setUploadDir(this.configurationService
                .getConfig().getVolume());
        this.fileStorageLocation = Paths.get(this.fileStorageDTO
                .getUploadDir()).toAbsolutePath().normalize();

        try {
            String companyFiles = "companyFiles";
            Path folderStorageLocation;
            folderStorageLocation = Paths.get(this.fileStorageLocation + File.separator +companyFiles);
            Path filePath;
            filePath = folderStorageLocation.resolve(companyLogo).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            System.out.print("*****************Resource: " + resource.getInputStream());
            return resource;
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + companyLogo, ex);
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