package dpaw.com.storagetrac.database;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dpaw.com.storagetrac.R;

/**
 * Class for providing adapter functionality to the container recycler view.
 */
public class ContainerAdapter extends RecyclerView.Adapter<ContainerAdapter.ContainerViewHolder> {

    /**
     * List of container names.
     */
    private ArrayList<String> _containerNames;

    public static class ContainerViewHolder extends RecyclerView.ViewHolder {
        // List of data that's contained in each container list item
        public ImageView image;
        public TextView textView;

        /**
         * Constructor for creating a new list item view.
         * @param itemView The list item view to add
         */
        public ContainerViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.containerImage);
            textView = itemView.findViewById(R.id.containerTitle);
        }
    }

    /**
     * Constructor.
     * @param containerNames the list of container names
     */
    public ContainerAdapter(ArrayList<String> containerNames) {
        _containerNames = containerNames;
    }

    @NonNull
    @Override
    public ContainerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view based off of the list item view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.storage_unit_list_item, parent, false);

        // Add the view to the container list
        ContainerViewHolder vh = new ContainerViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContainerViewHolder holder, int position) {
        holder.textView.setText(_containerNames.get(position)); // Set the container name
    }

    @Override
    public int getItemCount() {
        return _containerNames.size();
    }

}
