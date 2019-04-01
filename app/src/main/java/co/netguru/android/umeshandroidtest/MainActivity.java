package co.netguru.android.umeshandroidtest;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ClickItemPosition {

    private ImageView ivMovie;
    private TextView tvName;
    private TextView tvYear;
    private TextView tvRatings;
    private RecyclerView rcvGenre;
    private TabLayout tabLayout;
    private ViewPager vpFragments;
    private ArrayList<Fragment> fragmentsList;
    private ListFragment fragmentTab1;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Accept Ticket Screen");
        initViews();
    }

    private void initViews() {

        ivMovie = (ImageView) findViewById(R.id.iv_movie);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvYear = (TextView) findViewById(R.id.tv_year);
        tvRatings = (TextView) findViewById(R.id.tv_ratings);
        rcvGenre = (RecyclerView) findViewById(R.id.rcv_genre);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        vpFragments = (ViewPager) findViewById(R.id.vp_fragments);


        fragmentsList = new ArrayList<>();

        fragmentTab1 = new ListFragment();
        fragmentsList.add(fragmentTab1);
        fragmentsList.add(fragmentTab1);


        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentsList);
        vpFragments.setAdapter(mPagerAdapter);
        vpFragments.setOffscreenPageLimit(fragmentsList.size());
        tabLayout.setupWithViewPager(vpFragments);
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentsList;

        public PagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragmentsList) {
            super(fm);
            this.mFragmentsList = mFragmentsList;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Tab : "+position;
        }

        @Override
        public int getCount() {
            return mFragmentsList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentsList.get(position);
        }
    }

    @Override
    public void clickOnItem(MovieModel object) {

    }
}
