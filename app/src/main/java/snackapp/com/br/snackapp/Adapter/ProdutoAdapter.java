package snackapp.com.br.snackapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import snackapp.com.br.snackapp.Entity.Produto;
import snackapp.com.br.snackapp.R;

/**
 * Created by moise on 16/11/2017.
 */

public class ProdutoAdapter extends BaseAdapter {


    private LayoutInflater MInfLater;
    private ArrayList<Produto> lstProdutos;
    private Context context;

    public ProdutoAdapter(){}
    public ProdutoAdapter(ArrayList<Produto> lstProdutos,Context context){
        this.lstProdutos = lstProdutos;
        this.context = context;
        this.MInfLater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return this.lstProdutos.size();
    }

    @Override
    public Object getItem(int i) {
        return this.lstProdutos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Produto produto = lstProdutos.get(i);
        view = MInfLater.inflate(R.layout.layout_produto,null);

        ((TextView) view.findViewById(R.id.cbprod)).setText(produto.getDesc().toString());
        ((TextView) view.findViewById(R.id.valorP)).setText(Float.toString(produto.getValor()));


        return view;
    }
}
