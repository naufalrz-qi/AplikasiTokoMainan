package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextView tvWelcome, tvAkses;
    Button bDataMainan, bDataMasuk, bDataKeluar, bLaporan, bSignOut, bDataDiri,btnLpMasuk, btnAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        tvWelcome = findViewById(R.id.tv_welcome);
        tvAkses = findViewById(R.id.tv_akses);

        bDataMainan = findViewById(R.id.btnDataMainan);
        bDataKeluar = findViewById(R.id.btnDataKeluar);
        bDataMasuk = findViewById(R.id.btnDataMasuk);
        bLaporan = findViewById(R.id.btnLaporan);
        bSignOut = findViewById(R.id.btnSignOut);
        bDataDiri = findViewById(R.id.btnDataDiri);
        btnLpMasuk = findViewById(R.id.btnLaporanMasuk);
        btnAbout = findViewById(R.id.btnAboutUs);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


        btnAbout.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        });
        bDataDiri.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,DataDiriActivity.class));
        });

        bSignOut.setOnClickListener(view -> logout());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users")
                .child(Objects.requireNonNull(currentUser).getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user usr = snapshot.getValue(user.class);
                if(Objects.equals(Objects.requireNonNull(usr).getModeakses(), "Gudang")){

                    tvWelcome.setText(getString(R.string.welcome)+ " " + usr.getNamapendek());
                    tvAkses.setText(getString(R.string.get_access_as)+ " " + usr.getModeakses());

                    bLaporan.setBackgroundColor(getResources().getColor(R.color.gray));
                    bLaporan.setText(R.string.no_access);
                    bDataKeluar.setBackgroundColor(getResources().getColor(R.color.gray));
                    bDataKeluar.setText(R.string.no_access);
                    bDataMainan.setBackgroundColor(getResources().getColor(R.color.gray));
                    bDataMainan.setText(R.string.no_access);
                    btnLpMasuk.setBackgroundColor(getResources().getColor(R.color.gray));
                    btnLpMasuk.setText(R.string.no_access);

                    bDataMasuk.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ListMainanActivity.class)));


                }else if(Objects.equals(usr.getModeakses(), "Kasir")){
                    tvWelcome.setText(getString(R.string.welcome)+ " " + usr.getNamapendek());
                    tvAkses.setText(getString(R.string.get_access_as)+ " " + usr.getModeakses());

                    bLaporan.setBackgroundColor(getResources().getColor(R.color.gray));
                    bLaporan.setText(R.string.no_access);
                    bDataMainan.setBackgroundColor(getResources().getColor(R.color.gray));
                    bDataMainan.setText(R.string.no_access);
                    bDataMasuk.setBackgroundColor(getResources().getColor(R.color.gray));
                    bDataMasuk.setText(R.string.no_access);
                    btnLpMasuk.setBackgroundColor(getResources().getColor(R.color.gray));
                    btnLpMasuk.setText(R.string.no_access);

                    bDataKeluar.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, DataKeluarActivity.class)));

                }else if(Objects.equals(usr.getModeakses(), "Admin")){
                    tvWelcome.setText(getString(R.string.welcome)+ " " + usr.getNamapendek());
                    tvAkses.setText(getString(R.string.get_access_as)+ " " + usr.getModeakses());

                    bLaporan.setBackgroundColor(getResources().getColor(R.color.gray));
                    bLaporan.setText(R.string.no_access);
                    bDataKeluar.setBackgroundColor(getResources().getColor(R.color.gray));
                    bDataKeluar.setText(R.string.no_access);
                    bDataMasuk.setBackgroundColor(getResources().getColor(R.color.gray));
                    bDataMasuk.setText(R.string.no_access);
                    btnLpMasuk.setBackgroundColor(getResources().getColor(R.color.gray));
                    btnLpMasuk.setText(R.string.no_access);

                    bDataMainan.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, DataMainanActivity.class)));
                }else if(Objects.equals(usr.getModeakses(), "Pemilik")){
                    tvWelcome.setText(getString(R.string.welcome)+ " " + usr.getNamapendek());
                    tvAkses.setText(getString(R.string.get_access_as)+ " " + usr.getModeakses());

                    bDataMainan.setBackgroundColor(getResources().getColor(R.color.gray));
                    bDataMainan.setText(R.string.no_access);
                    bDataKeluar.setBackgroundColor(getResources().getColor(R.color.gray));
                    bDataKeluar.setText(R.string.no_access);
                    bDataMasuk.setBackgroundColor(getResources().getColor(R.color.gray));
                    bDataMasuk.setText(R.string.no_access);

                    btnLpMasuk.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LaporanMasukActivity.class)));
                    bLaporan.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LaporanActivity.class)));
                }else if(Objects.equals(usr.getModeakses(), "Super Admin")){
                    tvWelcome.setText(getString(R.string.welcome)+ " " + usr.getNamapendek());
                    tvAkses.setText(getString(R.string.get_access_as)+ " " + usr.getModeakses());
                    bLaporan.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LaporanActivity.class)));
                    btnLpMasuk.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LaporanMasukActivity.class)));

                    bDataMainan.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, DataMainanActivity.class)));

                    bDataKeluar.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, DataKeluarActivity.class)));

                    bDataMasuk.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ListMainanActivity.class)));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
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