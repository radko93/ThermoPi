package zpi.thermopi;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerCallbacks {


    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        setupNavigationDrawer();
        setupViewPager();

    }

    private void setupNavigationDrawer()
    {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData("Jan Kalinicz", "test@pwr.edu.pl", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
    }
    private void setupViewPager()
    {
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),
                MainActivity.this, mNavigationDrawerFragment.getSelectedItemId(0).getId()));

        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setAlpha(normalizedposition);
            }
        });
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

        if(mNavigationDrawerFragment!=null && viewPager!=null) {
            MyPagerAdapter adapter = (MyPagerAdapter) viewPager.getAdapter();
            adapter.setTerarriumId(mNavigationDrawerFragment.getSelectedItemId(position).getId());
        }


    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {

            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

}


