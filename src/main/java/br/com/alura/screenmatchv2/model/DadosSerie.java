package br.com.alura.screenmatchv2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias ("Title") String titulo,
                         @JsonAlias ("totalSeasons") Integer totalTemporadas,
                         @JsonAlias ("imdbRating") String avalicao) {

    @Override
    public String toString() {
        return "Título: " + titulo + ". Total de temporadas: " + totalTemporadas + ". Avaliação: " + avalicao;
    }
}
