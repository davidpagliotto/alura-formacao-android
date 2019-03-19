package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.enumerated.CorEnum;
import br.com.alura.ceep.model.Cor;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaCorNotaAdapter;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity {


    public static final String TITULO_APPBAR_INSERE = "Insere nota";
    public static final String TITULO_APPBAR_ALTERA = "Altera nota";
    private int posicaoRecibida = POSICAO_INVALIDA;
    private View containerFormularioNota;
    private TextView titulo;
    private TextView descricao;
    private RecyclerView listaCorNota;
    private ListaCorNotaAdapter adapterCorNota;
    private String corBackground;
    private Nota nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITULO_APPBAR_INSERE);
        inicializaCampos();

        Intent dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra(CHAVE_NOTA)){
            setTitle(TITULO_APPBAR_ALTERA);
            nota = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);
            posicaoRecibida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            preencheCampos(nota);
        }
        else
            nota = novaNota();
    }

    @Override
    protected void onResume() {
        super.onResume();
        preencheCampos(nota);
    }

    private Nota novaNota() {
        return new Nota();
    }

    private void preencheCampos(Nota notaRecebida) {
        titulo.setText(notaRecebida.getTitulo());
        descricao.setText(notaRecebida.getDescricao());
        mudaBackground(notaRecebida.getCorEnum().getCorHexa());
    }

    private void inicializaCampos() {
        containerFormularioNota = findViewById(R.id.container_formulario_nota);
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);

        configuraRecyclerView();
    }

    private void configuraRecyclerView() {
        listaCorNota = findViewById(R.id.lista_cor_nota);
        configuraAdapter(listaCorNota);
    }

    private void configuraAdapter(RecyclerView listaCorNota) {
        adapterCorNota = new ListaCorNotaAdapter(this, todasCores());
        listaCorNota.setAdapter(adapterCorNota);
        adapterCorNota.setOnItemCorClickListener(new ListaCorNotaAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(Cor cor, int posicao) {
                mudaBackground(cor.getCorHexa());

            }
        });
    }

    private void mudaBackground(String corHexa) {
        corBackground = corHexa;
        containerFormularioNota.setBackgroundColor(Color.parseColor(corHexa));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(ehMenuSalvaNota(item)){
            atualizaNota();
            retornaNota();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNota() {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicaoRecibida);
        setResult(Activity.RESULT_OK,resultadoInsercao);
    }

    @NonNull
    private void atualizaNota() {
        CorEnum corEnum = CorEnum.valueOfCorHexa(corBackground);

        nota.setTitulo(titulo.getText().toString());
        nota.setDescricao(descricao.getText().toString());
        nota.setCorEnum(corEnum);
    }

    private boolean ehMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }
    
    private List<Cor> todasCores() {
        List<Cor> listaCorNota = new ArrayList<>();
        listaCorNota.add(new Cor(CorEnum.BRANCO.getCorHexa(), R.drawable.fundo_item_cor_branco));
        listaCorNota.add(new Cor(CorEnum.AZUL.getCorHexa(), R.drawable.fundo_item_cor_azul));
        listaCorNota.add(new Cor(CorEnum.VERMELHO.getCorHexa(), R.drawable.fundo_item_cor_vermelho));
        listaCorNota.add(new Cor(CorEnum.VERDE.getCorHexa(), R.drawable.fundo_item_cor_verde));
        listaCorNota.add(new Cor(CorEnum.AMARELO.getCorHexa(), R.drawable.fundo_item_cor_amarelo));
        listaCorNota.add(new Cor(CorEnum.LILAS.getCorHexa(), R.drawable.fundo_item_cor_lilas));
        listaCorNota.add(new Cor(CorEnum.CINZA.getCorHexa(), R.drawable.fundo_item_cor_cinza));
        listaCorNota.add(new Cor(CorEnum.MARROM.getCorHexa(), R.drawable.fundo_item_cor_marrom));
        listaCorNota.add(new Cor(CorEnum.ROXO.getCorHexa(), R.drawable.fundo_item_cor_roxo));

        return listaCorNota;
    }
}
