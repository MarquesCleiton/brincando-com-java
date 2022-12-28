package br.com.marquescleiton.xmlconverter.newer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Faz a leitura de um arquivo XML e retorna suas propriedades
 */
public class XmlExtractor{

    /**
     *
     * @param file Arquivo XML
     * @param xmlField Campo que deseja extrair
     * @return Retorna uma lista com todas as ramificações do campo selecionado
     * @throws IOException
     */
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
     * @see
     */
    public String getValueFromTag(String xmlNode, String tag){
        int indexOne = xmlNode.indexOf("<"+tag+">");
        int indexTwo = xmlNode.indexOf("</"+tag+">");

        if(indexOne == -1 || indexTwo == -1 )
            return null;
        indexOne = indexOne + tag.length() + 2;

        return xmlNode.substring(indexOne, indexTwo);
    }
}
