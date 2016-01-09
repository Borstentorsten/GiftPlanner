package pb.giftplanner.business;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;

import pb.giftplanner.R;

/**
 * Created by Paule on 29.04.2015.
 */
public class EventAdapter extends BaseAdapter {
    Activity mActivity;

    LinkedList<Event> mEventList;

    public EventAdapter(Activity activity) {
        mActivity = activity;

        ContactsAdapter contactsAdapter = Worker.getInstance().getContactFactory().getContactsAdapterFromPhone(mActivity);

        mEventList = new LinkedList<Event>();
        for(int nPos = 0; nPos < contactsAdapter.getCount(); nPos++) {
            Contact contact = contactsAdapter.getContact(nPos);
            Event event = new Event();
            event.setDate(Worker.getInstance().getContactFactory().getContactBirthday(contact, activity));

            String title = "Geburtstag ";
            title += contact.getName();
            event.setTitle(title);
        }
    }

    @Override
    public int getCount() {
        return mEventList.size();
    }

    @Override
    public Object getItem(int i) {
        Event event = mEventList.get(i);
        return event;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.event_list_item, null);
        }

        Event event = (Event)getItem(i);
        TextView txtDate = (TextView)view.findViewById(R.id.txtEventDate);
        TextView txtEventName = (TextView)view.findViewById(R.id.txtEventName);

        txtDate.setText(event.getDate().toString());
        txtEventName.setText(event.getTitle());
        return view;
    }
}
