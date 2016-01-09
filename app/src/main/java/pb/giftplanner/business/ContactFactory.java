package pb.giftplanner.business;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.widget.SimpleCursorAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import pb.giftplanner.R;

/**
 * Created by Paule on 05.11.2014.
 */
public class ContactFactory {

    ContentResolver mContentResolver = null;

    public ContactFactory(Context context) {
        mContentResolver = context.getContentResolver();
    }

    public ContactsAdapter getContactsAdapterFromPhone(Activity context) {

        String fromCursor[] = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.LOOKUP_KEY,
                ContactsContract.Contacts.DISPLAY_NAME};

        int toResourceIds[] = new int[] {R.id.chkContactItem};

        String where = "";
        String[] whereArgs = null;
        Cursor contactsCursor = mContentResolver.query(ContactsContract.Contacts.CONTENT_URI, fromCursor,
                where, whereArgs, null);

        ContactsAdapter cursorAdapter = new ContactsAdapter(context, contactsCursor,
                fromCursor, toResourceIds);

        return cursorAdapter;
    }

    public Boolean setContactBirthday(Contact contact, Calendar birthDay, Activity context) {
        Boolean ret = false;

        String[] columns = new String[] {
                ContactsContract.CommonDataKinds.Event.LOOKUP_KEY,
                ContactsContract.CommonDataKinds.Event.START_DATE,
                ContactsContract.CommonDataKinds.Event.TYPE
        };

        String where = String.format("%s=? AND %s=? AND %s=?",
                ContactsContract.CommonDataKinds.Event.MIMETYPE,
                ContactsContract.CommonDataKinds.Event.LOOKUP_KEY,
                ContactsContract.CommonDataKinds.Event.TYPE);

        String[] whereArgs = new String[] {
                ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                contact.getLookupKey(),
                String.valueOf(ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY)
        };

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues values = new ContentValues();
        values.put(ContactsContract.CommonDataKinds.Event.START_DATE, df.format(birthDay));

        if(0 < mContentResolver.update(ContactsContract.Contacts.CONTENT_URI, values, where, whereArgs)) {
            ret = true;
        }

        return ret;
    }

    public Calendar getContactBirthday(Contact contact, Context context) {
        Calendar retDate = Calendar.getInstance();

        String[] columns = new String[] {
                //ContactsContract.CommonDataKinds.Event.MIMETYPE,
                ContactsContract.CommonDataKinds.Event.LOOKUP_KEY,
                ContactsContract.CommonDataKinds.Event.START_DATE,
                ContactsContract.CommonDataKinds.Event.TYPE
        };

        String where = String.format("%s=? AND %s=? AND %s=?",
                ContactsContract.CommonDataKinds.Event.MIMETYPE,
                ContactsContract.CommonDataKinds.Event.LOOKUP_KEY,
                ContactsContract.CommonDataKinds.Event.TYPE);

        String[] whereArgs = new String[] {
                ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                contact.getLookupKey(),
                String.valueOf(ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY)
        };


        Cursor birthdayCursor = mContentResolver.query(ContactsContract.Contacts.CONTENT_URI, columns,
                where, whereArgs, null);

        if (birthdayCursor.moveToFirst()) {
            String birthdayString = birthdayCursor.getString(
                    birthdayCursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                retDate.setTime(df.parse(birthdayString));
            }
            catch (ParseException exc) {
                new AlertDialog.Builder(context).setMessage(exc.getMessage()).show();
            }
        }

        return retDate;
    }

    public ArrayList<Contact> getContactsByIdea(Idea idea) {

        ArrayList<Contact> contacts = new ArrayList<Contact>();

        ArrayList<Long> contactIds = IdeaContactLink.getContactIdsByIdea(idea.getId());
        if(contactIds.size() > 0) {

            String fromCursor[] = new String[]{
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME};

            int toResourceIds[] = new int[]{R.id.chkContactItem};

            String where = "";
            ArrayList<String> whereArgs = new ArrayList<String>(contactIds.size());
            for (Long contactId : contactIds) {

                if (where.length() > 0) where += " AND ";

                where += String.format("%s = ?", ContactsContract.Contacts._ID);
                whereArgs.add(contactId.toString());
            }
            Cursor contactsCursor = mContentResolver.query(ContactsContract.Contacts.CONTENT_URI, fromCursor,
                    where, whereArgs.toArray(new String[whereArgs.size()]), null);

            Boolean cont = contactsCursor.moveToFirst();
            while (cont) {
                Contact contact = new Contact();
                contact.initByPhoneCursor(contactsCursor);
                cont = contactsCursor.moveToNext();
                contacts.add(contact);
            }
        }

        return contacts;
    }
}
