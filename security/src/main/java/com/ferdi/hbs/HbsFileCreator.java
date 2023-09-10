package com.ferdi.hbs;

import com.ferdi.hbs.fileCreation.ControllerFileCreator;
import com.ferdi.hbs.fileCreation.MapperFileCreator;
import com.ferdi.hbs.fileCreation.ServiceFileCreator;
import com.ferdi.restapi.FileCreator;
import com.ferdi.restapi.dto.DirectoryFile;
import com.ferdi.restapi.dto.JavaFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HbsFileCreator {
    private final FileCreator fileCreator;
    private final ControllerFileCreator controllerFileCreator;
    private final ServiceFileCreator serviceFileCreator;
    private final MapperFileCreator mapperFileCreator;


    public void createFile(JavaFile javaFile, String path) {
        //create top directory


        DirectoryFile hbsDir = new DirectoryFile();
        hbsDir.setDirectoryPath(path);
        hbsDir.setDirectoryName(javaFile.getJavaName());

        List<DirectoryFile> directoryFiles = new ArrayList<>();

        //create controller
        //dir
        DirectoryFile hbsController = controllerFileCreator.getDirectoryFileController(javaFile, path);
        directoryFiles.add(hbsController);

        //create service
        //dir
        DirectoryFile hbsService = serviceFileCreator.getDirectoryFileService(javaFile, path);
        directoryFiles.add(hbsService);
// daha helper koncak içine


        //mapper
        DirectoryFile hbsMapper = mapperFileCreator.getDirectoryFileService(javaFile, path);
        directoryFiles.add(hbsMapper);


        hbsDir.setDirectoryFiles(directoryFiles);

        //create entity
        //create service
        //create repo
        //create
        try {
            fileCreator.createDirectory(hbsDir, path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




}
