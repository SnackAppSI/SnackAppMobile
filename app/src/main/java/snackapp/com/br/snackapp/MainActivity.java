package snackapp.com.br.snackapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import snackapp.com.br.snackapp.tasks.TaskLoginUsuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static boolean verificacao;

    public void cadastrar(View view) {
        Intent intent = new Intent(this, CadastrarUsuarioActivity.class);
        startActivity(intent);
    }

    public void verLogin(View view) {
        String url = "http://snackapp.esy.es/loginAPI2.php";

        EditText editlogin = (EditText) findViewById(R.id.txtLogin);
        EditText editsenha = (EditText) findViewById(R.id.txtSenha);

        TaskLoginUsuario task = new TaskLoginUsuario(this,"", "", editlogin.getText().toString(), editsenha.getText().toString());
        task.execute(url);


        //Intent intent = new Intent(this, HomeActivity.class);
        //startActivity(intent);

    }
}
