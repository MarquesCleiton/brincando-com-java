package br.com.marquescleiton.xmlconverter.xmlmapper;

import br.com.marquescleiton.xmlconverter.annotation.XmlMapper;

public class User {

    @XmlMapper("nomusu")
    private java.lang.CharSequence name;

    @XmlMapper("numfav")
    private java.lang.Integer favorite_number;

    @XmlMapper("corfav")
    private java.lang.CharSequence favorite_color;
}
