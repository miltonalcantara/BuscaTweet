package br.com.miltonalcantara.buscatweet.network;

import java.util.List;

import br.com.miltonalcantara.buscatweet.modelo.Trend;
import retrofit.Callback;
import retrofit.http.GET;

public interface TwitterLite {

    @GET("/trends")
    void trends(Callback<List<Trend>> trends);

}
