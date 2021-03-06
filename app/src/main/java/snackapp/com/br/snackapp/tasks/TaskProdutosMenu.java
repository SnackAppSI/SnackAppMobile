package snackapp.com.br.snackapp.tasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
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

import snackapp.com.br.snackapp.Adapter.EmpresaAdapter;
import snackapp.com.br.snackapp.Adapter.ProdutoAdapter;
import snackapp.com.br.snackapp.Entity.Produto;
import snackapp.com.br.snackapp.HomeActivity;
import snackapp.com.br.snackapp.MainActivity;

/**
 * Created by moise on 16/11/2017.
 */

public class TaskProdutosMenu extends AsyncTask<String, Void, String> {
    public ArrayList<Produto> lstProdutos;
    public Context context;
    public int id_empresa;
    public ListView ListP;

    public TaskProdutosMenu(Context context, ArrayList<Produto> lstProdutos, ListView ListP, int id_empresa) {
        this.context = context;
        this.lstProdutos = lstProdutos;
        this.ListP = ListP;
        this.id_empresa = id_empresa;

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

            JSONObject dados = new JSONObject();
            dados.accumulate("retorno", preferences.getString("retorno", "null"));
            dados.accumulate("dados", cliente);
            dados.accumulate("token", preferences.getString("token", "null"));
            dados.accumulate("id_empresa", id_empresa);


            // 4. convert JSONObject to JSON to String
            json = dados.toString();

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
        //txtRetorno.setText(s);

        //Log.d("teste",s);
        JSONObject objjson = null;
        try {
            objjson = new JSONObject(s);

            if (objjson.getString("retorno").equals("true")) {

                JSONArray arrayProdutos = objjson.getJSONArray("lista_produtos");
                int total = arrayProdutos.length();

                for (int i = 0; i < total; i++) {
                    JSONObject obj = arrayProdutos.getJSONObject(i);
                    Produto p = new Produto();
                    p.setIdprod(obj.getInt("id_produto"));
                    p.setQuant(obj.getInt("quantidade"));
                    p.setIdemp(obj.getInt("id_empresa"));
                    p.setDesc(obj.getString("descricao"));
                    p.setValor((float) obj.getDouble("valor"));


                    this.lstProdutos.add(p);

                }


                atualizarListView();
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


    }

    private void atualizarListView() {
        //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,android.R.id.text1,nomes);
        ProdutoAdapter adapter = new ProdutoAdapter(this.lstProdutos, this.context);
        this.ListP.setAdapter(adapter);
    }
}
