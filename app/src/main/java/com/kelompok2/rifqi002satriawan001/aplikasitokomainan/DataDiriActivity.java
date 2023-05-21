package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DataDiriActivity extends AppCompatActivity {
    TextView nama, email, alamat, notelp, akses;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_diri);

        nama = findViewById(R.id.tvNamaD);
        email = findViewById(R.id.tvEmailD);
        alamat = findViewById(R.id.tvAlamatD);
        notelp = findViewById(R.id.tvNotelpD);
        akses = findViewById(R.id.tvAksesD);
        edit = findViewById(R.id.btnEditD);

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
                nama.setText("Nama :"+ usr.getNama());
                email.setText("Email :"+ usr.getEmail());
                alamat.setText("Alamat :"+ usr.getAlamat());
                notelp.setText("NoTelp :"+ usr.getNoTelp());
                akses.setText("Akses :"+ usr.getModeakses());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataDiriActivity.this, FormEditDataDiriActivity.class));
                finish();
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