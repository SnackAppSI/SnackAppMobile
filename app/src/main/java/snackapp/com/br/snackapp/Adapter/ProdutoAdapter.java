package snackapp.com.br.snackapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
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
    private int selecionado = -1;

    public ProdutoAdapter(){}
    public ProdutoAdapter(ArrayList<Produto> lstProdutos,Context context){
        this.lstProdutos = lstProdutos;
        this.context = context;
        this.MInfLater = LayoutInflater.from(context);

    }

    public int getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(int selecionado) {
        this.selecionado = selecionado;
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

        final Produto produto = lstProdutos.get(i);
        view = MInfLater.inflate(R.layout.layout_produto,null);
        CheckBox cbprod =((CheckBox) view.findViewById(R.id.cbprod));
        ((TextView) view.findViewById(R.id.cbprod)).setText(produto.getDesc().toString());
        ((TextView) view.findViewById(R.id.valorP)).setText(Float.toString(produto.getValor()));
        cbprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = null;
                /*for( int count = 0; count < size ;count++){
                    radio = (RadioButton) viewGroup.getChildAt(count);
                    radio.setChecked(false);
                    lstEmpresas.get(count).setChecked(false);
                }*/
                checkBox = (CheckBox) view;
                if (!produto.getChecked()) {
                    checkBox.setChecked(true);
                    produto.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                    produto.setChecked(false);
                }
                //Log.d("teste",empresa.getChecked().toString());
            }
        });


        return view;
    }
}
