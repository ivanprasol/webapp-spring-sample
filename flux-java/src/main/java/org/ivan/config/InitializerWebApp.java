package org.ivan.config;

import org.springframework.web.server.adapter.AbstractReactiveWebInitializer;

public class InitializerWebApp extends AbstractReactiveWebInitializer {
    @Override
    protected Class<?>[] getConfigClasses() {
        return new Class[] { ConfigWeb.class };
    }
}
