package warehouseinventory.com.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "custItems")
public class Item {
    public static String tableName = "custItems";
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int customerID;
    @ColumnInfo
    private String itemName;
    @ColumnInfo
    private double itemCost;
    @ColumnInfo
    private double itemQuantity;
    @ColumnInfo
    private String itemDes;
    @ColumnInfo
    private boolean frozenButton;

    public Item(String itemName, double itemCost, double itemQuantity, String itemDes, boolean frozenButton) {
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemQuantity = itemQuantity;
        this.itemDes = itemDes;
        this.frozenButton = frozenButton;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemCost() {
        return itemCost;
    }

    public double getItemQuantity() {
        return itemQuantity;
    }

    public String getItemDes() {
        return itemDes;
    }

    public boolean isFrozenButton() {
        return frozenButton;
    }


}
