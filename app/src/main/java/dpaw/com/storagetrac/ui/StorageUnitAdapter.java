package dpaw.com.storagetrac.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import dpaw.com.storagetrac.R;
import dpaw.com.storagetrac.StorageUnitList;
import dpaw.com.storagetrac.data.Item;
import dpaw.com.storagetrac.data.StorageUnit;

/**
 * Class for providing adapter functionality to the storage unit recycler view.
 */
public class StorageUnitAdapter extends RecyclerView.Adapter<StorageUnitAdapter.StorageUnitViewHolder> {

    /**
     * List of items.
     */
    private ArrayList<Item> _items;

    public static class StorageUnitViewHolder extends RecyclerView.ViewHolder {
        // List of data that's contained in each storage unit
        public ImageView image;
        public ImageView warning;
        public TextView name;
        public TextView quantity;
        public TextView expiry;

        /**
         * Constructor for creating a new list item view.
         * @param itemView The list item view to add
         */
        public StorageUnitViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.itemImage);
            name = itemView.findViewById(R.id.itemName);
            quantity = itemView.findViewById(R.id.itemQuantity);
            warning = itemView.findViewById(R.id.warning);
            expiry = itemView.findViewById(R.id.itemExpiry);
        }
    }

    /**
     * Constructor.
     * @param storageUnit reference to the storage unit
     */
    public StorageUnitAdapter(StorageUnit storageUnit) {
        _items = (ArrayList<Item>)storageUnit.get_items();
    }

    @NonNull
    @Override
    public StorageUnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view based off of the list item view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.storage_unit_item, parent, false);

        // Add the view to the storage unit list
        StorageUnitViewHolder vh = new StorageUnitViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull StorageUnitViewHolder holder, final int position) {
        // Set item properties
        Item item = _items.get(position);
        holder.name.setText(item.get_name());
        holder.image.setImageResource(item.get_iconId());
        holder.quantity.setText(item.get_quantity() + " " + item.get_unit());
    }

    @Override
    public int getItemCount() {
        return _items.size();
    }
}
