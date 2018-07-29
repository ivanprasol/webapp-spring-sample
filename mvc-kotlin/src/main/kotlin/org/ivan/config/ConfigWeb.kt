package org.ivan.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.core.env.Environment
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.stereotype.Controller
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.resource.CssLinkResourceTransformer
import org.springframework.web.servlet.resource.VersionResourceResolver

@Configuration
@EnableWebMvc
@ComponentScan(basePackages=["org.ivan.controllers"], useDefaultFilters=false,
        includeFilters = [ComponentScan.Filter(value = [Controller::class], type = FilterType.ANNOTATION)])
open class ConfigWeb : WebMvcConfigurer {
    @Autowired
    lateinit var env : Environment
    @Autowired
    lateinit var objectMapper : ObjectMapper

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        val cacheResources = !this.env.acceptsProfiles("dev");
        val cachePeriod = 31556926;
        val versionResourceResolver = VersionResourceResolver().addContentVersionStrategy("/**");

        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/")
                .setCachePeriod(cachePeriod)
                .resourceChain(cacheResources)
                .addResolver(versionResourceResolver)
                .addTransformer(CssLinkResourceTransformer());

        registry.addResourceHandler("/*.js").addResourceLocations("/")
                .setCachePeriod(cachePeriod)
                .resourceChain(cacheResources)
                .addResolver(versionResourceResolver);
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(MappingJackson2HttpMessageConverter(this.objectMapper))
    }

    override fun configureViewResolvers(registry: ViewResolverRegistry) {
        registry.jsp("/WEB-INF/jsp/", ".jsp");
    }
}