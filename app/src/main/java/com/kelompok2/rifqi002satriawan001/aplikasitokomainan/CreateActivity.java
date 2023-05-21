package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateActivity extends AppCompatActivity {
    EditText eNamaMainan, eMerk, eHarga, IdMainan;
    Button bSimpan;
    Spinner spinner;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        eNamaMainan = findViewById(R.id.edNamaMainan);
        eMerk = findViewById(R.id.edMerk);
        eHarga = findViewById(R.id.edHarga);
        bSimpan = findViewById(R.id.btnSimpan);
        IdMainan = findViewById(R.id.edIdMainan);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


        spinner = findViewById(R.id.spSatuan);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.satuan_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        if(eHarga.getText().toString().isEmpty()){
            eHarga.setText("0");
        }
        eHarga.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(eHarga.getText().toString().isEmpty()) {
                    eHarga.setText("0");
                }
            }
        });


        bSimpan.setOnClickListener(view -> simpandata());



    }

    private void simpandata() {
        if(eHarga.getText().toString().isEmpty()){
            eHarga.setText("0");
        }
        String getId = IdMainan.getText().toString();
        String getNamaMainan = eNamaMainan.getText().toString();
        String getMerk = eMerk.getText().toString();
        int getHarga = Integer.parseInt(eHarga.getText().toString());
        String getSatuan = spinner.getSelectedItem().toString();
        int stok_mainan = 0;
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat curFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String FormattedDate = curFormatter.format(date);
        String comp = getNamaMainan+getMerk;

        if (getNamaMainan.isEmpty()){
            eNamaMainan.setError("Masukkan Nama");
        } else if(getId.isEmpty()) {
            IdMainan.setError("Masukkan Id");
        } else if(getMerk.isEmpty()) {
            eMerk.setError("Masukkan Merk");
        }else if(eHarga.getText().toString().isEmpty()) {
            eHarga.setError("Masukkan Harga");
        }else{
            database.child("mainan").child(getId).setValue(new ModelMainan(getNamaMainan, getMerk,getHarga,getSatuan,stok_mainan, comp.toUpperCase().replace(" ","_"), 1 , FormattedDate, FormattedDate))
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(CreateActivity.this, "Data berhasil disimpan",
                                Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CreateActivity.this, DataMainanActivity.class));
                        finish();
                    }).addOnFailureListener(e -> Toast.makeText(CreateActivity.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show());
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