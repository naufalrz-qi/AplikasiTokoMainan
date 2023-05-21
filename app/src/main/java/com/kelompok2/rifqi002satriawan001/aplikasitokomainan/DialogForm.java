package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import android.annotation.SuppressLint;
import android.app.Dialog;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DialogForm extends DialogFragment {
    String nama_mainan, merk, satuan, comp, key, pilih,created_at, updated_at;
    int stok;
    long harga;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DialogForm(String nama_mainan, String merk, String satuan, String comp, String key,int stok, String pilih, long harga,String created_at, String updated_at) {
        this.nama_mainan = nama_mainan;
        this.merk = merk;
        this.satuan = satuan;
        this.comp = comp;
        this.key = key;
        this.stok = stok;
        this.pilih = pilih;
        this.harga = harga;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    EditText tNama, tMerk, tHarga, tId;
    Spinner tSatuan;
    Button btnUpdate;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_create, container, false);

        TextView tv_update = view.findViewById(R.id.textView2);
        tv_update.setText("Edit Data");
        tNama = view.findViewById(R.id.edNamaMainan);
        tMerk = view.findViewById(R.id.edMerk);
        tHarga = view.findViewById(R.id.edHarga);
        tSatuan = view.findViewById(R.id.spSatuan);
        tId = view.findViewById(R.id.edIdMainan);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.satuan_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        tSatuan.setAdapter(adapter);
        btnUpdate = view.findViewById(R.id.btnSimpan);

        tId.setText(key);
        tNama.setText(nama_mainan);
        tMerk.setText(merk);
        tHarga.setText(String.valueOf(harga));
        tSatuan.setSelection(adapter.getPosition(satuan));
        if(tHarga.getText().toString().equals("")){
            tHarga.setText("0");
        }
        tHarga.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(tHarga.getText().toString().equals("")){
                    tHarga.setText("0");
                }
            }
        });
        btnUpdate.setOnClickListener(view1 -> {
            database.child(key).removeValue();
            if(tHarga.getText().toString().equals("")){
                tHarga.setText("0");
            }
            String gNamaMainan = tNama.getText().toString();
            String idMainan = tId.getText().toString();
            String gMerk = tMerk.getText().toString();
            String gHarga = tHarga.getText().toString();
            String gSatuan = tSatuan.getSelectedItem().toString();
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat curFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String FormattedDate = curFormatter.format(date);
            String gComp;

            if(pilih.equals("Ubah")){


                gComp=gNamaMainan+gMerk;
                if (gNamaMainan.isEmpty()){
                    tNama.setError("Masukkan Nama");
                } else if(idMainan.isEmpty()) {
                    tId.setError("Masukkan Id");
                } else if(gMerk.isEmpty()) {
                    tMerk.setError("Masukkan Merk");
                }else if(gHarga.isEmpty()) {
                    tHarga.setError("Masukkan Harga");
                }else {
                    database.child("mainan").child(idMainan).setValue(new ModelMainan(gNamaMainan, gMerk, Long.parseLong(gHarga) , gSatuan, stok, gComp.toUpperCase().replace(" ","_"),1, created_at, FormattedDate))
                            .addOnSuccessListener(unused ->
                                    Toast.makeText(view1.getContext(), "Berhasil di Update", Toast.LENGTH_SHORT).show()

                            ).addOnFailureListener(e ->
                                    Toast.makeText(view1.getContext(), "Gagal update data", Toast.LENGTH_SHORT).show());
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog!=null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
