package com.ferdi.hbs.fileCreation;

import com.ferdi.fileCreator.fileComponent.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service

public class MapperFileCreator {

    private final FileCreatorUtil util;

    public DirectoryFile getDirectoryFileService(JavaFile javaFile, String path) {
        DirectoryFile hbsMapper = new DirectoryFile();
        hbsMapper.setDirectoryName("mapper");
        hbsMapper.setDirectoryPath(path + "/" + javaFile.getJavaName());


        //javafile ekleMapper
        List<JavaFile> javaFileMapper = new ArrayList<>();
        var ekleMapper=createServiceImpJavaFile(javaFile,"EkleRequestDTO","EkleMapper");
        javaFileMapper.add(ekleMapper);

        //javafile guncelleMapper
        var guncelleMapper=createServiceImpJavaFile(javaFile,"GuncelleRequestDTO","GuncelleMapper");
        javaFileMapper.add(guncelleMapper);

        //javafile sorgulaMapper
        var sorgulaMapper=createServiceImpJavaFile(javaFile,"","SorgulaMapper");
        sorgulaMapper.getMethods().get(0).setName("entityToDto");
        sorgulaMapper.getMethods().get(0).setReturnType(util.capitilizeFirstLetter(javaFile.getJavaName())+"ResponseDTO");
        javaFileMapper.add(sorgulaMapper);


        hbsMapper.setJavaFiles(javaFileMapper);


        return hbsMapper;

    }

    private JavaFile createServiceImpJavaFile(JavaFile entityJavaFile,String dtoSuffix,String classSuffix) {
        JavaFile mapperJavaFile = new JavaFile();

        //name etc
        mapperJavaFile.setJavaName(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + classSuffix);
        mapperJavaFile.setType("interface");
        mapperJavaFile.setAnnotations(List.of("Mapper(uses = {EnumMapper.class})"));



        //fields
        List<Field> fields = new ArrayList<>();
        Field field = util.setFieldText(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + classSuffix, "INSTANCE = Mappers.getMapper(" + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + classSuffix + ".class)");
        field.setAccessModifier("");
        fields.add(field);
        mapperJavaFile.setFields(fields);

        //methods
        List<Method> methods = new ArrayList<>();

        //dtoToEntity
        Method method = get_dtoToEntity_Method(entityJavaFile,dtoSuffix);
        methods.add(method);

        //enumlar için gerekli default metod eklenmiyor şimdilik

        mapperJavaFile.setMethods(methods);

        return mapperJavaFile;
    }


    private Method get_dtoToEntity_Method(JavaFile entityJavaFile,String dtoSuffix) {
        Method method = new Method();
         method.setName("dtoToEntity");
        method.setReturnType( util.capitilizeFirstLetter(entityJavaFile.getJavaName()));

        //parametre
        Parametre parametre = new Parametre();
        parametre.setType(util.capitilizeFirstLetter(entityJavaFile.getJavaName())+ dtoSuffix );
        parametre.setName(entityJavaFile.getJavaName()+ dtoSuffix );
         method.setParametres(List.of(parametre));



        return method;
    }
}
