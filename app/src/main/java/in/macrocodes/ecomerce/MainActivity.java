package in.macrocodes.ecomerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.macrocodes.ecomerce.services.UserService;

public class MainActivity extends AppCompatActivity {

    private View loginLayout;
    private View registerLayout;

    private Button registerButton;

    private EditText registerFullName;
    private EditText registerUsername;
    private EditText registerPassword;
    private EditText loginUsername;
    private EditText loginPassword;
    private TextView switchToRegister;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        loginLayout = findViewById(R.id.loginLayout);
        registerLayout = findViewById(R.id.registerLayout);
        registerFullName = findViewById(R.id.fullNameRegister);
        registerUsername = findViewById(R.id.nomUtilisateurInscription);
        registerPassword = findViewById(R.id.passwordRegister);
        loginUsername = findViewById(R.id.usernameLogin);
        loginPassword = findViewById(R.id.passwordLogin);
        switchToRegister=findViewById(R.id.switchToRegister);

        userService = new UserService(this);
        registerButton=findViewById(R.id.btnRegister);
        // Set onClick listener for "Register" button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = registerFullName.getText().toString();
                String userName = registerUsername.getText().toString();
                String password = registerPassword.getText().toString();

                if (fullName.isEmpty() || userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter full name, email, and password", Toast.LENGTH_SHORT).show();
                } else {
                    if (userService.registerUser(fullName, userName, password)) {
                        Toast.makeText(MainActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                        // Switch to login layout after registration
                        loginLayout.setVisibility(View.VISIBLE);
                        registerLayout.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // Set onClick listener for "Login" button
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else {
                    if (userService.loginUser(username, password)) {
                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        // Proceed to main application logic or dashboard

                        // Start the new activity here
                        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                        startActivity(intent);

                        // Finish the current activity
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        // Set onClick listener for "Switch to Register" text
        switchToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Switch to register layout
                loginLayout.setVisibility(View.GONE);
                registerLayout.setVisibility(View.VISIBLE);
            }
        });

        // Set onClick listener for "Switch to Login" text
        findViewById(R.id.switchToLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Switch to login layout
                registerLayout.setVisibility(View.GONE);
                loginLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}
