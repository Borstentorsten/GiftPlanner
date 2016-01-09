package pb.giftplanner;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;
import android.util.Log;

import pb.giftplanner.business.IdeaContactLink;
import pb.giftplanner.business.Worker;

/**
 * Created by Paule on 26.09.2015.
 */
public class ShowIdeaContactLinks extends ApplicationTestCase<Application>  {
    public ShowIdeaContactLinks() {
        super(Application.class);
    }
    public ShowIdeaContactLinks(Class<Application> applicationClass) {
        super(applicationClass);
    }

    public void testShowIdeaContactLinks() {
        Worker.initializeWorker(getContext());
        SQLiteDatabase db = Worker.getInstance().getSQLiteHelper().getReadableDatabase();
        Cursor cursor = db.query(IdeaContactLink.TABLE_NAME, IdeaContactLink.COMPLETE_COLUMN_LIST, null, null, null, null, null);
        while(cursor.moveToNext()) {
            String message = String.format("IdeaId=%d; ContactId=%d",
                    cursor.getInt(cursor.getColumnIndex(IdeaContactLink.COL_IDEA_ID)),
                    cursor.getInt(cursor.getColumnIndex(IdeaContactLink.COL_CONTACT_ID)));
            System.out.println(message);
        }
    }
}
