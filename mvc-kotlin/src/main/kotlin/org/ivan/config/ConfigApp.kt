package org.ivan.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.stereotype.Controller

@Configuration
@ComponentScan(basePackages= ["org.ivan"],
        excludeFilters = [
            ComponentScan.Filter(value = [Controller::class], type = FilterType.ANNOTATION),
            ComponentScan.Filter(value = [Configuration::class], type = FilterType.ANNOTATION)
        ])
open class ConfigApp {
    @Bean
    open fun objectMapper() : ObjectMapper = Jackson2ObjectMapperBuilder()
            .findModulesViaServiceLoader(true)
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build()

    @Bean
    open fun messageSource() : MessageSource {
        val bundle = ReloadableResourceBundleMessageSource();
        bundle.setBasenames("i18n/messages");
        bundle.setDefaultEncoding("UTF-8");
        bundle.setFallbackToSystemLocale(false);
        bundle.setUseCodeAsDefaultMessage(true);
        return bundle;
    }
}