import br.com.marquescleiton.xmlconverter.xmlavromapper.User;
import java.lang.reflect.Field;

public class Teste {
    public static void main(String...args){
        User user = new User();
        showAnyObjectFields(user);
    }

    private static void showAnyObjectFields(Object obj){
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields){
            System.out.println(field.getName());
        }
    }
}
