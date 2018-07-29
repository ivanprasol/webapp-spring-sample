package org.ivan.config

import org.springframework.web.server.adapter.AbstractReactiveWebInitializer

class InitializerWebApp : AbstractReactiveWebInitializer() {
    override fun getConfigClasses(): Array<Class<*>> = arrayOf(ConfigWeb::class.java)
}