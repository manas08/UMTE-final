package cz.uhk.umte.umte_final.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import cz.uhk.umte.umte_final.Manager.SessionManager;
import cz.uhk.umte.umte_final.R;

public class DirectorAddRatingFrag extends Fragment {

    EditText ratingText;
    RatingBar ratingStars;
    Button save;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_director_add_rating, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ratingText = getView().findViewById(R.id.directorAddRatingText);
        ratingStars = getView().findViewById(R.id.directorAddRatingStars);
        save = getView().findViewById(R.id.directorAddRating);

        sessionManager = new SessionManager(getContext());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRating();
            }
        });
    }

    public void addRating(){

        String URL="https://umte-final.000webhostapp.com/addDirectorRating.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("1")){
                            Toast.makeText(getContext(),"Hodnocení vloženo.",Toast.LENGTH_LONG).show();
                            ratingStars.setRating(0);
                            ratingText.setText("");
                        }else {
                            Toast.makeText(getContext(),"Špatné heslo nebo login." + response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"Chyba s připojenim." ,Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("ratingText", ratingText.getText().toString());
                params.put("ratingStars", String.valueOf(ratingStars.getRating()));
                params.put("login", sessionManager.getUserLogin());
                params.put("director", getActivity().getIntent().getStringExtra("id"));
                return params;
            }

        };
        Volley.newRequestQueue(getContext()).add(postRequest);
    }
}
