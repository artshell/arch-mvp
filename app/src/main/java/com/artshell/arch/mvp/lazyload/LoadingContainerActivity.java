package com.artshell.arch.mvp.lazyload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.artshell.arch.mvp.R;

/**
 * @author artshell on 2018/6/1
 */
public class LoadingContainerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_lazy_loading_container);
        ViewPager viewPager = findViewById(R.id.pager_container);
        TabLayout layout    = findViewById(R.id.tab_container);
        viewPager.setAdapter(new LazyLoadingPager(getSupportFragmentManager()));
        layout.setupWithViewPager(viewPager);
    }
}
