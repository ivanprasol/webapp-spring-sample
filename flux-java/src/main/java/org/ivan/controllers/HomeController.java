package org.ivan.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class HomeController {
    private Logger log = LogManager.getLogger();

    @GetMapping
    public String displayUi(final Locale locale, final Model model) {
        log.info("Starting the UI...");

        model.addAttribute("appLanguage", "Java");
        model.addAttribute("locale", locale);

        return "ui";
    }
}
