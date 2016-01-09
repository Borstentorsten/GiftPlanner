package pb.giftplanner.app;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import pb.giftplanner.R;
import pb.giftplanner.business.Contact;
import pb.giftplanner.business.ContactsList;
import pb.giftplanner.business.Idea;
import pb.giftplanner.business.Worker;

public class IdeaFragment extends Fragment
        implements View.OnClickListener, ContactPickerEvents {

    Activity mParentActivity = null;
    Idea mExistingIdea = null;
    ContactsList mContacts = new ContactsList();

    TextView mTextViewIdeaName = null;
    TextView mTextViewIdeaDescription = null;
    TextView mTextViewContactList = null;

    public static IdeaFragment newInstance() {
        IdeaFragment fragment = new IdeaFragment();
        return fragment;
    }

    public IdeaFragment() {
        // Required empty public constructor
    }

    public void setExistingIdea(Idea idea) {
        mExistingIdea = idea;
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

        Button btnChooseContacts = (Button)getView().findViewById(R.id.btnChooseContacts);
        if(btnChooseContacts != null) {
            btnChooseContacts.setOnClickListener(this);
        }


        mTextViewIdeaName = (TextView)getView().findViewById(R.id.editIdeaName);
        mTextViewIdeaDescription = (TextView)getView().findViewById(R.id.editIdeaDescription);
        mTextViewContactList = (TextView)getView().findViewById(R.id.txtIdeaPersonList);

        if(mExistingIdea != null) {
            mTextViewIdeaName.setText(mExistingIdea.getName());
            mTextViewIdeaDescription.setText(mExistingIdea.getDescription());

            String contactString = "";

            List<Contact> contacts = Worker.getInstance().getContactFactory().getContactsByIdea(mExistingIdea);
            for(Contact contact: contacts) {
                contactString += contact.getName();
                contactString += ";";
                mContacts.add(contact);
            }
            mTextViewContactList.setText(contactString);
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
                String ideaName = mTextViewIdeaName.getText().toString();
                String ideaDescription = mTextViewIdeaDescription.getText().toString();

                if(mExistingIdea == null) {
                    Worker.getInstance().createIdea(ideaName, ideaDescription, mContacts);
                }
                else {
                    mExistingIdea.setName(ideaName);
                    mExistingIdea.setDescription(ideaDescription);

                    Worker.getInstance().updateIdea(mExistingIdea, mContacts);
                }

                ((MainActivity)mParentActivity).OnIdeaSaved();
            }
            break;

            case R.id.btnChooseContacts: {
                ((MainActivity)mParentActivity).startContactsPicker(this, mContacts);
            }
            break;
        }
    }

    @Override
    public void contactsChosen(List<Contact> contacts) {
        mContacts.clear();
        for(Contact contact: contacts) mContacts.add(contact);
        TextView txtContacts = (TextView)getView().findViewById(R.id.txtIdeaPersonList);
        if(txtContacts != null) {
            String sContacts = ""; //txtContacts.getText().toString();
            for(Contact contact : contacts) {
                sContacts += contact.getName();
                sContacts += ";";
            }
            txtContacts.setText(sContacts);
        }

    }
}