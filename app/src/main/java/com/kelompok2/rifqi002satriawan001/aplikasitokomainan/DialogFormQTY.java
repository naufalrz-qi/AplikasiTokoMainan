package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class DialogFormQTY extends DialogFragment {
    String nama_mainan, merk, satuan, comp, key, pilih,created_at, updated_at;
    long harga;
    int stok;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DialogFormQTY(String nama_mainan, String merk, String satuan, String comp, String key, String pilih, String created_at, String updated_at, long harga, int stok) {
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_data_mainan_masuk, container, false);

        TextView tv_InputData = view.findViewById(R.id.textView4);
        tv_InputData.setText("Tambah Jumlah Item");
        tNama = view.findViewById(R.id.edNamaMainanMasuk);
        tMerk = view.findViewById(R.id.edMerkMasuk);
        tQty = view.findViewById(R.id.edQty);
        TextView tvQTY = view.findViewById(R.id.textView5);

        tvQTY.setText("Qty :");
        tQty.setHint("Qty");

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

                int gStok = Integer.parseInt(tQty.getText().toString());

                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat curFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String FormattedDate = curFormatter.format(date);

                if(gStok>stok){
                    Toast.makeText(view.getContext(), "Stok tidak mencukupi permintaan", Toast.LENGTH_SHORT).show();

                }else{
                    if(pilih.equals("Ubah")) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        FirebaseUser currentUser = auth.getCurrentUser();
                        String userr =  FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(currentUser).getUid()).getKey();
                        database.child(userr).child(key).setValue(new ModelMainan(nama_mainan, merk, harga*gStok, satuan, stok, comp,gStok, created_at, FormattedDate))
                                .addOnSuccessListener(unused ->
                                        Toast.makeText(view.getContext(), "Berhasil di Update", Toast.LENGTH_SHORT).show()

                                ).addOnFailureListener(e ->
                                        Toast.makeText(view.getContext(), "Gagal update data", Toast.LENGTH_SHORT).show());

                    }
                }


            }
        });
        return view;

    }
}
