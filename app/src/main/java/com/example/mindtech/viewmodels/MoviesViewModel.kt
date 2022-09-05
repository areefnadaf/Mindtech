package com.example.mindtech.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mindtech.datamodels.Movie
import com.example.mindtech.datamodels.MovieCatogories
import com.example.mindtech.datamodels.MoviesDataModel
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream


class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    var mutableLiveData: MutableLiveData<ArrayList<Movie>> = MutableLiveData()
    var catmutableLiveData: MutableLiveData<List<String>> = MutableLiveData()
    var application1 = application

    fun getMovies(position: Int) {
        var jsonFile = when (position) {
            0 -> "Comedy"
            1 -> "Crime"
            2 -> "Animation"
            3 -> "Action"
            4 -> "Romance"
            5 -> "Horror"
            else -> "Sport"
        }
        val todoItem: MoviesDataModel
        val gson = Gson()
        todoItem = gson.fromJson(
            readJSONFile("$jsonFile.json"),
            MoviesDataModel::class.java
        )
        mutableLiveData?.value = todoItem.movies

    }

    fun getMovieCategories() {
        val catItems: MovieCatogories
        val gson = Gson()
        catItems = gson.fromJson(
            readJSONFile("moviecategory.json"),
            MovieCatogories::class.java
        )
        println("Catogories  " + catItems.genres.size)
        catmutableLiveData.value = catItems.genres
    }

    fun readJSONFile(jsonFile: String): String {
        var jsonString = ""
        try {
            val inputStream: InputStream = application1.assets.open(jsonFile)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)

            jsonString = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return jsonString
    }
}