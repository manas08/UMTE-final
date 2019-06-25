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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cz.uhk.umte.umte_final.ActorDetailActivity;
import cz.uhk.umte.umte_final.Listeners.Top10ActorsViewListener;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.adapter.Top10ActorsFeedAdapter;
import cz.uhk.umte.umte_final.model.Actor;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class Top10ActorsFrag extends Fragment implements Top10ActorsViewListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;

    List<Actor> actors;
    List<Float> value;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_top10_actors, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //RECYCLERVIEW
        recyclerView = getView().findViewById(R.id.recyclerViewTop10Actors);

        //MANAGER LINEAR - budou pod sebou
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        loadData();
    }

    //ADAPTER
    private void renderView(List<Actor> list){
        adapter = new Top10ActorsFeedAdapter(list, this, value);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClicked(Actor f) {
        Intent intent = new Intent(getActivity(), ActorDetailActivity.class);
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

        String URL="https://umte-final.000webhostapp.com/top10Actors.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("")){
                            actors = new ArrayList<>();
                            value = new ArrayList<>();
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

                                        if (!testObject.getString(8).equals("null")){
                                            value.add(Float.parseFloat(testObject.getString(8)));
                                        } else {
                                            float v = 0;
                                            value.add(v);
                                        }


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

                                        Actor actor = new Actor(id,firstName,lastName,vek,birth,birthPlace,death,deathPlace, imageName);
                                        actors.add(actor);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            renderView(actors);
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
