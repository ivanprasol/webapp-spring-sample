package org.ivan.config

import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer
import javax.servlet.Filter

class InitializerWebApp : AbstractAnnotationConfigDispatcherServletInitializer() {
    override fun getRootConfigClasses(): Array<Class<*>>? = arrayOf(ConfigApp::class.java);

    override fun getServletConfigClasses(): Array<Class<*>>? = arrayOf(ConfigWeb::class.java);

    override fun getServletMappings(): Array<String> = arrayOf("/")

    override fun getServletFilters(): Array<Filter>? = arrayOf(
            ResourceUrlEncodingFilter()
    )
}