package pb.giftplanner.app;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Pair;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pb.giftplanner.R;
import pb.giftplanner.business.Contact;
import pb.giftplanner.business.ContactsAdapter;
import pb.giftplanner.business.ContactsList;
import pb.giftplanner.business.Worker;

public class ContactListFragment extends Fragment
                                 implements View.OnClickListener, android.widget.AdapterView.OnItemLongClickListener,
                                            DialogInterface.OnClickListener, android.widget.DatePicker.OnDateChangedListener {

    Contact mSelectedContact = null;

    ContactsAdapter mContactCursorAdapter = null;

    HashMap<Long, Boolean> mSelectedItems = new HashMap<Long, Boolean>();

    ContactsList mPreSelectContacts = null;

    boolean mShowCheckBoxes;

    public ContactListFragment() {
        // Required empty public constructor
    }

    public void setShowCheckBoxes(boolean show) { mShowCheckBoxes = show; }

    public void preSelectContacts(ContactsList contacts) {
        mPreSelectContacts = contacts;
    }

    public List<Contact> getChosenContacts() {
        return mContactCursorAdapter.getCheckedContacts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView contactsList = (ListView)view.findViewById(R.id.listViewContactList);

        mContactCursorAdapter = Worker.getInstance().getContactFactory().getContactsAdapterFromPhone(getActivity());

        mContactCursorAdapter.setPreSelectContacts(mPreSelectContacts);
        mContactCursorAdapter.setShowCheckBoxes(mShowCheckBoxes);

        contactsList.setAdapter(mContactCursorAdapter);

        getView().findViewById(R.id.btnContactListOk).setOnClickListener(this);
        getView().findViewById(R.id.btnContactListCancel).setOnClickListener(this);

        contactsList.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContactListCancel: {
                ((MainActivity)getActivity()).closeContactsPicker();
            }
            break;

            case R.id.btnContactListOk: {
                ((MainActivity)getActivity()).closeContactsPicker();
            }
        }
    }

    @Override
    public boolean onItemLongClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long l) {
        Cursor cursor = (Cursor)mContactCursorAdapter.getItem(i);
        mSelectedContact = new Contact();
        mSelectedContact.initByPhoneCursor(cursor);

        AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
        dlg.setTitle("Person...");

        CharSequence[] items = new CharSequence[] { "Geburtstag hinterlegen" };
        dlg.setItems(items, this);
        dlg.show();

        return true;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        // Contextmenu listener
        switch (i) {
            case 0:
                DatePickerDialog dlg = new DatePickerDialog(getActivity(), (android.app.DatePickerDialog.OnDateSetListener)this, 1, 1, 1);
                dlg.show();
        }
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        // Birthdate entered
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        if(!Worker.getInstance().getContactFactory().setContactBirthday(mSelectedContact, calendar, getActivity())) {
            new AlertDialog.Builder(getActivity()).setMessage("Fehler beim Setzen des Geburtstages").show();
        }
    }
}

interface ContactPickerEvents {
    public void contactsChosen(List<Contact> contacts);
}
