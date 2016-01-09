package pb.giftplanner.business;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Paule on 04.04.2015.
 */
public class IdeaContactLink {
    public static final String TABLE_NAME = "IdeaContactLink";
    public static final String COL_IDEA_ID = "IdeaId";
    public static final String COL_CONTACT_ID = "ContactId";

    public static final String COMPLETE_COLUMN_LIST[] = new String[] {COL_IDEA_ID, COL_CONTACT_ID};

    public static boolean createLink(long ideaId, long contactId) {
        SQLiteDatabase db = Worker.getInstance().getSQLiteHelper().getWritableDatabase();
        if(db != null) {
            ContentValues values = new ContentValues();
            values.put(COL_IDEA_ID, ideaId);
            values.put(COL_CONTACT_ID, contactId);
            db.insert(TABLE_NAME, null, values);
            db.close();
        }

        return true;
    }

    public static boolean deleteLink(Long ideaId, Long contactId) {
        SQLiteDatabase db = Worker.getInstance().getSQLiteHelper().getWritableDatabase();
        if(db != null) {
            String where = String.format("%s = ? AND %s = ?", COL_IDEA_ID, COL_CONTACT_ID);
            String whereArgs[] = new String[] {ideaId.toString(), contactId.toString()};
            int nResult = db.delete(TABLE_NAME, where, whereArgs);
            if(nResult == 0) {
            }
            db.close();
        }

        return  true;
    }

    public static boolean existsLink(Long ideaId, Long contactId) {
        boolean bExists = false;
        SQLiteDatabase db = Worker.getInstance().getSQLiteHelper().getReadableDatabase();
        if(db != null) {
            String selection = String.format("%s=? AND %s=?", COL_IDEA_ID, COL_CONTACT_ID);
            String selectionArgs[] = new String[] {ideaId.toString(), contactId.toString()};
            Cursor cursor = db.query(TABLE_NAME, new String[]{}, selection, selectionArgs, "", "", "");

            if(cursor.getCount() > 0) {
                bExists = true;
            }
        }

        return bExists;
    }

    public static ArrayList<Long> getContactIdsByIdea(Long ideaId) {
        ArrayList<Long> retList = new ArrayList<Long>();
        SQLiteDatabase db = Worker.getInstance().getSQLiteHelper().getReadableDatabase();
        if(db != null) {
            String selection = String.format("%s=?", COL_IDEA_ID);
            String selectionArgs[] = new String[] {ideaId.toString() };
            Cursor cursor = db.query(TABLE_NAME, new String[]{COL_CONTACT_ID}, selection, selectionArgs, "", "", "");

            boolean cont = cursor.moveToFirst();
            while(cont) {
                Long contactId = cursor.getLong(cursor.getColumnIndex(COL_CONTACT_ID));
                retList.add(contactId);
                cont = cursor.moveToNext();
            }
        }

        return retList;
    }
}
