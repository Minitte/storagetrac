package dpaw.com.storagetrac.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import dpaw.com.storagetrac.R;

public class EmailHolder extends RecyclerView.ViewHolder  {

    public TextView emailText;
    public ImageButton deleteButton;

    /**
     * Constructor for an email holder
     * @param itemView
     */
    public EmailHolder(View itemView) {
        super(itemView);

        emailText = itemView.findViewById(R.id.emailText);
        deleteButton = itemView.findViewById(R.id.deleteButton);
    }

    public void setEmailText(String email) {
        emailText.setText(email);
    }
}
