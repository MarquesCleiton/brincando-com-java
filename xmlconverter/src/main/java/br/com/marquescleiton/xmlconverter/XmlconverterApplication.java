package br.com.marquescleiton.xmlconverter;

import br.com.marquescleiton.xmlconverter.avro.user.UserAvro;
import br.com.marquescleiton.xmlconverter.avroconverter.XmlUtil;
import br.com.marquescleiton.xmlconverter.avroconverter.XmlUtil2;
import br.com.marquescleiton.xmlconverter.xmlmapper.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.NodeList;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class XmlconverterApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(XmlconverterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		File currentDirFile = new File("");

		//String path = currentDirFile.getAbsolutePath()+"\\src\\main\\resources\\xml-files";
		String path = "C:\\Users\\Cleiton\\Desktop\\xml";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		List<UserAvro> usuarios = new ArrayList<>();

		XmlUtil xmlUtil = new XmlUtil();

//		for (File file : listOfFiles) {
//			NodeList nodeList = xmlUtil.getAllNodesFromFile(file, "usuario");
//			for(int i = 0; i < nodeList.getLength(); i++){
//				UserAvro avro = (UserAvro) xmlUtil.convertXmltoAvro(nodeList.item(i), new User(), new UserAvro());
//				System.out.println(avro.getName() + "|"+ avro.getFavoriteColor()+"|"+avro.getFavoriteNumber());
//			}
//		}

		XmlUtil2 xmlUtil2 = new XmlUtil2();
		for (File file : listOfFiles) {
			String[] nodes = xmlUtil2.getAllNodesFromXml(file, "usuario");
			for(String node : nodes){
				UserAvro avro = (UserAvro) xmlUtil2.convertXmltoAvro(UserAvro.class, node, User.class);
				usuarios.add(avro);
			}
		}

		for(UserAvro usuario : usuarios){
			System.out.println(usuario.getName());
			System.out.println(usuario.getFavoriteNumber());
			System.out.println(usuario.getFavoriteColor());
		}
	}

	private void exibeNodesString(String[]nodes){
		for(String node : nodes){
			System.out.println(node);
		}
	}
}
