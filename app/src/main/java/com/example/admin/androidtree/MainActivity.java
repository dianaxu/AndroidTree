package com.example.admin.androidtree;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.diana.image.ImageManager;
import com.example.admin.androidtree.activity.fragment.BaseFragment;
import com.example.admin.androidtree.activity.fragment.home.HomeFragment;
import com.example.admin.androidtree.activity.fragment.knowledge.KnowledgeFragment;
import com.example.admin.androidtree.base.util.SourceUtils;


public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, IMainView, BaseFragment.OnActionBarUpdateListener {

    private DrawerLayout mDrawerLayout;

    private MainPresenter mPresenter;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mPresenter = new MainPresenter(this);
        mFragmentManager = getSupportFragmentManager();
        showKnowledge();
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //设置圆形头像
        ImageView headerIcon = (ImageView) navigationView.getHeaderView(0)
                .findViewById(R.id.iv_header_icon);
        ImageManager.ImageOption option = new ImageManager.ImageOption();
        option.shape = ImageManager.ImageOption.IMAGE_SHAPE_CIRCLE;
        option.loadingDrawable = getDrawable(R.drawable.e);
        option.failDrawable = getDrawable(R.drawable.a);
        ImageManager.getInstance(this).displayImage(SourceUtils.randomImageUrl(),
                headerIcon, option);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("menuIndex", 2);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_setting:
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        mDrawerLayout.closeDrawers();
        mPresenter.selectFragment(item.getItemId());
        return false;
    }

    @Override
    public void showHome() {
        setActionBar(R.string.menu_home_title);
        mFragmentManager.beginTransaction().replace(
                R.id.fl_list,
                new HomeFragment(),
                HomeFragment.class.getSimpleName()).commit();
    }

    @Override
    public void showSetting() {
        setActionBar(R.string.menu_setting_title);
    }

    @Override
    public void showKnowledge() {
        setActionBar(R.string.menu_knowladge_title);
        mFragmentManager.beginTransaction().replace(
                R.id.fl_list,
                new KnowledgeFragment(),
                KnowledgeFragment.class.getSimpleName()).commit();
    }

    @Override
    public void showThirdLibrary() {
        setActionBar(R.string.menu_third_title);
    }

    @Override
    public void updateActionBar(Toolbar toolbar) {
        if (toolbar != null)
            setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.menu_knowladge_title);

    }

    private void setActionBar(@StringRes int resId) {
        setActionBar(getResources().getString(resId));
    }

    private void setActionBar(String title) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }
}
