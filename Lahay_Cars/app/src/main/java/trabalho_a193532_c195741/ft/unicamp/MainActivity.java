package trabalho_a193532_c195741.ft.unicamp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URI;
import java.security.Permission;

import trabalho_a193532_c195741.ft.unicamp.carros.CarrosFragment;
import trabalho_a193532_c195741.ft.unicamp.detalhescarro.DetalhesFragment;
import trabalho_a193532_c195741.ft.unicamp.carros.venda.VendaFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final int GALLERIA_IMAGENS =1;
    private final int PERMISSON_REQUEST = 2;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

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
        }

        if (id == R.id.nav_comprar) {
            Fragment carrosFragment = fragmentManager.findFragmentByTag("carros_fragment");
            if (carrosFragment == null)
                carrosFragment = new CarrosFragment();
            replaceFragment(carrosFragment, "carros_fragment");



        } else if (id == R.id.nav_vender) {
            Fragment vendaFragment = fragmentManager.findFragmentByTag("venda_Fragment");
            if (vendaFragment == null)
                vendaFragment = new VendaFragment();
            replaceFragment(vendaFragment, "venda_Fragment");


        } else if (id == R.id.nav_send) {
            Fragment sobreFragment = fragmentManager.findFragmentByTag("sobre_Fragment");
            if (sobreFragment == null)
                sobreFragment = new SobreFragment();
            replaceFragment(sobreFragment, "sobre_Fragment");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /*public void abrirAutoresFragment(String mensagem) {
        AutoresFragment autoresFragment = (AutoresFragment) fragmentManager.findFragmentByTag("autores_fragment");
        if (autoresFragment == null)
            autoresFragment = new AutoresFragment();
        autoresFragment.setMailContent(mensagem);
        replaceFragment(autoresFragment, "autores_fragment");
    }*/


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
    super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK && requestCode == GALLERIA_IMAGENS){
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath, null, null ,null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap imagemGaleria = (BitmapFactory.decodeFile(picturePath));
            ((VendaFragment) fragmentManager.findFragmentByTag("venda_Fragment")).setImageGaleria(imagemGaleria);
        }
    }

    public void onRequestPermissonResult (int requestCode, String permisson[], int[] gratResult) {
        if (requestCode == PERMISSON_REQUEST) {
            if (gratResult.length > 0 && gratResult[0] == PackageManager.PERMISSION_GRANTED) {

            } else {

            }
            return;
        }


    }
}

