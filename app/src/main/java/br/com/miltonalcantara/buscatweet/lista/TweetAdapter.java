package br.com.miltonalcantara.buscatweet.lista;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.miltonalcantara.buscatweet.R;
import br.com.miltonalcantara.buscatweet.modelo.Tweet;
import br.com.miltonalcantara.buscatweet.utils.BitmapManager;
import br.com.miltonalcantara.buscatweet.utils.DateUtils;

public class TweetAdapter extends ArrayAdapter<Tweet> {

    private Context context;
    private ArrayList<Tweet> tweets;

    public TweetAdapter(Context context, int resource, ArrayList<Tweet> tweets) {
        super(context, resource, tweets);
        this.context = context;
        this.tweets = tweets;
    }

    private static class ViewHolder {
        ImageView imagemUsuario;
        TextView nome;
        TextView loginDoUsuario;
        TextView texto;
        TextView dataDoTwitte;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.linha_tweet, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.imagemUsuario = (ImageView) convertView.findViewById(R.id.imagem_usuario);
            viewHolder.nome = (TextView) convertView.findViewById(R.id.nome);
            viewHolder.loginDoUsuario = (TextView) convertView.findViewById(R.id.login_do_usuario);
            viewHolder.texto = (TextView) convertView.findViewById(R.id.texto);
            viewHolder.dataDoTwitte = (TextView) convertView.findViewById(R.id.data_do_twitte);

            convertView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        BitmapManager.getInstance().loadBitmap(tweets.get(position).getImagemUsuario(), holder.imagemUsuario);
        holder.nome.setText(tweets.get(position).getNome());
        String output = "@" + tweets.get(position).getLoginDoUsuario();
        holder.loginDoUsuario.setText(output);
        holder.texto.setText(tweets.get(position).getTexto());
        holder.dataDoTwitte.setText(DateUtils.setDateFormat(tweets.get(position).getDataDoTwitte()));

        return convertView;
    }
}
