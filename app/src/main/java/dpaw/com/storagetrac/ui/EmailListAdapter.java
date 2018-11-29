package dpaw.com.storagetrac.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dpaw.com.storagetrac.R;
import dpaw.com.storagetrac.data.StorageUnit;
import dpaw.com.storagetrac.database.Firestone.FirestoneDatabaseAccess;

public class EmailListAdapter extends RecyclerView.Adapter<EmailHolder> {

    private StorageUnit storageUnit;

    /**
     * Firestore database object
     */
    private FirestoneDatabaseAccess db;

    /**
     * Constructor for Email list adapter
     * @param su a storage unit object
     */
    public EmailListAdapter(StorageUnit su) {
        storageUnit = su;

        db = new FirestoneDatabaseAccess();
    }

    @Override
    public void onBindViewHolder(@NonNull EmailHolder holder, final int position) {
        holder.setEmailText(storageUnit.get_sharedEmails().get(position));

        final EmailHolder emailHolder = holder;

        holder.deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String removed = storageUnit.get_sharedEmails().remove(position);

                db.removeFromBorrowed(emailHolder.emailText.getText().toString(), storageUnit.get_fireStoneID());

                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public EmailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view based off of the list item view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.email_share_item, parent, false);

        // Add the view to the EmailHolder item
        EmailHolder holder = new EmailHolder(v);
        return holder;
    }

    @Override
    public int getItemCount() {
        return storageUnit.get_sharedEmails().size();
    }
}
