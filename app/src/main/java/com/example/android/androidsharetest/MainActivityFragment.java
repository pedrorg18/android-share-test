package com.example.android.androidsharetest;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.File;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    EditText tvUserInput;

    ShareActionProvider mShareActionProvider;


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        tvUserInput = (EditText)rootView.findViewById(R.id.tvUserInput);

        return rootView;

    }

    public String getUserInput(){
        return tvUserInput.getText().toString();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main_fragment, menu);

        // Get the menu item.
        MenuItem menuItem = menu.findItem(R.id.menu_item_share);
        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        // Set history different from the default before getting the action
        // view since a call to MenuItem.getActionView() calls
        // onCreateActionView() which uses the backing file name. Omit this
        // line if using the default share history file is desired.
//        mShareActionProvider.setShareHistoryFileName("custom_share_history.xml");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_item_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
//            Uri uri = Uri.fromFile(new File(getFilesDir(), "foo.jpg"));
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
            shareIntent.putExtra(Intent.EXTRA_TEXT, tvUserInput.getText());
//            startActivity(Intent.createChooser(shareIntent, "Share link!"));
            doShare(shareIntent);
            return true;
        }

        return true;
    }

    public void doShare(Intent shareIntent) {
        // When you want to share set the share intent.
        mShareActionProvider.setShareIntent(shareIntent);
    }
}
