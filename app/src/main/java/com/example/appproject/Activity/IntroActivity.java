package com.example.appproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appproject.Helper.DatabaseHelper;
import com.example.appproject.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        findViewById(R.id.startBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText)findViewById(R.id.usernameEditText)).getText().toString().trim();
                String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(IntroActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                } else {

                    if (dbHelper.validateUser(username, password)) {

                        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();
                    } else {

                        Toast.makeText(IntroActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}