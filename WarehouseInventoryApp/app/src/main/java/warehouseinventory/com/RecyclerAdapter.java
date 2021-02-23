package warehouseinventory.com;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import warehouseinventory.com.provider.Item;
import warehouseinventory.com.provider.ItemViewModel;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<Item> itemData = new ArrayList<>();
    RemoveEntityListener delListener;

    public RecyclerAdapter(RemoveEntityListener delListener){
        this.delListener = delListener;
    }
    public void addData(ArrayList<Item> itemData) {
        this.itemData = itemData;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(itemData.get(position).getItemName());
        holder.description.setText(itemData.get(position).getItemDes());
        holder.frozenButton.setText(String.valueOf(itemData.get(position).isFrozenButton()));
        holder.cost.setText(String.valueOf(itemData.get(position).getItemCost()));
        holder.quantity.setText(String.valueOf(itemData.get(position).getItemQuantity()));
        holder.removeItem.setOnClickListener(view ->{
            delListener.onRemoveEntity(itemData.get(position).getCustomerID());
        });
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView cost;
        TextView quantity;
        TextView description;
        TextView frozenButton;
        Button removeItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemnameid);
            cost = itemView.findViewById(R.id.costid);
            quantity = itemView.findViewById(R.id.quantityid);
            description = itemView.findViewById(R.id.descriptionid);
            frozenButton = itemView.findViewById(R.id.frozenid);
            removeItem = itemView.findViewById(R.id.del_item);

        }
    }
}
