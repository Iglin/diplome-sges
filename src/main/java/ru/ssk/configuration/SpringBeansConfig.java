package ru.ssk.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ssk.service.AddressService;
import ru.ssk.service.OwnerService;
import ru.ssk.service.impl.AddressServiceImpl;
import ru.ssk.service.impl.OwnerServiceImpl;

/**
 * Created by user on 29.03.2016.
 */
@Configuration
public class SpringBeansConfig {
    @Bean
    public OwnerService ownerService() {
        return new OwnerServiceImpl();
    }

    @Bean
    public AddressService addressService() {
        return new AddressServiceImpl();
    }
}
