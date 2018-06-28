package com.bxy.indexmaker.configuration;

import org.springframework.beans.factory.annotation.Value;
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

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public XlsFileAddress getXlsFileAddress() {
        return XlsFileAddress.builder()
                .setXlsFilePath("${excel.file.path.windows}")
                .setXlsFileName("${excel.file.name.test}")
                .build();
    }


}
