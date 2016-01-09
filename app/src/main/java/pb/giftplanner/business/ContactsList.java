package pb.giftplanner.business;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Paule on 29.04.2015.
 */
public class ContactsList extends LinkedList<Contact> {
    Contact findByDisplayName(String displayName) {
        for(Contact contact: this) {
            if(contact.getName().equals(displayName)) {
                return contact;
            }
        }

        return null;
    }
}
