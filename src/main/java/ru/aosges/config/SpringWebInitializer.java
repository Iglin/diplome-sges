package ru.aosges.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by user on 10.03.2016.
 */
@EnableWebMvc
@Configuration
@ComponentScan(value = "ru.aosges.controller")
public class SpringWebInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() { return new Class[]{ SpringSecurityConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
                SpringWebConfig.class,
                SpringBeansConfig.class,
                PersistenceJPAConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() { return new String[]{ "/" }; }
}
