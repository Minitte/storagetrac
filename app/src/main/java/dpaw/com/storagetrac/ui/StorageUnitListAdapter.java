package dpaw.com.storagetrac.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import dpaw.com.storagetrac.R;
import dpaw.com.storagetrac.StorageUnitList;
import dpaw.com.storagetrac.activity.LoginActivity;
import dpaw.com.storagetrac.data.StorageUnit;

/**
 * Class for providing adapter functionality to the storage unit recycler view.
 */
public class StorageUnitListAdapter extends RecyclerView.Adapter<StorageUnitListAdapter.StorageUnitListViewHolder> {

    /**
     * List of storage units.
     */
    private ArrayList<StorageUnit> _storageUnits;

    /**
     * Listener for the storage unit adapter.
     */
    private static StorageUnitListListener _storageUnitListListener;

    public static class StorageUnitListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // List of data that's contained in each storage unit
        public ImageView image;
        public TextView name;
        public ImageButton deleteButton;
        public ImageButton shareButton;

        /**
         * Constructor for creating a new list item view.
         * @param itemView The list item view to add
         */
        public StorageUnitListViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.storageUnitImage);
            name = itemView.findViewById(R.id.emailText);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            shareButton = itemView.findViewById(R.id.shareButton);
            itemView.setOnClickListener(this); // Set the on click listener
        }

        @Override
        public void onClick(View v) {
            _storageUnitListListener.selectStorageUnit(this.getAdapterPosition());
        }
    }

    /**
     * Constructor.
     * @param storageUnits the list of storage units
     */
    public StorageUnitListAdapter(ArrayList<StorageUnit> storageUnits, StorageUnitListListener adapterListener) {
        _storageUnits = storageUnits;
        _storageUnitListListener = adapterListener;
    }

    @NonNull
    @Override
    public StorageUnitListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view based off of the list item view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.storage_unit_list_item, parent, false);

        // Add the view to the storage unit list
        StorageUnitListViewHolder vh = new StorageUnitListViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull StorageUnitListViewHolder holder, final int position) {
        holder.name.setText(_storageUnits.get(position).get_name()); // Set the storage unit nam
        holder.image.setImageResource(_storageUnits.get(position).get_iconId());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _storageUnits.remove(position); // Remove this item
                notifyDataSetChanged(); // Tell the adapter to update
            }
        });

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check if logged in
                FirebaseAuth auth = FirebaseAuth.getInstance();

                if (auth.getCurrentUser() == null) {
                    _storageUnitListListener.onRequireLogin();
                    return;
                }

                _storageUnitListListener.onOpenShareList(position);
            }
        });

        if (StorageUnitList.editing) {
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.shareButton.setVisibility(View.VISIBLE);
        } else {
            holder.deleteButton.setVisibility(View.INVISIBLE);
            holder.shareButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return _storageUnits.size();
    }
}

