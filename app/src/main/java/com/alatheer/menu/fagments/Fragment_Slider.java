package com.alatheer.menu.fagments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alatheer.menu.R;
import com.alatheer.menu.languagehelper.LanguageHelper;
import com.alatheer.menu.tags.Tags;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by elashry on 15/09/2018.
 */

public class Fragment_Slider extends Fragment {
    private static String TITLE="0";
    private static String IMAGE="1";


    private ImageView image;
    private TextView tv_title;


    @Override
    public void onAttach(Context context) {

        Paper.init(context);
        String lang = Paper.book().read("language");

        if (Paper.book().read("language").equals("ar")) {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(Tags.AR_FONT_NAME)
                    .setFontAttrId(R.attr.fontPath)
                    .build());

        } else {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(Tags.EN_FONT_NAME)
                    .setFontAttrId(R.attr.fontPath)
                    .build());
        }
        super.onAttach(CalligraphyContextWrapper.wrap(LanguageHelper.onAttach(context, lang)));
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_slider,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        image = view.findViewById(R.id.image);
        tv_title = view.findViewById(R.id.tv_title);

        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            int img = bundle.getInt(IMAGE);
            String title = bundle.getString(TITLE);

            UpdateUi(img,title);
        }
    }

    private void UpdateUi(int img, String title) {
        image.setImageResource(img);
        tv_title.setText(title);
    }

    public static Fragment_Slider getInstance(String title,int image)
    {
        Fragment_Slider fragment_slider = new Fragment_Slider();
        Bundle  bundle = new Bundle();
        bundle.putString(TITLE,title);
        bundle.putInt(IMAGE,image);
        fragment_slider.setArguments(bundle);
        return fragment_slider;
    }
}
