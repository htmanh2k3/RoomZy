package com.app.roomzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Ánh xạ các thành phần giao diện
        emailEditText = findViewById(R.id.loginEmail);
        passwordEditText = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginBtn);

        // Thêm sự kiện cho nút đăng nhập
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    // Phương thức đăng nhập
    private void login() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Kiểm tra email và password không được để trống
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập email!!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập pashdsword!!", Toast.LENGTH_LONG).show();
            return;
        }

        // Thực hiện đăng nhập bằng email và password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Nếu đăng nhập thành công, chuyển đến MainActivity
                            Toast.makeText(getApplicationContext(), "Đăng Nhập Thành Công!!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            // Nếu đăng nhập thất bại, hiển thị thông báo lỗi
                            String errorMessage = task.getException().getMessage(); // Lấy thông điệp lỗi
                            Toast.makeText(getApplicationContext(), "Đăng nhập không thành công: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}