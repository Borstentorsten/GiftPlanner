package pb.giftplanner.business;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Paule on 01.05.2015.
 */
public class Event {

    Calendar mDate;
    String mTitle;

    Calendar getDate() { return mDate; }
    void setDate(Calendar date) { mDate = date; }

    String getTitle() { return mTitle; }
    void setTitle(String title) { mTitle = title; }
}
