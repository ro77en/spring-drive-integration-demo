package com.ro77en.drive_integration_demo.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Configuration
@Slf4j
public class GoogleDriveConfig {

    private static final String APPLICATION_NAME = "Google Drive Spring Boot Connection";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

    @Bean
    public Drive googleDriveService() throws GeneralSecurityException, IOException {
        log.info("Creating Google Drive Service...");

        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(CREDENTIALS_FILE_PATH))
                .createScoped(SCOPES);

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
        final HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        log.info("Google Drive Service successfully created!");

        return new Drive.Builder(httpTransport, JSON_FACTORY, requestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
