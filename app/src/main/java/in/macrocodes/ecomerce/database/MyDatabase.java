package in.macrocodes.ecomerce.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDao userDao1();

    private static MyDatabase instance;

    public static synchronized MyDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "database")
                    .fallbackToDestructiveMigration()
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            new Thread(() -> {
                                MyDatabase database = getInstance(context.getApplicationContext());
                                database.userDao1().insert(new User("Admin", "Admin", "Admin500G"));
                            }).start();
                        }
                    })
                    .build();
        }
        return instance;
    }
}
