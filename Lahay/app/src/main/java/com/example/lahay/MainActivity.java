package com.example.lahay;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lahay.carrinho.CarrinhoFragment;
import com.example.lahay.comprar.Comprar;
import com.example.lahay.comprar.ComprarFragment;
import com.example.lahay.firstfrag.FirstFragment;
import com.example.lahay.gerenciar.GerenciarVendas;
import com.example.lahay.vender.VenderFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int PERMISSON_REQUEST = 2;
    FragmentManager fragmentManager;
    ArrayList <Comprar> listaCarrinho;
    Comprar carroDetalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaCarrinho = new ArrayList<>();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frame, new FirstFragment(), "first_fragment");
            fragmentTransaction.commit();
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSON_REQUEST);
            }
        }

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Fragment firstFragment = fragmentManager.findFragmentByTag("first_fragment");
            if (firstFragment == null)
                firstFragment = new FirstFragment();
            replaceFragment(firstFragment, "first_fragment");


        } else if (id == R.id.nav_comprar) {

            Fragment comprarFragment = fragmentManager.findFragmentByTag("comprar_fragment");
            if (comprarFragment == null)
                comprarFragment = new ComprarFragment();
            replaceFragment(comprarFragment, "comprar_fragment");

        } else if (id == R.id.nav_vender) {

            Fragment venderFragment = fragmentManager.findFragmentByTag("vender_fragment");
            if (venderFragment == null)
                venderFragment = new VenderFragment();
            replaceFragment(venderFragment, "vender_fragment");

        } else if (id == R.id.nav_carrinho) {

            if(listaCarrinho.isEmpty()){
                Toast.makeText(this, "Primeiro adicione um item no Carrinho", Toast.LENGTH_SHORT).show();

            }else{

                Fragment carrinhoFragment = fragmentManager.findFragmentByTag("carrinhoFragment");
                if (carrinhoFragment == null)
                    carrinhoFragment = new CarrinhoFragment();
                replaceFragment(carrinhoFragment, "carrinhoFragment");

            }

        } else if (id == R.id.nav_gerenciar) {

            Fragment gerenciarVendas = fragmentManager.findFragmentByTag("gerenciarVendas_fragment");
            if (gerenciarVendas == null)
                gerenciarVendas = new GerenciarVendas();
            replaceFragment(gerenciarVendas, "gerenciarVendas_fragment");

        } else if (id == R.id.nav_sobre) {

            Fragment sobreFragment = fragmentManager.findFragmentByTag("sobre_fragment");
            if (sobreFragment == null)
                sobreFragment = new Sobre();
            replaceFragment(sobreFragment, "sobre_fragment");

        } else if (id == R.id.nav_sair) {

            signOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void signOut(){

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(MainActivity.this, Login.class));
                        finish();
                    }
                });

    }

    public void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public ArrayList<Comprar> getListaCarrinho() {
        return listaCarrinho;
    }

    public void setListaCarrinho(ArrayList<Comprar> listaCarrinho) {
        this.listaCarrinho = listaCarrinho;
    }

    public void adicionarCarrinho(Comprar compra) {
        this.listaCarrinho.add(compra);
    }

    public void limparListaCarrinho(){
        this.listaCarrinho.clear();
    }

    public Comprar getCarroDetalhes() {
        return carroDetalhes;
    }

    public void setCarroDetalhes(Comprar carroDetalhes) {
        this.carroDetalhes = carroDetalhes;
    }

}
