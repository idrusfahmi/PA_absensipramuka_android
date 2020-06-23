package com.akagami.absensipramuka.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.akagami.absensipramuka.R;

import java.util.ArrayList;

public class AbsensiAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<AbsensiAdapterItems> listAbsensiAdapter;

    public AbsensiAdapter(ArrayList<AbsensiAdapterItems> listAbsensiAdapter, Context context) {
        this.listAbsensiAdapter = listAbsensiAdapter;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("fahmi", "bbb");
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_view_absensi_siswa, parent, false);
            final AbsensiAdapterItems items = listAbsensiAdapter.get(position);

            TextView bulan = convertView.findViewById(R.id.absensi_bulan);
            bulan.setText(items.getBulan());

            TextView absensi_1 = convertView.findViewById(R.id.absensi_1);
            absensi_1.setText(items.getMinggu_1());

            TextView absensi_2 = convertView.findViewById(R.id.absensi_2);
            absensi_2.setText(items.getMinggu_2());

            TextView absensi_3 = convertView.findViewById(R.id.absensi_3);
            absensi_3.setText(items.getMinggu_3());

            TextView absensi_4 = convertView.findViewById(R.id.absensi_4);
            absensi_4.setText(items.getMinggu_4());

            TextView absensi_5 = convertView.findViewById(R.id.absensi_5);
            absensi_5.setText(items.getMinggu_5());

            Log.i("fahmi", "aaa");
        }
        Log.i("fahmi", "ccc");
        return convertView;

    }
}
