package pb.giftplanner.app;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import pb.giftplanner.R;
import pb.giftplanner.business.Idea;
import pb.giftplanner.business.Worker;

public class IdeaListFragment extends ListFragment
                              implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, DialogInterface.OnClickListener {

    Activity mParentActivity = null;

    SimpleCursorAdapter mIdeaAdapter = null;

    Idea _selectedIdea = null;

    public IdeaListFragment() {
        // Required empty public constructor
    }

    private void refreshView() {
        if(getView() != null) {
            ListView ideaView = (ListView) getView().findViewById(R.id.listViewIdeas);
            mIdeaAdapter = Worker.getInstance().getIdeaFactory().getIdeaListAdapter(mParentActivity);
            ideaView.setAdapter(mIdeaAdapter);
            ideaView.setOnItemLongClickListener(this);
            ideaView.setOnItemClickListener(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_idea_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        refreshView();
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
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        CharSequence options[] = new CharSequence[] {"Löschen"};
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(mParentActivity);
        dlgBuilder.setTitle("Idee");
        dlgBuilder.setItems(options, this);

        _selectedIdea = new Idea((Cursor)mIdeaAdapter.getItem((int)id-1));
        dlgBuilder.show();
        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case 0: {
                Worker.getInstance().getIdeaFactory().deleteIdea(_selectedIdea.getId());
                refreshView();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor idea = (Cursor)mIdeaAdapter.getItem(position);
        Idea selectedIdea = new Idea(idea);
        if(selectedIdea != null) {
            ((MainActivity)mParentActivity).showIdea(selectedIdea);
        }
    }
}
