package com.ferdi.hbs.fileCreation;

import com.ferdi.fileCreator.fileComponent.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RepoFileCreator {
    private final FileCreatorUtil util;

    public DirectoryFile getDirectoryFileService(JavaFile javaFile, String path) {
        DirectoryFile hbsRepo = new DirectoryFile();
        hbsRepo.setDirectoryName("repository");
        hbsRepo.setDirectoryPath(path + "/" + javaFile.getJavaName());

        //javafile Reposistory interface
        List<JavaFile> javaFilesRepo = new ArrayList<>();
        var javaFileResult = createServiceImpJavaFile(javaFile);
        javaFileResult.setExtenedClass("PagingAndSortingBaseRepository<" + util.capitilizeFirstLetter(javaFile.getJavaName()) + ", Long>, JpaSpecificationExecutor<" + util.capitilizeFirstLetter(javaFile.getJavaName()) + "> ");
        javaFilesRepo.add(javaFileResult);
        hbsRepo.setJavaFiles(javaFilesRepo);

        //specification dir
        DirectoryFile hbsSpecs = new DirectoryFile();
        hbsSpecs.setDirectoryName("specification");
        hbsSpecs.setDirectoryPath(path + "/" + javaFile.getJavaName());

        //javafile specification
        List<JavaFile> javaFilesSpec = new ArrayList<>();
        var javaFileSpecResult = createSpecJavaFile(javaFile);
        javaFileSpecResult.setExtenedClass("PagingAndSortingBaseRepository<" + util.capitilizeFirstLetter(javaFile.getJavaName()) + ", Long>, JpaSpecificationExecutor<" + util.capitilizeFirstLetter(javaFile.getJavaName()) + "> ");

        javaFilesSpec.add(javaFileSpecResult);

        hbsSpecs.setJavaFiles(javaFilesSpec);




        hbsRepo.setDirectoryFiles(List.of(hbsSpecs));


        return hbsRepo;


    }


    public JavaFile createServiceImpJavaFile(JavaFile entityJavaFile) {
        JavaFile serviceJavaFile = new JavaFile();

        //name etc
        serviceJavaFile.setJavaName(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "Repository");
        serviceJavaFile.setType("interface");


        return serviceJavaFile;

    }

    public JavaFile createSpecJavaFile(JavaFile entityJavaFile) {


        JavaFile specJavaFile = new JavaFile();

        //name etc
        specJavaFile.setJavaName(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "Specification");
        specJavaFile.setType("class");


        //methods
        List<Method> methods = new ArrayList<>();

        //sorgula
        Method method = byPageRequestDTO(entityJavaFile);
        methods.add(method);


        specJavaFile.setMethods(methods);


        return specJavaFile;

    }

    private Method byPageRequestDTO(JavaFile entityJavaFile) {
        Method method = new Method();
        method.setAccessModifier("public static");
        method.setName("byPageRequestDTO");
        method.setReturnType("Specification <" + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + ">");
        method.setImplementation(
                "return SpecificationMapper.SpecificationBuilder.newBuilder()\n" +
                        ".forCriteriaDTO(pageRequestDTO)\n" +
                        getWiths(entityJavaFile) +
                        ".build();\n"


        );

        Parametre parametreEkle = new Parametre();
        parametreEkle.setType("PageRequestDTO ");
        parametreEkle.setName("pageRequestDTO");
        method.setParametres(List.of(parametreEkle));
        return method;

    }

    private String getWiths(JavaFile entityJavaFile) {
        String str = "";
        for (int i = 0; i < entityJavaFile.getFields().size(); i++) {
            str += ".with(\"" + entityJavaFile.getFields().get(i).getName() + "\"," + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "_." + entityJavaFile.getFields().get(i).getName() + ")\n";

        }
        return str;
    }

}
