package com.pinkrose.rsa;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
public class Start extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp
                (
                        R.id.navigation_drawer,
                        (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    private EditText message, privKeyT, pubKeyT, maxKey, encDat;
    private PrivateKey privKey;
    private PublicKey pubKey;
    private ClipboardManager cm;
    private TextView t2, t4;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        message = (EditText) findViewById(R.id.editText);
        pubKeyT = (EditText) findViewById(R.id.editText2);
        privKeyT = (EditText) findViewById(R.id.editText3);
        maxKey = (EditText) findViewById(R.id.editText4);
        encDat = (EditText) findViewById(R.id.editText5);

        t2 = (TextView) findViewById(R.id.textView2);
        t4 = (TextView) findViewById(R.id.textView4);

        Button encB = (Button) findViewById(R.id.button);
        Button genB = (Button) findViewById(R.id.button2);
        Button setB = (Button) findViewById(R.id.button3);
        Button copyB = (Button) findViewById(R.id.button4);
        Button decB = (Button) findViewById(R.id.button5);
        Button copyB2 = (Button) findViewById(R.id.button6);

        encB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t2.setText(RSA.encrypt(message));
            }
        });

        setB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RSA.setKeys(pubKeyT.getText().toString(),
                        maxKey.getText().toString(),
                        privKeyT.getText().toString());
            }
        });
        copyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData data = ClipData.newPlainText("Encrypted Text", t2.getText().toString());
                cm.setPrimaryClip(data);
            }
        });
        genB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RSA.genKeys();
                pubKeyT.setText(RSA.getPubKey().toString());
                privKeyT.setText(RSA.getPrivKey().toString());
                maxKey.setText(RSA.getPubKey().mToString());
            }
        });
        decB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t4.setText(RSA.decrypt(new BigInteger(encDat.getText().toString())));
            }
        });
        copyB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData data = ClipData.newPlainText("Encrypted Text", t4.getText().toString());
                cm.setPrimaryClip(data);
            }
        });
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
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
            getMenuInflater().inflate(R.menu.start, menu);
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
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_start, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Start) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
