package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListItemTransaksi extends AppCompatActivity {
    AdapterListItem adapterListItem;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<ItemMainan> listIem;
    RecyclerView rv_item;
    EditText pencarian;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_transaksi);
        pencarian = findViewById(R.id.edPencarianItem);

        spinner = findViewById(R.id.sp_filterItem);
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

                    if(!pencarian.getText().equals("")){
                        filternama();
                    }
                }else if(spinner.getSelectedItem().equals("Harga")){
                    if(!pencarian.getText().equals("")){
                        filterharga();
                    }
                }else if(spinner.getSelectedItem().equals("Merk")){
                    if(!pencarian.getText().equals("")){
                        filterMerk();
                    }
                }else if(spinner.getSelectedItem().equals("Id")){
                    if(!pencarian.getText().equals("")){
                        filterId();
                    }
                }

            }
        });

        rv_item = findViewById(R.id.rvListItem);
        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        rv_item.setLayoutManager(mLayout);
        rv_item.setItemAnimator(new DefaultItemAnimator());

        tampilData();
    }

    private void filterId() {
        listIem.clear();
        ArrayList<ItemMainan> filteredlist = new ArrayList<ItemMainan>();
        String cari = pencarian.getText().toString().toLowerCase();
        database.child("mainan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (!pencarian.getText().equals("")) {
                        ItemMainan mainan = item.getValue(ItemMainan.class);
                        mainan.setKey(item.getKey());

                        if (mainan.getKey().toLowerCase().contains(cari)) {
                            filteredlist.add(mainan);
                        }
                    }
                    if (!filteredlist.isEmpty()) {
                        adapterListItem = new AdapterListItem(filteredlist, ListItemTransaksi.this);
                        rv_item.setAdapter(adapterListItem);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filterharga() {
        listIem.clear();
        ArrayList<ItemMainan> filteredlist = new ArrayList<ItemMainan>();
        String cari = pencarian.getText().toString();
        database.child("mainan").orderByChild("harga").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (!pencarian.getText().equals("")) {
                        ItemMainan mainan = item.getValue(ItemMainan.class);
                        mainan.setKey(item.getKey());

                        if (String.valueOf(mainan.getHarga()).contains(cari)) {
                            filteredlist.add(mainan);
                        }
                        if (!filteredlist.isEmpty()) {
                            adapterListItem = new AdapterListItem(filteredlist, ListItemTransaksi.this);
                            rv_item.setAdapter(adapterListItem);
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
        listIem.clear();
        ArrayList<ItemMainan> filteredlist = new ArrayList<ItemMainan>();
        String cari = pencarian.getText().toString().toLowerCase();
        database.child("mainan").orderByChild("merk").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren())
                {
                    if(!pencarian.getText().equals("")){
                        ItemMainan mainan = item.getValue(ItemMainan.class);
                        mainan.setKey(item.getKey());

                        if (mainan.getMerk().toLowerCase().contains(cari)) {
                            filteredlist.add(mainan);
                        }
                        if (!filteredlist.isEmpty()) {
                            adapterListItem = new AdapterListItem(filteredlist, ListItemTransaksi.this);
                            rv_item.setAdapter(adapterListItem);
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
        listIem.clear();
        ArrayList<ItemMainan> filteredlist = new ArrayList<ItemMainan>();
        String cari = pencarian.getText().toString().toLowerCase();
        database.child("mainan").orderByChild("nama_mainan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (!pencarian.getText().equals("")) {
                        ItemMainan mainan = item.getValue(ItemMainan.class);
                        mainan.setKey(item.getKey());

                        if (mainan.getNama_mainan().toLowerCase().contains(cari)) {
                            filteredlist.add(mainan);
                        }
                        if (!filteredlist.isEmpty()) {
                            adapterListItem = new AdapterListItem(filteredlist, ListItemTransaksi.this);
                            rv_item.setAdapter(adapterListItem);
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
                listIem = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren())
                {
                    ItemMainan mainan = item.getValue(ItemMainan.class);
                    mainan.setKey(item.getKey());
                    listIem.add(mainan);

                }
                adapterListItem = new AdapterListItem(listIem, ListItemTransaksi.this);
                rv_item.setAdapter(adapterListItem);
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