package com.ro77en.drive_integration_demo.strategy;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.ro77en.drive_integration_demo.dto.DriveFileDTO;
import com.ro77en.drive_integration_demo.enums.DriveProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleDriveStrategy implements DriveStorageStrategy {

    private final Drive driveService;

    public List<DriveFileDTO> listItems(String folderId)  {
        try {
            FileList res = driveService.files().list()
                    .setQ("'" + folderId + "' in parents and trashed = false")
                    .setFields("files(id, name, webViewLink, mimeType, size, createdTime)")
                    .execute();

            return res.getFiles().stream()
                    .map(this::toDriveFileDTO)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.info("Error: {}", e.getMessage());
            return List.of();
        }
    }

    public DriveProvider getProvider() {
        return DriveProvider.GOOGLE_DRIVE;
    }

    private DriveFileDTO toDriveFileDTO(File googleDriveFile) {
        return DriveFileDTO.builder()
                .id(googleDriveFile.getId())
                .fileName(googleDriveFile.getName())
                .webViewLink(googleDriveFile.getWebViewLink())
                .mimeType(googleDriveFile.getMimeType())
                .bytes(googleDriveFile.getSize())
                .createdAt(LocalDate.ofInstant(new Date().toInstant(), ZoneId.systemDefault()))
                .driveProvider(DriveProvider.GOOGLE_DRIVE)
                .build();
    }
}
