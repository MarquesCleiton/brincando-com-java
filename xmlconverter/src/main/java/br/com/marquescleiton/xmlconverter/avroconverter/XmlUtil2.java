package br.com.marquescleiton.xmlconverter.avroconverter;

import br.com.marquescleiton.xmlconverter.annotation.XmlMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class XmlUtil2 extends XmlConverter{

    public String[] getAllNodesFromXml(File file, String xmlField) throws IOException {

        if(file.isFile()) {
            BufferedReader reader = Files.newBufferedReader(file.toPath());
            String xml = Files.readString(file.toPath(), StandardCharsets.ISO_8859_1);
            String node;
            List<String> list = new ArrayList<>();
            int indexOne = xml.indexOf("<"+xmlField+">");
            int indexTwo = xml.indexOf("</"+xmlField+">");

            while (indexOne != -1 && indexTwo != -1 && indexTwo < xml.length()){
                node = xml.substring(indexOne,indexTwo+(xmlField.length()+3));
                xml = xml.substring(indexTwo+(xmlField.length()+3), xml.length()-1);
                indexOne = xml.indexOf("<"+xmlField+">");
                indexTwo = xml.indexOf("</"+xmlField+">");
                list.add(node);
            }
            return list.toArray(String[]::new);
        }
        return null;
    }

    /**
     *
     * @param xmlNode xml com os campos que deseja resgatar um valor
     * @param tag nome do tag que deseja resgatar o valor
     * @return retorna o valor no formato String ou nulo caso não encontre
     */
    public String getValueFromXmlTag(String xmlNode, String tag){
        int indexOne = xmlNode.indexOf("<"+tag+">");
        int indexTwo = xmlNode.indexOf("</"+tag+">");

        if(indexOne == -1 || indexTwo == -1 )
            return null;
        indexOne = indexOne + tag.length() + 2;

        return xmlNode.substring(indexOne, indexTwo);
    }

    public Object convertXmltoAvro(Class<?> avroClass, String xmlNode, Class<?> xmlMapper) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        return preecheAvroObject(avroClass, xmlNode, xmlMapper);
    }

    private Object preecheAvroObject(Class<?> avro, String xmlNode, Class<?> xmlMapper) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        Object objectMapper = newInstance(xmlMapper);
        Object avroMapper = newInstance(avro);

        Field[]fields = avroMapper.getClass().getDeclaredFields();
        for(Field field : fields){
            String xmlFieldName = getXmlField(objectMapper, field.getName());
            if(xmlFieldName != null) {
                String value = getValueFromXmlTag(xmlNode, xmlFieldName);
                field.setAccessible(true);
                super.setReflectionField(avroMapper, field, value);
                field.setAccessible(false);
            }
        }
        return avroMapper;
    }

    private String getXmlField(Object xmlMapperClass, String avroField){
        try{
            Field field = xmlMapperClass.getClass().getDeclaredField(avroField);
            if(field.isAnnotationPresent(XmlMapper.class)){
                XmlMapper xmlMapper = field.getAnnotation(XmlMapper.class);
                return xmlMapper.value();
            }
        }catch (NoSuchFieldException ignored){}

        return null;
    }
}
