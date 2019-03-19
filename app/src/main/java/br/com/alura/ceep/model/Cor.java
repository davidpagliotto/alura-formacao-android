package br.com.alura.ceep.model;

import java.io.Serializable;

public class Cor implements Serializable{

    private String corHexa;
    private final int drawableId;

    public Cor(String corHexa, int drawableId) {
        this.corHexa = corHexa;
        this.drawableId = drawableId;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public String getCorHexa() {
        return corHexa;
    }
}