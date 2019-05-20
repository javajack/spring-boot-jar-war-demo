package com.sumhr.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Slf4j
@Configuration
public class Config {

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StringTrimModule stringTrimModule;

    @PostConstruct
    public void postConstruct() {
        objectMapper.registerModule(stringTrimModule);
    }

    @Bean
    public Docket productApi(final ApplicationContext applicationContext) {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.sumhr"))
                .paths(PathSelectors.regex("/.*")).build().useDefaultResponseMessages(false)
                .tags(new Tag("000-auth", ""), new Tag("001-account", ""), new Tag("demo", ""),
                        new Tag("999-util", ""));
    }

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(false);
        filter.setIncludeHeaders(true);
        return filter;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {

        log.info("~  app-ready-active-profiles : {} ~", Arrays.asList(environment.getActiveProfiles()));
    }

}
