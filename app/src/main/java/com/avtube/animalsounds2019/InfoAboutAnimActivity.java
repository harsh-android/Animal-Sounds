package com.avtube.animalsounds2019;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avtube.entertainment.animalsounds.R;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.ads.MobileAds;

import java.util.HashMap;

import static com.avtube.animalsounds2019.AllJSONAPI.subCatData;
import static com.avtube.animalsounds2019.ViewAnimalActivity.SelectItemInfo;
import static com.avtube.animalsounds2019.adsid.bannerads_load;

public class InfoAboutAnimActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private TextView GujaratiTextView, EnglishTextView;
    private TextView GnameTextView, GdescriptionTextView, GScientificallyTextView, GGestationPeriodTextView, GLifeSpanTextView, GMassTextView, GTropicalLevelTextView, GDoYouKnowTextView;
    private LinearLayout GujaratiLinearlayout, EnglishLinearlayout;
    private TextView EnameTextView, EdescriptionTextView, EScientificallyTextView, EGestationPeriodTextView, ELifeSpanTextView, EMassTextView, ETropicalLevelTextView, EDoYouKnowTextView;
    private SliderLayout sliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_info);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MobileAds.initialize(this,String.valueOf(R.string.appid));

        //ADMOB BANNER ADS
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.adView);
        String id =getString(R.string.bannerid);
        bannerads_load(InfoAboutAnimActivity.this, relativeLayout, id);

        GujaratiTextView = (TextView) findViewById(R.id.GujaratiTextView);
        EnglishTextView = (TextView) findViewById(R.id.EnglishTextView);

        GujaratiLinearlayout = (LinearLayout) findViewById(R.id.GujaratiLinearlayout);
        EnglishLinearlayout = (LinearLayout) findViewById(R.id.EnglishLinearlayout);

        GujaratiTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EnglishLinearlayout.setVisibility(View.GONE);
                EnglishTextView.setBackgroundColor(Color.parseColor("#ffffff"));

                if (GujaratiLinearlayout.getVisibility() == View.GONE) {
                    GujaratiLinearlayout.setVisibility(View.VISIBLE);
                    GujaratiTextView.setBackgroundColor(Color.parseColor("#A5D6A7"));


                }

            }
        });

        EnglishTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GujaratiLinearlayout.setVisibility(View.GONE);
                GujaratiTextView.setBackgroundColor(Color.parseColor("#ffffff"));

                if (EnglishLinearlayout.getVisibility() == View.GONE) {
                    EnglishLinearlayout.setVisibility(View.VISIBLE);
                    EnglishTextView.setBackgroundColor(Color.parseColor("#A5D6A7"));
                }

            }
        });


        GnameTextView = (TextView) findViewById(R.id.GnameTextView);
        GdescriptionTextView = (TextView) findViewById(R.id.GdescriptionTextView);
        GScientificallyTextView = (TextView) findViewById(R.id.GScientificallyTextView);
        GGestationPeriodTextView = (TextView) findViewById(R.id.GGestationPeriodTextView);
        GLifeSpanTextView = (TextView) findViewById(R.id.GLifeSpanTextView);
        GMassTextView = (TextView) findViewById(R.id.GMassTextView);
        GTropicalLevelTextView = (TextView) findViewById(R.id.GTropicalLevelTextView);
        GDoYouKnowTextView = (TextView) findViewById(R.id.GDoYouKnowTextView);

        EnameTextView = (TextView) findViewById(R.id.EnameTextView);
        EdescriptionTextView = (TextView) findViewById(R.id.EdescriptionTextView);
        EScientificallyTextView = (TextView) findViewById(R.id.EScientificallyTextView);
        EGestationPeriodTextView = (TextView) findViewById(R.id.EGestationPeriodTextView);
        ELifeSpanTextView = (TextView) findViewById(R.id.ELifeSpanTextView);
        EMassTextView = (TextView) findViewById(R.id.EMassTextView);
        ETropicalLevelTextView = (TextView) findViewById(R.id.ETropicalLevelTextView);
        EDoYouKnowTextView = (TextView) findViewById(R.id.EDoYouKnowTextView);


        for (int i = 0; i < subCatData.size(); i++) {

            if (SelectItemInfo == subCatData.get(i).getId()) {

                GnameTextView.setText(subCatData.get(i).getGName());

                GdescriptionTextView.setText(subCatData.get(i).getGDiscription());

                GScientificallyTextView.setText(subCatData.get(i).getGScientificallyName());

                GGestationPeriodTextView.setText(subCatData.get(i).getGGestationPeriod());

                GLifeSpanTextView.setText(subCatData.get(i).getGLifeSpan());

                GMassTextView.setText(subCatData.get(i).getGMass());

                GTropicalLevelTextView.setText(subCatData.get(i).getGTropicalLevel());

                GDoYouKnowTextView.setText(subCatData.get(i).getGDoYouKnow());


                EnameTextView.setText(subCatData.get(i).getEName());

                EdescriptionTextView.setText(subCatData.get(i).getEDiscription());

                EScientificallyTextView.setText(subCatData.get(i).getEScientificallyName());

                EGestationPeriodTextView.setText(subCatData.get(i).getEGestationPeriod());

                ELifeSpanTextView.setText(subCatData.get(i).getELifeSpan());

                EMassTextView.setText(subCatData.get(i).getEMass());

                ETropicalLevelTextView.setText(subCatData.get(i).getETropicalLevel());

                EDoYouKnowTextView.setText(subCatData.get(i).getEDoYouKnow());

                //Sliderrrr CODE
                sliderLayout = (SliderLayout) findViewById(R.id.sliderlayout);

                HashMap<String, String> file_maps = new HashMap<String, String>();

                if (!subCatData.get(i).getImg1().isEmpty()) {
                    file_maps.put("1." + subCatData.get(i).getAnimalName(), subCatData.get(i).getImg1());
                }

                if (!subCatData.get(i).getImg2().isEmpty()) {
                    file_maps.put("2." + subCatData.get(i).getAnimalName(), subCatData.get(i).getImg2());
                }

                if (!subCatData.get(i).getImg3().isEmpty()) {
                    file_maps.put("3." + subCatData.get(i).getAnimalName(), subCatData.get(i).getImg3());
                }

                if (!subCatData.get(i).getImg4().isEmpty()) {
                    file_maps.put("4." + subCatData.get(i).getAnimalName(), subCatData.get(i).getImg4());
                }
                if (!subCatData.get(i).getImg5().isEmpty()) {
                    file_maps.put("5." + subCatData.get(i).getAnimalName(), subCatData.get(i).getImg5());
                }
                if (!subCatData.get(i).getImg6().isEmpty()) {
                    file_maps.put("5." + subCatData.get(i).getAnimalName(), subCatData.get(i).getImg6());
                }
                if (!subCatData.get(i).getImg7().isEmpty()) {
                    file_maps.put("5." + subCatData.get(i).getAnimalName(), subCatData.get(i).getImg7());
                }
                if (!subCatData.get(i).getImg8().isEmpty()) {
                    file_maps.put("5." + subCatData.get(i).getAnimalName(), subCatData.get(i).getImg8());
                }
                if (!subCatData.get(i).getImg9().isEmpty()) {
                    file_maps.put("5." + subCatData.get(i).getAnimalName(), subCatData.get(i).getImg9());
                }
                if (!subCatData.get(i).getImg10().isEmpty()) {
                    file_maps.put("5." + subCatData.get(i).getAnimalName(), subCatData.get(i).getImg10());
                }

                for (String name : file_maps.keySet()) {
                    TextSliderView textSliderView = new TextSliderView(InfoAboutAnimActivity.this);
                    // initialize a SliderLayout
                    textSliderView.description(name)
                            .image(file_maps.get(name))
                            .setOnSliderClickListener(this);

                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", name);

                    sliderLayout.addSlider(textSliderView);
                }

                sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                sliderLayout.setCustomAnimation(new DescriptionAnimation());
                sliderLayout.setDuration(3000);
                sliderLayout.addOnPageChangeListener(this);
            }

        }

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}

