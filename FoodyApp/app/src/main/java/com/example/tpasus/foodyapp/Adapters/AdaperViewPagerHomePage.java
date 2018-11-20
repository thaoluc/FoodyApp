package com.example.tpasus.foodyapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tpasus.foodyapp.View.Fragments.AddressFragment;
import com.example.tpasus.foodyapp.View.Fragments.FoodFragment;

public class AdaperViewPagerHomePage extends FragmentStatePagerAdapter {

    FoodFragment foodFragment;
    AddressFragment addressFragment;

    public AdaperViewPagerHomePage(FragmentManager fm) {
        super(fm);

        foodFragment = new FoodFragment();
        addressFragment = new AddressFragment();
    }

    //getItem trả ra item tương ứng với position mà nó gọi (truyền vào position cho 2 fragment)
    //ViewPager dạng mảng, bắt đầu từ 0
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return addressFragment;
            case 1:
                return foodFragment;
            default:
                return null;
        }

    }

    //chạy vào getCount trước, cho biết ViewPager sẽ có bao nhiêu item
    @Override
    public int getCount() {
        return 2;
    }
}
