package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import br.com.alura.ceep.R;
import br.com.alura.ceep.util.SharedPreferencesUtil;

import static br.com.alura.ceep.util.SharedPreferencesUtil.PRIMEIRA_VEZ_KEY;
import static br.com.alura.ceep.util.SharedPreferencesUtil.PRIMEIRA_VEZ_VALUE;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        int tempoDelay = SharedPreferencesUtil.existePreferencia(getApplication(), PRIMEIRA_VEZ_KEY) ? 500 : (2 * 1000);

        new Handler(getMainLooper()).
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intentListaNotas = new Intent(SplashScreenActivity.this, ListaNotasActivity.class);
                        startActivity(intentListaNotas);
                        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                        finish();

                        SharedPreferencesUtil.gravarPreferencia(getApplication(), PRIMEIRA_VEZ_KEY, PRIMEIRA_VEZ_VALUE);
                    }
                }, tempoDelay);
    }
}
