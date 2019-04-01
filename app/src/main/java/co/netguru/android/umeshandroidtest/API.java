package co.netguru.android.umeshandroidtest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @POST("json/movies.json")
    Call<List<MovieModel>> getMovieList();
}
