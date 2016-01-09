package pb.giftplanner.business;

import android.app.Activity;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Paule on 27.10.2014.
 */
public class Worker {
    private SQLiteHelper mSQLiteHelper = null;

    private IdeaFactory mIdeaFactory = null;
    private ContactFactory mContactFactory = null;

    static Worker mWorker = null;

    public static void initializeWorker(Context context) {
        mWorker = new Worker(context);
    }

    public static Worker getInstance() {
        return mWorker;
    }

    public Worker(Context context) {
        mSQLiteHelper = new SQLiteHelper(context);
        mContactFactory = new ContactFactory(context);
    }


    public IdeaFactory getIdeaFactory() {
        if(mIdeaFactory == null) {
            mIdeaFactory = new IdeaFactory();
        }

        return mIdeaFactory;
    }

    public ContactFactory getContactFactory() {
        return mContactFactory;
    }

    public SQLiteHelper getSQLiteHelper() {
        return mSQLiteHelper;
    }

    public ContactsList parseSemicolonSeperatedContacts(String contacts) {
        String[] contactItems = contacts.split(";");

        ContactsList retList = new ContactsList();
        for(String strIter: contactItems) {
            Contact contact = new Contact();
            contact.setName(strIter);
            retList.add(contact);
        }

        return retList;
    }

    private void createIdeaContactLinks(Long ideaId, List<Contact> contactList) {
        for (Contact contact : contactList) {
            long contactId = contact.getId();

            if(contactId > 0) {
                if(!IdeaContactLink.existsLink(ideaId, contactId)) {
                    IdeaContactLink.createLink(ideaId, contactId);
                }
            }
        }
    }
    /**
     * @param contactList List of contact Names, seperated by ';'
      */
    public boolean createIdea(String name, String description, ContactsList contactList) {
        Idea idea = getIdeaFactory().createIdea(name, description);

        if(idea != null) {
            createIdeaContactLinks(idea.getId(), contactList);

        }

        return true;
    }

    public boolean updateIdea(Idea idea, ContactsList contactList) {
        getIdeaFactory().updateIdea(idea);
        List<Contact> linkedContacts = getContactFactory().getContactsByIdea(idea);
        for(Contact linkedContact: linkedContacts) {

            // Delete all linked Contacts, that are no longer chosen by the user
            if(!contactList.contains(linkedContact)) {
                IdeaContactLink.deleteLink(idea.getId(), linkedContact.getId());
            }
        }

        // Create the new contact Links
        createIdeaContactLinks(idea.getId(), contactList);

        return true;
    }

    public EventAdapter getEventAdapter(Activity activity) {
        EventAdapter adapter = new EventAdapter(activity);
        return adapter;
    }
}
