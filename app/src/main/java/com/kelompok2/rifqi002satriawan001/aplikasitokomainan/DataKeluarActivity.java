package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;


@SuppressWarnings("ALL")
public class DataKeluarActivity extends AppCompatActivity {
    HashMap<String, String> hashMap = new HashMap<>();
    AdapterTransaksi adapterTransaksi;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<ItemMainan> listItem1;
    RecyclerView rv_Item;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    String userr =  FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(currentUser).getUid()).getKey();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_keluar);



        Button bAddItem = findViewById(R.id.btnTambahItem);
        Button bSimpanTr = findViewById(R.id.btnSimpanTr);
        Button cancel = findViewById(R.id.btnCancelTr);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = new Intent(DataKeluarActivity.this, TrKembalianAct.class);

        bAddItem.setOnClickListener(view -> startActivity(new Intent(DataKeluarActivity.this, ListItemTransaksi.class)));
        bSimpanTr.setOnClickListener(view -> {

                intent.putExtra("map", hashMap);
                startActivity(intent);
        });
        cancel.setOnClickListener(view -> {
            database.child(userr).removeValue();
            startActivity(new Intent(DataKeluarActivity.this, MainActivity.class));
            finish();
        });

        rv_Item = findViewById(R.id.rvItem);
        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        rv_Item.setLayoutManager(mLayout);
        rv_Item.setItemAnimator(new DefaultItemAnimator());

        tampilData();

    }


    @SuppressLint("SetTextI18n")
    private void tampilData() {
        TextView total = findViewById(R.id.tvBigTotal);
        TextView itemCount = findViewById(R.id.tvItemCount);
        NumberFormat formatter = new DecimalFormat("#,###");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss", Locale.getDefault());
        String date = dateFormat.format(new Date());
        TextView tvUser = findViewById(R.id.tvUser);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(currentUser).getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user usr = snapshot.getValue(user.class);
                tvUser.setText("User : "+ usr.getNama());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        TextView DateNTime = findViewById(R.id.tvDateTime);
        DateNTime.setText("Date/Time : " + date);

        database.child(userr).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listItem1= new ArrayList<ItemMainan>();
                int d = 0;
                int qty = 0;
                long sum = 0;
                for (DataSnapshot item : snapshot.getChildren())
                {
                    ItemMainan mainan = item.getValue(ItemMainan.class);
                    long value = item.child("harga").getValue(Long.class);
                    int qtyval = item.child("qty").getValue(int.class);
                    mainan.setKey(item.getKey());
                    long total = value;
                    int toqty = qtyval;
                    itemCount.setText("Jumlah item : "+ ++d);

                    listItem1.add(mainan);

                    sum = sum + total;
                    qty = qty + toqty;



                }


                String formattedNumber = formatter.format(sum);
                total.setText("Rp"+formattedNumber+".00");
                if(listItem1.isEmpty()){
                    itemCount.setText("Jumlah item : ");
                }

                hashMap.put("total", String.valueOf(sum));
                hashMap.put("jumlahItem", String.valueOf(d));
                hashMap.put("qty", String.valueOf(qty));
                adapterTransaksi = new AdapterTransaksi(listItem1, DataKeluarActivity.this);
                rv_Item.setAdapter(adapterTransaksi);
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