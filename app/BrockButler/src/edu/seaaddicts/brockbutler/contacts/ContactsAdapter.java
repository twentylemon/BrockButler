package edu.seaaddicts.brockbutler.contacts;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import edu.seaaddicts.brockbutler.R;

public class ContactsAdapter extends BaseAdapter implements OnClickListener {
    private Context context;

    private List<Contact> mContactsList;

    public ContactsAdapter(Context context, List<Contact> listPhonebook) {
        this.context = context;
        this.mContactsList = listPhonebook;
    }

    public int getCount() {
        return mContactsList.size();
    }

    public Object getItem(int position) {
        return mContactsList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Contact c = mContactsList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_contact_full, null);
        }
        TextView tvContact = (TextView) convertView.findViewById(R.id.tv_contact_name);
        tvContact.setText(c.mFirstName + " " + c.mLastName);

        TextView tvEmail = (TextView) convertView.findViewById(R.id.tv_email);
        tvEmail.setText(c.mEmail);

        TextView tvMail = (TextView) convertView.findViewById(R.id.lv_courses);
        tvMail.setText(c.mEmail);

        // Set the onClick Listener on this button
        Button btnRemove = (Button) convertView.findViewById(R.id.btnRemove);
        btnRemove.setFocusableInTouchMode(false);
        btnRemove.setFocusable(false);
        btnRemove.setOnClickListener(this);
        
        // Set the entry, so that you can capture which item was clicked and
        // then remove it. As an alternative, you can use the id/position of the item to capture
        // the item that was clicked.
        btnRemove.setTag(c);
        // btnRemove.setId(position);
       

        return convertView;
    }

    public void onClick(View view) {
        Contact c = (Contact) view.getTag();
        mContactsList.remove(c);
        // listPhonebook.remove(view.getId());
        notifyDataSetChanged();

    }

    private void showDialog(Contact c) {
        // Create and show your dialog
        // Depending on the Dialogs button clicks delete it or do nothing
    }

}
