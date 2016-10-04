package br.com.miltonalcantara.buscatweet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.com.miltonalcantara.buscatweet.R;
import br.com.miltonalcantara.buscatweet.TimelineActivity;
import br.com.miltonalcantara.buscatweet.database.DBHelper;

public class BuscaFragment extends Fragment {

    EditText txtDescription;
    Editable palavra;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_busca, container, false);

        txtDescription = (EditText) view.findViewById(R.id.edit_search_tag);

        Button btn_pesquisar = (Button) view.findViewById(R.id.btn_pesquisar);
        btn_pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                palavra = txtDescription.getText();

                //Apagando o texto do EditText
                txtDescription.setText("");

                //Colocando o termo pesquisado no BD
                DBHelper myDb = new DBHelper(getContext());
                myDb.inserirPalavraPesquisada("" + palavra);

                //Passando o termo pesquisado para a próxima activity para busca
                Bundle bundle = new Bundle();
                bundle.putString("palavra", "" + palavra);
                Intent intent = new Intent(getContext(), TimelineActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return view;
    }
}
