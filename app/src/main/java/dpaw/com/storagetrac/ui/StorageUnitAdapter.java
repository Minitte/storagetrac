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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    /**
     * Listener that handles communication between the list components and the activity.
     */
    private static StorageUnitListener _storageUnitListener;

    /**
     * Holds the display information of each list item in the recycler view.
     */
    public static class StorageUnitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            itemView.setOnClickListener(this); // Set the on click listener
        }

        @Override
        public void onClick(View view) {
            _storageUnitListener.selectItem(this.getAdapterPosition());
        }
    }

    /**
     * Constructor.
     * @param storageUnit reference to the storage unit
     */
    public StorageUnitAdapter(StorageUnit storageUnit, StorageUnitListener adapterListener) {
        _items = (ArrayList<Item>)storageUnit.get_items();
        _storageUnitListener = adapterListener;
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
        // Set item display properties
        Item item = _items.get(position);
        holder.name.setText(item.get_name());
        holder.image.setImageResource(item.get_iconId());
        holder.quantity.setText(item.get_quantity() + " " + item.get_unit());

        // Setting the expiration date text if it exists
        if (item.get_expiryDate() != null) {
            // Create calendar object for both dates
            Calendar current = Calendar.getInstance();
            Calendar expiry = Calendar.getInstance();
            expiry.setTime(item.get_expiryDate());

            // Compare time difference
            long msDiff = expiry.getTimeInMillis() - current.getTimeInMillis();
            long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);

            // Display hours if under a day left
            if (daysDiff < 1) {
                long hoursDiff = TimeUnit.MILLISECONDS.toHours(msDiff);
                holder.expiry.setText("Expires in " + hoursDiff + " hour(s)");
                holder.warning.setVisibility(View.VISIBLE); // Show warning
            } else {
                holder.expiry.setText("Expires in " + daysDiff + " day(s)");
                holder.warning.setVisibility(View.INVISIBLE); // Hide warning
            }
        }
    }

    @Override
    public int getItemCount() {
        return _items.size();
    }
}
