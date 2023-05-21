package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LaporanActivity extends AppCompatActivity {
    AdapterLaporan adapterLaporan;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<ItemTransaksi> listTransaksi;
    RecyclerView rv_trans;
    EditText pencarian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        pencarian = findViewById(R.id.edPencarianLp);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        pencarian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter();
            }
        });

        rv_trans = findViewById(R.id.rvTr);
        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        rv_trans.setLayoutManager(mLayout);
        rv_trans.setItemAnimator(new DefaultItemAnimator());

        tampilData();

    }

    private void tampilData() {
        database.child("transaksi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listTransaksi = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren())
                {
                    ItemTransaksi ItemTr = item.getValue(ItemTransaksi.class);
                    ItemTr.setKey_tr(item.getKey());
                    listTransaksi.add(ItemTr);

                }
                adapterLaporan = new AdapterLaporan(listTransaksi, LaporanActivity.this);
                rv_trans.setAdapter(adapterLaporan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filter() {
        listTransaksi.clear();
        ArrayList<ItemTransaksi> filteredlist = new ArrayList<ItemTransaksi>();
        String cari = pencarian.getText().toString();
        if(!pencarian.getText().equals("")){
            database.child("transaksi").orderByChild("waktuTransaksi").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot item : snapshot.getChildren())
                    {
                        ItemTransaksi ItemTr = item.getValue(ItemTransaksi.class);
                        ItemTr.setKey_tr(item.getKey());

                        if (String.valueOf(ItemTr.getWaktuTransaksi()).contains(cari)) {
                            filteredlist.add(ItemTr);
                        }
                    }
                    if(filteredlist.isEmpty()){
                        Toast.makeText(LaporanActivity.this, "Data yang dicari tidak ada", Toast.LENGTH_SHORT).show();
                    }else{
                        adapterLaporan = new AdapterLaporan(filteredlist, LaporanActivity.this);
                        rv_trans.setAdapter(adapterLaporan);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

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