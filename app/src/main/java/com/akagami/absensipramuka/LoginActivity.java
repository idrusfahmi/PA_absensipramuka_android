package com.akagami.absensipramuka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = findViewById(R.id.btlogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApiLogin();
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String session = sharedPreferences.getString("username", "empty");
        if(!Objects.equals(session, "empty")){
            sharedPreferences.edit().clear().apply();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    private void callApiLogin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASEURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);

        EditText etusername = findViewById(R.id.username);
        EditText etpassword = findViewById(R.id.password);
        String username = etusername.getText().toString();
        String password = etpassword.getText().toString();

        Call<String> call = api.postLogin(username, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> res) {
                if (res.isSuccessful() && res.body() != null) {
                    String jsonResponse = res.body();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        String status = jsonObject.getString("status");
                        if(status.equals("sukses")){
                            sessionLogin(jsonObject);
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Kesalahan Sistem", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Kesalahan Sistem", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sessionLogin(JSONObject jsonObject) throws JSONException{
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putString("username", jsonObject.getString("username")).apply();
        sharedPreferences.edit().putString("jabatan", jsonObject.getString("jabatan")).apply();
        sharedPreferences.edit().putString("nama", jsonObject.getString("nama")).apply();
        sharedPreferences.edit().putString("avatar", jsonObject.getString("avatar")).apply();

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}