package com.example.tpasus.foodyapp.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.tpasus.foodyapp.Adapters.AdaperViewPagerHomePage;
import com.example.tpasus.foodyapp.R;

//OnPageChangeListener: lắng nghe sự kiện thay đổi page của viewpager, sự kiện scroll
//OnCheckedChangeListener: lắng nghe sự thay đổi checked của radiobutton
public class HomePageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    ViewPager viewPagerHomePage;
    RadioButton rdAddress, rdFood;
    RadioGroup rd_adress_food;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_homepage);

        viewPagerHomePage = findViewById(R.id.viewpager_homepage);
        rdAddress = findViewById(R.id.rdAddress);
        rdFood= findViewById(R.id.rdFood);
        rd_adress_food=findViewById(R.id.rd_address_food);

        AdaperViewPagerHomePage adaperViewPagerHomePage = new AdaperViewPagerHomePage(getSupportFragmentManager()); //gọi adapter
        viewPagerHomePage.setAdapter(adaperViewPagerHomePage);  //gán adpater cho viewpagerhomepage

        viewPagerHomePage.addOnPageChangeListener(this);
        rd_adress_food.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //khi event đc thay đổi, chạy vào onPageSelect lấy vị trí position mà viewpager đang hiển thị
    @Override
    public void onPageSelected(int position) {
        switch (position){
            //gán sự kiện check vào rdAddress khi position của viewpager là 0
            case 0:
                rdAddress.setChecked(true);
                break;

            //gán sự kiện check vào rdFood khi position của viewpager là 1
            case 1:
                rdFood.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rdAddress:
                viewPagerHomePage.setCurrentItem(0);
                break;
            case R.id.rdFood:
                viewPagerHomePage.setCurrentItem(1);
                break;
        }
    }
}
