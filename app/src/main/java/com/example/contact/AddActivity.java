package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtPhone;
    private Button btnAdd;
    private Button btnBack;
    private boolean isEdited = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Database db = new Database(this,Database.DB_NAME,null, Database.DB_VERSION);
        setContentView(R.layout.activity_add);
        map();
        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        if(bundle != null){
            isEdited = true;
            edtName.setText(bundle.getString("Name"));
            edtPhone.setText(bundle.getString("Phone"));
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanhBa danhBa = new DanhBa(
                        edtName.getText().toString(),
                        edtPhone.getText().toString()
                );
                db.addDanhBa(danhBa);
                Intent it = new Intent(AddActivity.this,MainActivity.class);

                Bundle bundle1 = new Bundle();

                startActivity(it);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AddActivity.this,MainActivity.class);
                startActivity(it);

                /*
                setResult(400);
                finish();

                 */
            }
        });
    }

    private void map(){
        edtName = findViewById(R.id.edtNameUpdate);
        edtPhone = findViewById(R.id.edtPhoneUpdate);
        btnAdd = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.btnBackAdd);
    }
}