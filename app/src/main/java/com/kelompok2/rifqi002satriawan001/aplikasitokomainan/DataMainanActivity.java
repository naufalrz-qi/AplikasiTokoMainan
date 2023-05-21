package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataMainanActivity extends AppCompatActivity {
    AdapterMainan adapterMainan;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<ModelMainan> listMainan;
    RecyclerView rv_mainan;
    EditText pencarian;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_mainan);
        pencarian = findViewById(R.id.edPencarian);
        Button tambah = findViewById(R.id.btnTambah);

        spinner = findViewById(R.id.sp_filter);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinner.getSelectedItem().equals("Nama")){
                    pencarian.setInputType(InputType.TYPE_CLASS_TEXT);
                }else if(spinner.getSelectedItem().equals("Harga")){
                    pencarian.setInputType(InputType.TYPE_CLASS_NUMBER);
                    pencarian.setText("0");
                    if(pencarian.getText().toString().equals("")){
                        pencarian.setText("0");
                    }

                }else{
                    pencarian.setInputType(InputType.TYPE_CLASS_TEXT);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        pencarian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(spinner.getSelectedItem().equals("Nama")){

                        filternama();

                }else if(spinner.getSelectedItem().equals("Harga")){

                        filterharga();

                }else if(spinner.getSelectedItem().equals("Merk")){

                        filterMerk();

                }else if(spinner.getSelectedItem().equals("Id")){

                        filterId();
                }

            }
        });


        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataMainanActivity.this, CreateActivity.class));
            }
        });

        rv_mainan = findViewById(R.id.rv_mainan);
        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        rv_mainan.setLayoutManager(mLayout);
        rv_mainan.setItemAnimator(new DefaultItemAnimator());

        tampilData();
    }

    private void filterId() {
        listMainan.clear();
        ArrayList<ModelMainan> filteredlist = new ArrayList<ModelMainan>();
        String cari = pencarian.getText().toString().toLowerCase();
        database.child("mainan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (!pencarian.getText().equals("")) {
                        ModelMainan mainan = item.getValue(ModelMainan.class);
                        mainan.setKey(item.getKey());

                        if (mainan.getKey().toLowerCase().contains(cari)) {
                            filteredlist.add(mainan);
                        }
                    }
                    if (!filteredlist.isEmpty()) {
                        adapterMainan = new AdapterMainan(filteredlist, DataMainanActivity.this);
                        rv_mainan.setAdapter(adapterMainan);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filterharga() {
        listMainan.clear();
        ArrayList<ModelMainan> filteredlist = new ArrayList<ModelMainan>();
        String cari = pencarian.getText().toString();
        database.child("mainan").orderByChild("harga").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (!pencarian.getText().equals("")) {
                        ModelMainan mainan = item.getValue(ModelMainan.class);
                        mainan.setKey(item.getKey());

                        if (String.valueOf(mainan.getHarga()).contains(cari)) {
                            filteredlist.add(mainan);
                        }
                        if (!filteredlist.isEmpty()) {
                            adapterMainan = new AdapterMainan(filteredlist, DataMainanActivity.this);
                            rv_mainan.setAdapter(adapterMainan);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filterMerk() {
        listMainan.clear();
        ArrayList<ModelMainan> filteredlist = new ArrayList<ModelMainan>();
        String cari = pencarian.getText().toString().toLowerCase();
        database.child("mainan").orderByChild("merk").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren())
                {
                    if(!pencarian.getText().equals("")){
                        ModelMainan mainan = item.getValue(ModelMainan.class);
                        mainan.setKey(item.getKey());

                        if (mainan.getMerk().toLowerCase().contains(cari)) {
                            filteredlist.add(mainan);
                        }
                        if (!filteredlist.isEmpty()) {
                            adapterMainan = new AdapterMainan(filteredlist, DataMainanActivity.this);
                            rv_mainan.setAdapter(adapterMainan);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filternama() {
        listMainan.clear();
        ArrayList<ModelMainan> filteredlist = new ArrayList<ModelMainan>();
        String cari = pencarian.getText().toString().toLowerCase();
        database.child("mainan").orderByChild("nama_mainan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (!pencarian.getText().equals("")) {
                        ModelMainan mainan = item.getValue(ModelMainan.class);
                        mainan.setKey(item.getKey());

                        if (mainan.getNama_mainan().toLowerCase().contains(cari)) {
                            filteredlist.add(mainan);
                        }
                        if (!filteredlist.isEmpty()) {
                            adapterMainan = new AdapterMainan(filteredlist, DataMainanActivity.this);
                            rv_mainan.setAdapter(adapterMainan);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void tampilData() {
        database.child("mainan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMainan = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren())
                {
                    ModelMainan mainan = item.getValue(ModelMainan.class);

                    if (mainan != null) {
                        mainan.setKey(item.getKey());
                        listMainan.add(mainan);
                    }


                }
                adapterMainan = new AdapterMainan(listMainan, DataMainanActivity.this);
                rv_mainan.setAdapter(adapterMainan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}