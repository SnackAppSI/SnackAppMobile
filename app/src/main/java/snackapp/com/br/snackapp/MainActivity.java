package snackapp.com.br.snackapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import snackapp.com.br.snackapp.tasks.TaskLoginUsuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean verificacao;

    public void cadastrar(View view) {
        Intent intent = new Intent(this, CadastrarUsuarioActivity.class);
        startActivity(intent);
    }

    public void verLogin(View view) {
        /*String url = "http://snackapp.tk/usuario.php";
        EditText editnome = (EditText) findViewById(R.id.txnome);
        EditText edittel = (EditText) findViewById(R.id.txtel);
        EditText editlogin = (EditText) findViewById(R.id.txLogin);
        EditText editsenha = (EditText) findViewById(R.id.txsenha);
        TaskLoginUsuario task = new TaskLoginUsuario(editnome.getText().toString(), edittel.getText().toString(), editlogin.getText().toString(), editsenha.getText().toString());
        task.execute(url);
        if (verificacao == true) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        else{
            this.recreate();
        }*/

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }
}
