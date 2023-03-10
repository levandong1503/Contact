package com.example.contact;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.CALL_PHONE;

public class DanhBaAdapter extends ArrayAdapter<DanhBa> {

    private Context c;
    private int res;
    private List<DanhBa> dsdb;
    private ArrayList<DanhBa> danhBaBackUp;
    private ArrayList<DanhBa> danhBaFilter;
    public ArrayList<Integer> Checks = new ArrayList<>();

    public DanhBaAdapter(@NonNull Context context, int resource, @NonNull List<DanhBa> objects) {
        super(context, resource, objects);
        c = context; res = resource; dsdb = objects; danhBaFilter = (ArrayList<DanhBa>) objects;
    }


    @Override
    public int getCount() {
        return danhBaFilter.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.view_item,null);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtPhone = convertView.findViewById(R.id.txtPhone);
        CheckBox ckb = convertView.findViewById(R.id.ckbChoose);
        ImageView btnCall = convertView.findViewById(R.id.imgCall);
        ImageView btnMess = convertView.findViewById(R.id.imgChat);

        DanhBa danhBa =  danhBaFilter.get(position);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = danhBa.getPhone();



                if (ContextCompat.checkSelfPermission(DanhBaAdapter.this.c.getApplicationContext(),CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED) {
                    if(!TextUtils.isEmpty(tel)){
                        String dial = "tel:"+tel;
                        DanhBaAdapter.this.c.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    }
                    else {
                        Toast.makeText(DanhBaAdapter.this.c,"So dien thoai khong hop le. Kiem tra lai",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    ((AppCompatActivity)DanhBaAdapter.this.c).requestPermissions(new String[]{CALL_PHONE}, 1);
                }

            }
        });


        btnMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = danhBa.getPhone();
                if(!TextUtils.isEmpty(phone)){
                    Intent it = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phone));
                    it.putExtra("sms_body","");
                    DanhBaAdapter.this.c.startActivity(it);
                }
                else{
                    Toast.makeText(DanhBaAdapter.this.c,"So dien thoai khong hop le", Toast.LENGTH_LONG);
                }
            }
        });

        ckb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity context = (MainActivity) c;
                if(isChecked) Checks.add(new Integer(danhBa.getId()));
                else Checks.remove(new Integer(danhBa.getId()));
            }
        });

        txtName.setText(danhBa.getName());
        txtPhone.setText(danhBa.getPhone());
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults fr = new FilterResults();
                if(danhBaBackUp == null){
                    danhBaBackUp = new ArrayList<>(dsdb);
                }
                if(constraint == null || constraint.length() == 0){
                    fr.count = dsdb.size();
                    fr.values = dsdb;
                }
                else{
                    ArrayList<DanhBa> newData = new ArrayList<>();
                    for(DanhBa i : dsdb){
                        if(i.getName().toLowerCase().contains(
                                constraint.toString().toLowerCase()
                        )){ newData.add(i); }
                    }
                    fr.count = newData.size();
                    fr.values = newData;
                }

                return fr;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                    ArrayList<DanhBa> data = new ArrayList<>();
                    ArrayList<DanhBa> tmp = (ArrayList<DanhBa>) results.values;
                for (DanhBa i: tmp
                     ) {
                    data.add(i);
                }

                danhBaFilter = data;
                notifyDataSetChanged();
            }
        };
        return f;
    }
}
