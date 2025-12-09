package br.com.ramonmluz.moviehub.stubs

import br.com.ramonmluz.moviehub.data.model.Movie

object MovieStub {
    fun getMovieStub() = Movie(
        id = 1309012,
        overview = "Num presente alternativo" +
        "humanos geneticamente aprimorados dominam a sociedade." +
        "Os párias Leon e Chloe lutam por justiça" +
        "contra políticos corruptos que exploram a disparidade genética,"+
        "arriscando tudo para desafiar o sistema opressor",
        posterPath = "/6QlAcGRaUrgHcZ4WTBh5lsPnzKx.jpg",
        originalTitle = "Altered",
        voteAverage = 6.471F,
        releaseDate = "2025-09-18",
        backdropPath = "/zEsHEpCGZwGg3M2b0oSZuaPLwBh.jpg")
}