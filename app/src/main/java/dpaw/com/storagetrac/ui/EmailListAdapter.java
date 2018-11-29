package dpaw.com.storagetrac.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dpaw.com.storagetrac.R;
import dpaw.com.storagetrac.StorageUnitList;
import dpaw.com.storagetrac.data.StorageUnit;
import dpaw.com.storagetrac.data.UserFireStoreData;
import dpaw.com.storagetrac.database.Firestone.FirestoneDatabaseAccess;
import dpaw.com.storagetrac.database.Firestone.IUserDataResultHandler;

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
                final String removedEmail = storageUnit.get_sharedEmails().remove(position);

                db.getRemoteUserData(removedEmail, new IUserDataResultHandler() {
                    @Override
                    public void onUserDataResult(UserFireStoreData data) {
                        if (data == null) {
                            data = new UserFireStoreData();
                        }

                        // remove this storage unit from the user's borrowed list
                        data.get_borrowedStorage().remove(storageUnit.get_fireStoneID());
                        db.setRemoteUserData(removedEmail, data, null);

                        // remove user from share list
                        storageUnit.removeShared(removedEmail);
                        db.setStorageUnit(storageUnit, null);

                        StorageUnitList.saveLocalDatabase();

                        notifyDataSetChanged();
                    }
                });
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
