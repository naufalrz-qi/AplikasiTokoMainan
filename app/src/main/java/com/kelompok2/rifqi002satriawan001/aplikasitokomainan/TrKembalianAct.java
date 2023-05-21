package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class TrKembalianAct extends AppCompatActivity {
    TextView tjumlahItem, tDateTime, tqty, totalharga, kembalian;
    EditText eUangTerima;
    Button bBack, bConfirm;
    ArrayList<ModelMainan> listItem;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    HashMap<String, String> map;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    String userr =  FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(currentUser).getUid()).getKey();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tr_kembalian);

        totalharga = findViewById(R.id.tvCTotalHarga);
        tjumlahItem = findViewById(R.id.tvCJumlahItem);
        kembalian = findViewById(R.id.tvBigKembalian);
        tqty = findViewById(R.id.tvCQty);
        eUangTerima = findViewById(R.id.cashCount);
        bBack = findViewById(R.id.btnBack);
        bConfirm = findViewById(R.id.btnConfirmTr);

        bConfirm.setEnabled(false);
        NumberFormat formatter = new DecimalFormat("#,###");

        bBack.setOnClickListener(view -> {
            startActivity(new Intent(TrKembalianAct.this,DataKeluarActivity.class));
            finish();
        });



        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss", Locale.getDefault());
        String date = dateFormat.format(new Date());

        tDateTime = findViewById(R.id.tvCTanggalJam);
        tDateTime.setText("Date/Time : " + date);
        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>)intent.getSerializableExtra("map");
        int JumlahItem = Integer.parseInt(hashMap.get("jumlahItem"));
        long totalHarga = Long.parseLong(hashMap.get("total"));
        int qty = Integer.parseInt(hashMap.get("qty"));


        totalharga.setText("Total harga jual : Rp"+formatter.format(totalHarga)+".00");
        tjumlahItem.setText("jumlah item : "+JumlahItem);
        tqty.setText("Total Qty : "+qty);
        kembalian.setText("Rp"+formatter.format(-totalHarga)+".00");

        eUangTerima.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(eUangTerima.getText().toString().equals("")){
                    eUangTerima.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(eUangTerima.getText().toString().equals("")){
                    eUangTerima.setText("0");
                }
                kembalian.setText("Rp"+formatter.format(Long.parseLong(eUangTerima.getText().toString())-totalHarga)+".00");
                bConfirm.setEnabled(Long.parseLong(eUangTerima.getText().toString()) - totalHarga >= 0);
            }
        });


        database.child(userr).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listItem = new ArrayList<ModelMainan>();
                map = new HashMap<String, String>();
                for ( DataSnapshot item : snapshot.getChildren()) {
                    ModelMainan mainan = item.getValue(ModelMainan.class);
                    mainan.setKey(item.getKey());
                    listItem.add(mainan);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        bConfirm.setOnClickListener(view -> {
            tambahdata();
            for (int i = 0; i < JumlahItem; i++) {
                if(listItem.size()>i){
                    ModelMainan mainan = listItem.get(i);

                    map.put("key_mainan", mainan.getKey());
                    map.put("qty", String.valueOf(mainan.getQty()));
                    int stokchange = mainan.getStok_mainan() - mainan.getQty();
                    if (map.get("key") != null || map.get("qty") != null) {
                        database.child("transaksi").push().setValue(new ItemTransaksi(userr, Objects.requireNonNull(map.get("key_mainan")), Integer.parseInt(Objects.requireNonNull(map.get("qty"))), totalHarga, date));
                        database.child("mainan").child(Objects.requireNonNull(map.get("key_mainan"))).child("stok_mainan").setValue(stokchange);
                    }
                }

            }
            Toast.makeText(TrKembalianAct.this, "Transaksi sukses", Toast.LENGTH_SHORT).show();
            database.child(userr).removeValue();
            Intent intent2 = new Intent(TrKembalianAct.this, DataKeluarActivity.class);
            startActivity(intent2);
        });


    }

    private void tambahdata() {

    }

}