package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        Button bLogin = findViewById(R.id.btnLogin);
        TextView tvSwitchR = findViewById(R.id.tvSwRegist);

        bLogin.setOnClickListener(view -> CekLogin());

        tvSwitchR.setOnClickListener(view -> SwitchToRegister());
    }

    private void SwitchToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void CekLogin() {
        EditText eEmail = findViewById(R.id.edEmail);
        EditText ePass = findViewById(R.id.edPassword);
        String email = eEmail.getText().toString();
        String password = ePass.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        if (email.isEmpty()){
            eEmail.setError("Masukkan email");
        } else if(password.isEmpty()){
            ePass.setError("Masukkan password");
        }else{
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }

    }
}