package org.example.rules;

import org.example.rules.abstrata.RegraAbstrata;

public class Regra1 extends RegraAbstrata {
    @Override
    public boolean check(String password) {
        if(password == null || password.isEmpty() || password.isBlank()){
            System.out.println("Senha vazia ou nula");
            return false;
        }
        return checkNext(password);
    }
}
