package br.com.marquescleiton.xmlconverter;

import br.com.marquescleiton.xmlconverter.avro.user.UserAvro;
import br.com.marquescleiton.xmlconverter.avroconverter.XmlUtil;
import br.com.marquescleiton.xmlconverter.newer.XmlExtractor;
import br.com.marquescleiton.xmlconverter.newer.XmlModelMapper;
import br.com.marquescleiton.xmlconverter.xmlmapper.User;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
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

		String path = currentDirFile.getAbsolutePath() + "\\src\\main\\resources\\xml-files";

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		List<UserAvro> usuarios = new ArrayList<>();

		XmlUtil xmlUtil = new XmlUtil();

		ModelMapper modelMapper = new ModelMapper();

		XmlModelMapper xmlModelMapper = new XmlModelMapper();
		XmlExtractor xmlExtractor = new XmlExtractor();

		for (File file : listOfFiles) {
			String[] nodes = xmlExtractor.getAllNodesFromXml(file, "usuario");
			for(String node : nodes){
				UserAvro avro = xmlModelMapper.map(node, User.class, UserAvro.class);
				System.out.println(avro.getName());
			}
		}
	}

	private void exibeNodesString(String[]nodes){
		for(String node : nodes){
			System.out.println(node);
		}
	}
}
