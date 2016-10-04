package br.com.miltonalcantara.buscatweet.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Criado por Milton Alcântara on 29/09/2016.
 */
public class Utils {

    private static Context context;

    public Utils() {
    }

    public Utils(Context context) {
        this.context = context;
    }

    /**
     * @param email - E-mail
     */
    public void mandarEmail(String email) {
        String[] TO = { email };
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        context.startActivity(emailIntent);
    }

    /**
     * @param paginaFacebookID - Colocar o ID do facebook
     * @param paginaFacebookLink - Colocar o Link Html
     */
    public static void getOpenFacebookIntent(String paginaFacebookID, String paginaFacebookLink) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checar se o FB está instalado.
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(paginaFacebookID)));
        } catch (Exception e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(paginaFacebookLink))); //Caso não esteja, abrir em um navagador
        }
    }

    /**
     *
     * @param palavra - Palavra Pesquisada ou Trend
     * @return - Palavra trabalhada para procurar conforme parametros do Twitter
     */
    public String testeTwitter(String palavra) {

        int espacosEmBranco = palavra.length() - palavra.replaceAll(" ", "").length();

        if (espacosEmBranco > 1) { //Se tiver 1 espaço em branco colocar %20 no lugar
            palavra = palavra.replaceAll(" ", "%20");
        } else if (espacosEmBranco == 1) { //Se tiver + de 1 espaço em branco colocar %20 no lugar do espaço e %22 no ínicio e fim
            palavra = palavra.replaceAll(" ", "%20");
            palavra = "%22" + palavra + "%22";
        }

        //trocando # por %23
        if (palavra.charAt(0) == '#') {
            palavra = palavra.replaceAll("#", "%23");
        }

        //trocando ñ por %C3%B1
        int n = palavra.length() - palavra.replaceAll("ñ", "").length();
        if (n > 0) {
            palavra = palavra.replaceAll("ñ", "%C3%B1");
        }

        return palavra;
    }
}
