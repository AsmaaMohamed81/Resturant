package com.alatheer.menu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.fagments.Fragment_Admin_Chefs;
import com.alatheer.menu.fagments.Fragment_Admin_Notifications;
import com.alatheer.menu.fagments.Fragment_Admin_Restaurants;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

public class AdminActivity extends AppCompatActivity {

    private TextView tv_title;
    private AHBottomNavigation ahBottomNav;
    private Toolbar toolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initView();
    }

    private void initView() {
        tv_title =findViewById(R.id.tv_title);
        toolBar = findViewById(R.id.toolBar);
        ahBottomNav = findViewById(R.id.ahBottomNav);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.restaurants),R.drawable.nav_restaurant, R.color.black);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.chefs),R.drawable.nav_chef, R.color.black);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.nots),R.drawable.nav_notification, R.color.black);

        ahBottomNav.setAccentColor(ContextCompat.getColor(this,R.color.colorPrimary));
        ahBottomNav.setDefaultBackgroundColor(ContextCompat.getColor(this,R.color.white));
        ahBottomNav.setColored(false);
        ahBottomNav.setForceTint(false);
        ahBottomNav.setInactiveColor(ContextCompat.getColor(this,R.color.black));
        ahBottomNav.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        ahBottomNav.setTitleTextSizeInSp(17f,13f);
        ahBottomNav.addItem(item1);
        ahBottomNav.addItem(item2);
        ahBottomNav.addItem(item3);
        ahBottomNav.setCurrentItem(0,false);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Fragment_Admin_Restaurants.getInstance()).commit();
        UpdateTitle(getString(R.string.restaurants));
        ahBottomNav.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                ahBottomNav.setCurrentItem(position,false);
                if (position==0)
                {
                    UpdateTitle(getString(R.string.restaurants));
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Fragment_Admin_Restaurants.getInstance()).commit();


                }else if (position==1)
                {
                    UpdateTitle(getString(R.string.chefs));
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Fragment_Admin_Chefs.getInstance()).commit();


                }else if (position==2)
                {
                    UpdateTitle(getString(R.string.nots));
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Fragment_Admin_Notifications.getInstance()).commit();


                }

                return false;
            }
        });

        AddNotificationCount(15);

    }

    private void AddNotificationCount(int count) {
        if (count==0)
        {
            AHNotification ahNotification = new AHNotification.Builder()
                    .setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary))
                    .setText("")
                    .setTextColor(ContextCompat.getColor(this,R.color.white))
                    .build();
            ahBottomNav.setNotification(ahNotification,2);
        }else if (count>0)
        {
            AHNotification ahNotification = new AHNotification.Builder()
                    .setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary))
                    .setText(String.valueOf(count))
                    .setTextColor(ContextCompat.getColor(this,R.color.white))
                    .build();
            ahBottomNav.setNotification(ahNotification,2);
        }


    }

    private void UpdateTitle(String title)
    {
        tv_title.setText(title);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.daily_report:
                Intent intent1 = new Intent(AdminActivity.this,AdminReportsActivity.class);
                startActivity(intent1);
                break;
            case R.id.weekly_report:
                Intent intent2 = new Intent(AdminActivity.this,AdminReportsActivity.class);
                startActivity(intent2);
                break;
            case R.id.monthly_report:
                Intent intent3 = new Intent(AdminActivity.this,AdminReportsActivity.class);
                startActivity(intent3);
                break;
            case R.id.annual_report:
                Intent intent4 = new Intent(AdminActivity.this,AdminReportsActivity.class);
                startActivity(intent4);
                break;
            case R.id.logout:
                break;
        }
        return true;
    }
}
