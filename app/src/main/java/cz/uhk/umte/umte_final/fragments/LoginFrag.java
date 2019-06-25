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

public class LoginFrag extends Fragment {

    EditText login, password;
    Button btn;
    SessionManager sessionManager;
    String securePassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_login_frag, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        login = getView().findViewById(R.id.loginUsername);
        password = getView().findViewById(R.id.loginPassword);
        btn = getView().findViewById(R.id.loginEnter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                byte[] salt = new byte[16];
                securePassword = getSecurePassword(password.getText().toString(), salt);
                login();
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

    public void login(){

        String URL="https://umte-final.000webhostapp.com/login.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("")){
                            JSONArray test = null;
                            String inlogin = null;
                            String inemail = null;
                            try {
                                test = new JSONArray(response);
                                JSONObject testObject = test.getJSONObject(0);
                                inlogin = testObject.getString("login");
                                inemail = testObject.getString("email");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!inlogin.equals("null")){
                                sessionManager = new SessionManager(getContext());
                                sessionManager.createLoginSession(inlogin,inemail);
                                startActivity(new Intent(getContext(), MyProfileActivity.class));
                                getActivity().finish();
                            }else {
                                Toast.makeText(getContext(),"Spatne heslo nebo login.",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getContext(),"Spatne heslo nebo login.",Toast.LENGTH_LONG).show();
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
                params.put("username", login.getText().toString());
                params.put("password", securePassword);
                return params;
            }

        };
        Volley.newRequestQueue(getContext()).add(postRequest);
    }
}
