package br.com.marquescleiton.xmlconverter.avroconverter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public abstract class XmlConverter {

    protected void setReflectionField(Object object, Field field, String value) throws IllegalAccessException {
        if(value != null && !value.isEmpty()){
            if(field.getType().equals(String.class) || field.getType().equals(CharSequence.class)) {
                field.set(object, value);
            }else if(field.getType().equals(Integer.class)){
                Integer var = Integer.valueOf(value);
                field.set(object, var);
            }else if(field.getType().equals(Short.class)){
                Short var = Short.valueOf(value);
                field.set(object, var);
            }else if(field.getType().equals(Double.class)){
                Double var = Double.valueOf(value);
                field.set(object, var);
            }else if(field.getType().equals(Float.class)){
                Float var = Float.valueOf(value);
                field.set(object, var);
            }
        }
    }

    protected Object newInstance(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = clazz.getConstructor();
        return constructor.newInstance();
    }
}
