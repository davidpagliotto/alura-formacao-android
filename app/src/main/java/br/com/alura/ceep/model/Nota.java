package br.com.alura.ceep.model;

import java.io.Serializable;

import br.com.alura.ceep.enumerated.CorEnum;

public class Nota implements Serializable{

    private String titulo;
    private String descricao;
    private CorEnum corEnum;

    public Nota() { }

    public Nota(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.corEnum = CorEnum.BRANCO;
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
}