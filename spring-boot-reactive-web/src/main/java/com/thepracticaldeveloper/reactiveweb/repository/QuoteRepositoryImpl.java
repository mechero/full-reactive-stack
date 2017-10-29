package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.Quote;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

@Repository
public class QuoteRepositoryImpl implements QuoteRepository {

    private ArrayList<Quote> quotes;

    public QuoteRepositoryImpl() {
        this.quotes = new ArrayList<>();
        this.quotes.add(new Quote("Q1", "El Quijote", "En un lugar de la Mancha, de cuyo nombre " +
                "no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero, " +
                "adarga antigua, rocín flaco y galgo corredor."));
        this.quotes.add(new Quote("Q2", "El Quijote", "En efecto, rematado ya su juicio, vino a dar " +
                "en el más extraño pensamiento que jamás dio loco en el mundo, y fue que le pareció convenible y " +
                "necesario, así para el aumento de su honra, como para el servicio de su república, hacerse caballero" +
                " andante, e irse por todo el mundo con sus armas y caballo a buscar las aventuras, y a ejercitarse " +
                "en todo aquello que él había leído, que los caballeros andantes se ejercitaban, deshaciendo todo" +
                " género de agravio, y poniéndose en ocasiones y peligros, donde acabándolos, cobrase " +
                "eterno nombre y fama."));
        this.quotes.add(new Quote("Q3", "El Quijote", "Limpias, pues, sus armas, hecho del morrión" +
                " celada, puesto nombre a su rocín, y confirmándose a sí mismo, se dió a entender que no le" +
                " faltaba otra cosa, sino buscar una dama de quien enamorarse, porque el caballero andante sin " +
                "amores, era árbol sin hojas y sin fruto, y cuerpo sin alma. Decíase él: si yo por malos de" +
                " mis pecados, por por mi buena suerte, me encuentro por ahí con algún gigante, " +
                "como de ordinario les acontece a los caballeros andantes [...]"));
        this.quotes.add(new Quote("Q4", "El Quijote", "¡Oh, cómo se holgó nuestro buen caballero," +
                " cuando hubo hecho este discurso, y más cuando halló a quién dar nombre de su dama! Y fue, a lo " +
                "que se cree, que en un lugar cerca del suyo había una moza labradora de muy buen parecer, " +
                "de quien él un tiempo anduvo enamorado, aunque según se entiende, ella jamás lo supo " +
                "ni se dió cata de ello."));
    }

    @Override
    public Flux<Quote> retrieveAllQuotes() {
        return Flux.fromIterable(quotes);
    }
}
