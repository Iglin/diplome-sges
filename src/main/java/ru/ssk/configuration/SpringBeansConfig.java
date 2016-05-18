package ru.ssk.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ssk.model.PhysicalPerson;
import ru.ssk.service.AddressService;
import ru.ssk.service.OwnerService;
import ru.ssk.service.PhysicalPersonService;
import ru.ssk.service.impl.AddressServiceImpl;
import ru.ssk.service.impl.OwnerServiceImpl;
import ru.ssk.service.impl.PhysicalPersonServiceImpl;

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

    @Bean
    public PhysicalPersonService personService() {
        return new PhysicalPersonServiceImpl();
    }
}
