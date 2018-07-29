package org.ivan.config;

import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class InitializerWebApp extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override protected Class<?>[] getRootConfigClasses() {
        return new Class[] { ConfigApp.class };
    }

    @Override protected Class<?>[] getServletConfigClasses() {
        return new Class[] { ConfigWeb.class };
    }

    @Override protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] {
                new ResourceUrlEncodingFilter()
//            new HiddenHttpMethodFilter(),
//            new CharacterEncodingFilter()
        };
    }
}
