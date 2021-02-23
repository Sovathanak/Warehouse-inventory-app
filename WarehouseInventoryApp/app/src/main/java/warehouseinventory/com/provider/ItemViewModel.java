package warehouseinventory.com.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository repository;
    private LiveData<List<Item>> allItems;

    public ItemViewModel(@NonNull Application app) {
        super(app);
        repository = new ItemRepository(app);
        allItems = repository.getAllItems();
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    public void insert(Item item) {
        repository.insert(item);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
    public void deleteById(int id){
        repository.deleteByID(id);
    }
}
