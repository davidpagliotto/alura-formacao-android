package br.com.alura.ceep.model;

import java.io.Serializable;

import br.com.alura.ceep.enumerated.CorEnum;

public class Nota implements Serializable{

    private String id;
    private String titulo;
    private String descricao;
    private CorEnum corEnum;
    private int posicao;

    public Nota() {
        corEnum = CorEnum.BRANCO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CorEnum getCorEnum() {
        return corEnum;
    }

    public void setCorEnum(CorEnum corEnum) {
        this.corEnum = corEnum;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
}