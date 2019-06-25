package cz.uhk.umte.umte_final.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import cz.uhk.umte.umte_final.Manager.SessionManager;
import cz.uhk.umte.umte_final.MyProfileActivity;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Director;
import cz.uhk.umte.umte_final.model.Film;

public class RegistrationFrag extends Fragment {

    EditText login, password, confirmPassword, email;
    Button btn;
    SessionManager sessionManager;
    String securePassword;
    int pom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_registration_frag, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        login = getView().findViewById(R.id.regUsername);
        password = getView().findViewById(R.id.regPassword);
        confirmPassword = getView().findViewById(R.id.regConfirmPassword);
        email = getView().findViewById(R.id.regEmail);
        btn = getView().findViewById(R.id.regEnter);
        sessionManager = new SessionManager(getContext());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pom=0;
                validation();
            }
        });
    }

    private static String getSecurePassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(salt);
            //Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public void register(){
        String URL="https://umte-final.000webhostapp.com/register.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("1")){
                            sessionManager = new SessionManager(getContext());
                            sessionManager.createLoginSession(login.getText().toString(),email.getText().toString());
                            startActivity(new Intent(getContext(), MyProfileActivity.class));
                            getActivity().finish();
                        }else {
                            Toast.makeText(getContext(),"Spatne heslo nebo login." + response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"Chyba s pripojenim." ,Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("login", login.getText().toString());
                params.put("password", securePassword);
                params.put("email", email.getText().toString());
                return params;
            }

        };
        Volley.newRequestQueue(getContext()).add(postRequest);
    }

    public void validation(){
        String URL="https://umte-final.000webhostapp.com/validation.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        JSONArray test = null;
                        String login = null;
                        String email = null;
                        try {
                            test = new JSONArray(response);


                            for (int i = 0; i<1; i++){
                                JSONObject testObject = test.getJSONObject(0);
                                login = testObject.getString("login");
                                email = testObject.getString("email");
                                System.out.println(login + " " + email + "TTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                                if (!login.equals("null") || !email.equals("null")){
                                    pom = 1;
                                }
                            }

                            if (pom==1){
                                Toast.makeText(getContext(),"Uživatel s tímto loginem nebo emailem již existuje." ,Toast.LENGTH_LONG).show();
                            }else {
                                if (password.getText().toString().equals(confirmPassword.getText().toString())){
                                    byte[] salt = new byte[16];
                                    securePassword = getSecurePassword(password.getText().toString(), salt);
                                    register();
                                } else{
                                    Toast.makeText(getContext(),"Spatne zadene hesla." ,Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"Chyba s pripojenim." ,Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("login", login.getText().toString());
                params.put("email", email.getText().toString());
                return params;
            }

        };
        Volley.newRequestQueue(getContext()).add(postRequest);
    }
}
