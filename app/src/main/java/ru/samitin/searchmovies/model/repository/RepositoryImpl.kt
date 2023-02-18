package ru.samitin.searchmovies.model.repository


import android.os.Build
import androidx.annotation.RequiresApi
import ru.samitin.searchmovies.entities.Category
import ru.samitin.searchmovies.entities.Movie
import ru.samitin.searchmovies.model.data.description.DescriptionMovieDTO
import ru.samitin.searchmovies.model.load.MovieLoad
import ru.samitin.searchmovies.utils.mapDescriptionMovieDTOtoMovie


class RepositoryImpl : Repository {
    private var movies = listOf<Movie>(
        Movie("Космический джем: Новое поколение","0","78","08 июль 2021",
        "Чтобы спасти сына, знаменитый чемпион НБА отправляется в сказочный мир," +
                " где в команде мультяшек вынужден сражаться на баскетбольной площадке с " +
                "цифровыми копиями знаменитых игроков."),

        Movie("Чёрная вдова","1","79","07 июл 2021",
            "Наташе Романофф предстоит лицом к лицу встретиться со своим прошлым. Чёрной Вдове придется вспомнить о том," +
                    " что было в её жизни задолго до присоединения к команде Мстителей, и узнать об опасном заговоре," +
                    " в который оказываются втянуты её старые знакомые — Елена, Алексей (известный как Красный Страж) и Мелина."),

        Movie("Судная ночь навсегда","2","78","30 июн 2021",
            "Этим летом все правила будут нарушены. Группа мародеров решает," +
                    " что ежегодная Судная ночь не должна заканчиваться с наступлением утра, " +
                    "а может продолжаться бесконечно. Никто больше не будет в безопасности."),
        Movie("Босс-молокосос 2","3","79","01 июл 2021",
            "Продолжение приключений героев мультфильма, с которыми зрители познакомились в предыдущей части \"Босс-молокосос\" 2017 года."),
        Movie("Война будущего","4","82","30 июн 2021",
            "В будущем идёт разрушительный конфликт с инопланетной расой." +
                    " В попытке переломить ход войны учёные начинают призывать в свою армию солдат из прошлого.")
    )
    private var categories = listOf(
        Category("Боевики", movies),
        Category("Комедии", movies),
        Category("Фантастика", movies),
        Category("Ужасы", movies),
        Category("Мультфильмы", movies)
    )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getMovieDescriptionFromServer(id: String, loaded:(movie:Movie)->Unit, failed:(err:Throwable)->Unit) {
        MovieLoad(object : MovieLoad.MovieLoadListener{
            override fun onLoaded(descriptionMovie: DescriptionMovieDTO) {
                loaded(mapDescriptionMovieDTOtoMovie(descriptionMovie))
            }

            override fun onFailed(error: Throwable) {
                failed(error)
            }

        },id).loadMovie()
    }

    override fun getMoviesFromLocalStorage() = movies
    override fun getMoviesFromServer() = movies
}