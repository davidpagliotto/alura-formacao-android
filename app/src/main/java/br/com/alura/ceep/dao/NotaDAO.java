package br.com.alura.ceep.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.alura.ceep.model.Nota;

public class NotaDAO {

    private final static ArrayList<Nota> notas = new ArrayList<>();

    public List<Nota> todos() {

        if (notas.isEmpty()) {
            notas.add(new Nota("Estudar", "POO\n - Herança\n - Polimofismo\nReflection\nLambda"));
            notas.add(new Nota("Treinar", "Inglês"));
            notas.add(new Nota("Lembrete", "Cortar cabelo amanhã"));
            notas.add(new Nota("Compromisso", "Jantar na casa da mãe"));
        }

        return (List<Nota>) notas.clone();
    }

    public void insere(Nota... notas) {
        NotaDAO.notas.addAll(Arrays.asList(notas));
    }

    public void altera(int posicao, Nota nota) {
        notas.set(posicao, nota);
    }

    public void remove(int posicao) {
        notas.remove(posicao);
    }

    public void troca(int posicaoInicio, int posicaoFim) {
        Collections.swap(notas, posicaoInicio, posicaoFim);
    }

    public void removeTodos() {
        notas.clear();
    }
}
