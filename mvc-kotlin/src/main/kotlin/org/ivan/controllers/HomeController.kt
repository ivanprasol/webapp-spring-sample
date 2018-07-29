package org.ivan.controllers

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
class HomeController {
    private val log = LogManager.getLogger()

    @GetMapping("/")
    fun displayUi(locale: Locale) : ModelAndView {
        log.info("Starting the UI...");

        val mv = ModelAndView("ui");
        mv.addObject("appLanguage", "Kotlin");
        mv.addObject("locale", locale);

        return mv;
    }
}