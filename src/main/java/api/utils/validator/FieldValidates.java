package api.utils.validator;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FieldValidates {

    String name();
    String[] validators() default "";

}
