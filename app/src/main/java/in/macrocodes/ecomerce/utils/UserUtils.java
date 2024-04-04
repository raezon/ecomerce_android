package in.macrocodes.ecomerce.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class UserUtils {

    private static final String PREF_NAME = "UserPreferences";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    private SharedPreferences sharedPreferences;

    public UserUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean registerUser(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        return editor.commit();
    }

    public boolean loginUser(String username, String password) {
        String storedUsername = sharedPreferences.getString(KEY_USERNAME, null);
        String storedPassword = sharedPreferences.getString(KEY_PASSWORD, null);

        return storedUsername != null && storedPassword != null &&
                storedUsername.equals(username) && storedPassword.equals(password);
    }

    public boolean isUserRegistered() {
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }
}
