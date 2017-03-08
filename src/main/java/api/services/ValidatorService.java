package api.services;


import api.utils.validator.Validates;
import api.utils.validator.Validator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class ValidatorService {

    private final Map<String, Validator> validators = new ConcurrentHashMap<>();


    /**
     * Тут мы ищем все классы, у которых есть аннотация @Validates
     * // TODO: Измените basePackage у findCandidateComponents при изменении путки к package аннотаций!
     */
    ValidatorService() {
        final ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(Validates.class));

        for (BeanDefinition bd: scanner.findCandidateComponents("src.main.java.api.validators")) {
            System.out.println(bd.getBeanClassName());
        }
    }

}
