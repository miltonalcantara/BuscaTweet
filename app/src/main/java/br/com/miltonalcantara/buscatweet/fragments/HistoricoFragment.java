package br.com.miltonalcantara.buscatweet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import br.com.miltonalcantara.buscatweet.database.DBHelper;

public class HistoricoFragment extends Fragment {

    ListView listViewPesquisas;
    ArrayList<String> arrayListPesquisas = new ArrayList<>();

    DBHelper myDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        listViewPesquisas = (ListView) view.findViewById(R.id.listView_ultimas_pesquisas);
        listViewPesquisas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Passando o termo pesquisado para a próxima activity para busca
                Bundle bundle = new Bundle();
                bundle.putString("palavra", "" + arrayListPesquisas.get(position));
                Intent intent = new Intent(getContext(), TimelineActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        FloatingActionButton fabLimparPesquisas = (FloatingActionButton) view.findViewById(R.id.fab_limpar_pesquisas);
        fabLimparPesquisas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDb.apagarLista();
                Toast.makeText(getContext(), R.string.pesquisa_limpa, Toast.LENGTH_LONG).show();

                // Refresh no Fragment
                getFragmentManager().beginTransaction().detach(HistoricoFragment.this).attach(HistoricoFragment.this).commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        arrayListPesquisas.clear();

        myDb = new DBHelper(getContext());
        ArrayList<String> arrayPesquisas = new ArrayList<>();

        int x = myDb.PegarTodasPesquisas().size();

        if (myDb.PegarTodasPesquisas().size() > 0) {
            atualizarLista(myDb, arrayPesquisas);
        }

        // Cada linha na lista armazena a palavra pesquisada e o ícone
        List<HashMap<String, String>> ListaHashMap = new ArrayList<>();

        for (int i = 0; i < x; i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("txt", arrayPesquisas.get(i));
            hm.put("icone", Integer.toString(R.drawable.ic_history));
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
        listViewPesquisas.setAdapter(adapter);
    }

    private void atualizarLista(DBHelper myDb, ArrayList<String> arrayNomes) {
        for (int i = 0; i < myDb.PegarTodasPesquisas().size(); i++) {
            arrayNomes.add(myDb.PegarTodasPesquisas().get(i));
            arrayListPesquisas.add(myDb.PegarTodasPesquisas().get(i));
        }
    }
}