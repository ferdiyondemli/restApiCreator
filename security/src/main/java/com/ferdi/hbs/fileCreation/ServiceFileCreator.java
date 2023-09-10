package com.ferdi.hbs.fileCreation;

import com.ferdi.restapi.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ServiceFileCreator {
    private final FileCreatorUtil util;

    public DirectoryFile getDirectoryFileService(JavaFile javaFile, String path) {
        DirectoryFile hbsService = new DirectoryFile();
        hbsService.setDirectoryName("service");
        hbsService.setDirectoryPath(path + "/" + javaFile.getJavaName());

        //javafile imp
        List<JavaFile> javaFilesService = new ArrayList<>();
        var javaFileResult=createServiceImpJavaFile(javaFile);
        javaFileResult.setImplementedInterfaces(List.of(util.capitilizeFirstLetter(javaFile.getJavaName())+"Service"));
         javaFilesService.add(javaFileResult);


        JavaFile interfaceFile = new JavaFile();
        interfaceFile.setMethods(javaFileResult.getMethods());
        interfaceFile.setType("interface");
        interfaceFile.setJavaName(util.capitilizeFirstLetter(javaFile.getJavaName())+"Service");
        javaFilesService.add(interfaceFile);

        hbsService.setJavaFiles(javaFilesService);
        return hbsService;


    }


    public JavaFile createServiceImpJavaFile(JavaFile entityJavaFile) {
        JavaFile serviceJavaFile = new JavaFile();

        //name etc
        serviceJavaFile.setJavaName(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "ServiceImpl");
        serviceJavaFile.setType("class");
        serviceJavaFile.setAnnotations(List.of("Service", "RequiredArgsConstructor"));

        //fields
        List<Field> fields = new ArrayList<>();
        fields.add(util.setFieldText(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "Repository", entityJavaFile.getJavaName() + "Repository"));
        fields.add(util.setFieldText(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "EkleServiceHelper", entityJavaFile.getJavaName() + "EkleServiceHelper"));
        fields.add(util.setFieldText(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "EkleServiceHelper", entityJavaFile.getJavaName() + "EkleServiceHelper"));
        fields.add(util.setFieldText(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "IptalServiceHelper", entityJavaFile.getJavaName() + "IptalServiceHelper"));
        serviceJavaFile.setFields(fields);

        //methods
        List<Method> methods = new ArrayList<>();

        //sorgula
        Method sorgulaMethod = getSorgulaMethodServiceImpl(entityJavaFile);
        methods.add(sorgulaMethod);

        //eklemethod
        Method ekleMethod = getEkleMethodServiceImpl(entityJavaFile);
        methods.add(ekleMethod);


        //guncelle method
        Method guncelleMethod = getGuncelleMethodServiceImpl(entityJavaFile);
        methods.add(guncelleMethod);


        //goruntule method
        Method goruntuleMethod = getGoruntuleMethodServiceImpl(entityJavaFile);
        methods.add(goruntuleMethod);


        //iptal method
        Method iptalMethod = getIptalMethodServiceImpl(entityJavaFile);
         methods.add(iptalMethod);

        serviceJavaFile.setMethods(methods);

        return serviceJavaFile;

     }

    private Method getSorgulaMethodServiceImpl(JavaFile entityJavaFile) {
        Method method = new Method();
        method.setAnnotations(List.of("Override", "Transactional(readOnly = true)"));
        method.setName("sorgula");
        method.setReturnType("Page<" + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + ">");
        method.setAccessModifier("public");

        //parametre
        Parametre parametre = new Parametre();
        parametre.setType("PageRequestDTO" );
        parametre.setName("pageRequestDTO ");
        method.setParametres(List.of(parametre));

        method.setImplementation(
                "Specification<"+util.capitilizeFirstLetter(entityJavaFile.getJavaName())+"> specification = byPageRequestDTO(pageRequestDTO);\n"+
                        "PageRequest pageRequest = PageRequestDTO.PageRequestBuilder.of(pageRequestDTO).build();\n"+
                        "Page<"+util.capitilizeFirstLetter(entityJavaFile.getJavaName())+"> "+entityJavaFile.getJavaName()+"Page="+
                        entityJavaFile.getJavaName()+"Repository.findAll(specification, pageRequest);\n"+
                        "return "+entityJavaFile.getJavaName()+"Page ;\n"


        );

        return method;
    }
    private Method getGoruntuleMethodServiceImpl(JavaFile entityJavaFile) {
        Method method = new Method();
        method.setAnnotations(List.of("Override", "Transactional(readOnly = true)"));
        method.setName("goruntule");
        method.setReturnType( util.capitilizeFirstLetter(entityJavaFile.getJavaName()));
        method.setAccessModifier("public");

        //parametre
        Parametre parametre = new Parametre();
        parametre.setType("Long" );
        parametre.setName("id");
        method.setParametres(List.of(parametre));

        method.setImplementation(
              "return " +entityJavaFile.getJavaName() + "Repository.findById(id).orElse(null);\n"
        );

        return method;
    }
    private Method getEkleMethodServiceImpl(JavaFile entityJavaFile) {
        Method method = new Method();
        method.setAnnotations(List.of("Override", "Transactional"));
        method.setName("ekle");
        method.setReturnType("Long");
        method.setAccessModifier("public");

        //parametre
        Parametre parametre = new Parametre();
        parametre.setType(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) );
        parametre.setName(entityJavaFile.getJavaName());
        method.setParametres(List.of(parametre));

        method.setImplementation(
                "return "+entityJavaFile.getJavaName() + "EkleServiceHelper.ekle("+entityJavaFile.getJavaName()+");\n"
        );

        return method;
    }
    private Method getGuncelleMethodServiceImpl(JavaFile entityJavaFile) {
        Method method = new Method();
        method.setAnnotations(List.of("Override", "Transactional"));
        method.setName("guncelle");
        method.setReturnType("void");
        method.setAccessModifier("public");

        //parametre
        Parametre parametre = new Parametre();
        parametre.setType(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) );
        parametre.setName(entityJavaFile.getJavaName());

        Parametre parametreKullanici = new Parametre();
        parametreKullanici.setType("LoginOlanKullaniciBilgileriDTO " );
        parametreKullanici.setName("loginOlanKullaniciBilgileriDTO");



        method.setParametres(List.of(parametre,parametreKullanici));

        method.setImplementation(
                "return "+entityJavaFile.getJavaName() + "GuncelleServiceHelper.guncelle("+entityJavaFile.getJavaName()+", loginOlanKullaniciBilgileriDTO);\n"
        );

        return method;
    }
    private Method getIptalMethodServiceImpl(JavaFile entityJavaFile) {
        Method method = new Method();
        method.setAnnotations(List.of("Override", "Transactional"));
        method.setName("iptal");
        method.setReturnType("void");
        method.setAccessModifier("public");

        //parametre
        Parametre parametre = new Parametre();
        parametre.setType("IdAndVersionDTO"  );
        parametre.setName( "idAndVersionDTO");

        Parametre parametreKullanici = new Parametre();
        parametreKullanici.setType("LoginOlanKullaniciBilgileriDTO " );
        parametreKullanici.setName("loginOlanKullaniciBilgileriDTO");



        method.setParametres(List.of(parametre,parametreKullanici));

        method.setImplementation(
                "return "+entityJavaFile.getJavaName() + "IptalServiceHelper.iptal(idAndVersionDTO, loginOlanKullaniciBilgileriDTO);\n"
        );

        return method;
    }
}
