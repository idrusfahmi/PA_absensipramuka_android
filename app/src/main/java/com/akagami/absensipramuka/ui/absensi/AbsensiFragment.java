package com.akagami.absensipramuka.ui.absensi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.akagami.absensipramuka.API;
import com.akagami.absensipramuka.R;
import com.akagami.absensipramuka.adapter.AbsensiAdapter;
import com.akagami.absensipramuka.adapter.AbsensiAdapterItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AbsensiFragment extends Fragment {

    private View view;
    private ListView listView;
    private ArrayList<AbsensiAdapterItems> listAbsensiData = new ArrayList<>();
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AbsensiViewModel absensiViewModel = ViewModelProviders.of(this).get(AbsensiViewModel.class);
        return inflater.inflate(R.layout.fragment_absensi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view = v;

        listView = view.findViewById(R.id.lv_absensi);
        callApiAbsensi();
    }

    private void callApiAbsensi() {
        listAbsensiData.clear();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASEURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String username = sharedPreferences.getString("username", "empty");

        Call<String> call = api.postAbsensiSiswa(username);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> res) {
                if (res.isSuccessful() && res.body() != null) {
                    String jsonResponse = res.body();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        String status = jsonObject.getString("status");
                        if(status.equals("sukses")){
                            try{
                                absensiSiswa(jsonObject);
                            } catch (JSONException e){
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(getContext(), "Kesalahan Sistem", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Kesalahan Sistem", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void absensiSiswa(JSONObject jsonObject) throws JSONException {
        JSONArray data = jsonObject.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);

            String bulan = object.getString("bulan");

            String mg1;
            if (object.has("minggu_1")) {
                mg1 = object.getString("minggu_1");
            }
            else{
                mg1 = "-";
            }

            String mg2;
            if (object.has("minggu_2")) {
                mg2 = object.getString("minggu_2");
            }
            else{
                mg2 = "-";
            }

            String mg3;
            if (object.has("minggu_3")) {
                mg3 = object.getString("minggu_3");
            }
            else{
                mg3 = "-";
            }

            String mg4;
            if (object.has("minggu_4")) {
                mg4 = object.getString("minggu_4");
            }
            else{
                mg4 = "-";
            }

            String mg5;
            if (object.has("minggu_5")) {
                mg5 = object.getString("minggu_5");
            }
            else{
                mg5 = "-";
            }

            listAbsensiData.add(new AbsensiAdapterItems(
                    bulan, mg1, mg2, mg3, mg4, mg5
            ));
        }

        AbsensiAdapter absensiAdapter = new AbsensiAdapter(listAbsensiData, getContext());
        listView.setAdapter(absensiAdapter);
    }
}
