package com.example.contact;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lstDanhBa;
    private Button btnAdd;
    private Button btnDelete;
    private DanhBaAdapter adapter;
    private EditText edtSearch ;
    //private ImageView btnSearch;
    private ArrayList<DanhBa> danhBa;
    private ArrayList<DanhBa> danhBabackup;
    private FloatingActionButton btnAddFloating;
    public static ArrayList<Integer> checks = new ArrayList<>();
    int selectedId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map();
        Database db = new Database(this,Database.DB_NAME,null,Database.DB_VERSION);
        danhBa = db.getAll();
        adapter = new DanhBaAdapter(this,R.layout.view_item,danhBa);
        lstDanhBa.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this,AddActivity.class);
                startActivity(it);
            }
        });
        btnAddFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this,AddActivity.class);
                startActivity(it);
            }
        });



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xoa lien lac");
                builder.setMessage("Ban co chac muon xoa cac lien lac da danh dau");
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = "Danh ba: ";
                        for (int i:adapter.Checks
                        ) {
                            s += i +",";
                            db.deleteDanhBa(i);
                        }


                        Toast.makeText(MainActivity.this, s + " da bi xoa", Toast.LENGTH_LONG).show();
                        ArrayList<DanhBa> danhBa = db.getAll();
                        adapter = new DanhBaAdapter(MainActivity.this,R.layout.view_item,danhBa);
                        lstDanhBa.setAdapter(adapter);

                    }
                });

                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"Da huy thao tac xoa lien lac",Toast.LENGTH_LONG).show();
                    }
                });

                builder.create().show();

            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void map(){
        lstDanhBa = findViewById(R.id.lstDanhBa);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        edtSearch = findViewById(R.id.edtSearch);
        btnAddFloating = findViewById(R.id.btnToActivityAdd);
    }


}