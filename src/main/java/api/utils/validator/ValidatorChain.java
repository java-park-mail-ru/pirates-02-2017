package api.utils.validator;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public final class ValidatorChain {

    public static boolean isValid(final Object json, final boolean strict, final ApplicationContext appContext) {

        for (Method getter: json.getClass().getMethods()) {
            if (!getter.isAnnotationPresent(FieldValidates.class)) {
                continue;
            }

            FieldValidates validates = getter.getAnnotation(FieldValidates.class);

            for (String name: validates.validators()) {

                if (name.length() == 0) {
                    continue;
                }

                Validator validator = (Validator) appContext.getBean(name + "Validator");
                String key = validates.name();
                Object value = "";

                try {
                    value = getter.invoke(json);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                if (!validator.isValid(value.toString(), strict)) {
                    return false;
                }
            }
        }

        return true;
    }

}
