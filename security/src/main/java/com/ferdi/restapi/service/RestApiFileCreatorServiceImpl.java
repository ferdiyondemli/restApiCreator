package com.ferdi.restapi.service;

import com.ferdi.restapi.RestApiCreation;
import com.ferdi.restapi.dto.DirectoryFile;
import com.ferdi.restapi.dto.JavaFile;
import com.ferdi.restapi.dto.RequestFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static java.rmi.server.LogStream.log;

@Service
@Slf4j
public class RestApiFileCreatorServiceImpl implements RestApiFileCreatorService {

    @Override
    public void createFiles(RequestFile requestFile) {

        requestFile.getDirectoryFiles().forEach(
                directoryFile -> createDirectory(directoryFile, requestFile.getPath())
        );

    }


    private void createDirectory(DirectoryFile directoryFile, String path) {
         path = RestApiCreation.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = "/C:/Users/user/Downloads/security/security/src/main/java/com/ferdi";
        File f = new File(path+"/"+directoryFile.getDirectoryName());

        if (f.mkdir()) {
            log("Directory named as "+ directoryFile.getDirectoryName()+" has been created successfully");
        }
        else {
            log("Directory named as "+ directoryFile.getDirectoryName()+" cannot be created");
         }
    }


    private void createJavaFiles(List<JavaFile> javaFiles) {

    }


}
