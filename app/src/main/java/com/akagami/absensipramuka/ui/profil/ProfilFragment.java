package com.akagami.absensipramuka.ui.profil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akagami.absensipramuka.API;
import com.akagami.absensipramuka.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ProfilFragment extends Fragment {

    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfilViewModel profilViewModel = ViewModelProviders.of(this).get(ProfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profil, container, false);
//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        profilViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        view = v;
        super.onViewCreated(view, savedInstanceState);

        callApi();
    }

    private void callApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASEURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String username = sharedPreferences.getString("username", "empty");

        Call<String> call = api.postProfilSiswa(username);
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
                                tampilProfil(jsonObject);
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

    private void tampilProfil(JSONObject jsonObject) throws JSONException{
        JSONObject data = jsonObject.getJSONObject("data");

        ImageView avatar = view.findViewById(R.id.profil_avatar);
        Picasso.get().load(data.getString("avatar")).into(avatar);

        TextView nama = view.findViewById(R.id.profil_nama);
        nama.setText(data.getString("nama_siswa"));

        TextView tempat = view.findViewById(R.id.profil_ttl);
        tempat.setText(String.format("%s %s", data.getString("tempat_lahir_siswa"), data.getString("tanggal_lahir_siswa")));

        TextView alamat = view.findViewById(R.id.profil_alamat);
        alamat.setText(data.getString("alamat_siswa"));

        TextView kelas = view.findViewById(R.id.profil_kelas);
        kelas.setText(data.getString("kelas_siswa"));

        TextView jurusan = view.findViewById(R.id.profil_jurusan);
        jurusan.setText(data.getString("jurusan_siswa"));

        TextView wali = view.findViewById(R.id.profil_nama_wali);
        wali.setText(data.getString("nama_wali"));
    }
}
