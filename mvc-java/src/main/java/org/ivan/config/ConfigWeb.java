package org.ivan.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.util.List;

@Configuration(proxyBeanMethods = false)
@EnableWebMvc
@ComponentScan(basePackages="org.ivan.controllers", useDefaultFilters=false,
        includeFilters = @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION))
public class ConfigWeb implements WebMvcConfigurer {
    private final Environment env;
    private final ObjectMapper objectMapper;

    public ConfigWeb(Environment env, ObjectMapper objectMapper) {
        this.env = env;
        this.objectMapper = objectMapper;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        var cacheResources = !this.env.acceptsProfiles(Profiles.of("dev"));
        var cachePeriod = 31556926;
        var versionResourceResolver = new VersionResourceResolver().addContentVersionStrategy("/**");

        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/")
                .setCachePeriod(cachePeriod)
                .resourceChain(cacheResources)
                .addResolver(versionResourceResolver)
                .addTransformer(new CssLinkResourceTransformer());

        registry.addResourceHandler("/*.js").addResourceLocations("/")
                .setCachePeriod(cachePeriod)
                .resourceChain(cacheResources)
                .addResolver(versionResourceResolver);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(this.objectMapper));
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/jsp/", ".jsp");
    }

//    @Bean
//    public LocaleResolver localeResolver() {
//        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
//        localeResolver.setCookieName("lang");
//        return localeResolver;
//    }
}
