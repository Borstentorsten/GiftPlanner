package pb.giftplanner.business;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pb.giftplanner.R;

/**
 * Created by Paule on 16.03.2015.
 */


public class ContactsAdapter extends SimpleCursorAdapter {
    Context mContext;

    HashMap<Integer, View> mViewCache = new HashMap<Integer, View>();
    HashMap<Integer, Contact> mContactsCache = new HashMap<Integer, Contact>();
    Cursor mContactsCursor;

    ContactsList mPreSelectContacts;
    boolean mShowCheckBoxes;

    public ContactsAdapter(Activity context, Cursor contactsCursor, String fromCursor[], int toResourceIds[]) {
        super(context, R.layout.contact_list_item, contactsCursor, fromCursor, null, 0);
        mContext = context;
        mContactsCursor = contactsCursor;
    }

    public void setPreSelectContacts(ContactsList contacts) {
        mPreSelectContacts = contacts;
    }

    public void setShowCheckBoxes(boolean show) { mShowCheckBoxes = show; }

    public Contact getContact(int position) {
        Contact contact = null;

        if(!mContactsCache.containsKey(position)) {
            // Create cache-contact
            mContactsCursor.moveToPosition(position);

            contact = new Contact();

            contact.initByPhoneCursor(mContactsCursor);

            mContactsCache.put(position, contact);
        }
        else {
            contact = mContactsCache.get(position);
        }


        return contact;
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.contact_list_item, null);
        }


        Contact contact = getContact(position);

        // fill the view
        CheckBox chkContact = (CheckBox)view.findViewById(R.id.chkContactItem);
        TextView txtContactName = (TextView)view.findViewById(R.id.txtContactName);
        TextView txtContactBirthday = (TextView)view.findViewById(R.id.txtContactBirthday);

        txtContactName.setText(contact.getName());

        String birthday = "";
        Calendar dateBirthday = Worker.getInstance().getContactFactory().getContactBirthday(contact, mContext);

        if(dateBirthday != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            birthday = dateFormat.format(dateBirthday);
        }
        txtContactBirthday.setText(birthday);

        // select the contact, if preselected
        chkContact.setChecked(false);

        if(!mShowCheckBoxes) {
            chkContact.setVisibility(View.GONE);
        }

         if(mPreSelectContacts != null) {
            if(null != mPreSelectContacts.findByDisplayName(contact.getName())) {
                chkContact.setChecked(true);
            }
        }

        mViewCache.put(position, view);

        return view;
    }

    public List<Contact> getCheckedContacts() {
        List<Contact> retList = new LinkedList<Contact>();
        for(Map.Entry<Integer, View> entry : mViewCache.entrySet()) {
            CheckBox chkChecked = (CheckBox)entry.getValue().findViewById(R.id.chkContactItem);
            if(chkChecked != null) {
                if(chkChecked.isChecked()) {
                    Contact contact = (Contact)mContactsCache.get(entry.getKey());
                    if(contact != null) {
                        retList.add(contact);
                    }
                }
            }
        }

        return retList;
    }
}
