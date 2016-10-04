package br.com.miltonalcantara.buscatweet;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.miltonalcantara.buscatweet.lista.TweetAdapter;
import br.com.miltonalcantara.buscatweet.modelo.Tweet;
import br.com.miltonalcantara.buscatweet.utils.TwitterUtils;
import br.com.miltonalcantara.buscatweet.utils.Utils;

public class TimelineActivity extends AppCompatActivity {

    private ListView lvLinhaDoTempo;
    private String palavra;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //Recuperando a palavra passada pela Classe MainActivity
        palavra = getIntent().getStringExtra("palavra");

        SetandoAToolbar();

        lvLinhaDoTempo = (ListView) findViewById(R.id.lv_linha_do_tempo);

        new GetTimelineTask().execute();
    }

    private void SetandoAToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(palavra); //Setando o titulo na Toolbar
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        //colocar a seta para voltar para a Activity anterior
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Colocando a seta na Toolbar
        final Drawable upArrow;
        if (Build.VERSION.SDK_INT >= 21) {
            upArrow = ContextCompat.getDrawable(this, R.drawable.ic_menu_back);
        } else {
            upArrow = ContextCompat.getDrawable(this, R.drawable.ic_back);
        }
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        //Setando o Listener para fechar a tela ao clicar na Seta
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void updateListView(ArrayList<Tweet> tweets) {
        lvLinhaDoTempo.setAdapter(new TweetAdapter(this, R.layout.linha_tweet, tweets));
    }

    class GetTimelineTask extends AsyncTask<Object, Void, ArrayList<Tweet>> {

        private ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TimelineActivity.this);
            progressDialog.setTitle(getResources().getString(R.string.label_tweet_search_loader));
            progressDialog.show();
        }

        @Override
        protected ArrayList<Tweet> doInBackground(Object... params) {
            Utils utils = new Utils();
            return TwitterUtils.getTimelineForSearchTerm(utils.testeTwitter(palavra));
        }

        @Override
        protected void onPostExecute(ArrayList<Tweet> timeline) {
            super.onPostExecute(timeline);
            progressDialog.dismiss();

            if (timeline.isEmpty()) {
                Toast.makeText(TimelineActivity.this,
                        getResources().getString(R.string.label_tweets_not_found),
                        Toast.LENGTH_SHORT).show();
            } else {
                updateListView(timeline);
            }
        }

    }
}
