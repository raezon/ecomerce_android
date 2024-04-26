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

public class Main2Activity extends AppCompatActivity {


    private View loginLayout;
    private View registerLayout;
    private TextView switchToRegister;
    private EditText fullNameRegister;
    private EditText usernameRegister;
    private EditText passwordRegister;
    private EditText usernameLogin;
    private EditText passwordLogin;

    private Button buttonLogin;
    private Button buttonRegister;

    private UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Implémenter un system d'authentification basic
         * Ajouter un admin avec une condition
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // relier les proprietes de la class avec les vue
        loginLayout = findViewById(R.id.loginLayout);
        registerLayout = findViewById(R.id.registerLayout);
        // relier les proprietes de la class avec les champs des interfaces
        switchToRegister=findViewById(R.id.switchToRegister);
        fullNameRegister=findViewById(R.id.fullNameRegister);
        usernameRegister=findViewById(R.id.nomUtilisateurInscription);
        passwordRegister=findViewById(R.id.passwordRegister);
        usernameLogin=findViewById(R.id.usernameLogin);
        passwordLogin=findViewById(R.id.passwordRegister);
        buttonLogin=findViewById(R.id.btnLogin);
        buttonRegister=findViewById(R.id.btnRegister);
        //crée une instance de userService
        userService=new UserService(Main2Activity.this);
        //Vérification de login
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName=fullNameRegister.getText().toString();
                String userName=usernameRegister.getText().toString();
                String password =passwordRegister.getText().toString();


                if(fullName.isEmpty()|| userName.isEmpty()|| password.isEmpty()){
                    Toast.makeText(Main2Activity.this,"Vous avez oublier de remplir les champs",Toast.LENGTH_LONG).show();

                }else {
                    if(userService.registerUser(fullName,userName,password)){
                        registerLayout.setVisibility(View.GONE);
                        loginLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(Main2Activity.this,"Vous avez terminer votre inscription",Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=usernameLogin.getText().toString();
                String password=passwordLogin.getText().toString();
               // Intent interfaceProduit=new Intent(Main2Activity.this,Product.class);
               // startActivity(interfaceProduit);
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(Main2Activity.this,"Veuillez remplir les champs",Toast.LENGTH_SHORT).show();
                }else{

                    if(username.equals("Admin")&&password.equals("Admin")){
                        Intent redirection=new Intent(Main2Activity.this, ProductActivity.class);
                        startActivity(redirection);
                        finish();
                    }
                    else
                        if(userService.loginUser(username,password)){
                        Intent redirection=new Intent(Main2Activity.this, UserProductActivity.class);
                        startActivity(redirection);
                        finish();
                    }
                }

            }
        });



        switchToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        switchToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLayout.setVisibility(View.GONE);
                registerLayout.setVisibility(View.VISIBLE);
            }
        });


    }

}