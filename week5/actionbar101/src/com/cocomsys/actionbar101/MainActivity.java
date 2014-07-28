package com.cocomsys.actionbar101;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.cocomsys.actionbar101.ui.fragments.BarFragment;
import com.cocomsys.actionbar101.ui.fragments.FooFragment;

public class MainActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.mi_save:
                showMsg(item);
                break;
            case R.id.mi_settings:
                showMsg(item);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void showMsg(MenuItem item){
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void init(){
        initActionBar();
    }

    ActionBar actionBar;
    Fragment frgFoo, frgBar;

    private void initActionBar(){
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tabFoo = actionBar.newTab().setText("Foo");
        ActionBar.Tab tabBar = actionBar.newTab().setText("Bar");

        frgFoo = new FooFragment();
        frgBar = new BarFragment();

        tabFoo.setTabListener(new MainTabListener(frgFoo));
        tabBar.setTabListener(new MainTabListener(frgBar));

        actionBar.addTab(tabFoo);
        actionBar.addTab(tabBar);
    }

    private class MainTabListener implements ActionBar.TabListener{

        private Fragment fragment;
        public MainTabListener(Fragment fragment){
            this.fragment = fragment;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            ft.add(R.id.container, fragment, null);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            ft.remove(fragment);
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
    }

}
