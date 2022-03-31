package com.avtube.animalsounds2019;

import android.app.Activity;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avtube.entertainment.animalsounds.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class adsid {

    public static void bannerads_load(Activity activity, RelativeLayout relativeLayout, String id) {

        AdView adView = new AdView(activity);
        adView.setAdSize(AdSize.SMART_BANNER);

        adView.setAdUnitId(id);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        relativeLayout.addView(adView);
    }

    public  static void interstitial_load(Activity activity, InterstitialAd mInterstitialAd, String id)
    {

        mInterstitialAd.setAdUnitId(id);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

//        Toast.makeText(activity, "AAA  "+activity, Toast.LENGTH_SHORT).show();
    }
}
