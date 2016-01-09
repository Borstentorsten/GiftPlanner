package pb.giftplanner;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class IdeaFragment extends Fragment implements View.OnClickListener{

    Activity mParentActivity = null;

    public static IdeaFragment newInstance() {
        IdeaFragment fragment = new IdeaFragment();
        return fragment;
    }

    public IdeaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_idea, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button btnSaveIdea = (Button)getView().findViewById(R.id.btnSaveIdea);
        if(btnSaveIdea != null) {
            btnSaveIdea.setOnClickListener(this);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mParentActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mParentActivity = null;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnSaveIdea: {
                ((IdeaFragmentEvents)mParentActivity).OnIdeaSaved();
            }
            break;
        }
    }
}

interface IdeaFragmentEvents {
    public void OnIdeaSaved();
}