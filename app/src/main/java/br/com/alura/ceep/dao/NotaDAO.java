package br.com.alura.ceep.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import br.com.alura.ceep.enumerated.CorEnum;
import br.com.alura.ceep.model.Nota;

public class NotaDAO extends BaseDAO {

    private static String TB_NOTA = "nota";

    public NotaDAO(Context context) {
        super(context);
    }

    private final static ArrayList<Nota> notas = new ArrayList<>();

    public List<Nota> todasNotas() {
        notas.clear();

        Cursor c = db.rawQuery("SELECT * FROM nota ORDER BY posicao", new String[0]);
        while (c.moveToNext()) {
            Nota nota = new Nota();
            nota.setId(c.getString(c.getColumnIndex("id")));
            nota.setTitulo(c.getString(c.getColumnIndex("titulo")));
            nota.setDescricao(c.getString(c.getColumnIndex("descricao")));
            nota.setCorEnum(CorEnum.valueOf(c.getString(c.getColumnIndex("cor"))));

            notas.add(nota);
        }
        c.close();

        return notas;
    }

    public Nota insere(Nota nota) {
        geraUUID(nota);
        notas.add(0, nota);
        db.insert(TB_NOTA, null, converterDadosNota(nota));

        atualizaPosicoes();
        return nota;
    }

    private void atualiza(Nota nota) {
        db.update(TB_NOTA, converterDadosNota(nota), "id = ?", new String[]{nota.getId()});
    }

    public void remove(Nota nota) {
        notas.remove(nota);
        db.delete(TB_NOTA, "id = ?", new String[]{String.valueOf(nota.getId())});
        atualizaPosicoes();
    }

    public void alteraPosicao(int posicao, Nota nota) {
        notas.set(posicao, nota);
        atualizaPosicoes();
    }

    public void trocaPosicao(int posicaoInicio, int posicaoFim) {
        Collections.swap(notas, posicaoInicio, posicaoFim);
        atualizaPosicoes();
    }

    public void removeTodos() {
        db.delete(TB_NOTA, null, new String[0]);
    }

    private void geraUUID(Nota nota) {
        nota.setId(UUID.randomUUID().toString());
    }

    private ContentValues converterDadosNota(Nota nota) {
        ContentValues values = new ContentValues();
        values.put("id", nota.getId());
        values.put("titulo", nota.getTitulo());
        values.put("descricao", nota.getDescricao());
        values.put("cor", nota.getCorEnum().name());
        values.put("posicao", nota.getPosicao());
        return values;
    }

    private void atualizaPosicoes() {
        for (Nota _nota : notas) {
            _nota.setPosicao(notas.indexOf(_nota));
            atualiza(_nota);
        }
    }
}
