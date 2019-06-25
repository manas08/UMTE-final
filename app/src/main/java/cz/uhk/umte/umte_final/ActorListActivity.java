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
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cz.uhk.umte.umte_final.Listeners.ActorViewListener;
import cz.uhk.umte.umte_final.Manager.SessionManager;
import cz.uhk.umte.umte_final.adapter.ActorFeedAdapter;
import cz.uhk.umte.umte_final.model.Actor;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class ActorListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActorViewListener {

    private List<Actor> actors;

    private SearchView searchView;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_list);
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
        recyclerView = findViewById(R.id.recyclerViewActors);

        //MANAGER LINEAR - budou pod sebou
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        loadData();
    }

    private void doMySearch(String query) {
        List<Actor> filteredActors = new ArrayList<>();

        if (query.length()>1){
            for (Actor a : actors){
                String normalized1 = Normalizer.normalize(a.jmeno, Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                String normalized2 = Normalizer.normalize(query, Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                String normalized3 = Normalizer.normalize(a.prijmeni, Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                String normalized4 = Normalizer.normalize(query, Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

                if (normalized1.toLowerCase().startsWith(normalized2.toLowerCase()) || normalized3.toLowerCase().startsWith(normalized4.toLowerCase())){
                    filteredActors.add(a);
                }
            }
            renderView(filteredActors);
        }else{
            renderView(filteredActors);
        }
    }


    //ADAPTER
    private void renderView(List<Actor> list){
        adapter = new ActorFeedAdapter(list, this);
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
        searchView = (SearchView) findViewById(R.id.searchActors);
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
            Intent intent = new Intent(ActorListActivity.this, FilmListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_actors) {
            Intent intent = new Intent(ActorListActivity.this, ActorListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_directors) {
            Intent intent = new Intent(ActorListActivity.this, DirectorListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_top10) {
            Intent intent = new Intent(ActorListActivity.this, Top10Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_log_in) {
            Intent intent = new Intent(ActorListActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_profile) {
            if (sessionManager.isLoggedIn()) {
                Intent intent = new Intent(ActorListActivity.this, MyProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(ActorListActivity.this, LoginActivity.class);
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
    public void onClicked(Actor f) {
        Intent intent = new Intent(ActorListActivity.this, ActorDetailActivity.class);
        intent.putExtra("id", Integer.toString(f.getId()));
        intent.putExtra("actorFirst", f.getJmeno());
        intent.putExtra("actorLast", f.getPrijmeni());
        intent.putExtra("actorAge", Integer.toString(f.getVek()));
        intent.putExtra("placeBirth", f.getMistoNarozeni());
        intent.putExtra("dateBirth", f.getDatumNarozeni());
        intent.putExtra("placeDeath", f.getMistoUmrti());
        intent.putExtra("dateDeath", f.getDatumUmrti());
        intent.putExtra("actorImageName", f.getAdresar());

        startActivityForResult(intent, 1001);
    }

    public void loadData(){

        String URL="https://umte-final.000webhostapp.com/actorList.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("")){
                            actors = new ArrayList<>();
                            JSONArray test = null;
                            int id;
                            String firstName = null;
                            String lastName = null;
                            String birthDate = null;
                            String birthPlace = null;
                            String deathDate = null;
                            String deathPlace = null;
                            String imageName = null;
                            try {
                                test = new JSONArray(response);


                                for (int i = 0; i<1; i++){
                                    JSONArray testArray = test.getJSONArray(0);
                                    for (int j = 0 ; j<testArray.length();j++){
                                        JSONArray testObject = testArray.getJSONArray(j);
                                        id = testObject.getInt(0);
                                        firstName = testObject.getString(1);
                                        lastName = testObject.getString(2);
                                        birthDate = testObject.getString(3);
                                        birthPlace = testObject.getString(4);
                                        deathDate = testObject.getString(5);
                                        deathPlace = testObject.getString(6);
                                        imageName = testObject.getString(7);

                                        Calendar a = getCalendar(birthDate);
                                        Calendar b = getCalendar(deathDate);
                                        int vek = b.get(YEAR) - a.get(YEAR);
                                        if (a.get(MONTH) > b.get(MONTH) ||
                                                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
                                            vek--;
                                        }
                                        String birth = a.get(DAY_OF_MONTH) + ". " + a.get(MONTH) + ". " + a.get(YEAR);
                                        String death = deathDate;
                                        if (!deathDate.equals(""))
                                            death = b.get(DAY_OF_MONTH) + ". " + b.get(MONTH) + ". " + b.get(YEAR);

                                        Actor actor = new Actor(id,firstName,lastName,vek,birth,birthPlace,death,deathPlace,imageName);
                                        actors.add(actor);
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

    public static Calendar getCalendar(String date) {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            if (!date.equals(""))
                cal.setTime(df.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }
}
