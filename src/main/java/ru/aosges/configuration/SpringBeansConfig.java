package ru.aosges.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by user on 10.03.2016.
 */

@Configuration
@ComponentScan(basePackages = { "ru.aosges.dao" })
public class SpringBeansConfig {

    //@Bean
    //public ElectricMeterDAO typeValidator() { return new TypeValidator(); }
}
