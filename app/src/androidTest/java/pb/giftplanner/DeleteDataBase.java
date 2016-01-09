package pb.giftplanner;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import pb.giftplanner.business.SQLiteHelper;

/**
 * Created by Paule on 02.11.2014.
 */
public class DeleteDataBase extends ApplicationTestCase<Application> {

    public DeleteDataBase() {
        super(Application.class);
    }

    public DeleteDataBase(Class<Application> applicationClass) {
        super(applicationClass);
    }

    public void testDeleteDataBase() {
        //String dataBasePath = getContext().getDatabasePath(SQLiteHelper.DATABASE_NAME).getPath();
        Log.d("test", "test");
        getContext().deleteDatabase(SQLiteHelper.DATABASE_NAME);
        //SQLiteHelper sqlLiteHelper = new SQLiteHelper(getContext());
        //sqlLiteHelper.getWritableDatabase().
        assertEquals(true, true);
    }
}
