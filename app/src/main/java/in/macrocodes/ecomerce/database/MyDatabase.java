// TaskDatabase.java
package  in.macrocodes.ecomerce.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract ProductDao productDao();


    private static MyDatabase instance;

    public static synchronized MyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}