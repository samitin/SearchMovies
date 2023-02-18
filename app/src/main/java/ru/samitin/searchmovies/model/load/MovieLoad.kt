package ru.samitin.searchmovies.model.load

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import ru.samitin.searchmovies.model.data.description.DescriptionMovieDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


class MovieLoad(private val movieLoadListener: MovieLoadListener,private val id:String ="tt1375666") {
    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMovie(){
        try {
            val uri = URL("https://imdb-api.com/ru/API/Title/k_lg25y9g2/$id")
            val handler = Handler()
            Thread{
                lateinit var urlConnection : HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10_000

                    val bufferedReader =BufferedReader(InputStreamReader(urlConnection.inputStream))
                    // преобразование ответа от сервера (JSON) в модель данных (DescriptionMovieDTO)
                    val descriptionMovieDTO : DescriptionMovieDTO =
                        Gson().fromJson(getLines(bufferedReader),DescriptionMovieDTO::class.java)
                    handler.post { movieLoadListener.onLoaded(descriptionMovieDTO) }
                }catch (e: Exception){
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    movieLoadListener.onFailed(e)
                }
            }.start()


        } catch (e: MalformedURLException) {
        Log.e("", "Fail URI", e)
        e.printStackTrace()
        movieLoadListener.onFailed(e)
    }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader):String{
        return reader.lines().collect(Collectors.joining("\n"))
    }

    interface MovieLoadListener{
        fun onLoaded(descriptionMovie: DescriptionMovieDTO)
        fun onFailed(error:Throwable)
    }
}