package br.com.marquescleiton.xmlconverter.avroconverter;

import br.com.marquescleiton.xmlconverter.annotation.XmlMapper;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class XmlUtil extends XmlConverter{

    public Collection<?> convertXmltoAvroList(File file, String xmlField, Object xmlMapperClass, Object avroClass) throws IOException, ParserConfigurationException, SAXException, NoSuchFieldException, IllegalAccessException {
        List<Object> avroList = new ArrayList<>();
        if (file.isFile()) {
            NodeList nodeList = getAllNodesFromFile(file, xmlField);
            for(int i = 0; i< nodeList.getLength(); i++){
                Object avro = preencheObjetos(nodeList.item(i), xmlMapperClass, avroClass);
                avroList.add(avro);
            }
        }
        return avroList;
    }

    public Object convertXmltoAvro(Node xmlNode, Object xmlMapperClass, Object avroClass) throws NoSuchFieldException, IllegalAccessException {
        return preencheObjetos(xmlNode, xmlMapperClass, avroClass);
    }

    private Object preencheObjetos(Node xmlNode, Object xmlMapperClass,Object avroClass) throws IllegalAccessException {

        Field[]fields = avroClass.getClass().getDeclaredFields();

        for(Field field : fields){
            String xmlFieldName = getXmlField(xmlMapperClass, field.getName());
            if(xmlFieldName != null) {
                String value = getXmlValueFromNode(xmlNode, xmlFieldName);
                field.setAccessible(true);
                setReflectionField(avroClass, field, value);
                field.setAccessible(false);
            }
        }
        return avroClass;
    }

    public NodeList getAllNodesFromFile(File file, String xmlField) throws IOException, SAXException, ParserConfigurationException {

        BufferedReader reader = Files.newBufferedReader(file.toPath());

        String a = reader.lines().collect(Collectors.joining());

        System.out.println(StringUtils.substringBetween("usuario","/usuario"));

        ByteBuffer byteBuffer = StandardCharsets.ISO_8859_1.encode(a);
        a = new String(byteBuffer.array(), StandardCharsets.ISO_8859_1);

        System.out.println(a);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(a));
        Document doc = db.parse(is);

        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(xmlField);
    }

    public String getXmlValueFromNode(Node xmlNode, String xmlField){
        if(xmlField == null || xmlField.isEmpty()){
            return null;
        }
        return ((Element) xmlNode).getElementsByTagName(xmlField).item(0).getTextContent();
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
