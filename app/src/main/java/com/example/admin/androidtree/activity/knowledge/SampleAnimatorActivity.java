package com.example.admin.androidtree.activity.knowledge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.admin.androidtree.R;
import com.example.admin.androidtree.activity.fragment.BaseFragment;
import com.example.admin.androidtree.activity.fragment.factory.AnimatorFactory;
import com.example.admin.androidtree.activity.fragment.factory.IBuilderFactory;
import com.example.admin.androidtree.base.util.Constant;

/**
 * @author Diana
 * @date 2017/7/18
 */

public class SampleAnimatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_custom_controls);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int fragIndex = getIntent().getIntExtra(Constant.Extra.FRAGMENT_INDEX, 0);
        IBuilderFactory factory = new AnimatorFactory();
        BaseFragment detailF = (BaseFragment) factory.builderFragment(getSupportFragmentManager(), fragIndex);

        getSupportActionBar().setTitle(detailF.getTabTitle());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content, detailF, detailF.getClass().getSimpleName())
                .commit();
    }
}
