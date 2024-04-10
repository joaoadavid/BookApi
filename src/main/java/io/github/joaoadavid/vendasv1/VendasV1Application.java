package io.github.joaoadavid.vendasv1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class VendasV1Application {

   @Cachorro
    private Animal animal;

    @Bean(name = "executarAnimal")
    public CommandLineRunner executar() {
        return args -> {
            this.animal.fazerBarulho();
        };
    }

   @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/hello")//mapeia o mapping para ao acessar a url retorna essa mensagem
    public String hellorWorld(){
        return applicationName;
    }
    public static void main(String[] args) {
        SpringApplication.run(VendasV1Application.class, args);
    }

}
