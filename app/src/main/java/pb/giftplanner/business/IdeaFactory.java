package pb.giftplanner.business;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.widget.Adapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import pb.giftplanner.R;

/**
 * Created by Paule on 27.10.2014.
 */
public class IdeaFactory {

    public Idea createIdea(String name, String description) {
        SQLiteDatabase database = Worker.getInstance().getSQLiteHelper().getWritableDatabase();

        Idea idea = new Idea();
        idea.setName(name);
        idea.setDescription(description);

        long id = database.insert(Idea.TABLE_NAME, null, idea.getContentValues());

        if(id != 0) {
            idea.setId(id);
        }
        else {
            idea = null;
        }

        return idea;
    }

    public boolean updateIdea(Idea idea) {
        SQLiteDatabase db = Worker.getInstance().getSQLiteHelper().getWritableDatabase();
        if(db != null) {
            String where = String.format("%s=?", Idea.COLUMN_ID);
            Long id = idea.getId();
            String whereArgs[] = new String[] { id.toString() };
            db.update(Idea.TABLE_NAME, idea.getContentValues(), where, whereArgs);
        }

        return true;
    }

    public Boolean deleteIdea(long id) {
        SQLiteDatabase database = Worker.getInstance().getSQLiteHelper().getWritableDatabase();
        database.delete(Idea.TABLE_NAME, Idea.COLUMN_ID + "=" + id, null);

        return true;
    }

    private Cursor getIdeaCursor() {
        SQLiteDatabase database = Worker.getInstance().getSQLiteHelper().getReadableDatabase();

        String columns[] = new String[] {Idea.COLUMN_ID, Idea.COLUMN_NAME, Idea.COLUMN_DESCRIPTION};
        Cursor cursor = database.query(Idea.TABLE_NAME, columns, "", null, "", "", "", "");

        return cursor;
    }

    public SimpleCursorAdapter getIdeaListAdapter(Context context) {
        String fromColumns[] = Idea.COMPLETE_COLUMN_LIST;
        int toViews[] = new int[] { 0, R.id.textViewIdeaItemName, 0 };
        Cursor cursor = getIdeaCursor();
        cursor.moveToFirst();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, R.layout.idea_list_item, cursor, fromColumns, toViews, 0);

        return adapter;
    }

    public List<Idea> getIdeaList() {
        List<Idea> retList = new ArrayList<Idea>();

        SQLiteDatabase database = Worker.getInstance().getSQLiteHelper().getReadableDatabase();

        String columns[] = new String[] {Idea.COLUMN_ID, Idea.COLUMN_NAME, Idea.COLUMN_DESCRIPTION};
        Cursor cursor = getIdeaCursor();

        if(cursor != null) {
            Boolean bValid = cursor.moveToFirst();
            while (bValid) {
                Idea idea = new Idea();
                idea.setId(cursor.getLong(0));
                idea.setName(cursor.getString(1));
                idea.setDescription(cursor.getString(2));

                retList.add(idea);

                bValid = cursor.moveToNext();
            }
        }

        return retList;
    }
}
