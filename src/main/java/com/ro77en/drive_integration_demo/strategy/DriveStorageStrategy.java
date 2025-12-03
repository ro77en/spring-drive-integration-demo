package com.ro77en.drive_integration_demo.strategy;

import com.ro77en.drive_integration_demo.dto.DriveFileDTO;
import com.ro77en.drive_integration_demo.enums.DriveProvider;

import java.util.List;

public interface DriveStorageStrategy {
    List<DriveFileDTO> listItems(String folderId);
    DriveProvider getProvider();

}
