package pb.giftplanner.business;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Paule on 27.10.2014.
 */
public class Idea extends DbObject {
    public static final String TABLE_NAME = "Idea";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";

    public static final String COMPLETE_COLUMN_LIST[] = new String[] {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION};

    long mId = 0;
    String mName;
    String mDescription;

    public Idea() {

    }

    public Idea(Cursor dbRow) {
        mId = dbRow.getLong(dbRow.getColumnIndex(COLUMN_ID));
        mName = dbRow.getString(dbRow.getColumnIndex(COLUMN_NAME));
        mDescription = dbRow.getString(dbRow.getColumnIndex(COLUMN_DESCRIPTION));
    }

    public Long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    @Override
    ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(Idea.COLUMN_NAME, mName);
        values.put(Idea.COLUMN_DESCRIPTION, mDescription);
        return values;
    }
}
