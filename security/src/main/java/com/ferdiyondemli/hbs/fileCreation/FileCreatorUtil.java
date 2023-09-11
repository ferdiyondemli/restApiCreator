package com.ferdiyondemli.hbs.fileCreation;

import com.ferdiyondemli.fileCreator.fileComponent.Field;
import com.ferdiyondemli.fileCreator.fileComponent.JavaFile;
import com.ferdiyondemli.fileCreator.fileComponent.Method;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileCreatorUtil {

     public JavaFile createServiceImpJavaFile(JavaFile entityJavaFile) {
        JavaFile serviceJavaFile = new JavaFile();

        //name etc
        serviceJavaFile.setJavaName(capitilizeFirstLetter(entityJavaFile.getJavaName()) + "ServiceImpl");
        serviceJavaFile.setAnnotations(List.of("Service", "RequiredArgsConstructor"));
        serviceJavaFile.setImplementedInterfaces(List.of(capitilizeFirstLetter(entityJavaFile.getJavaName()) + "Service"));

        //fields
        List<Field> fields = new ArrayList<>();
        fields.add(setFieldText(capitilizeFirstLetter(entityJavaFile.getJavaName()) + "Repository", entityJavaFile.getJavaName() + "Repository"));
        fields.add(setFieldText(capitilizeFirstLetter(entityJavaFile.getJavaName()) + "EkleServiceHelper", entityJavaFile.getJavaName() + "EkleServiceHelper"));
        fields.add(setFieldText(capitilizeFirstLetter(entityJavaFile.getJavaName()) + "GuncelleServiceHelper", entityJavaFile.getJavaName() + "GuncelleServiceHelper"));
        fields.add(setFieldText(capitilizeFirstLetter(entityJavaFile.getJavaName()) + "IptalServiceHelper", entityJavaFile.getJavaName() + "IptalServiceHelper"));
        serviceJavaFile.setFields(fields);

        //methods
        List<Method> methods = new ArrayList<>();

        //sorgula
   /*     Method sorgulaMethod = getSorgulaMethodService(entityJavaFile);
        methods.add(sorgulaMethod);*/



        return serviceJavaFile;
    }



    public Field setFieldText(String type, String name) {
        Field fieldTemp = new Field();
        fieldTemp.setType(type);
        fieldTemp.setName(name);
        fieldTemp.setAccessModifier("private final");
        return fieldTemp;
    }


    public String capitilizeFirstLetter(String str) {

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public String camelToSnake(String str) {

        // Empty String
        String result = "";

        // Append first character(in lower case)
        // to result string
        char c = str.charAt(0);
        result = result + Character.toLowerCase(c);

        // Traverse the string from
        // ist index to last index
        for (int i = 1; i < str.length(); i++) {

            char ch = str.charAt(i);

            // Check if the character is upper case
            // then append '_' and such character
            // (in lower case) to result string
            if (Character.isUpperCase(ch)) {
                result = result + '_';
                result = result + Character.toLowerCase(ch);
            }

            // If the character is lower case then
            // add such character into result string
            else {
                result = result + ch;
            }
        }

        // return the result
        return result;
    }
}
