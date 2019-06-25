package cz.uhk.umte.umte_final.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cz.uhk.umte.umte_final.FilmDetailActivity;
import cz.uhk.umte.umte_final.Listeners.Top10FilmsViewListener;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.adapter.Top10FilmsFeedAdapter;
import cz.uhk.umte.umte_final.model.Director;
import cz.uhk.umte.umte_final.model.Film;

public class Top10FilmsFrag extends Fragment implements Top10FilmsViewListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;

    List<Film> films;
    List<Float> value;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_top10_films, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //RECYCLERVIEW
        recyclerView = getView().findViewById(R.id.recyclerViewTop10Films);

        //MANAGER LINEAR - budou pod sebou
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        loadData();
    }

    //ADAPTER
    private void renderView(List<Film> list){
        adapter = new Top10FilmsFeedAdapter(list, this, value);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClicked(Film f) {
        Intent intent = new Intent(getActivity(), FilmDetailActivity.class);
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

        String URL="https://umte-final.000webhostapp.com/top10Films.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("")){
                            films = new ArrayList<>();
                            value = new ArrayList<>();
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
                            String imageName = null;
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
                                        if (!testObject.getString(10).equals("null")){
                                            value.add(Float.parseFloat(testObject.getString(10)));
                                        } else {
                                            float v = 0;
                                            value.add(v);
                                        }
                                        Film film = new Film(id,title, nameOrigin, year, state, genre, imageName);
                                        Director director = new Director(directorid,firstName, lastName, 0, "","","","", "");
                                        film.setReziser(director);
                                        films.add(film);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            renderView(films);
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
        Volley.newRequestQueue(getContext()).add(postRequest);
    }
}
