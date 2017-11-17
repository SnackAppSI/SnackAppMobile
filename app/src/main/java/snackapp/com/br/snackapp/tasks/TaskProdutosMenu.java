package snackapp.com.br.snackapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import snackapp.com.br.snackapp.Adapter.ProdutoAdapter;
import snackapp.com.br.snackapp.Entity.Produto;
import snackapp.com.br.snackapp.Entity.Usuario;

/**
 * Created by moise on 16/11/2017.
 */

public class TaskProdutosMenu extends AsyncTask<String, Void, String> {
    public ArrayList<Produto> lstProdutos;
    public Context context;
    public String sdesc;
    public String svalor;
    public ListView ListP;

    public TaskProdutosMenu(Context context,ArrayList<Produto> lstProdutos, ListView ListP) {
        this.context = context;
        this.lstProdutos = lstProdutos;
        this.ListP = ListP;
        //this.sdesc = sdesc;
        //this.svalor = svalor;

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

            String json = "";

            // 3. build cliente
            JSONObject cliente = new JSONObject();
            cliente.accumulate("nome", "moises");
            cliente.accumulate("telefone", "22222");
            cliente.accumulate("login", "3015106480");
            cliente.accumulate("senha", "abc123");
            JSONObject dados = new JSONObject();
            dados.accumulate("retorno","true");
            dados.accumulate("dados",cliente);
            dados.accumulate("token","3df858adccf96099d7bf84b42a2dbd59");
            dados.accumulate("id_empresa",3);


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

        Log.d("teste",s);
        JSONObject reader = null;
        try {
            reader = new JSONObject(s);

            JSONArray arrayProdutos = reader.getJSONArray("lista_produtos");
            int total=arrayProdutos.length();

            for(int i=0;i<total;i++){
                JSONObject obj= arrayProdutos.getJSONObject(i);
                Produto p = new Produto();
                p.setDesc(obj.getString("descricao"));
                p.setValor((float) obj.getDouble("valor"));



                this.lstProdutos.add(p);

            }


            atualizarListView();
            //StringBuilder sb = new StringBuilder();
            /*for(Pessoa p:lstPessoas){
                sb.append(p.getName());
                sb.append("\n");
            }*/
            //txtRetorno.setText(sb.toString());
            //JSONObject primeiro = arrayPessoas.getJSONObject(0);
            //txtRetorno.setText(String.valueOf(total));
            //txtRetorno.setText(primeiro.getString("name"));
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
