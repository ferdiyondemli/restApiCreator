package com.ferdi.hbs;

import com.ferdi.hbs.fileCreation.*;
import com.ferdi.fileCreator.FileCreator;
import com.ferdi.fileCreator.fileComponent.DirectoryFile;
import com.ferdi.fileCreator.fileComponent.JavaFile;
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
    private final RepoFileCreator repoFileCreator;
    private final EntityFileCreator entityFileCreator;


    public void createFile(JavaFile javaFile, String path) {
        //create top directory
        DirectoryFile hbsDir = new DirectoryFile();
        hbsDir.setDirectoryPath(path);
        hbsDir.setDirectoryName(javaFile.getJavaName());

        List<DirectoryFile> directoryFiles = new ArrayList<>();


        //create entity
         DirectoryFile hbsEntity = entityFileCreator.getDirectoryFileController(javaFile, path);
        directoryFiles.add(hbsEntity);


        //create controller
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

        //repo
        DirectoryFile hbsRepo = repoFileCreator.getDirectoryFileService(javaFile, path);
        directoryFiles.add(hbsRepo);




        hbsDir.setDirectoryFiles(directoryFiles);


        //create
        try {
            fileCreator.createDirectory(hbsDir, path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




}
