package com.example.android.androidsharetest;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.File;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    EditText tvUserInput;

    ShareActionProvider shareActionProvider;


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
//        tvUserInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                setShareIntent(createShareIntent(getUserInput()));
//            }
//        });

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
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.getItemId() == R.id.menu_item_share) {
                View itemChooser = item.getActionView();
                if (itemChooser != null) {
                    itemChooser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setShareIntent(createShareIntent(getUserInput()));
                        }
                    });
                }
            }
        }

        //Ponemos un listener para cuando se pulse el boton compartir
        shareActionProvider.setOnShareTargetSelectedListener(new ShareActionProvider.OnShareTargetSelectedListener() {
            @Override
            public boolean onShareTargetSelected(ShareActionProvider actionProvider, Intent intent) {
                //Actualizamos el Intent con el texto del usuario
                setShareIntent(createShareIntent(getUserInput()));
                return false;
            }
        });

        setShareIntent(createShareIntent(getUserInput()));
//        if(shareActionProvider!=null){
//            shareActionProvider.setShareIntent(createShareIntent("Holaaaaaa"));
//        }else{
//            Log.d(LOG_TAG, "Share Action Provider is null?");
//        }

    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(shareIntent);
        }else{
            Log.d(LOG_TAG, "Share Action Provider is null?");
        }
    }

    private Intent createShareIntent(String textToShare){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        //Para que al terminar de compartir vuelva a nuestra app y no la que gestiona el intent
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                textToShare);
        return shareIntent;
    }
}
