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
import android.widget.ImageView;
import android.widget.TextView;

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
import cz.uhk.umte.umte_final.Listeners.DirectorsFilmsViewListener;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.adapter.ActorsFilmsFeedAdapter;
import cz.uhk.umte.umte_final.adapter.DirectorsFilmsFeedAdapter;
import cz.uhk.umte.umte_final.model.Director;
import cz.uhk.umte.umte_final.model.Film;

public class DirectorDetailFrag extends Fragment implements DirectorsFilmsViewListener {

    private TextView directorLast, directorAge, placeBirth, dateBirth, placeDeath, dateDeath, director, death;
    private ImageView imageView;

    private List<Film> films;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_director_description, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        directorLast = getView().findViewById(R.id.directorLast);
        directorAge = getView().findViewById(R.id.directorAge);
        placeBirth = getView().findViewById(R.id.placeBirth);
        dateBirth = getView().findViewById(R.id.dateBirth);
        placeDeath = getView().findViewById(R.id.placeDeath);
        dateDeath = getView().findViewById(R.id.dateDeath);

        director = getView().findViewById(R.id.director);
        death = getView().findViewById(R.id.death);

        directorLast.setText(getActivity().getIntent().getStringExtra("directorFirst") + " " + getActivity().getIntent().getStringExtra("directorLast"));
        directorAge.setText(getActivity().getIntent().getStringExtra("directorAge"));
        placeBirth.setText(getActivity().getIntent().getStringExtra("placeBirth"));
        dateBirth.setText(getActivity().getIntent().getStringExtra("dateBirth"));
        placeDeath.setText(getActivity().getIntent().getStringExtra("placeDeath"));
        dateDeath.setText(getActivity().getIntent().getStringExtra("dateDeath"));

        if (getActivity().getIntent().getStringExtra("placeDeath").equals("")){
            director.setVisibility(View.INVISIBLE);
            death.setVisibility(View.INVISIBLE);
            placeDeath.setVisibility(View.INVISIBLE);
            dateDeath.setVisibility(View.INVISIBLE);
        }

        imageView = getView().findViewById(R.id.directorImageView);
        if (!getActivity().getIntent().getStringExtra("directorImageName").equals(""))
            imageView.setImageResource(getResources().getIdentifier(getActivity().getIntent().getStringExtra("directorImageName"), "drawable", getContext().getPackageName()));

        //RECYCLERVIEW
        recyclerView = getView().findViewById(R.id.recyclerDirectorsFilmsView);

        //MANAGER LINEAR - budou pod sebou
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        loadData();
    }


    public void loadData(){

        String URL="https://umte-final.000webhostapp.com/directorsFilmsList.php";
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
                params.put("director", getActivity().getIntent().getStringExtra("id"));
                return params;
            }

        };
        Volley.newRequestQueue(getContext()).add(postRequest);
    }

    //ADAPTER
    private void renderView(List<Film> list){
        adapter = new DirectorsFilmsFeedAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClicked(Film item) {
        Intent intent = new Intent(getContext(), FilmDetailActivity.class);
        intent.putExtra("id", Integer.toString(item.getId()));
        intent.putExtra("filmName", item.getNazev());
        intent.putExtra("originalFilmName", item.getAnglickyNazev());
        intent.putExtra("filmYear", Integer.toString(item.getRok()));
        intent.putExtra("filmDirector", item.getReziser().getJmeno() + " " + item.getReziser().getPrijmeni());
        intent.putExtra("filmCountry", item.getZemeNataceni());
        intent.putExtra("filmGenre", item.getGenre());
        intent.putExtra("filmImageName", item.getAdresar());

        startActivityForResult(intent, 1001);
    }
}
