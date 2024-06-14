package br.com.alura.screenmatchv2.principal;

import br.com.alura.screenmatchv2.model.DadosEpisodio;
import br.com.alura.screenmatchv2.model.DadosSerie;
import br.com.alura.screenmatchv2.model.DadosTemporada;
import br.com.alura.screenmatchv2.model.Episodio;
import br.com.alura.screenmatchv2.service.ConsumoApi;
import br.com.alura.screenmatchv2.service.ConverteDados;
import org.springframework.cglib.core.Local;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=605c3c70";

    public void exibeMenu() {
        System.out.println("Digite o nome da série para busca:");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumo.obterDados("https://www.omdbapi.com/?t=" + nomeSerie.replace(" ","+") + "&season=" + i + "&apikey=605c3c70");
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
        List<DadosEpisodio> dadosEpisodios= temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                        ).collect(Collectors.toList());


        episodios.forEach(System.out::println);

        System.out.println("Você quer buscar os episódios lançados a partir de qual ano?");
        var ano = leitura.nextInt();
        leitura.nextLine();


        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getAnoLancamento() != null && e.getAnoLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                        " Episódio:  " + e.getTitulo() +
                        " Data de lançamento: " + e.getAnoLancamento().format(formatador)
                ));
    }
}
