package pb.giftplanner.business;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Paule on 05.11.2014.
 */
public class Contact {

    String mName;
    long mId;
    String mLookupKey;

    public void initByPhoneCursor(Cursor cursor) {
        mName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        mId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        mLookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
    }

    public String getName() {
        return mName;
    }
    public Long getId() { return mId; }

    public String getLookupKey() {
        return mLookupKey;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
    public void setId(long id) { this.mId = id; };
}
