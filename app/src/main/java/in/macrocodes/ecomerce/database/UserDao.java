package in.macrocodes.ecomerce.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// c'est une sorte contrat entre userDao et la base de donné
@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE userName = :userName AND password = :password")
    User login(String userName, String password);
}