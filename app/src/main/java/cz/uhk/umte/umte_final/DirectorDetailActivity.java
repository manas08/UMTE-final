package cz.uhk.umte.umte_final;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Button;
import cz.uhk.umte.umte_final.Manager.SessionManager;
import cz.uhk.umte.umte_final.fragments.DirectorAddRatingFrag;
import cz.uhk.umte.umte_final.fragments.DirectorDetailFrag;
import cz.uhk.umte.umte_final.fragments.DirectorRatingFrag;

public class DirectorDetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button info, ratings, newRating;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment directorRatingFrag, directorDetailFrag, directorAddRating;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle(getIntent().getStringExtra("directorFirst") + " " + getIntent().getStringExtra("directorLast"));

        sessionManager = new SessionManager(this);
        fragmentManager = getSupportFragmentManager();

        directorRatingFrag = new DirectorRatingFrag();
        directorDetailFrag = new DirectorDetailFrag();
        directorAddRating = new DirectorAddRatingFrag();

        info = findViewById(R.id.directorSwitch1);
        ratings = findViewById(R.id.directorSwitch2);
        newRating = findViewById(R.id.directorSwitch3);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragmentDirector, directorDetailFrag, null);
        fragmentTransaction.commit();
        info.setEnabled(false);
        ratings.setEnabled(true);
        newRating.setEnabled(true);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFragmentDirector, directorDetailFrag, null);
                fragmentTransaction.commit();
                info.setEnabled(false);
                ratings.setEnabled(true);
                newRating.setEnabled(true);
            }
        });

        ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFragmentDirector, directorRatingFrag, null);
                fragmentTransaction.commit();
                info.setEnabled(true);
                ratings.setEnabled(false);
                newRating.setEnabled(true);
            }
        });

        newRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessionManager.isLoggedIn()){
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mainFragmentDirector, directorAddRating, null);
                    fragmentTransaction.commit();
                    info.setEnabled(true);
                    ratings.setEnabled(true);
                    newRating.setEnabled(false);
                }else {
                    Intent intent = new Intent(DirectorDetailActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });

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
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            Intent intent = new Intent(DirectorDetailActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_films) {
            Intent intent = new Intent(DirectorDetailActivity.this, FilmListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_actors) {
            Intent intent = new Intent(DirectorDetailActivity.this, ActorListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_directors) {
            finish();
        } else if (id == R.id.nav_top10) {
            Intent intent = new Intent(DirectorDetailActivity.this, Top10Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_log_in) {
            Intent intent = new Intent(DirectorDetailActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_profile) {
            if (sessionManager.isLoggedIn()) {
                Intent intent = new Intent(DirectorDetailActivity.this, MyProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(DirectorDetailActivity.this, LoginActivity.class);
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
