package br.com.alura.screenmatchv2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String titulo,
                            @JsonAlias("Episode") Integer numero,
                            @JsonAlias("imdbRating") String avaliacao,
                            @JsonAlias("Released") String anoLancamento) {
    @Override
    public String toString() {
        return "Título: " + titulo +
                ". Episódio: " + numero +
                ". Avaliação: " + avaliacao +
                ". Data de lançamento: " + anoLancamento + ".";
    }
}
