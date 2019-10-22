package com.example.student.phantuantai_sqlite;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder builder;
    CustomApdater adt;
    ArrayList<NhanVien> nvs;
    ListView listView;
    EditText edtId,edtName;
    Button btnEdit,btnOption;
    DBContext db;
    int id;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder =  new AlertDialog.Builder(this);
        db = new DBContext(this);
        db.createTable(db.getWritableDatabase());
        Cursor c = db.getData("select * from NhanVien");
        nvs = new ArrayList<>();
        while (c.moveToNext()){
            NhanVien nv = new NhanVien();
            nv.setMaNV(c.getInt(c.getColumnIndex("maNV")));
            nv.setTenNV(c.getString(c.getColumnIndex("tenNV")));
            nvs.add(nv);
        }

        listView = findViewById(R.id.listview);
        edtId = findViewById(R.id.edtID);
        edtName = findViewById(R.id.edtName);
        btnOption  = findViewById(R.id.btnOption);
        btnEdit = findViewById(R.id.btnEdit);
        adt = new CustomApdater(this,nvs,R.layout.layout_list_view);
        listView.setAdapter(adt);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edtId.setEnabled(true);
                index = i;
                id = nvs.get(i).getMaNV();
                Display(nvs.get(i));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                builder.setMessage("Delete ?");
                builder.setCancelable(false);
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Delete();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });
    }
    public NhanVien createObj(){
        int id = Integer.parseInt(edtId.getText().toString());
        String name = edtName.getText().toString();
        NhanVien nv = new NhanVien(id,name);
        return  nv;
    }
    public void Add(View v){
        NhanVien nv = createObj();
        long index = db.insert(nv);
        if(index != -1){
            nvs.add(nv);
            adt.notifyDataSetChanged();
            Toast.makeText(this, "Inserted",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Insert fail",Toast.LENGTH_LONG).show();
        }
    }
    public void Delete(){
        if(db.delete(id)) {
            nvs.remove(nvs.get(index));
            adt.notifyDataSetChanged();
            Clear();
            Toast.makeText(this, "Deleted",Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(this, "Delete fail",Toast.LENGTH_LONG).show();
    }
    public void Display(NhanVien nv){
        edtId.setText(Integer.toString(nv.getMaNV()));
        edtName.setText(nv.getTenNV());
    }
    public void Clear(){
        edtId.setText("");
        edtName.setText("");
    }
    public void Edit(View v){
       if(db.update(createObj()) != -1) {
           nvs = new ArrayList<>();
           Cursor c = db.getData("select * from NhanVien");
           while (c.moveToNext()){
               NhanVien nv = new NhanVien();
               nv.setMaNV(c.getInt(c.getColumnIndex("maNV")));
               nv.setTenNV(c.getString(c.getColumnIndex("tenNV")));
               nvs.add(nv);
           }
           adt = new CustomApdater(this,nvs,R.layout.layout_list_view);
           listView.setAdapter(adt);
           //adt.notifyDataSetChanged();
           Toast.makeText(this, "Updated",Toast.LENGTH_LONG).show();
       }
       else {
           Toast.makeText(this, "Fail",Toast.LENGTH_LONG).show();
       }
    }
}
