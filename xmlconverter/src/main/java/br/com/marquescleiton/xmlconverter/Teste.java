package br.com.marquescleiton.xmlconverter;

public class Teste {

    public static void main(String[]args){
        String a = "Meu coração por te bate, como um caroço de abacate";
        int indexOne = a.indexOf("um");
        int indexTwo = a.indexOf("de");

        String b = a.substring(indexOne,indexTwo);

        System.out.println(b);
        System.out.println(b.length());
    }

}
