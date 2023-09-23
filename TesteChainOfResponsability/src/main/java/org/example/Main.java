package org.example;

import org.example.rules.Regra1;
import org.example.rules.abstrata.RegraAbstrata;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //Pega todas as classes dentro de um package específico
        List<RegraAbstrata> regras = retornaRegras("org.example.rules", RegraAbstrata.class);

        //retira a primeira regra
        //Para senhas, não faz sentido validar o resto se a próprias estiver vazia ou nula
        //Por isso ela deve ser a primeira
        //Se não tiver uma ordem, poderia criar uma implementação passando somente a lista
         regras = regras.stream()
                .filter(s -> !s.getClass().isInstance(new Regra1()))
                .collect(Collectors.toList());

        //Passando a cadeia de responsabilidade.
        RegraAbstrata regraAbstrata = RegraAbstrata.link(new Regra1(), regras);

        //Executando a validação da senha.
        regraAbstrata.check(null);
        regraAbstrata.check("");
        regraAbstrata.check("   ");
        System.out.println();
        regraAbstrata.check("12345678910");
        regraAbstrata.check("234234A");
        regraAbstrata.check("12345");
    }

    private static <R> List<R> retornaRegras(String packageName, Class clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<R> regras = null;
        List<Class> classes = findAllClasses(packageName);

        if(classes != null || classes.size() != 0) {
            regras = new ArrayList<>();

            for(Class classe: classes){
                Object obj = classe.getConstructor().newInstance();
                if(clazz.isInstance(obj)){
                    regras.add((R) obj);
                }
            }
        }
        return regras;
    }

    public static List<Class> findAllClasses(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class)
                .stream()
                .filter(c -> c.getPackageName().equals(packageName))
                .collect(Collectors.toList());
    }
}