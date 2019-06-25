package cz.uhk.umte.umte_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.uhk.umte.umte_final.Listeners.FilmViewListener;
import cz.uhk.umte.umte_final.Manager.SessionManager;
import cz.uhk.umte.umte_final.adapter.FilmFeedAdapter;
import cz.uhk.umte.umte_final.model.Director;
import cz.uhk.umte.umte_final.model.Film;

public class FilmListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FilmViewListener {

    private SearchView searchView;
    private List<Film> films;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sessionManager = new SessionManager(this);

        //RECYCLERVIEW
        recyclerView = findViewById(R.id.recyclerView);

        //MANAGER LINEAR - budou pod sebou
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        loadData();
    }

    private void doMySearch(String query) {

        List<Film> filteredFilms = new ArrayList<>();

        if (query.length()>1){

            for (Film f : films){
                String normalized1 = Normalizer.normalize(f.nazev, Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                String normalized2 = Normalizer.normalize(query, Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

                if (normalized1.toLowerCase().startsWith(normalized2.toLowerCase()) || f.anglickyNazev.toLowerCase().startsWith(query.toLowerCase())){
                    filteredFilms.add(f);
                }
            }
            renderView(filteredFilms);
        }else{
            renderView(filteredFilms);
        }
    }


    //ADAPTER
    private void renderView(List<Film> list){
        adapter = new FilmFeedAdapter(list, this);
        recyclerView.setAdapter(adapter);
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
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                doMySearch(s);
                return false;
            }
        });
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
            finish();
        } else if (id == R.id.nav_films) {

        } else if (id == R.id.nav_actors) {
            Intent intent = new Intent(FilmListActivity.this, ActorListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_directors) {
            Intent intent = new Intent(FilmListActivity.this, DirectorListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_top10) {
            Intent intent = new Intent(FilmListActivity.this, Top10Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_log_in) {
            Intent intent = new Intent(FilmListActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_profile) {
            if (sessionManager.isLoggedIn()) {
                Intent intent = new Intent(FilmListActivity.this, MyProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(FilmListActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClicked(Film f) {
        Intent intent = new Intent(FilmListActivity.this, FilmDetailActivity.class);
        intent.putExtra("id", Integer.toString(f.getId()));
        intent.putExtra("filmName", f.getNazev());
        intent.putExtra("originalFilmName", f.getAnglickyNazev());
        intent.putExtra("filmYear", Integer.toString(f.getRok()));
        intent.putExtra("filmDirector", f.getReziser().getJmeno() + " " + f.getReziser().getPrijmeni());
        intent.putExtra("filmCountry", f.getZemeNataceni());
        intent.putExtra("filmGenre", f.getGenre());
        intent.putExtra("filmImageName", f.getAdresar());

        startActivityForResult(intent, 1001);
    }

    public void loadData(){

        String URL="https://umte-final.000webhostapp.com/filmList.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("")){
                            films = new ArrayList<>();
                            JSONArray test = null;
                            int id;
                            String title = null;
                            String nameOrigin = null;
                            int year;
                            String state = null;
                            String genre = null;
                            int directorid;
                            String firstName = null;
                            String lastName = null;
                            String imageName;
                            try {
                                test = new JSONArray(response);


                                for (int i = 0; i<1; i++){
                                    JSONArray testArray = test.getJSONArray(0);
                                    for (int j = 0 ; j<testArray.length();j++){
                                        JSONArray testObject = testArray.getJSONArray(j);
                                        id = testObject.getInt(0);
                                        title = testObject.getString(1);
                                        nameOrigin = testObject.getString(2);
                                        year = testObject.getInt(3);
                                        state = testObject.getString(4);
                                        genre = testObject.getString(5);
                                        imageName = testObject.getString(6);
                                        directorid = testObject.getInt(7);
                                        firstName = testObject.getString(8);
                                        lastName = testObject.getString(9);
                                        Film film = new Film(id,title, nameOrigin, year, state, genre, imageName);
                                        Director director = new Director(directorid,firstName, lastName, 0, "","","","","");
                                        film.setReziser(director);
                                        films.add(film);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                return params;
            }

        };
        Volley.newRequestQueue(this).add(postRequest);
    }
}
