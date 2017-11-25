package snackapp.com.br.snackapp.tasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import snackapp.com.br.snackapp.Entity.Produto;
import snackapp.com.br.snackapp.HomeActivity;
import snackapp.com.br.snackapp.Entity.Usuario;
import snackapp.com.br.snackapp.MainActivity;

/**
 * Created by moise on 26/10/2017.
 */

public class TaskRealizaPedido extends AsyncTask<String, Void, String> {

    public ArrayList<Produto> lstProdutos;

    public Context context;
    //public ProgressBar progressBar;

    public TaskRealizaPedido(Context context, ArrayList<Produto> lstProdutos) {
        this.context = context;
        this.lstProdutos = lstProdutos;

    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    @Override
    protected String doInBackground(String... params) {


        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(params[0]);

            SharedPreferences preferences = this.context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);

            String json = "";

            // 3. build cliente
            JSONObject cliente = new JSONObject();
            cliente.accumulate("id_cliente", preferences.getInt("id_cliente", 0));
            cliente.accumulate("nome", preferences.getString("nome", "null"));
            cliente.accumulate("telefone", preferences.getString("telefone", "null"));
            cliente.accumulate("login", preferences.getString("login", "null"));
            cliente.accumulate("senha", preferences.getString("senha", "null"));

            JSONArray lista_produtos = new JSONArray();
            int count =0;
            for (Produto prod : lstProdutos) {
                JSONObject id_produto = new JSONObject();

                if (prod.getChecked()) {
                    id_produto.accumulate("id_produto", prod.getIdprod());
                    lista_produtos.put(count,id_produto);
                    count++;
                }


            }

            JSONObject jsondados = new JSONObject();
            jsondados.accumulate("retorno", preferences.getString("retorno", "null"));
            jsondados.accumulate("dados", cliente);
            jsondados.accumulate("token", preferences.getString("token", "null"));
            jsondados.accumulate("lista_produtos", lista_produtos);

            // 4. convert JSONObject to JSON to String
            json = jsondados.toString();
            //Log.d("testando",json);
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

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //Log.d("teste1",s);
        JSONObject objjson = null;
        try {
            // Log.d("teste",s);
            objjson = new JSONObject(s);
            //Log.d("teste",s);

            if (objjson.getString("retorno").equals("true")) {


                Intent intent = new Intent(this.context, HomeActivity.class);
                this.context.startActivity(intent);


                Toast.makeText(this.context, "Pedido Realizado", Toast.LENGTH_LONG).show();

            } else {
                SharedPreferences preferences = this.context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = preferences.edit();
                ed.clear();
                Toast.makeText(this.context, "Acesso Negado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this.context, MainActivity.class);
                this.context.startActivity(intent);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //txtRetorno.setText(s);
        /*Log.d("teste1",s);
        JSONObject objjson = null;*/
        // try {
            /*
            objjson = new JSONObject(s);
            Log.d("teste",objjson.getString("login"));
            Usuario user = new Usuario();
            user.setLogin(objjson.getString("login"));

            user.setSenha(objjson.getString("senha"));*/













        /*} catch (JSONException e) {
            e.printStackTrace();
        }




    }

            /* super.onPostExecute(s);
            //txtRetorno.setText(s);
            progressBar.setVisibility(View.INVISIBLE);

            JSONObject pbjjson = null;
            try {
                pbjjson = new JSONObject(s);
                JSONArray arrayPessoas = pbjjson.getJSONArray("results");
                int total=arrayPessoas.length();

                JSONObject primeiro = arrayPessoas.getJSONObject(0);
                //txtRetorno.setText(String.valueOf(total));
                txtRetorno.setText(primeiro.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }*/
    }
}

