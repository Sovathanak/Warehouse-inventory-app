package warehouseinventory.com.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {
    private ItemDao myItemDao;
    private LiveData<List<Item>> allItems;

    ItemRepository(Application app){
        ItemDatabase itemDB = ItemDatabase.getDatabase(app);
        myItemDao = itemDB.itemDao();
        allItems = myItemDao.getAllItems();
    }
    LiveData<List<Item>> getAllItems(){
        return allItems;
    }
    void insert(Item item){
        ItemDatabase.databaseWriteExecutor.execute(() -> myItemDao.addItem(item));
    }

    void deleteAll(){
        ItemDatabase.databaseWriteExecutor.execute(()->{myItemDao.deleteAllItems();
        });
    }
    void deleteByID(int id){
        ItemDatabase.databaseWriteExecutor.execute(()->{myItemDao.deleteItemById(id);
        });
    }
}
