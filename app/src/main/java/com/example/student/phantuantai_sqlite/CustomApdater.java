package com.example.student.phantuantai_sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomApdater extends BaseAdapter {
    Context context;
    ArrayList<NhanVien> nvs;
    int layoutID;

    public CustomApdater(Context context, ArrayList<NhanVien> nv, int layoutID) {
        this.context = context;
        this.nvs = nv;
        this.layoutID = layoutID;
    }

    @Override
    public int getCount() {
        return nvs.size();
    }

    @Override
    public Object getItem(int i) {
        return nvs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return nvs.get(i).getMaNV();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(layoutID,null);
        TextView id = view.findViewById(R.id.id);
        TextView name = view.findViewById(R.id.name);
        NhanVien nv = nvs.get(i);
        id.setText(Integer.toString(nv.getMaNV()));
        name.setText(nv.getTenNV());
        return view;
    }
}
