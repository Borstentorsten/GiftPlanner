package pb.giftplanner.business;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Paule on 27.10.2014.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GiftPlanner";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createIdeaTable(sqLiteDatabase);
        //createContactTable(sqLiteDatabase);
        createIdeaContactLinkTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    private void createIdeaTable(SQLiteDatabase sqLiteDatabase) {
        String command = "CREATE TABLE Idea (" +
                Idea.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Idea.COLUMN_NAME + " TEXT," +
                Idea.COLUMN_DESCRIPTION + " TEXT" +
                ")";

        sqLiteDatabase.execSQL(command);
    }

    /*private void createContactTable(SQLiteDatabase sqLiteDatabase) {
        String command = "CREATE TABLE Contact (" +
                Contact.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contact.COLUMN_NAME + " TEXT," +
                Contact.COLUMN_BIRTHDAY + " INTEGER" +
                ")";

        sqLiteDatabase.execSQL(command);
    }*/

    private void createIdeaContactLinkTable(SQLiteDatabase sqLiteDatabase) {
        String command = "CREATE TABLE "+ IdeaContactLink.TABLE_NAME + "(" +
                IdeaContactLink.COL_CONTACT_ID + " INTEGER," +
                IdeaContactLink.COL_IDEA_ID + " INTEGER)";

        sqLiteDatabase.execSQL(command);
    }
}
