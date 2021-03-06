package org.ivan.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages="org.ivan",
        excludeFilters = {
                @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION),
                @ComponentScan.Filter(value = Configuration.class, type = FilterType.ANNOTATION)
        })
public class ConfigApp {
    @Bean
    public ObjectMapper objectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .findModulesViaServiceLoader(true)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
        bundle.setBasenames("i18n/messages");
        bundle.setDefaultEncoding("UTF-8");
        bundle.setFallbackToSystemLocale(false);
        bundle.setUseCodeAsDefaultMessage(true);
        return bundle;
    }
}
