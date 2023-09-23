package org.example.rules;

import org.example.rules.abstrata.RegraAbstrata;

public class Regra3 extends RegraAbstrata {
    @Override
    public boolean check(String password) {

        if(!password.matches("^[0-9]+")){
            System.out.println("Não pode conter letras");
            return false;
        }
        return checkNext(password);
    }
}
