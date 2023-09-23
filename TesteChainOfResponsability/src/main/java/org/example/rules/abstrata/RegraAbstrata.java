package org.example.rules.abstrata;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

public abstract class RegraAbstrata {
    private RegraAbstrata proxima;

    public static RegraAbstrata link(RegraAbstrata first, List<RegraAbstrata> chain) {
        RegraAbstrata head = first;
        for (RegraAbstrata nextInChain: chain) {
            head.proxima = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract boolean check(String password);
    protected boolean checkNext(String password) {
        if (proxima == null) {
            System.out.println("Tudo Oskay");
            return true;
        }
        return proxima.check(password);
    }
}
