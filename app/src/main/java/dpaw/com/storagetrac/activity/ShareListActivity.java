package dpaw.com.storagetrac.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import dpaw.com.storagetrac.R;
import dpaw.com.storagetrac.data.StorageUnit;
import dpaw.com.storagetrac.database.Firestone.FirestoneDatabaseAccess;
import dpaw.com.storagetrac.ui.EmailListAdapter;

public class ShareListActivity extends AppCompatActivity {

    /**
     * Target storage unit
     */
    public StorageUnit storageUnit;

    /**
     * list adapter for shared emails
     */
    private EmailListAdapter adapter;

    /**
     * Field for emails
     */
    private EditText emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_list);

        initRecyclerView();
    }

    /**
     * Initalizes the recycler view for emails
     */
    private void initRecyclerView() {
        RecyclerView rv = findViewById(R.id.emailList);

        adapter = new EmailListAdapter(storageUnit);

        rv.setAdapter(adapter);

        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Adds email from list
     * @param view
     */
    public void addEmailFromField(View view) {
        // spilt to allow example@email.com, hello@world.ca, etc@etc.org
        String[] emails = emailField.getText().toString().split(",");

        // fast referance to list
        List<String> shared = storageUnit.get_sharedEmails();

        // database object
        FirestoneDatabaseAccess db = new FirestoneDatabaseAccess();

        // add all non existing emails
        for (int i = 0; i < emails.length; i++) {
            if (!shared.contains(emails[i])) {
                db.addToBorrowed(emails[i], storageUnit);
                shared.add(emails[i]);
            }
        }

        // clear text
        emailField.setText("");
    }
}
