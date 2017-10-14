package snackapp.com.br.snackapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CadastrarUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);
    }

    public void cadastrarCartao (View view)
    {
        Intent intent = new Intent(this, CadastrarCartaoActivity.class);
        startActivity(intent);
    }
}
