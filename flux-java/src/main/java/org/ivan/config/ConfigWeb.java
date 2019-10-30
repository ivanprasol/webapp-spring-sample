package org.ivan.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.resource.CssLinkResourceTransformer;
import org.springframework.web.reactive.resource.VersionResourceResolver;
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@EnableWebFlux
@ComponentScan(basePackages="org.ivan")
public class ConfigWeb implements WebFluxConfigurer {
    private final Environment env;

    public ConfigWeb(Environment env) {
        this.env = env;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        var cacheResourses = !this.env.acceptsProfiles(Profiles.of("dev"));
        var versionResourceResolver = new VersionResourceResolver().addContentVersionStrategy("/**");

        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/")
                .resourceChain(cacheResourses)
                .addResolver(versionResourceResolver)
                .addTransformer(new CssLinkResourceTransformer());

        registry.addResourceHandler("/*.js").addResourceLocations("/")
                .resourceChain(cacheResourses)
                .addResolver(versionResourceResolver);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(this.viewResolver());
    }

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        var objectMapper = this.objectMapper();

        configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
        configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        var templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);

        return templateResolver;
    }

    @Bean
    public SpringWebFluxTemplateEngine templateEngine(){
        var templateEngine = new SpringWebFluxTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean
    public ThymeleafReactiveViewResolver viewResolver(){
        var viewResolver = new ThymeleafReactiveViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
//        viewResolver.setViewNames(new String[] {".html"});
        return viewResolver;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .findModulesViaServiceLoader(true)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();
    }

    @Bean
    public MessageSource messageSource() {
        var bundle = new ReloadableResourceBundleMessageSource();
        bundle.setBasenames("i18n/messages");
        bundle.setDefaultEncoding("UTF-8");
        bundle.setFallbackToSystemLocale(false);
        bundle.setUseCodeAsDefaultMessage(true);
        return bundle;
    }
}
