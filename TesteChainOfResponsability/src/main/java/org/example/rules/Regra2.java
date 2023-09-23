package org.example.rules;

import org.example.rules.abstrata.RegraAbstrata;

public class Regra2 extends RegraAbstrata {
    @Override
    public boolean check(String password) {

        if(password.length() > 10){
            System.out.println("Senha muito longa");
            return false;
        }

        return checkNext(password);
    }
}
