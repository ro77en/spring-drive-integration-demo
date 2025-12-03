package com.ro77en.drive_integration_demo.controller;

import com.ro77en.drive_integration_demo.dto.DriveFileDTO;
import com.ro77en.drive_integration_demo.enums.DriveProvider;
import com.ro77en.drive_integration_demo.factory.DriveStorageFactory;
import com.ro77en.drive_integration_demo.strategy.DriveStorageStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drive")
@RequiredArgsConstructor
public class DriveController {

    private final DriveStorageFactory driveStorageFactory;

    @GetMapping("/{provider}/files")
    public ResponseEntity<List<DriveFileDTO>> getDriveFiles(
            @PathVariable DriveProvider provider,
            @RequestParam String folderId
    ) {
        DriveStorageStrategy strategy = driveStorageFactory.getStrategy(provider);
        List<DriveFileDTO> files = strategy.listItems(folderId);
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
}
