package br.com.alura.ceep.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtil {

    public static String PREFERENCIAS = "br.com.alura.ceep.preferencia";

    public static String LAYOUT_KEY = "LAYOUT";
    public static String LAYOUT_LINEAR = "LINEAR";
    public static String LAYOUT_GRID = "GRID";

    public static String PRIMEIRA_VEZ_KEY = "PRIMEIRA_VEZ";
    public static String PRIMEIRA_VEZ_VALUE = "NAO";

    public static boolean existePreferencia(Context context, String chave) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCIAS, MODE_PRIVATE);
        return sharedPreferences.contains(chave);
    }

    public static String recuperaPreferencia(Context context, String chave, String valorPadrao) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCIAS, MODE_PRIVATE);
        return sharedPreferences.getString(chave, valorPadrao);
    }

    public static void gravarPreferencia(Context context, String chave, String valor) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCIAS, MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(chave, valor)
                .apply();
    }
}
