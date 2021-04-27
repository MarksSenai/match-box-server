package com.develop.machinecomm.Machinecomm.interfaces;

import com.develop.machinecomm.Machinecomm.dto.UploadFileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ManageFiles {
    UploadFileDTO fileStorage(MultipartFile file, String obj);
}
