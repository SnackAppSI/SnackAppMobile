package snackapp.com.br.snackapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;

import snackapp.com.br.snackapp.classes.Usuario;
import snackapp.com.br.snackapp.tasks.TaskCadastrarUsuario;

public class CadastrarUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);



    }
    public void postUser (View view){


        String url = "http://snackapp.esy.es/usuarioAPI.php";
        EditText editnome = (EditText) findViewById(R.id.txnome);
        EditText edittel = (EditText) findViewById(R.id.txtel);
        EditText editlogin = (EditText) findViewById(R.id.txLogin);
        EditText editsenha = (EditText) findViewById(R.id.txsenha);

    try {
        TaskCadastrarUsuario task = new TaskCadastrarUsuario(this,editnome.getText().toString(),edittel.getText().toString(),editlogin.getText().toString(),editsenha.getText().toString());
        task.execute(url);

    }catch (Exception e){
        e.printStackTrace();

    }



        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);



    }
    /*
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public class TaskCadastrarUsuario extends AsyncTask<String, Void, String> {

        public String cadnome;
        public String cadtel;
        public String cadlogin;
        public String cadsenha;

        //public ProgressBar progressBar;

        public TaskCadastrarUsuario(String snome, String stel, String slogin, String ssenha){
            this.cadnome = snome;
            this.cadtel = stel;
            this.cadlogin = slogin;
            this.cadsenha = ssenha;

        }

        @Override
        protected String doInBackground(String... params) {


            Usuario user = new Usuario();

            user.setNome(cadnome);
            user.setTelefone(cadtel);
            user.setLogin(cadlogin);
            user.setSenha(cadsenha);
            InputStream inputStream = null;
            String result = "";
            try {

                // 1. create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // 2. make POST request to the given URL
                HttpPost httpPost = new HttpPost(params[0]);

                String json = "";

                // 3. build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("nome", user.getNome().toString());
                jsonObject.accumulate("telefone", user.getTelefone());
                jsonObject.accumulate("login", user.getLogin());
                jsonObject.accumulate("senha", user.getSenha());

                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();

                // ** Alternative way to convert Person object to JSON string usin Jackson Lib
                // ObjectMapper mapper = new ObjectMapper();
                // json = mapper.writeValueAsString(person);

                // 5. set json to StringEntity
                StringEntity se = new StringEntity(json);

                // 6. set httpPost Entity
                httpPost.setEntity(se);

                // 7. Set some headers to inform server about the type of the content
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                // 8. Execute POST request to the given URL
                HttpResponse httpResponse = httpclient.execute(httpPost);

                // 9. receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // 10. convert inputstream to string
                if (inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            // 11. return result
            return result;
            /*String url= params[0];
            String result = "";

            try {
                URL myURL = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
                connection.setRequestMethod("POST");
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(10000);
                connection.connect();

                String inputLine="";
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                StringBuilder stringBuilder= new StringBuilder();

                while((inputLine = bufferedReader.readLine())!=null){
                    stringBuilder.append(inputLine);
                }

                streamReader.close();
                bufferedReader.close();

                result = stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), "Cadastro Concluido "+ this.cadnome, Toast.LENGTH_LONG).show();

            /* super.onPostExecute(s);
            //txtRetorno.setText(s);
            progressBar.setVisibility(View.INVISIBLE);

            JSONObject reader = null;
            try {
                reader = new JSONObject(s);
                JSONArray arrayPessoas = reader.getJSONArray("results");
                int total=arrayPessoas.length();

                JSONObject primeiro = arrayPessoas.getJSONObject(0);
                //txtRetorno.setText(String.valueOf(total));
                txtRetorno.setText(primeiro.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        }
    }*/
}

    /*public void cadastrarCartao (View view)
    {
        Intent intent = new Intent(this, CadastrarCartaoActivity.class);
        startActivity(intent);
    }*/

