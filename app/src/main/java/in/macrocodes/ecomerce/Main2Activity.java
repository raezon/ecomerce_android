package in.macrocodes.ecomerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.macrocodes.ecomerce.database.MyDatabase;

import in.macrocodes.ecomerce.database.User;
import in.macrocodes.ecomerce.database.UserDao;
import in.macrocodes.ecomerce.services.UserService;

public class Main2Activity extends AppCompatActivity {


    private View loginLayout;
    private View registerLayout;
    private TextView switchToRegister;
    private TextView switchToLogin;
    private EditText fullNameRegister;
    private EditText usernameRegister;
    private EditText passwordRegister;
    private EditText usernameLogin;
    private EditText passwordLogin;

    private Button buttonLogin;
    private Button buttonRegister;

    private MyDatabase database;
    private UserDao userDao;

    private UserService userService;
    private ExecutorService executorService;



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
        switchToLogin=findViewById(R.id.switchToLogin);
        fullNameRegister=findViewById(R.id.fullNameRegister);
        usernameRegister=findViewById(R.id.nomUtilisateurInscription);
        passwordRegister=findViewById(R.id.passwordRegister);
        usernameLogin=findViewById(R.id.usernameLogin);
        passwordLogin=findViewById(R.id.passwordLogin);
        buttonLogin=findViewById(R.id.btnLogin);
        buttonRegister=findViewById(R.id.btnRegister);
        //faire connexion avec bdd
        //userService=new UserService(Main2Activity.this);
        // Initialize the database and DAO
        database=MyDatabase.getInstance(this);
        userDao=database.userDao1();
        executorService= Executors.newSingleThreadExecutor();

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
                    registerUser(fullName,userName,password);



                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=usernameLogin.getText().toString();
                String password=passwordLogin.getText().toString();
              //  Toast.makeText(Main2Activity.this, "Username: " + username + "\nPassword: " + password, Toast.LENGTH_SHORT).show();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(Main2Activity.this,"Veuillez remplir les champs ",Toast.LENGTH_SHORT).show();
                }else{

                        Toast.makeText(Main2Activity.this,"champs remplis",Toast.LENGTH_SHORT).show();
                        loginUser(username,password);
                }

            }
        });


        switchToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLayout.setVisibility(View.GONE);
                registerLayout.setVisibility(View.VISIBLE);
            }
        });

        switchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLayout.setVisibility(View.VISIBLE);
                registerLayout.setVisibility(View.GONE);
            }
        });


    }
    private void registerUser(String fullName, String userName, String password){
        if(!fullName.isEmpty() && !userName.isEmpty() && !password.isEmpty()){
        executorService.execute(()->{
            User user=new User(fullName,userName,password);
            userDao.insert(user);
            runOnUiThread(()->{
                fullNameRegister.getText().clear();
                usernameRegister.getText().clear();
                passwordRegister.getText().clear();
                registerLayout.setVisibility(View.GONE);
                loginLayout.setVisibility(View.VISIBLE);
                Toast.makeText(Main2Activity.this,"Vous avez terminer votre inscription",Toast.LENGTH_LONG).show();
            });
        });
        }
    }

   private void loginUser(String userName, String password) {

        if (!userName.isEmpty() && !password.isEmpty()) {
            final boolean[] result = {false};
            executorService.execute(() -> {
                User user = userDao.login(userName, password);
                runOnUiThread(() -> {
                    if (user != null) {
                        Toast.makeText(this, "Login with success", Toast.LENGTH_SHORT).show();
                        Intent redirection=new Intent(Main2Activity.this, UserProductActivity.class);
                        startActivity(redirection);
                        finish();

                    } else {
                        Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                });
            });
            Toast.makeText(Main2Activity.this, "Result: " + result[0], Toast.LENGTH_SHORT).show();

        } else {
            runOnUiThread(() -> Toast.makeText(this, "username or password false", Toast.LENGTH_SHORT).show());

        }
    }




}