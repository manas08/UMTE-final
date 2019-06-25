package cz.uhk.umte.umte_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;

import cz.uhk.umte.umte_final.Manager.SessionManager;
import cz.uhk.umte.umte_final.fragments.FilmAddRatingFrag;
import cz.uhk.umte.umte_final.fragments.FilmDetailFrag;
import cz.uhk.umte.umte_final.fragments.FilmRatingFrag;
import cz.uhk.umte.umte_final.fragments.Top10ActorsFrag;
import cz.uhk.umte.umte_final.fragments.Top10DirectorsFrag;
import cz.uhk.umte.umte_final.fragments.Top10FilmsFrag;

public class Top10Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button films, actors, directors;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment filmRanking, actorRanking, directorRanking;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Top 10");

        sessionManager = new SessionManager(this);
        fragmentManager = getSupportFragmentManager();

        filmRanking = new Top10FilmsFrag();
        actorRanking = new Top10ActorsFrag();
        directorRanking = new Top10DirectorsFrag();

        films = findViewById(R.id.rankSwitch1);
        actors = findViewById(R.id.rankSwitch2);
        directors = findViewById(R.id.rankSwitch3);


        films.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFragmentTop10, filmRanking, null);
                fragmentTransaction.commit();
                films.setEnabled(false);
                actors.setEnabled(true);
                directors.setEnabled(true);
            }
        });

        actors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFragmentTop10, actorRanking, null);
                fragmentTransaction.commit();
                films.setEnabled(true);
                actors.setEnabled(false);
                directors.setEnabled(true);
            }
        });

        directors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFragmentTop10, directorRanking, null);
                fragmentTransaction.commit();
                films.setEnabled(true);
                actors.setEnabled(true);
                directors.setEnabled(false);
            }
        });


        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragmentTop10, filmRanking, null);
        fragmentTransaction.commit();
        films.setEnabled(false);
        actors.setEnabled(true);
        directors.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            Intent intent = new Intent(Top10Activity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_films) {
            Intent intent = new Intent(Top10Activity.this, FilmListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_actors) {
            Intent intent = new Intent(Top10Activity.this, ActorListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_directors) {
            Intent intent = new Intent(Top10Activity.this, DirectorListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_log_in) {
            Intent intent = new Intent(Top10Activity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_profile) {
            if (sessionManager.isLoggedIn()) {
                Intent intent = new Intent(Top10Activity.this, MyProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(Top10Activity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
