package br.com.marquescleiton.xmlconverter.newer;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class XmlModelMapper {

    private XmlExtractor xmlExtractor;

    public XmlModelMapper(){
        xmlExtractor = new XmlExtractor();
    }


    public <D> D map(String xml, Class<?> mapperClass, Class<?> finalObject) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        return (D) preecheObjectComXml(xml,mapperClass,finalObject);
    }

    private Object preecheObjectComXml(String xml, Class<?> mapperClass,Class<?> finalObject) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        Object classMapper = newInstance(mapperClass);
        Object objectFinal = newInstance(finalObject);

        Field[]fields = objectFinal.getClass().getDeclaredFields();
        for(Field field : fields){
            String xmlFieldName = getXmlField(classMapper, field.getName());
            if(xmlFieldName != null) {
                String value = xmlExtractor.getValueFromTag(xml, xmlFieldName);
                field.setAccessible(true);
                setReflectionField(objectFinal, field, value);
                field.setAccessible(false);
            }
        }
        return objectFinal;
    }

    private String getXmlField(Object xmlMapperClass, String avroField){
        try{
            Field field = xmlMapperClass.getClass().getDeclaredField(avroField);
            if(field.isAnnotationPresent(br.com.marquescleiton.xmlconverter.annotation.XmlMapper.class)){
                br.com.marquescleiton.xmlconverter.annotation.XmlMapper xmlMapper = field.getAnnotation(br.com.marquescleiton.xmlconverter.annotation.XmlMapper.class);
                return xmlMapper.value();
            }
        }catch (NoSuchFieldException ignored){}
        return null;
    }

    protected Object newInstance(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = clazz.getConstructor();
        return constructor.newInstance();
    }

    protected void setReflectionField(Object object, Field field, String value) throws IllegalAccessException {
        if(value != null && !value.isEmpty()){
            if(field.getType().equals(String.class) || field.getType().equals(CharSequence.class)) {
                field.set(object, value);
            }else if(field.getType().equals(Integer.class)){
                Integer var = Integer.valueOf(value);
                field.set(object, var);
            }else if(field.getType().equals(Short.class)){
                Short var = Short.valueOf(value);
                field.set(object, var);
            }else if(field.getType().equals(Double.class)){
                Double var = Double.valueOf(value);
                field.set(object, var);
            }else if(field.getType().equals(Float.class)){
                Float var = Float.valueOf(value);
                field.set(object, var);
            }
        }
    }

}
