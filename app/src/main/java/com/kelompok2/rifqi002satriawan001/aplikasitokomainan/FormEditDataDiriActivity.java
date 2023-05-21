package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class FormEditDataDiriActivity extends AppCompatActivity {
    EditText nama, email, alamat, notelp, hakakses ;
    Button simpan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit_data_diri);

        nama = findViewById(R.id.edNamaDiri);
        email = findViewById(R.id.edEmailDiri);
        alamat = findViewById(R.id.edAlamatDiri);
        notelp = findViewById(R.id.edNoHPDiri);
        hakakses = findViewById(R.id.edHakAksesDiri);
        simpan = findViewById(R.id.btnSimpanDataDiri);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(currentUser).getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user usr = snapshot.getValue(user.class);
                nama.setText(usr.getNama());
                email.setText(usr.getEmail());
                alamat.setText(usr.getAlamat());
                notelp.setText(usr.getNoTelp());
                hakakses.setText(usr.getModeakses());

                if(notelp.getText().toString().isEmpty()){
                    notelp.setText("123456789");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notelp.getText().toString().isEmpty()){
                    notelp.setText("123456789");
                }
                String sNama = nama.getText().toString();
                String sEmail = email.getText().toString();
                String sAlamat = alamat.getText().toString();
                String sTelp =notelp.getText().toString();
                String akses = hakakses.getText().toString();
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat curFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String FormattedDate = curFormatter.format(date);
                String nama_pendek;
                if(sNama.trim().length()>10){
                    nama_pendek=sNama.substring(0,10);
                }else{
                    nama_pendek=sNama;
                }
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.getCurrentUser().updateEmail(sEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        user User = new user(sNama, nama_pendek ,sEmail, sTelp,sAlamat,akses, FormattedDate, FormattedDate);
                        FirebaseDatabase.getInstance().getReference("users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(User);
                        startActivity(new Intent(FormEditDataDiriActivity.this, DataDiriActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FormEditDataDiriActivity.this, "Gagal update data", Toast.LENGTH_SHORT).show();
                    }
                });

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