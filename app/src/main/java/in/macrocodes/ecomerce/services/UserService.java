package  in.macrocodes.ecomerce.services;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class UserService {

    private static final String PREF_NAME = "UserPreferences";
    private static final String KEY_FULLNAME = "fullname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    private SharedPreferences sharedPreferences;
    private Context context; // Added member variable to store context

    public UserService(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean registerUser(String fullName, String email, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        return editor.commit();
    }

    public boolean loginUser(String email, String password) {
        String storedEmail = sharedPreferences.getString(KEY_EMAIL, null);
        String storedPassword = sharedPreferences.getString(KEY_PASSWORD, null);

        // Use context to display toast messages
        //Toast.makeText(context, "Stored Email: " + storedEmail, Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, "Stored Password: " + storedPassword, Toast.LENGTH_SHORT).show();

        return storedEmail != null && storedPassword != null &&
                storedEmail.equals(email) && storedPassword.equals(password);
    }

    public boolean isUserRegistered() {
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }
}
