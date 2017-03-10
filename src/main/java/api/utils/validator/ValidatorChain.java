package api.utils.validator;

import api.utils.validator.generic.Validator;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public final class ValidatorChain {

    public static boolean isValid(final Object json, final boolean strict, final ApplicationContext appContext) {

        for (Method getter: json.getClass().getMethods()) {
            if (!getter.isAnnotationPresent(FieldValidates.class)) {
                continue;
            }

            final FieldValidates validates = getter.getAnnotation(FieldValidates.class);

            for (String name: validates.validators()) {

                if (name.isEmpty()) {
                    continue;
                }

                final Validator validator = (Validator) appContext.getBean(name + "Validator");
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
