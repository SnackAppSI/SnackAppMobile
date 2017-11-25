package snackapp.com.br.snackapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;

import snackapp.com.br.snackapp.Entity.Empresa;
import snackapp.com.br.snackapp.Entity.Produto;
import snackapp.com.br.snackapp.tasks.TaskProdutosMenu;
import snackapp.com.br.snackapp.tasks.TaskReadEmpresas;

public class FazerPedidoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public ArrayList<Empresa> lstEmpresas;
    public RadioButton rbButton;
    public ListView ListE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lstEmpresas = new   ArrayList<Empresa>();
        ListE = (ListView) findViewById(R.id.listE);
        String url = "http://snackapp.esy.es/readEmpresas.php";
        TaskReadEmpresas task = new TaskReadEmpresas(this,lstEmpresas,ListE);
        task.execute(url);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.minhaConta) {

            Intent intent = new Intent(this, MinhaContaActivity.class);
            startActivity(intent);

        } else if (id == R.id.home) {

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);

        }//else if (id == R.id.pedidos) {

        //}
        else if (id == R.id.logoff) {

            // Intent intent = new Intent(this, MainActivity.class);
            // startActivity(intent);
            this.finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void proximo (View view)
    {
        for (Empresa empresa: lstEmpresas) {
            if(empresa.getChecked()){
                Intent it = new Intent(this,ListaProdutosActivity.class);
                it.putExtra("id_empresa",empresa.getId_empresa());
                startActivity(it);
            }

        }

    }
}
