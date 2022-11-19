package br.com.marquescleiton.xmlconverter;

import br.com.marquescleiton.xmlconverter.annotation.XmlMapper;
import br.com.marquescleiton.xmlconverter.avro.user.UserAvro;
import br.com.marquescleiton.xmlconverter.xmlavromapper.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class XmlconverterApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(XmlconverterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Digite o path dos arquivos");
		String path = "C:\\Users\\Cleiton\\Desktop\\xml";

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		List<UserAvro> usuarios = new ArrayList<>();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				NodeList nodeList = getAllNodesFromFile(file, "usuario");
				for(int i = 0; i< nodeList.getLength(); i++){
					UserAvro UserAvro;
					UserAvro = (UserAvro) preencheObjetos(nodeList.item(i), new User(), new UserAvro());
					usuarios.add(UserAvro);
				}
			}
		}

		System.out.println(usuarios.size());

		for(UserAvro user : usuarios){
			System.out.println(user.getName());
			System.out.println(user.getFavoriteNumber());
			System.out.println(user.getFavoriteColor());
		}
	}


	private NodeList getAllNodesFromFile(File file, String field) throws IOException, SAXException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);

		doc.getDocumentElement().normalize();
		return doc.getElementsByTagName(field);
	}

	private String getXmlValue(Node xml, String field){
		if(field == null || field.isEmpty()){
			return null;
		}
		return ((Element) xml).getElementsByTagName(field).item(0).getTextContent();
	}

	private Object preencheObjetos(Node xml, Object mapper,Object object) throws NoSuchFieldException, IllegalAccessException {

		Field[]fields = object.getClass().getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
			String nomeCampo = getXmlField(mapper,field.getName());
			String value = getXmlValue(xml, nomeCampo);
			setReflectionField(object, field, value);
		}
		return object;
	}

	private void setReflectionField(Object object, Field field, String value) throws IllegalAccessException {
		if(field.getType().equals(String.class) || field.getType().equals(CharSequence.class)) {
			String var = (String) value;
			field.set(object, var);
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
	private String getXmlField(Object obj, String campo) throws NoSuchFieldException {
		try{
			Field field = obj.getClass().getDeclaredField(campo);
			if(field.isAnnotationPresent(XmlMapper.class)){
				XmlMapper xmlMapper = field.getAnnotation(XmlMapper.class);
				return xmlMapper.value();
			}
		}catch (NoSuchFieldException e){}

		return null;
	}
	
	private void xmlReader(File file) throws IOException, ParserConfigurationException, SAXException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);

		doc.getDocumentElement().normalize();
		NodeList nodeList = doc.getElementsByTagName("usuario");

		//Caminha pelo node principal
		for(int i = 0; i<nodeList.getLength(); i++){
			System.out.println(i + "********************");
			NodeList filhos = nodeList.item(i).getChildNodes();
			for(int j = 0; j < filhos.getLength(); j++){
				Node filho = filhos.item(j);
				if (filho != null && filho.getNodeType() == Node.ELEMENT_NODE) {
					String value = ((Element) nodeList.item(i)).getElementsByTagName(filho.getNodeName()).item(0).getTextContent();
					System.out.println(filho.getNodeName() + " : " +value);
				}
			}
		}
	}
}
