package snackapp.com.br.snackapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CadastrarCartaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_cartao);
    }

    public void confirmarCadastro (View view)
    {
        Intent intent = new Intent(this, TermoDeUsoActivity.class);
        startActivity(intent);
    }
}
