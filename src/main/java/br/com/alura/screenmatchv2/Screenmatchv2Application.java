package br.com.alura.screenmatchv2;

import br.com.alura.screenmatchv2.model.DadosTemporada;
import br.com.alura.screenmatchv2.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Screenmatchv2Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Screenmatchv2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}

