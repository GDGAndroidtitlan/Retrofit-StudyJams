package gdgandroidtitlan.retrofitstudyjams.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import gdgandroidtitlan.retrofitstudyjams.R;

/**
 * Created by Jhordan on 20/02/15.
 */
public abstract class BaseActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
          //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected abstract int getLayoutResource();

    public Toolbar getToolbar() {
        return toolbar;
    }

    protected void setActionBarIcon(int iconRes) {
        toolbar.setNavigationIcon(iconRes);
    }
}
