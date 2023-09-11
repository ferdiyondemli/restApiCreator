package com.ferdiyondemli.hbs.fileCreation;

import com.ferdiyondemli.fileCreator.fileComponent.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntityFileCreator {

    private final FileCreatorUtil util;

    public DirectoryFile getDirectoryFileController(JavaFile javaFile, String path) {
        DirectoryFile hbsController = new DirectoryFile();
        hbsController.setDirectoryName("entity");
        hbsController.setDirectoryPath(path + "/" + javaFile.getJavaName());

        //javafile
        List<JavaFile> javaFilesEntity = new ArrayList<>();
        javaFilesEntity.add(createEntityJavaFile(javaFile));
        hbsController.setJavaFiles(javaFilesEntity);
        return hbsController;
    }

    public JavaFile createEntityJavaFile(JavaFile entityJavaFile) {


        JavaFile entityTemp = new JavaFile();

        //name etc
        entityTemp.setJavaName(util.capitilizeFirstLetter(entityJavaFile.getJavaName()));
        entityTemp.setType("class");
        entityTemp.setExtenedClass("BaseEntity");
        entityTemp.setAnnotations(List.of(
                "Getter",
                "Setter",
                "Entity",
                "Table(name = \"" + util.camelToSnake(entityJavaFile.getJavaName()).toUpperCase() + "\")",
                " Where(clause = \"durum='AKTIF'\")"));

        //fields
        List<Field> fields = new ArrayList<>();
        for (int i = 0; i < entityJavaFile.getFields().size(); i++) {
            Field temp = new Field();
            temp.setType(entityJavaFile.getFields().get(i).getType());
            temp.setName(entityJavaFile.getFields().get(i).getName());
            temp.setAccessModifier("private");
            temp.setAnnotations(List.of(
                    " Column(name = \"" + util.camelToSnake(entityJavaFile.getFields().get(i).getName()).toUpperCase() + "\")"
            ));
            fields.add(temp);
        }

        entityTemp.setFields(fields);

        //methods
        List<Method> methods = new ArrayList<>();


        //guncelle method
        Method guncelleMethod = getGuncelleMethodEntity(entityJavaFile);
        methods.add(guncelleMethod);


        entityTemp.setMethods(methods);


        return entityTemp;

    }



    private Method getGuncelleMethodEntity(JavaFile entityJavaFile) {
        Method guncelleMethod = new Method();
        guncelleMethod.setAccessModifier("public");
        guncelleMethod.setName("guncelle");
        guncelleMethod.setReturnType("void");
         guncelleMethod.setImplementation(getFieldGuncelleImplText(entityJavaFile));
        Parametre parametreGuncelle = new Parametre();
        parametreGuncelle.setName(entityJavaFile.getJavaName()  );
        parametreGuncelle.setType(util.capitilizeFirstLetter(entityJavaFile.getJavaName()));
         guncelleMethod.setParametres(List.of(parametreGuncelle));
        return guncelleMethod;
    }

    private String getFieldGuncelleImplText(JavaFile entityJavaFile) {

        String str = "";
        for (int i = 0; i < entityJavaFile.getFields().size(); i++) {
            str += entityJavaFile.getFields().get(i).getName() + "=" +
                   entityJavaFile.getJavaName() + ".get" + util.capitilizeFirstLetter(entityJavaFile.getFields().get(i).getName()) + "();\n";

        }
        return str;
    }



}
