package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

/**
 * Created by livia on 8/21/17.
 */

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {
    private int headIndex = 0;
    private int bodyIndex = 0;
    private int legIndex = 0;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.android_me_linear_layout) != null){
            mTwoPane = true;
            //GridView gridView = (GridView) findViewById(R.id.images_grid_view);
            //gridView.setNumColumns(2);
            //Button nextButton = (Button) findViewById(R.id.next_button);
            //nextButton.setVisibility(View.GONE);
            if(savedInstanceState == null) {
                // In two-pane mode, add initial BodyPartFragments to the screen
                FragmentManager fragmentManager = getSupportFragmentManager();

                // Creating a new head fragment
                BodyPartFragment headFragment = new BodyPartFragment();
                headFragment.setImageIds(AndroidImageAssets.getHeads());
                // Add the fragment to its container using a transaction
                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                // New body fragment
                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImageIds(AndroidImageAssets.getBodies());
                fragmentManager.beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();

                // New leg fragment
                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setImageIds(AndroidImageAssets.getLegs());
                fragmentManager.beginTransaction()
                        .add(R.id.leg_container, legFragment)
                        .commit();
            }
        }
        else {
            mTwoPane = false;
        }
    }

    public void onImageSelected(int position){
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();
        int bodyPartNumber = position/12;
        int listIndex = position - 12*bodyPartNumber;
        if(mTwoPane){
            BodyPartFragment newFragment = new BodyPartFragment();

            // Set the currently displayed item for the correct body part fragment
            switch (bodyPartNumber) {
                case 0:
                    // A head image has been clicked
                    // Give the correct image resources to the new fragment
                    newFragment.setImageIds(AndroidImageAssets.getHeads());
                    newFragment.setListIndex(listIndex);
                    // Replace the old head fragment with a new one
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;
                case 1:
                    newFragment.setImageIds(AndroidImageAssets.getBodies());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, newFragment)
                            .commit();
                    break;
                case 2:
                    newFragment.setImageIds(AndroidImageAssets.getLegs());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, newFragment)
                            .commit();
                    break;
                default:
                    break;
            }
        }
        else {
            switch(bodyPartNumber){
                case 0: headIndex = listIndex;
                    break;
                case 1: bodyIndex = listIndex;
                    break;
                case 2: legIndex = listIndex;
                    break;
                default: break;
            }

            Bundle b = new Bundle();
            b.putInt("headIndex", headIndex);
            b.putInt("bodyIndex", bodyIndex);
            b.putInt("legIndex", legIndex);
            Log.d("headIndex", Integer.toString(headIndex));
            Log.d("bodyIndex", Integer.toString(bodyIndex));
            Log.d("legIndex", Integer.toString(legIndex));

            final Intent intent = new Intent(this, AndroidMeActivity.class);
            intent.putExtras(b);

            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    startActivity(intent);
                }
            });
        }


    }
}
