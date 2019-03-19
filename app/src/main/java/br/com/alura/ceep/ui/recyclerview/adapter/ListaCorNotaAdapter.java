package br.com.alura.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Cor;

public class ListaCorNotaAdapter extends RecyclerView.Adapter<ListaCorNotaAdapter.CorViewHolder> {

    private final List<Cor> cores;
    private final Context context;

    private OnItemClickListener onItemCorClickListener;

    public ListaCorNotaAdapter(Context context, List<Cor> cores) {
        this.context = context;
        this.cores = cores;
    }

    @Override
    public CorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_cor_nota, parent, false);
        return new CorViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(CorViewHolder holder, int position) {
        Cor cor = cores.get(position);
        holder.vincula(cor);
    }

    @Override
    public int getItemCount() {
        return cores.size();
    }

    public void setOnItemCorClickListener(OnItemClickListener onItemCorClickListener) {
        this.onItemCorClickListener = onItemCorClickListener;
    }

    class CorViewHolder extends RecyclerView.ViewHolder {

        private final View viewCor;
        private Cor cor;

        public CorViewHolder(View itemView) {
            super(itemView);
            viewCor = itemView.findViewById(R.id.view_cor);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemCorClickListener.onItemClick(cor, getAdapterPosition());
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void vincula(Cor cor) {
            this.cor = cor;
            viewCor.setBackground(context.getDrawable(cor.getDrawableId()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Cor cor, int posicao);
    }
}
