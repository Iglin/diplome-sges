package ru.ssk.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ssk.model.LegalEntity;
import ru.ssk.model.PhysicalPerson;
import ru.ssk.service.*;
import ru.ssk.service.impl.*;

/**
 * Created by user on 29.03.2016.
 */
@Configuration
public class SpringBeansConfig {
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

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

    @Bean
    public PassportService passportService() {
        return new PassportServiceImpl();
    }

    @Bean
    public LegalEntityService legalEntityService() {
        return new LegalEntityServiceImpl();
    }

    @Bean
    public MeterModelService meterModelService() {
        return new MeterModelServiceImpl();
    }

    @Bean
    public MeterService meterService() {
        return new MeterServiceImpl();
    }

    @Bean
    public EnterpriseService enterpriseService() {
        return new EnterpriseServiceImpl();
    }

    @Bean
    public MeteringPointService meteringPointService() {
        return new MeteringPointServiceImpl();
    }
}
