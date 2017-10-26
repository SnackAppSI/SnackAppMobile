package snackapp.com.br.snackapp.tasks;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

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
import java.util.IllegalFormatCodePointException;

import snackapp.com.br.snackapp.HomeActivity;
import snackapp.com.br.snackapp.MainActivity;
import snackapp.com.br.snackapp.classes.Usuario;

/**
 * Created by moise on 26/10/2017.
 */

public class TaskLoginUsuario extends AsyncTask<String, Void, String> {

    public String cadnome;
    public String cadtel;
    public String cadlogin;
    public String cadsenha;

    //public ProgressBar progressBar;

    public TaskLoginUsuario(String snome, String stel, String slogin, String ssenha){
        this.cadnome = snome;
        this.cadtel = stel;
        this.cadlogin = slogin;
        this.cadsenha = ssenha;

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

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //txtRetorno.setText(s);


        JSONObject objjson = null;
        try {
            objjson = new JSONObject(s);
            Usuario user = new Usuario();
            user.setLogin(objjson.getString("login"));
            user.setSenha(objjson.getString("senha"));
            MainActivity ma = new MainActivity();
            if ((user.getLogin().equals("")==true)&&(user.getSenha().equals("")==true)) {

                ma.verificacao = true;

            }
            else {
                ma.verificacao = false;
            }







        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

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


        }*/
    }
