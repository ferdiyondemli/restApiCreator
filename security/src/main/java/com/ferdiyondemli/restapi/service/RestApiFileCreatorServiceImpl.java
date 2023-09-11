package com.ferdiyondemli.restapi.service;

import com.ferdiyondemli.fileCreator.FileCreator;
import com.ferdiyondemli.fileCreator.fileComponent.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestApiFileCreatorServiceImpl implements RestApiFileCreatorService {

    private final FileCreator fileCreator;
    @Override
    public void createFiles(RequestFile requestFile) {

        requestFile.getDirectoryFiles().forEach(directoryFile -> {
            try {
                fileCreator.createDirectory(directoryFile, requestFile.getPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }



}
