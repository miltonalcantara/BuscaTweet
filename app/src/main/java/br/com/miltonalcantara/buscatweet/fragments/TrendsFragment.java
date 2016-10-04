package br.com.miltonalcantara.buscatweet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.miltonalcantara.buscatweet.R;
import br.com.miltonalcantara.buscatweet.TimelineActivity;
import br.com.miltonalcantara.buscatweet.modelo.Trend;
import br.com.miltonalcantara.buscatweet.network.TwitterLite;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TrendsFragment extends Fragment {

    private TwitterLite twitterLite;

    ListView listView;

    ArrayList<Trend> arrayTrends = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trends, container, false);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://raw.githubusercontent.com/MobileBootcamp/TwitterTrends/master/app/src/main/assets/api/")
                .build();

        twitterLite = restAdapter.create(TwitterLite.class);
        getTrends();

        listView = (ListView) view.findViewById(R.id.listview_trends);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Passando o trend para buscar tweets
                Bundle bundle = new Bundle();
                bundle.putString("palavra", "" + arrayTrends.get(i).getName());
                Intent intent = new Intent(getContext(), TimelineActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getTrends() {
        twitterLite.trends(new Callback<List<Trend>>() {
            @Override
            public void success(List<Trend> trends, Response response) {
                addTrends(trends);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getContext(), "error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addTrends(List<Trend> trends) {
        arrayTrends = (ArrayList<Trend>) trends;

        // Cada linha na lista armazena o trend e o ícone
        List<HashMap<String, String>> ListaHashMap = new ArrayList<>();

        for (int i = 0; i < arrayTrends.size(); i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("txt", "" + arrayTrends.get(i).getName());
            hm.put("icone", Integer.toString(R.drawable.ic_trend));
            ListaHashMap.add(hm);
        }

        // Chaves usadas no Hashmap
        String[] from = {"icone", "txt"};

        // Ids de views no listview_layout
        int[] to = {R.id.icone, R.id.palavra_pesquisada};

        // Instanciando um adapter para armazenar os itens
        // R.layout.listview_layout define o layout para cada item
        SimpleAdapter adapter = new SimpleAdapter(getContext(), ListaHashMap, R.layout.listview_historico_e_trend, from, to);

        // Setando o adapter no listView
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}
