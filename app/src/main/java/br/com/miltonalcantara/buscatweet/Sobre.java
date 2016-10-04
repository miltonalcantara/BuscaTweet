package br.com.miltonalcantara.buscatweet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;

import br.com.miltonalcantara.buscatweet.utils.Utils;

public class Sobre extends AppCompatActivity {

    private View myView;
    private boolean PrimeiraVez = true;

    Utils utils;
    FloatingActionButton email, homeFB, pessoalFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        utils = new Utils(this);

        email = (FloatingActionButton) findViewById(R.id.fab_email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.mandarEmail("milton.neto.alcantara@gmail.com");
            }
        });

        homeFB = (FloatingActionButton) findViewById(R.id.fab_web);
        homeFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getOpenFacebookIntent("fb://page/537884163031900", "https://m.facebook.com/miltonalcantaraApps/"); //business Page
            }
        });

        pessoalFB = (FloatingActionButton) findViewById(R.id.fab_pessoal);
        pessoalFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getOpenFacebookIntent("fb://profile/100001700044091", "https://www.facebook.com/MiltonNAlcantara"); //Pessoal
            }
        });

        myView = findViewById(R.id.id_icone_texto);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            PrimeiraVez = savedInstanceState.getBoolean("EstadoAnimacao");
            myView.setVisibility(View.VISIBLE);
        }

        if (PrimeiraVez && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            show(myView);
        } else {
            myView.setVisibility(View.VISIBLE);
        }
        PrimeiraVez = false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Salva o estado da página
        savedInstanceState.putBoolean("EstadoAnimacao", PrimeiraVez);

        super.onSaveInstanceState(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void show(final View view) {
        myView.post(new Runnable() {
            @Override
            public void run() {
                // get the center for the clipping circle
                int cx = (view.getLeft() + view.getRight()) / 2;
                int cy = (view.getTop() + view.getBottom()) / 2;

                // get the final radius for the clipping circle
                int finalRadius = Math.max(view.getWidth(), view.getHeight());

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy,
                        0, finalRadius);
                anim.setDuration(1000);

                // make the view visible and start the animation
                view.setVisibility(View.VISIBLE);
                anim.start();
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // To hide a previously visible view using this effect:
    private void hide(final View view) {
        // get the center for the clipping circle
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        // get the initial radius for the clipping circle
        int initialRadius = view.getWidth();

        // create the animation (the final radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy,
                initialRadius, 0);
        anim.setDuration(700);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
                finishAfterTransition();
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });

        // start the animation
        anim.start();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            hide(myView);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        }
    }
}
