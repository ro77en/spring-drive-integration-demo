package com.ro77en.drive_integration_demo.dto;

import com.ro77en.drive_integration_demo.enums.DriveProvider;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DriveFileDTO(
        String id,
        String fileName,
        String webViewLink,
        String mimeType,
        Long bytes,
        LocalDate createdAt,
        DriveProvider driveProvider
) {
}
