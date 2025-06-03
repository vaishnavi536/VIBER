package com.example.viber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setSubtitle("Welcome,Vaishnavi Shukla");
       // getSupportActionBar().setTitle("VIBER");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#682550'>VIBER</font>"));
       // getSupportActionBar().setIcon(R.drawable.baseline_person_24);

        //set adapter to the ViewPager
        ViewPager2 viewpage1=findViewById(R.id.viewpagerhome);
        ViewPagerAdopter adopter=new ViewPagerAdopter(getSupportFragmentManager(),getLifecycle());
        viewpage1.setAdapter(adopter);
        //scroll tabs onscrolling of viewpager
        TabLayout tabs=findViewById(R.id.tablayouthome);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               viewpage1.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });

        viewpage1.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                tabs.selectTab(tabs.getTabAt(position));

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout)
        {
            //statement executes on click of Logout optionment
            FirebaseAuth.getInstance().signOut();
            Intent in=new Intent(this,MainActivity.class);
            startActivity(in);
        }
        else if(item.getItemId()==R.id.setting)
        {//statement executes onclick of setting optionmenu

        }
        return super.onOptionsItemSelected(item);
    }
}




//crete an adapter for ViewPager
class ViewPagerAdopter extends FragmentStateAdapter
{

    public ViewPagerAdopter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new ChatFragment();
        else if (position==1)
        return new StoryFragment();
        else
        return new ProfileFragment();
    }


    @Override
    public int getItemCount() {
        return 3;
    }
}
















