package snackapp.com.br.snackapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import snackapp.com.br.snackapp.Entity.Empresa;
import snackapp.com.br.snackapp.Entity.Produto;
import snackapp.com.br.snackapp.R;

/**
 * Created by moise on 16/11/2017.
 */

public class EmpresaAdapter extends BaseAdapter {


    private LayoutInflater MInfLater;
    private ArrayList<Empresa> lstEmpresas;
    private Context context;
    private int selecionado = -1;
    private ListView ListE;
    public EmpresaAdapter(){}
    public EmpresaAdapter(ArrayList<Empresa> lstEmpresas, Context context,ListView ListeE){
        this.lstEmpresas = lstEmpresas;
        this.context = context;
        this.MInfLater = LayoutInflater.from(context);
        this.ListE = ListeE;

    }

    public int getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(int selecionado) {
        this.selecionado = selecionado;
    }

    @Override
    public int getCount() {
        return this.lstEmpresas.size();
    }

    @Override
    public Object getItem(int i) {
        return this.lstEmpresas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
  //      final int pos = i;
//        final int size = viewGroup.getChildCount();
        //((ListView) view.findViewById(R.id.listE)).setAdapter(new ArrayAdapter<Empresa>(context,android.R.layout.simple_list_item_single_choice, lstEmpresas));
        final Empresa empresa = lstEmpresas.get(i);
        view = MInfLater.inflate(R.layout.layout_empresa,null);
        RadioButton rbemp =((RadioButton) view.findViewById(R.id.rbEmp));
        ((RadioButton) view.findViewById(R.id.rbEmp)).setText(empresa.getNome_fant().toString());
//        Log.d("teste1",String.valueOf(size));
//        Log.d("teste2",String.valueOf(i));

        if(selecionado == i){
            rbemp.setChecked(true);

            empresa.setChecked(true);
        }
        else{
            rbemp.setChecked(false);
            empresa.setChecked(false);
        }

        rbemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RadioButton radio = null;
                /*for( int count = 0; count < size ;count++){
                    radio = (RadioButton) viewGroup.getChildAt(count);
                    radio.setChecked(false);
                    lstEmpresas.get(count).setChecked(false);
                }*/
                setSelecionado(i);
                //radio = (RadioButton) view;
            /*    if (!empresa.getChecked()) {
                    radio.setChecked(true);
                //    empresa.setChecked(true);
                } else {
                    radio.setChecked(false);
                  //  empresa.setChecked(false);
                }*/
                notifyDataSetChanged();
                //Log.d("teste",empresa.getChecked().toString());
            }
        });
        return view;
    }
}
