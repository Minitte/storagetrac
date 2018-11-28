package dpaw.com.storagetrac.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dpaw.com.storagetrac.R;
import dpaw.com.storagetrac.StorageUnitList;
import dpaw.com.storagetrac.data.StorageUnit;

/**
 * Class for providing adapter functionality to the storage unit recycler view.
 */
public class StorageUnitAdapter extends RecyclerView.Adapter<StorageUnitAdapter.StorageUnitViewHolder> {

    /**
     * List of storage units.
     */
    private ArrayList<StorageUnit> _storageUnits;

    /**
     * Listener for the storage unit adapter.
     */
    private static StorageUnitAdapterListener _storageUnitAdapterListener;

    public static class StorageUnitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // List of data that's contained in each storage unit
        public ImageView image;
        public TextView name;
        public ImageButton deleteButton;

        /**
         * Constructor for creating a new list item view.
         * @param itemView The list item view to add
         */
        public StorageUnitViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.storageUnitImage);
            name = itemView.findViewById(R.id.storageUnitName);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            itemView.setOnClickListener(this); // Set the on click listener
        }

        @Override
        public void onClick(View v) {
            _storageUnitAdapterListener.selectStorageUnit(this.getAdapterPosition());
        }
    }

    /**
     * Constructor.
     * @param storageUnits the list of storage units
     */
    public StorageUnitAdapter(ArrayList<StorageUnit> storageUnits, StorageUnitAdapterListener adapterListener) {
        _storageUnits = storageUnits;
        _storageUnitAdapterListener = adapterListener;
    }

    @NonNull
    @Override
    public StorageUnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view based off of the list item view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.storage_unit_list_item, parent, false);

        // Add the view to the storage unit list
        StorageUnitViewHolder vh = new StorageUnitViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull StorageUnitViewHolder holder, final int position) {
        holder.name.setText(_storageUnits.get(position).get_name()); // Set the storage unit nam
        holder.image.setImageResource(_storageUnits.get(position).get_iconId());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _storageUnits.remove(position); // Remove this item
                notifyDataSetChanged(); // Tell the adapter to update
            }
        });

        if (StorageUnitList.editing) {
            holder.deleteButton.setVisibility(View.VISIBLE);
        } else {
            holder.deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return _storageUnits.size();
    }
}
