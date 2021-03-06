package com.bxy.indexmaker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("com.bxy.indexmaker") // Enable component-scanning
@PropertySource(
        value = {"classpath:resources.properties"},
        ignoreResourceNotFound = true)
public class PropertyPlaceholderConfiguration {

    // TODO fix and use this
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
