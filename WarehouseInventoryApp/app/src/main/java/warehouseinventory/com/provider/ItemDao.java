package warehouseinventory.com.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("select * from custItems")
    LiveData<List<Item>> getAllItems();

    @Insert
    void addItem(Item item);

    @Query("delete from custItems where customerID=:id")
    void deleteItemById(int id);

    @Query("delete from custItems")
    void deleteAllItems();
}
