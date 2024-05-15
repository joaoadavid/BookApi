package io.github.joaoadavid.API.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class InternacionalizacaoConfig {

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource //é instanciado para ler o arquivo message
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");//caminho de onde vem os textos
        messageSource.setDefaultEncoding("ISO-8859-1");//é o tipo de codificação do texto
        messageSource.setDefaultLocale(Locale.getDefault());//pega a localização da aplicação entendendo o idioma da mensagem
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(){ //responsavel por substituir a expressão para a mensagem do arquivo
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
