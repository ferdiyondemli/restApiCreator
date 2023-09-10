package com.ferdi.restapi.service;

import com.ferdi.restapi.dto.*;
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

        requestFile.getDirectoryFiles().forEach(directoryFile -> {
            try {
                createDirectory(directoryFile, requestFile.getPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }


    private void createDirectory(DirectoryFile directoryFile, String path) throws IOException {
        //path = RestApiCreation.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = "/C:/Users/user/Downloads/security/security/src/main/java/com/ferdi";
        File f = new File(path + "/" + directoryFile.getDirectoryName());

        if (f.mkdir()) {
            System.out.println("Directory named as " + directoryFile.getDirectoryName() + " has been created successfully");
            if(directoryFile.getJavaFiles()==null)return;
            String finalPath = path;
            directoryFile.getJavaFiles().forEach(javaFile -> {
                try {
                    createJavaFile(javaFile, finalPath + "/" + directoryFile.getDirectoryName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        } else {
            System.out.println("Directory named as " + directoryFile.getDirectoryName() + " cannot be created");
        }
    }


    private void createJavaFile(JavaFile javaFile, String path) throws IOException {

        File myObj = new File(path + "/" + javaFile.getJavaName() + ".java");
        if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
            writeFile(javaFile, myObj.getPath());
        } else {
            System.out.println("File already exists.");
        }

    }

    private void writeFile(JavaFile javaFile, String fileName) {

        String text = createJavaFileText(javaFile);
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


        return getAnnotations(javaFile.getAnnotations()) +

                "public class " + javaFile.getJavaName() +

                getExtenedClass( javaFile.getExtenedClass())+

                getImplementedInterfaces(javaFile.getImplementedInterfaces()) +

                " {" +

                getFields(javaFile.getFields()) +

                getMethods(javaFile.getMethods()) +

                "\n }";

    }

    private String getExtenedClass(String extenedClass) {

        if (extenedClass == null) return "";


        return " extends " + extenedClass;
    }

    private String getMethods(List<Method> methods) {
        if (methods == null) return "";

        StringBuilder string = new StringBuilder("\n");
        for (int i = 0; i < methods.size(); i++) {

            string.append(methods.get(i).getAccessModifier() + " " + methods.get(i).getReturnType() + " " + methods.get(i).getName()
                    + "(" + getParametres(methods.get(i).getParametres()) + ")" +
                    "{" + "\n" +
                    methods.get(i).getImplementation() +

                    "\n }");

        }
        return string.toString();
    }

    private String getFields(List<Field> fields) {
        if (fields == null) return "";

        StringBuilder string = new StringBuilder("\n");

        for (int i = 0; i < fields.size(); i++) {

            string.append(fields.get(i).getAccessModifier() + " " + fields.get(i).getType() + " " + fields.get(i).getName() + ";" + "\n");

        }
        return string.toString();
    }

    private String getParametres(List<Parametre> parametres) {
        if (parametres == null) return "";

        StringBuilder string = new StringBuilder("");

        for (int i = 0; i < parametres.size(); i++) {
            String combiner = i == parametres.size() - 1 ? " " : ",";

            string.append(parametres.get(i).getType() + " " + parametres.get(i).getName() + combiner);

        }
        return string.toString();
    }

    private String getImplementedInterfaces(List<String> implementedInterfaces) {
        if (implementedInterfaces == null) return "";

        if (implementedInterfaces.size() == 0) {
            return "";
        }
        return " implements " + getListText(implementedInterfaces);
    }


    private String getListText(List<String> strings) {
        StringBuilder string = new StringBuilder(" ");

        for (int i = 0; i < strings.size(); i++) {
            String combiner = i == strings.size() - 1 ? " " : "\n";
            string.append(strings.get(i) + combiner);

        }

        return string.toString();
    }

    private String getAnnotations(List<String> strings) {

        StringBuilder string = new StringBuilder(" ");

        for (int i = 0; i < strings.size(); i++) {
            string.append("@" + strings.get(i) + "\n");
        }

        return string.toString();


    }
}
