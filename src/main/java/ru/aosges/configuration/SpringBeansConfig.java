package ru.aosges.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.aosges.service.OwnerService;
import ru.aosges.service.impl.OwnerServiceImpl;

/**
 * Created by user on 29.03.2016.
 */
@Configuration
public class SpringBeansConfig {
    @Bean
    public OwnerService ownerService() {
        return new OwnerServiceImpl();
    }
}
