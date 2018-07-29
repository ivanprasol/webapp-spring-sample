module org.ivan.flux.java {
    requires spring.beans;
    requires spring.context;
    requires spring.core;
    requires spring.web;
    requires spring.webflux;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires org.apache.logging.log4j;
    requires reactor.core;
    requires javax.servlet.api;
    requires thymeleaf;
    requires thymeleaf.spring5;
}