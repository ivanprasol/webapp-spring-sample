package org.ivan.controllers

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.util.*

@Controller
open class HomeController {
    private val log = LogManager.getLogger()

    @GetMapping
    open fun displayUi(locale: Locale, model: Model) : String {
        log.info("Starting the UI (locale={})...", locale);

        model.addAttribute("appLanguage", "Kotlin");
        model.addAttribute("locale", locale);

        return "ui";
    }
}