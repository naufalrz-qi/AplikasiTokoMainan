package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DialogFormMasuk extends DialogFragment {
    String nama_mainan, merk, satuan, comp, key, pilih,created_at, updated_at;
    long harga;
    int stok;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DialogFormMasuk(String nama_mainan, String merk, String satuan,
                           String comp, String key, String pilih, String created_at,
                           String updated_at, long harga, int stok)
    {
        this.nama_mainan = nama_mainan;
        this.merk = merk;
        this.satuan = satuan;
        this.comp = comp;
        this.key = key;
        this.pilih = pilih;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.harga = harga;
        this.stok = stok;
    }

    EditText tNama, tMerk, tQty;
    Button btnMasukkan;
    @Nullable
    @Override
    public View onCreateView
            (@NonNull LayoutInflater inflater,
             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        final View view = inflater
                .inflate(R.layout.activity_data_mainan_masuk, container, false);

        TextView tv_InputData = view.findViewById(R.id.textView4);
        tv_InputData.setText("Tambah Stok");
        tNama = view.findViewById(R.id.edNamaMainanMasuk);
        tMerk = view.findViewById(R.id.edMerkMasuk);
        tQty = view.findViewById(R.id.edQty);

        btnMasukkan = view.findViewById(R.id.btnSimpanMasuk);

        tNama.setText(nama_mainan);
        tMerk.setText(merk);
        tQty.setText("0");

        if(tQty.getText().toString().equals("")){
            tQty.setText("0");
        }
        tQty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(tQty.getText().toString().equals("")){
                    tQty.setText("0");
                }
            }
        });

        btnMasukkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tQty.getText().toString().equals("")){
                    tQty.setText("0");
                }
                String gNamaMainan = tNama.getText().toString();
                String gMerk = tMerk.getText().toString();
                int gStok = stok + Integer.parseInt(tQty.getText().toString());

                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat curFormatter = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss",
                        Locale.getDefault());
                String FormattedDate = curFormatter.format(date);

                if(pilih.equals("Ubah")) {

                    database.child("mainan").child(key.toLowerCase()
                                    .replace(" ","-"))
                            .setValue(new ModelMainan(gNamaMainan, gMerk, harga, satuan,
                                    gStok, comp,1, created_at, FormattedDate))
                            .addOnSuccessListener(unused ->{
                                        Toast.makeText(view.getContext(),
                                                "Berhasil di Update", Toast.LENGTH_SHORT).show();

                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                FirebaseUser currentUser = auth.getCurrentUser();
                                String userr =  FirebaseDatabase.getInstance()
                                        .getReference("users")
                                        .child(Objects.requireNonNull(currentUser).getUid()).getKey();


                                        database.child("mainanMasuk").push()
                                                .setValue(new ItemTransaksi(userr, key,
                                                        Integer.parseInt(tQty.getText().toString()),Integer.parseInt(tQty.getText().toString())*harga,FormattedDate));
                                    }
                            ).addOnFailureListener(e ->
                                    Toast.makeText(view.getContext(), "Gagal update data",
                                            Toast.LENGTH_SHORT).show());

                }

            }
        });
        return view;

    }
}
