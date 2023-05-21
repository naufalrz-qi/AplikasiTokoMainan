package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        Button bSignUp = findViewById(R.id.btnSignUp);
        TextView tvSwitchL = findViewById(R.id.tvSwLogin);



        Spinner spinner = findViewById(R.id.sp_mode);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mode_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);



        bSignUp.setOnClickListener(view -> registeruser());

        tvSwitchL.setOnClickListener(view -> SwitchToLogin());
    }

    private void SwitchToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void registeruser() {
        EditText erNama = findViewById(R.id.edNama);
        EditText erNoHP = findViewById(R.id.edNoHP);
        EditText erAlamat = findViewById(R.id.edAlamat);

        EditText erEmail = findViewById(R.id.edEmailR);
        EditText erPassword = findViewById(R.id.edPass1);
        EditText erConPass  = findViewById(R.id.edPass2);
        Spinner spinner = findViewById(R.id.sp_mode);

        String Nama = erNama.getText().toString();
        String NoHP = erNoHP.getText().toString();
        String Alamat = erAlamat.getText().toString();
        String email = erEmail.getText().toString();
        String password = erPassword.getText().toString();
        String conPassword = erConPass.getText().toString();
        String modeakses = spinner.getSelectedItem().toString();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat curFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String FormattedDate = curFormatter.format(date);

        String nama_pendek;
        if(Nama.trim().length()>10){
            nama_pendek=Nama.substring(0,10);
        }else{
            nama_pendek=Nama;
        }

        if (email.isEmpty()){
            erEmail.setError("Masukkan email");
        } else if(password.isEmpty()){
            erPassword.setError("Masukkan password");
        }else if(Nama.isEmpty()){
            erNama.setError("Masukkan nama");
        }else if(NoHP.isEmpty()){
            erNoHP.setError("Masukkan nomor handphone");
        }else if(Alamat.isEmpty()){
            erAlamat.setError("Masukkan alamat");
        }else if(password.trim().length()<6){
            erPassword.setError("Password harus lebih dari 6 karakter");
        }  else if (!conPassword.equals(password)){
            erConPass.setError("password tidak sesuai");
        } else  {

            user user = new user( Nama, nama_pendek ,email, NoHP, Alamat, modeakses, FormattedDate, FormattedDate );
            Intent intent = new Intent(this, LoginActivity.class);


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()){
                            //database.child("users").push().setValue(user);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(user);

                            Toast.makeText(RegisterActivity.this, "Pendaftaran berhasil",
                                    Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(RegisterActivity.this, "Pendaftaran gagal, email tersebut telah digunakan",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
        
    }
}