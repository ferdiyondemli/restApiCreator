package com.ferdi.hbs.fileCreation;

import com.ferdi.restapi.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ControllerFileCreator {

    private final FileCreatorUtil util;
  public   DirectoryFile getDirectoryFileController(JavaFile javaFile, String path) {
        DirectoryFile hbsController = new DirectoryFile();
        hbsController.setDirectoryName("controller");
        hbsController.setDirectoryPath(path + "/" + javaFile.getJavaName());
        //javafile
        List<JavaFile> javaFilesController = new ArrayList<>();
        javaFilesController.add(createControllerJavaFile(javaFile));
        hbsController.setJavaFiles(javaFilesController);
        return hbsController;
    }

    public JavaFile createControllerJavaFile(JavaFile entityJavaFile) {


        JavaFile contJavaFile = new JavaFile();

        //name etc
        contJavaFile.setJavaName(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "Controller");
        contJavaFile.setType("class");
        contJavaFile.setAnnotations(List.of("ResponseStatus(HttpStatus.OK)", "RestController", "RequiredArgsConstructor", "RequestMapping(\"/" + entityJavaFile.getJavaName() + "\")"));

        //fields
        List<Field> fields = new ArrayList<>();
        fields.add(util.setFieldText(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "Service", entityJavaFile.getJavaName() + "Service"));
        fields.add(util.setFieldText(util.capitilizeFirstLetter("oturumBilgisiServiceImpl"), "oturumBilgisiServiceImpl"));
        contJavaFile.setFields(fields);

        //methods
        List<Method> methods = new ArrayList<>();

        //sorgula
        Method sorgulaMethod = getSorgulaMethodController(entityJavaFile);
        methods.add(sorgulaMethod);


        //eklemethod
        Method ekleMethod = getEkleMethodController(entityJavaFile);
        methods.add(ekleMethod);


        //guncelle method
        Method guncelleMethod = getGuncelleMethodController(entityJavaFile);
        methods.add(guncelleMethod);


        //goruntule method
        Method goruntuleMethod = getGoruntuleMethodController(entityJavaFile);
        methods.add(goruntuleMethod);


        //iptal method
        Method iptalMethod = getIptalMethodController(entityJavaFile);
        methods.add(iptalMethod);


        contJavaFile.setMethods(methods);


        return contJavaFile;

    }

    private Method getIptalMethodController(JavaFile entityJavaFile) {
        Method iptalMethod = new Method();
        iptalMethod.setAccessModifier("public");
        iptalMethod.setName("iptal");
        iptalMethod.setReturnType("void");
        iptalMethod.setAnnotations(List.of("PostMapping(\"/iptal\")", "PreAuthorize(\"hasRole('ADMIN') or hasRole('DISBORC_BASKAN') or hasRole('DISBORC_UZMAN')\")", "ApiOperation(value =\"" + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + " bilgileri İptal İşlemini Gerçekleştirir.\")"));
        iptalMethod.setImplementation("LoginOlanKullaniciBilgileriDTO kullanici = oturumBilgisiServiceImpl.getirLoginOlanKullaniciBilgileri();\n" + entityJavaFile.getJavaName() + "Service.iptal(idAndVersionDTO, kullanici);\n");
        Parametre parametreIptalMethod = new Parametre();
        parametreIptalMethod.setName("idAndVersionDTO");
        parametreIptalMethod.setType("IdAndVersionDTO");
        parametreIptalMethod.setAnnotations(List.of("RequestBody"));
        iptalMethod.setParametres(List.of(parametreIptalMethod));
        return iptalMethod;
    }

    private Method getGoruntuleMethodController(JavaFile entityJavaFile) {
        Method goruntuleMethod = new Method();
        goruntuleMethod.setAccessModifier("public");
        goruntuleMethod.setName("goruntule");
        goruntuleMethod.setReturnType(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "ResponseDTO");
        goruntuleMethod.setAnnotations(List.of("GetMapping(\"/goruntule\")", "PreAuthorize(\"hasRole('DISBORC_SAYISTAY') or hasRole('ADMIN') or hasRole('DISBORC_BASKAN') or hasRole('DISBORC_UZMAN')\")", "ApiOperation(value =\"" + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + " bilgisi Getirir.\")"));
        goruntuleMethod.setImplementation(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + " " + entityJavaFile.getJavaName() + "=" + entityJavaFile.getJavaName() + "Service.goruntule(id);" + "return    " + entityJavaFile.getJavaName() + "SorgulaMapper.INSTANCE::entityToDto(" + entityJavaFile.getJavaName() + "); \n"

        );
        Parametre parametreGoruntule = new Parametre();
        parametreGoruntule.setName("@ApiParam(example = \"10001\", required = true) @RequestParam(\"id\") Long id");
        parametreGoruntule.setType("");
        goruntuleMethod.setParametres(List.of(parametreGoruntule));
        return goruntuleMethod;
    }

    private Method getGuncelleMethodController(JavaFile entityJavaFile) {
        Method guncelleMethod = new Method();
        guncelleMethod.setAccessModifier("public");
        guncelleMethod.setName("guncelle");
        guncelleMethod.setReturnType("void");
        guncelleMethod.setAnnotations(List.of("PostMapping(\"/guncelle\")", "PreAuthorize(\"hasRole('ADMIN') or hasRole('DISBORC_BASKAN') or hasRole('DISBORC_UZMAN')\")", "ApiOperation(value =\"" + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "guncelleme işlemini gerçekleştirir.\")"));
        guncelleMethod.setImplementation("LoginOlanKullaniciBilgileriDTO kullanici = oturumBilgisiServiceImpl.getirLoginOlanKullaniciBilgileri();\n" + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + " " + entityJavaFile.getJavaName() + "=" + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "GuncelleMapper.INSTANCE.dtoToEntity(" + entityJavaFile.getJavaName() + "GuncelleRequestDTO); \n" + entityJavaFile.getJavaName() + "Service.guncelle(" + entityJavaFile.getJavaName() + ", kullanici);\n"

        );
        Parametre parametreGuncelle = new Parametre();
        parametreGuncelle.setName(entityJavaFile.getJavaName() + "GuncelleRequestDTO");
        parametreGuncelle.setType(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "GuncelleRequestDTO");
        parametreGuncelle.setAnnotations(List.of("RequestBody", "Valid"));
        guncelleMethod.setParametres(List.of(parametreGuncelle));
        return guncelleMethod;
    }

    private Method getEkleMethodController(JavaFile entityJavaFile) {
        Method ekleMethod = new Method();
        ekleMethod.setAccessModifier("public");
        ekleMethod.setName("ekle");
        ekleMethod.setReturnType("IdAndVersionDTO");
        ekleMethod.setAnnotations(List.of("PostMapping(\"/ekle\")", "PreAuthorize(\"hasRole('ADMIN') or hasRole('DISBORC_BASKAN') or hasRole('DISBORC_UZMAN')\")", "ApiOperation(value =\"" + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "ekleme işlemini gerçekleştirir.\")"));
        ekleMethod.setImplementation("LoginOlanKullaniciBilgileriDTO kullanici = oturumBilgisiServiceImpl.getirLoginOlanKullaniciBilgileri();\n" + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + " " + entityJavaFile.getJavaName() + "=" + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "EkleMapper.INSTANCE.dtoToEntity(" + entityJavaFile.getJavaName() + "EkleRequestDTO); \n" + "Long id = " + entityJavaFile.getJavaName() + "Service.ekle(" + entityJavaFile.getJavaName() + ");\n" + " return new IdAndVersionDTO(id, 0); \n");
        Parametre parametreEkle = new Parametre();
        parametreEkle.setType(util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "RequestDTO");
        parametreEkle.setName(entityJavaFile.getJavaName() + "RequestDTO");
        parametreEkle.setAnnotations(List.of("RequestBody", "Valid"));
        ekleMethod.setParametres(List.of(parametreEkle));
        return ekleMethod;
    }

    private Method getSorgulaMethodController(JavaFile entityJavaFile) {
        Method sorgulaMethod = new Method();
        sorgulaMethod.setAccessModifier("public");
        sorgulaMethod.setName("sorgula");
        sorgulaMethod.setReturnType("PageResultDTO");
        sorgulaMethod.setAnnotations(List.of("PostMapping(\"/sorgula\")", "PreAuthorize(\"hasRole('DISBORC_SAYISTAY') or hasRole('ADMIN') or hasRole('DISBORC_BASKAN') or hasRole('DISBORC_UZMAN')\")", "ApiOperation(value =\"" + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + " bilgisini getirir\")"));
        sorgulaMethod.setImplementation("Page<" + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + ">" + entityJavaFile.getJavaName() + "Page" + "=" + entityJavaFile.getJavaName() + "Service.sorgula(pageRequestDTO);" + "return new PageResultDTO(" + entityJavaFile.getJavaName() + "Page," + util.capitilizeFirstLetter(entityJavaFile.getJavaName()) + "SorgulaMapper.INSTANCE::entityToDto);");
        Parametre parametre = new Parametre();
        parametre.setName("pageRequestDTO");
        parametre.setType("PageRequestDTO");
        parametre.setAnnotations(List.of("RequestBody"));
        sorgulaMethod.setParametres(List.of(parametre));
        return sorgulaMethod;
    }


}
