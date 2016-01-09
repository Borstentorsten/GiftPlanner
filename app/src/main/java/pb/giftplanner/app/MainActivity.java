package pb.giftplanner.app;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import java.util.List;

import pb.giftplanner.R;
import pb.giftplanner.business.Contact;
import pb.giftplanner.business.ContactsList;
import pb.giftplanner.business.Idea;
import pb.giftplanner.business.Worker;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    final int FRAGMENT_OVERVIEW = 0;
    final int FRAGMENT_NEW_IDEA = 1;
    final int FRAGMENT_IDEA_LIST = 2;
    final int FRAGMENT_CONTACT_LIST = 3;

    final int FRAGMENT_CONTACT_PICKER_LIST = 100;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    CharSequence mTitle;

    Fragment mCurrentFragment = null;

    ContactPickerEvents mCurrentContactPickerEventHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Worker.initializeWorker(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        switchFragment(position, null, null);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void OnIdeaSaved() {
        mNavigationDrawerFragment.selectItem(0);
        restoreActionBar();
    }

    public void startContactsPicker(ContactPickerEvents contactPickerEventHandler, ContactsList selectedContacts) {
        mCurrentContactPickerEventHandler = contactPickerEventHandler;
        switchFragment(FRAGMENT_CONTACT_PICKER_LIST, null, selectedContacts);
    }

    public void closeContactsPicker() {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStackImmediate();

        mCurrentContactPickerEventHandler.contactsChosen(((ContactListFragment)mCurrentFragment).getChosenContacts());

        mCurrentContactPickerEventHandler = null;
    }

    public void showIdea(Idea idea) {
        switchFragment(FRAGMENT_NEW_IDEA, idea, null);
    }

    private void switchFragment(int position, Idea idea, ContactsList selectedContacts) {
        Fragment newFragment = null;

        switch(position) {
            case FRAGMENT_OVERVIEW: {
                mTitle = "Übersicht";
                newFragment = OverviewFragment.newInstance();
            }
            break;

            case FRAGMENT_NEW_IDEA: {
                if(idea == null) {
                    mTitle = "Neue Idee";
                }
                else {
                    mTitle = "Idee bearbeiten";

                }
                newFragment = IdeaFragment.newInstance();
                ((IdeaFragment)newFragment).setExistingIdea(idea);
            }
            break;

            case FRAGMENT_IDEA_LIST: {
                mTitle = "Ideen";
                newFragment = new IdeaListFragment();
            }
            break;

            case FRAGMENT_CONTACT_LIST: {
                mTitle = "Kontakte";
                newFragment = new ContactListFragment();
                ((ContactListFragment)newFragment).setShowCheckBoxes(false);
            }
            break;

            case FRAGMENT_CONTACT_PICKER_LIST: {
                mTitle = "Kontakte";
                newFragment = new ContactListFragment();
                ((ContactListFragment)newFragment).preSelectContacts(selectedContacts);
                ((ContactListFragment)newFragment).setShowCheckBoxes(true);
            }
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, newFragment)
                .addToBackStack(null)
                .commit();

        mCurrentFragment = newFragment;
    }
}