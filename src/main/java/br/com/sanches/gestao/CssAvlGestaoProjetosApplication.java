package br.com.sanches.gestao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration 
@ComponentScan(basePackages = {
		"br.com.sanches.gestao"
})
public class CssAvlGestaoProjetosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CssAvlGestaoProjetosApplication.class, args);
	}

}
