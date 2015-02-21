package gdgandroidtitlan.retrofitstudyjams.activities;

import android.os.Bundle;

import gdgandroidtitlan.retrofitstudyjams.fragments.FragmentGithub;
import gdgandroidtitlan.retrofitstudyjams.R;


/**
 * Created by Jhordan on 20/02/15.
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getToolbar().setTitle(getResources().getString(R.string.app_name));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, FragmentGithub.newInstance())
                    .commit();
        }

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }


}
