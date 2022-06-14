package com.github.enid3.questionnaire.conf;

import com.github.enid3.questionnaire.QuestionnaireApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.util.Locale;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAspectJAutoProxy
@ComponentScan(basePackageClasses = QuestionnaireApplication.class)
public class RootConfig  {
    @Bean
    public Locale errorLocale() {
        return Locale.ENGLISH;
    }
}
