package org.ivan.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class HomeController {
    private final Logger log = LogManager.getLogger();

    @GetMapping("/")
    public ModelAndView displayUi(Locale locale) {
        log.info("Starting the UI...");

        var mv = new ModelAndView("ui");

        mv.addObject("appLanguage", "Java");
        mv.addObject("locale", locale);

        return mv;
    }
}
