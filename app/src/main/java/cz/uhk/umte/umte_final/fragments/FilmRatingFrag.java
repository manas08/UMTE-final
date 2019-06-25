package cz.uhk.umte.umte_final.fragments;

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
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.adapter.RatingFeedAdapter;
import cz.uhk.umte.umte_final.model.Ratings;

public class FilmRatingFrag extends Fragment {

    List<Ratings> ratings;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_film_ratings, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //RECYCLERVIEW
        recyclerView = getView().findViewById(R.id.recyclerViewRatings);

        //MANAGER LINEAR - budou pod sebou
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        loadData();
    }

    //ADAPTER
    private void renderView(List<Ratings> list){
        adapter = new RatingFeedAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    public void loadData(){
        String URL="https://umte-final.000webhostapp.com/filmListRatings.php";
        final StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("")){
                            ratings = new ArrayList<>();
                            JSONArray test = null;
                            int id;
                            int filmid;
                            String user = null;
                            float stars;
                            String ratingText = null;
                            try {
                                test = new JSONArray(response);


                                for (int i = 0; i<1; i++){
                                    JSONArray testArray = test.getJSONArray(0);
                                    for (int j = 0 ; j<testArray.length();j++){
                                        JSONArray testObject = testArray.getJSONArray(j);
                                        id = testObject.getInt(0);
                                        filmid = testObject.getInt(1);
                                        user = testObject.getString(2);
                                        ratingText = testObject.getString(3);
                                        stars = Float.parseFloat(testObject.getString(4));
                                        Ratings film = new Ratings(id,user,ratingText,stars);
                                        ratings.add(film);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            renderView(ratings);
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
                params.put("film", getActivity().getIntent().getStringExtra("id"));
                return params;
            }

        };
        Volley.newRequestQueue(getContext()).add(postRequest);
    }
}