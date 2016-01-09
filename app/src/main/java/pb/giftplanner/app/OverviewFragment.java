package pb.giftplanner.app;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import pb.giftplanner.R;
import pb.giftplanner.business.Worker;

/**
 * Created by Paule on 26.10.2014.
 */
public class OverviewFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static OverviewFragment newInstance() {
        OverviewFragment fragment = new OverviewFragment();
        return fragment;
    }

    public OverviewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }



    @Override
    public void onViewCreated(android.view.View view, android.os.Bundle savedInstanceState) {
        ListView listView = (ListView)getView().findViewById(R.id.listViewEvents);
        listView.setAdapter(Worker.getInstance().getEventAdapter(getActivity()));
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
