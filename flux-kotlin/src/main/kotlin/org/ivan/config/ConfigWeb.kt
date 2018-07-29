package org.ivan.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.core.env.Environment
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.ViewResolverRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.resource.CssLinkResourceTransformer
import org.springframework.web.reactive.resource.VersionResourceResolver
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver
import org.thymeleaf.templatemode.TemplateMode

@Configuration
@EnableWebFlux
@ComponentScan(basePackages=["org.ivan"])
open class ConfigWeb : WebFluxConfigurer {
    @Autowired
    lateinit var env : Environment

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        val cacheResourses = !this.env.acceptsProfiles("dev");
        val versionResourceResolver = VersionResourceResolver().addContentVersionStrategy("/**");

        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/")
                .resourceChain(cacheResourses)
                .addResolver(versionResourceResolver)
                .addTransformer(CssLinkResourceTransformer());

        registry.addResourceHandler("/*.js").addResourceLocations("/")
                .resourceChain(cacheResourses)
                .addResolver(versionResourceResolver);
    }

    override fun configureViewResolvers(registry: ViewResolverRegistry) {
        registry.viewResolver(this.viewResolver())
    }

    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        val objectMapper = this.objectMapper()

        configurer.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper));
        configurer.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper));
    }

    @Bean
    open fun templateResolver() : SpringResourceTemplateResolver {
        val templateResolver = SpringResourceTemplateResolver();
        templateResolver.prefix = "/templates/";
        templateResolver.suffix = ".html";
        templateResolver.templateMode = TemplateMode.HTML;
        templateResolver.isCacheable = true;

        return templateResolver;

    }

    @Bean
    open fun templateEngine() : SpringWebFluxTemplateEngine {
        val templateEngine = SpringWebFluxTemplateEngine()
        templateEngine.setTemplateResolver(this.templateResolver())
        templateEngine.enableSpringELCompiler = true
        return templateEngine
    }

    @Bean
    open fun viewResolver() : ThymeleafReactiveViewResolver {
        val viewResolver = ThymeleafReactiveViewResolver()
        viewResolver.templateEngine = this.templateEngine();
//        viewResolver.setViewNames(new String[] {".html"});
        return viewResolver;
    }

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