package com.ferdi.restapi.service;

import com.ferdi.restapi.RestApiCreation;
import com.ferdi.restapi.dto.DirectoryFile;
import com.ferdi.restapi.dto.JavaFile;
import com.ferdi.restapi.dto.RequestFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class RestApiFileCreatorServiceImpl implements RestApiFileCreatorService {

    @Override
    public void createFiles(RequestFile requestFile) {

        requestFile.getDirectoryFiles().forEach(
                directoryFile -> {
                    try {
                        createDirectory(directoryFile, requestFile.getPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

    }


    private void createDirectory(DirectoryFile directoryFile, String path) throws IOException {
        path = RestApiCreation.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = "/C:/Users/user/Downloads/security/security/src/main/java/com/ferdi";
        File f = new File(path + "/" + directoryFile.getDirectoryName());

        if (f.mkdir()) {
            System.out.println("Directory named as " + directoryFile.getDirectoryName() + " has been created successfully");
            String finalPath = path;
            directoryFile.getJavaFiles().forEach(javaFile -> {
                try {
                    createJavaFile(javaFile, finalPath +"/"+directoryFile.getDirectoryName());
                 } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        } else {
            System.out.println("Directory named as " + directoryFile.getDirectoryName() + " cannot be created");
        }
    }


    private void createJavaFile(JavaFile javaFile, String path) throws IOException {

        File myObj = new File(path+"/"+javaFile.getJavaName()+".java");
        if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
            writeFile(javaFile,myObj.getPath());
        } else {
            System.out.println("File already exists.");
        }

    }

private void writeFile(JavaFile javaFile,String fileName){

        String text=createJavaFileText(javaFile);
    try {
        FileWriter myWriter = new FileWriter(fileName);
        myWriter.write(text);
        myWriter.close();
        System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
}

    private String createJavaFileText(JavaFile javaFile) {

    return
"@RestController"+"\n"+
"public class "+ javaFile.getJavaName()+" {"+
        "\n"+
" public String ane;"+



        "\n"+"\n"+"\n"+
    "}";

    }
}
