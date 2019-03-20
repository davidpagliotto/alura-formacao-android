package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;
import br.com.alura.ceep.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback;
import br.com.alura.ceep.util.SharedPreferencesUtil;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;
import static br.com.alura.ceep.util.SharedPreferencesUtil.LAYOUT_GRID;
import static br.com.alura.ceep.util.SharedPreferencesUtil.LAYOUT_KEY;
import static br.com.alura.ceep.util.SharedPreferencesUtil.LAYOUT_LINEAR;

public class ListaNotasActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Notas";
    private ListaNotasAdapter adapter;
    private RecyclerView listaNotas;
    private Menu menuActivity;
    private NotaDAO notaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        Stetho.initializeWithDefaults(this);
        setTitle(TITULO_APPBAR);

        notaDAO = new NotaDAO(this);
        List<Nota> todasNotas = pegaTodasNotas();
        configuraRecyclerView(todasNotas);
        configuraBotaoInsereNota();
    }

    private void configuraBotaoInsereNota() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormularioNotaActivityInsere();
            }
        });
    }

    private void vaiParaFormularioNotaActivityInsere() {
        Intent iniciaFormularioNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        startActivityForResult(iniciaFormularioNota, CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private void vaiParaFormularioFeedback() {
        Intent iniciaFormularioFeedbac = new Intent(ListaNotasActivity.this, FeedbackActivity.class);
        startActivityForResult(iniciaFormularioFeedbac, CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private List<Nota> pegaTodasNotas() {
        return notaDAO.todasNotas();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ehResultadoInsereNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                adiciona(notaRecebida);
            }
        }

        if (ehResultadoAlteraNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                int posicaoRecebida = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
                if (ehPosicaoValida(posicaoRecebida)) {
                    altera(notaRecebida, posicaoRecebida);
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_lista_salva, menu);
        menuActivity = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (recuperarLayoutPreferido().equals(LAYOUT_LINEAR)) {
            menu.findItem(R.id.menu_formulario_lista_ic_linear).setVisible(false);
            menu.findItem(R.id.menu_formulario_lista_ic_grid).setVisible(true);
        }
        else {
            menu.findItem(R.id.menu_formulario_lista_ic_linear).setVisible(true);
            menu.findItem(R.id.menu_formulario_lista_ic_grid).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_formulario_lista_ic_grid) {
            alterarLayoutListaNotas(LAYOUT_GRID);
        } else if (item.getItemId() == R.id.menu_formulario_lista_ic_linear) {
            alterarLayoutListaNotas(LAYOUT_LINEAR);
        } else if (item.getItemId() == R.id.menu_formulario_lista_ic_feedback) {
            vaiParaFormularioFeedback();
        }

        onPrepareOptionsMenu(menuActivity);
        return true;
    }

    private void alterarLayoutListaNotas(String layout) {
        RecyclerView.LayoutManager layoutManager;
        if (layout.equals(LAYOUT_GRID))
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        else
            layoutManager = new LinearLayoutManager(ListaNotasActivity.this, LinearLayoutManager.VERTICAL, false);

        listaNotas.setLayoutManager(layoutManager);
        gravaLayoutPreferido(layout);
    }

    private String recuperarLayoutPreferido() {
        return SharedPreferencesUtil.recuperaPreferencia(getApplication(), LAYOUT_KEY, LAYOUT_LINEAR);
    }

    private void gravaLayoutPreferido(String layout) {
        SharedPreferencesUtil.gravarPreferencia(getApplication(), LAYOUT_KEY, layout);
    }

    private void altera(Nota nota, int posicao) {
        notaDAO.alteraPosicao(posicao, nota);
        adapter.altera();
    }

    private boolean ehPosicaoValida(int posicaoRecebida) {
        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoAlteraNota(requestCode) &&
                temNota(data);
    }

    private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }

    private void adiciona(Nota nota) {
        notaDAO.insere(nota);
        adapter.adiciona();
    }

    private boolean ehResultadoInsereNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode) &&
                temNota(data);
    }

    private boolean temNota(Intent data) {
        return data != null && data.hasExtra(CHAVE_NOTA);
    }

    private boolean resultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
        configuraItemTouchHelper(listaNotas);

        alterarLayoutListaNotas(recuperarLayoutPreferido());
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallback(this, adapter));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, todasNotas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new ListaNotasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int posicao) {
                vaiParaFormularioNotaActivityAltera(nota, posicao);
            }
        });
    }

    private void vaiParaFormularioNotaActivityAltera(Nota nota, int posicao) {
        Intent abreFormularioComNota = new Intent(ListaNotasActivity.this,
                FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        abreFormularioComNota.putExtra(CHAVE_POSICAO, posicao);
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }

}
