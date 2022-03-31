package com.avtube.animalsounds2019;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avtube.entertainment.animalsounds.R;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import static com.avtube.animalsounds2019.AllJSONAPI.CategoryJsonParse;
import static com.avtube.animalsounds2019.AllJSONAPI.CategorySubJsonParse;
import static com.avtube.animalsounds2019.AllJSONAPI.SliderImagesJsonParse;
import static com.avtube.animalsounds2019.AllJSONAPI.mainCatData;
import static com.avtube.animalsounds2019.AllJSONAPI.subCatData;
import static com.avtube.animalsounds2019.AllJSONAPI.animSliderImgModels;
import static com.avtube.animalsounds2019.adsid.bannerads_load;
import static com.avtube.animalsounds2019.adsid.interstitial_load;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, RewardedVideoAdListener {

    private RecyclerView CategoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    private Thread thread;
    private Intent intentCategory;
    public static String CategoryName;
    private SliderLayout sliderLayout;
    private ImageView shareImageView, rateUsImageView, moreImageView;
    private InterstitialAd mInterstitialAd2;
    private RewardedVideoAd mRewardedVideoAd;
    private Intent intentCategoryR;

    //    Facebook ADS
//    private InterstitialAd mFBInterstitialAd;
//    private RelativeLayout facebookad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MobileAds.initialize(this,String.valueOf(R.string.appid));

        //ADMOB BANNER ADS
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.adView);
        String id = getString(R.string.bannerid1);
        bannerads_load(MainActivity.this, relativeLayout, id);

        //ADMOB INTERSTITIAL ADS
        String InterstiId=getString(R.string.instatid1);
        mInterstitialAd2 = new InterstitialAd(MainActivity.this);
        interstitial_load(MainActivity.this,mInterstitialAd2,InterstiId);

        mInterstitialAd2.setAdListener(new AdListener() {

            public void onAdClosed() {
                // Load the next interstitial.
                startActivity(intentCategory);
                mInterstitialAd2.loadAd(new AdRequest.Builder().build());
            }

        });

        //ADMOB Reward ADS
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();


        requestStoragePermission();
        shareImageView = (ImageView) findViewById(R.id.shareImageView);
        rateUsImageView = (ImageView) findViewById(R.id.rateUsImageView);
        moreImageView = (ImageView) findViewById(R.id.moreImageView);

        CategoryRecyclerView = (RecyclerView) findViewById(R.id.CategoryRecyclerView);
        RecyclerView.LayoutManager categoryManager = new GridLayoutManager(MainActivity.this, 1, GridLayoutManager.VERTICAL, false);
        categoryAdapter = new CategoryAdapter();
        CategoryRecyclerView.setLayoutManager(categoryManager);
        CategoryRecyclerView.setAdapter(categoryAdapter);

        //Sliderrrr CODE
        sliderLayout = (SliderLayout) findViewById(R.id.sliderlayout);

        for (int i = 0; i < animSliderImgModels.size(); i++) {
            HashMap<String, String> file_maps = new HashMap<String, String>();

            if (animSliderImgModels.get(i).getId().equals("1") && animSliderImgModels.get(i).getName().equals("AllAnimal")) {
                file_maps.put("1." + animSliderImgModels.get(i).getName(), animSliderImgModels.get(i).getImages());
            }

            if (animSliderImgModels.get(i).getId().equals("2") && animSliderImgModels.get(i).getName().equals("Pet")) {
                file_maps.put("2." + animSliderImgModels.get(i).getName(), animSliderImgModels.get(i).getImages());
            }

            if (animSliderImgModels.get(i).getId().equals("3") && animSliderImgModels.get(i).getName().equals("Water")) {
                file_maps.put("3." + animSliderImgModels.get(i).getName(), animSliderImgModels.get(i).getImages());
            }

            if (animSliderImgModels.get(i).getId().equals("4") && animSliderImgModels.get(i).getName().equals("Wild")) {
                file_maps.put("4." + animSliderImgModels.get(i).getName(), animSliderImgModels.get(i).getImages());
            }

            if (animSliderImgModels.get(i).getId().equals("5") && animSliderImgModels.get(i).getName().equals("Insect")) {
                file_maps.put("5." + animSliderImgModels.get(i).getName(), animSliderImgModels.get(i).getImages());
            }

            if (animSliderImgModels.get(i).getId().equals("6") && animSliderImgModels.get(i).getName().equals("Birds")) {
                file_maps.put("6." + animSliderImgModels.get(i).getName(), animSliderImgModels.get(i).getImages());
            }


            for (String name : file_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(MainActivity.this);
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

        shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isReadStorageAllowed()) {

                    try {

                        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.shareiimag);
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/*");
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(MainActivity.this.getContentResolver(),
                                b, "Title", null);
                        Uri imageUri = Uri.parse(path);
                        share.putExtra(Intent.EXTRA_TEXT, "*All Animal Sound : Animal Information* is Best Application for Listening \uD83C\uDFA7 Animal \uD83E\uDD81 Sounds." + "\n\n\n" +
                                "https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName() + "\n\n" +
                                "*1.* Six Different Animal Categories Like Wild Animal \uD83D\uDC05, Birds \uD83E\uDD9A, Dinosaur \uD83E\uDD95, Pet Animal \uD83D\uDC04, Water Animal \uD83D\uDC0B, Insect \uD83D\uDC1C." + "\n" +
                                "*2.* HD Image of All Animal \uD83D\uDC06." + "\n" +
                                "*3.* Best Animal Sound \uD83C\uDFA7 of All Animals." + "\n" +
                                "*4.* All Information \uD83D\uDCDA About Animals with Image." + "\n" +
                                "*5.* Set Ringtone \uD83D\uDD14 of Animal Sounds." + "\n" +
                                "*6.* Also Play Animal Games." + "\n\n\n" +
                                "Please Install this Application on your \uD83D\uDCF2 and Give 5 Star \uD83C\uDF1F Rating with Long Review \uD83D\uDCDD" + "\n\n" +
                                "\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47" + "\n\n\n" +
                                "https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName() + "\n\n" +
                                "\uD83D\uDE4F\uD83D\uDE4F\uD83D\uDE4F\uD83D\uDE4F\uD83D\uDE4F");
                        share.putExtra(Intent.EXTRA_STREAM, imageUri);
                        startActivity(Intent.createChooser(share, "Share App Link Using..."));
                    } catch (Exception e) {

                    }
                } else {
                    requestStoragePermission();
                }
            }
        });

        rateUsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("market://details?id=" + MainActivity.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
                }
            }
        });

        moreImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String developerName = "pub:AV Store";     //where geeks is the company name in the play store
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=" + developerName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=" + developerName + "&hl=en")));
                }
            }
        });

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.progrees_dilog_item);
        dialog.setCanceledOnTouchOutside(false);

        ImageView loader = (ImageView) dialog.findViewById(R.id.loader);
        Glide.with(MainActivity.this).load(R.drawable.loadingloder).into(loader);

        dialog.show();

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        if (!mainCatData.isEmpty() && !animSliderImgModels.isEmpty()) {
            dialog.dismiss();
        }

        thread = new Thread() {
            @Override
            public void run() {
                try {

                    while (mainCatData.isEmpty()) {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                categoryAdapter.notifyDataSetChanged();

//                                Toast.makeText(MainActivity.this, "yess", Toast.LENGTH_SHORT).show();
                                if (!mainCatData.isEmpty() && !animSliderImgModels.isEmpty()) {
                                    dialog.dismiss();
                                } else {
                                    CategoryJsonParse(MainActivity.this);
                                    SliderImagesJsonParse(MainActivity.this);
                                }
                            }
                        });
                    }

                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getString(R.string.videoid),
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

        startActivity(intentCategoryR);
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }


    /* 21/6/2019 CategoryAdapter CODE Start */
    private class CategoryAdapter extends RecyclerView.Adapter<ViewHolder> {
        @NonNull

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = getLayoutInflater().inflate(R.layout.category_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


            viewHolder.categoryName.setText(mainCatData.get(i).getCategoryName());
            Glide.with(MainActivity.this).load(mainCatData.get(i).getCategoryImages()).into(viewHolder.categoryImages);


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    subCatData.clear();
                    switch (i) {
                        case 0:
                            CategoryName = "Wild Animal";
                            CategorySubJsonParse(MainActivity.this);
                            intentCategory = new Intent(MainActivity.this, SubCategoryActivity.class);


                            if (mInterstitialAd2.isLoaded()) {
                                mInterstitialAd2.show();
                            }
                            else
                            {
                                startActivity(intentCategory);
                            }

                            break;

                        case 1:
                            CategoryName = "Bird";
                            CategorySubJsonParse(MainActivity.this);
                            intentCategory = new Intent(MainActivity.this, SubCategoryActivity.class);


                            if (mInterstitialAd2.isLoaded()) {
                                mInterstitialAd2.show();
                            }
                            else
                            {
                                startActivity(intentCategory);
                            }

                            break;

                        case 2:
                            CategoryName = "Dianosure";
                            CategorySubJsonParse(MainActivity.this);
                            intentCategory = new Intent(MainActivity.this, SubCategoryActivity.class);

                            if (mInterstitialAd2.isLoaded()) {
                                mInterstitialAd2.show();
                            }
                            else
                            {
                                startActivity(intentCategory);
                            }

                            break;

                        case 3:
                            CategoryName = "Pet Animals";
                            CategorySubJsonParse(MainActivity.this);
                            intentCategory = new Intent(MainActivity.this, SubCategoryActivity.class);
                            startActivity(intentCategory);
                            break;

                        case 4:
                            CategoryName = "Water Animas";
                            CategorySubJsonParse(MainActivity.this);
                            intentCategory = new Intent(MainActivity.this, SubCategoryActivity.class);
                            if (mInterstitialAd2.isLoaded()) {
                                mInterstitialAd2.show();
                            }
                            else
                            {
                                startActivity(intentCategory);
                            }
                            break;

                        case 5:
                            CategoryName = "Insect";
                            CategorySubJsonParse(MainActivity.this);
                            intentCategory = new Intent(MainActivity.this, SubCategoryActivity.class);
                            if (mInterstitialAd2.isLoaded()) {
                                mInterstitialAd2.show();
                            }
                            else
                            {
                                startActivity(intentCategory);
                            }
                            break;

                        case 6:

                            intentCategoryR = new Intent(MainActivity.this, MainMind.class);

                            if (mRewardedVideoAd.isLoaded()) {
                                mRewardedVideoAd.show();
                            }else{

                                startActivity(intentCategoryR);
                            }

                            break;

                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return mainCatData.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView categoryImages;
        private final TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImages = (ImageView) itemView.findViewById(R.id.categoryImages);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
        }
    }

    /* 21/6/2019 CategoryAdapter CODE Finish */


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


    //We are calling this method to check the permission status
    final String[] permision = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};

    private boolean isReadStorageAllowed() {
        //Getting the permission status
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(MainActivity.this, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                return true;
            } else {
                requestPermissions(this.permision, 100);
            }
        } else {
            return true;
        }
        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 23);
    }


    @Override
    public void onBackPressed() {
        final Dialog dialogExit = new Dialog(MainActivity.this);
        dialogExit.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogExit.setContentView(R.layout.exit_dilog_item);
        dialogExit.setCanceledOnTouchOutside(false);

        Button ratebutton = (Button) dialogExit.findViewById(R.id.ratebutton);
        Button cancelbutton = (Button) dialogExit.findViewById(R.id.cancelbutton);
        Button exitbutton = (Button) dialogExit.findViewById(R.id.exitbutton);

        ImageView rateus = (ImageView) dialogExit.findViewById(R.id.rateus);

        Glide.with(MainActivity.this).load(R.drawable.exitloader).into(rateus);

        dialogExit.show();

        ratebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("market://details?id=" + MainActivity.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
                }

            }
        });

        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogExit.dismiss();
            }
        });

        exitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }




}
