package com.avtube.animalsounds2019;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avtube.entertainment.animalsounds.R;

//import com.facebook.ads.Ad;
//import com.facebook.ads.AdError;
//import com.facebook.ads.AdListener;
//import com.facebook.ads.InterstitialAd;
//import com.facebook.ads.InterstitialAdListener;

import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.avtube.animalsounds2019.AllJSONAPI.BirdSound;
import static com.avtube.animalsounds2019.AllJSONAPI.DianosureSound;
import static com.avtube.animalsounds2019.AllJSONAPI.InsectSound;
import static com.avtube.animalsounds2019.AllJSONAPI.bgMusic;
import static com.avtube.animalsounds2019.AllJSONAPI.subCatData;
import static com.avtube.animalsounds2019.AllJSONAPI.petSound;
import static com.avtube.animalsounds2019.AllJSONAPI.waterSound;
import static com.avtube.animalsounds2019.AllJSONAPI.wildSound;
import static com.avtube.animalsounds2019.MainActivity.CategoryName;
import static com.avtube.animalsounds2019.SubCategoryActivity.SelectAnimalposition;
import static com.avtube.animalsounds2019.SubCategoryActivity.TotalImages;
import static com.avtube.animalsounds2019.adsid.bannerads_load;
import static com.avtube.animalsounds2019.adsid.interstitial_load;

public class ViewAnimalActivity extends AppCompatActivity implements RewardedVideoAdListener {


    private Button playbutton, privesbutton, nextbutton, pausebutton, infobutton, setRingtonButton, setWallPaperButton, BackButton;
    private TextView animalName;
    private MediaPlayer mp;
    private int position;

    private GyroscopeObserver gyroscopeObserver;
    PanoramaImageView panoramaImageView;

    private String fNmae;
    private String fPAth;
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static String SelectItemInfo;
    private int[] SelectSound;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private Intent intent;
    private InterstitialAd mInterstitialAd2;
    private InterstitialAd mInterstitialAd3;
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MobileAds.initialize(this, String.valueOf(R.string.appid));
        //ADMOB BANNER ADS
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.adView);
        String id = getString(R.string.bannerid);
        bannerads_load(ViewAnimalActivity.this, relativeLayout, id);

        //ADMOB INTERSTITIAL ADS
        String InterstiId = getString(R.string.instatid);
        mInterstitialAd2 = new InterstitialAd(ViewAnimalActivity.this);
        interstitial_load(ViewAnimalActivity.this, mInterstitialAd2, InterstiId);

        mInterstitialAd2.setAdListener(new AdListener() {

            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd2.loadAd(new AdRequest.Builder().build());
            }

        });

        //ADMOB INTERSTITIAL ADS
        mInterstitialAd3 = new InterstitialAd(ViewAnimalActivity.this);
        interstitial_load(ViewAnimalActivity.this, mInterstitialAd3, InterstiId);

        mInterstitialAd3.setAdListener(new AdListener() {

            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd3.loadAd(new AdRequest.Builder().build());
            }

        });

        //ADMOB Reward ADS
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        if (CategoryName == "Wild Animal") {
            SelectSound = wildSound;
        }
        if (CategoryName == "Bird") {
            SelectSound = BirdSound;
        }
        if (CategoryName == "Dianosure") {
            SelectSound = DianosureSound;
        }
        if (CategoryName == "Pet Animals") {
            SelectSound = petSound;
        }
        if (CategoryName == "Water Animas") {
            SelectSound = waterSound;
        }
        if (CategoryName == "Insect") {
            SelectSound = InsectSound;
        }


        playbutton = (Button) findViewById(R.id.playbutton);
        pausebutton = (Button) findViewById(R.id.pausebutton);
        privesbutton = (Button) findViewById(R.id.privesbutton);
        nextbutton = (Button) findViewById(R.id.nextbutton);
        animalName = (TextView) findViewById(R.id.animalName);

        infobutton = (Button) findViewById(R.id.infobutton);
        BackButton = (Button) findViewById(R.id.BackButton);
        setRingtonButton = (Button) findViewById(R.id.setRingtonButton);
        setWallPaperButton = (Button) findViewById(R.id.setWallPaperButton);


        /* PanoraView Coding*/
        gyroscopeObserver = new GyroscopeObserver();
        gyroscopeObserver.setMaxRotateRadian(Math.PI / 9);
        panoramaImageView = (PanoramaImageView) findViewById(R.id.panorama);
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);

        progressBar = findViewById(R.id.pb_wall_details);


        mp = MediaPlayer.create(ViewAnimalActivity.this, SelectSound[SelectAnimalposition]);
        animalName.setText(subCatData.get(SelectAnimalposition).getAnimalName() + "\n(" + subCatData.get(SelectAnimalposition).getGName() + ")");

        Picasso.with(ViewAnimalActivity.this)
                .load(subCatData.get(SelectAnimalposition).getAnimalPanoraImage())
                .placeholder(R.drawable.loaderbg)
                .into(panoramaImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                        mp.start();
                        mp.setLooping(true);

                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                    }

                });

        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pausebutton.getVisibility() == View.GONE) {
                    pausebutton.setVisibility(View.VISIBLE);
                    playbutton.setVisibility(View.GONE);

                }

                if (mp.isPlaying() == false) {

                    mp.start();
                    mp.setLooping(true);

                }


            }
        });

        pausebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mp.isPlaying() == true) {

                    if (playbutton.getVisibility() == View.GONE) {
                        playbutton.setVisibility(View.VISIBLE);
                        pausebutton.setVisibility(View.GONE);
                    }
                    mp.pause();
                }


            }
        });

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playbutton.setVisibility(View.GONE);
                pausebutton.setVisibility(View.VISIBLE);
                mp.stop();

//                if (SelectAnimalposition % 5 == 0) {
//
//
//                    if (mInterstitialAd2.isLoaded()) {
//                        mInterstitialAd2.show();
//                    } else {
//                        NextButton();
//                    }
//
//                } else {
                NextButton();
//                }


            }
        });


        privesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playbutton.setVisibility(View.GONE);
                pausebutton.setVisibility(View.VISIBLE);
                mp.stop();


//                if (SelectAnimalposition % 5 == 0) {
//
//                    if (mInterstitialAd3.isLoaded()) {
//                        mInterstitialAd3.show();
//                    } else {
//                        PrevisButton();
//                    }
//                } else {

                PrevisButton();
//                }

            }
        });


        infobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelectItemInfo = subCatData.get(SelectAnimalposition).getId();
                intent = new Intent(ViewAnimalActivity.this, InfoAboutAnimActivity.class);

                if (mRewardedVideoAd.isLoaded()) {

                    mp.pause();
                    mRewardedVideoAd.show();

                }else{

                    startActivity(intent);
                }

            }
        });

        setRingtonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                String strDate = sdf.format(c.getTime());
                Log.d("Date", "DATE : " + strDate);

//                Toast.makeText(ViewAnimalActivity.this, "" + strDate, Toast.LENGTH_SHORT).show();

                fNmae = "camel" + strDate + ".mp3";
                fPAth = "android.resource://" + getPackageName() + "/raw/" + SelectSound[SelectAnimalposition];

                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkPermission()) {
                        if (Settings.System.canWrite(ViewAnimalActivity.this)) {
                            setRingtone();
                        } else {
                            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS)
                                    .setData(Uri.parse("package:" + getPackageName()))
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }


                        Log.e("value", "Permission already Granted, Now you can save image.");
                    } else {
                        requestPermission();
                    }
                } else {
                    setRingtone();
                    Log.e("value", "Not required for requesting runtime permission");
                }


            }
        });


        setWallPaperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(ViewAnimalActivity.this, "Wall", Toast.LENGTH_SHORT).show();
//                Bitmap result=null;
//
//                String uri=subCatData.get(SelectAnimalposition).getAnimalPanoraImage();
//                Toast.makeText(ViewAnimalActivity.this, ""+uri, Toast.LENGTH_SHORT).show();
//                try {
//                    result= Picasso.with(ViewAnimalActivity.this)
//                            .load(subCatData.get(1).getAnimalPanoraImage())
//                            .placeholder(R.drawable.loaderbg)
//                            .get();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
//                try {
//                    wallpaperManager.setBitmap(result);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }

            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                mp.stop();
                position = 0;
                mp.seekTo(0);
            }
        });


    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getString(R.string.videoid),
                new AdRequest.Builder().build());
    }



    public void PrevisButton() {
        if (SelectAnimalposition > 0) {
            mp.stop();
            SelectAnimalposition = SelectAnimalposition - 1;
            animalName.setText(subCatData.get(SelectAnimalposition).getAnimalName() + "\n(" + subCatData.get(SelectAnimalposition).getGName() + ")");
            mp = MediaPlayer.create(ViewAnimalActivity.this, SelectSound[SelectAnimalposition]);

            Picasso.with(ViewAnimalActivity.this)
                    .load(subCatData.get(SelectAnimalposition).getAnimalPanoraImage())
                    .placeholder(R.drawable.loaderbg)
                    .into(panoramaImageView, new Callback() {
                        @Override
                        public void onSuccess() {

                            progressBar.setVisibility(View.GONE);
                            mp.start();
                            mp.setLooping(true);
                        }

                        @Override
                        public void onError() {
                            progressBar.setVisibility(View.GONE);

                        }

                    });
        } else {
            Toast.makeText(this, "No More Items", Toast.LENGTH_SHORT).show();
        }

    }

    public void NextButton() {
        if (SelectAnimalposition < TotalImages - 1) {

            mp.stop();
            SelectAnimalposition = SelectAnimalposition + 1;
            animalName.setText(subCatData.get(SelectAnimalposition).getAnimalName() + "\n(" + subCatData.get(SelectAnimalposition).getGName() + ")");
            mp = MediaPlayer.create(ViewAnimalActivity.this, SelectSound[SelectAnimalposition]);

            Picasso.with(ViewAnimalActivity.this)
                    .load(subCatData.get(SelectAnimalposition).getAnimalPanoraImage())
                    .placeholder(R.drawable.loaderbg)
                    .into(panoramaImageView, new Callback() {
                        @Override
                        public void onSuccess() {

                            progressBar.setVisibility(View.GONE);
                            mp.start();
                            mp.setLooping(true);
                        }

                        @Override
                        public void onError() {
                            progressBar.setVisibility(View.GONE);

                        }

                    });

        } else {

            Toast.makeText(this, "No More Items", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
        gyroscopeObserver.register(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mp.stop();
        position = 0;
        mp.seekTo(0);
    }

    protected void onStop() {
        super.onStop();

        playbutton.setVisibility(View.GONE);
        pausebutton.setVisibility(View.VISIBLE);
        mp.pause();
        // insert here your instructions

        //PanoraImageView
        gyroscopeObserver.unregister();

    }

    public void onPrepared(MediaPlayer mediaPlayer) {

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(ViewAnimalActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(ViewAnimalActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(ViewAnimalActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(ViewAnimalActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }


    private void setRingtone() {
        AssetFileDescriptor openAssetFileDescriptor;
        ((AudioManager) getSystemService(AUDIO_SERVICE)).setRingerMode(2);
        File file = new File(Environment.getExternalStorageDirectory() + "/appkeeda", this.fNmae);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Uri parse = Uri.parse(this.fPAth);
        ContentResolver contentResolver = getContentResolver();
        try {
            openAssetFileDescriptor = contentResolver.openAssetFileDescriptor(parse, "r");
        } catch (FileNotFoundException e2) {
            openAssetFileDescriptor = null;
        }
        try {
            byte[] bArr = new byte[1024];
            FileInputStream createInputStream = openAssetFileDescriptor.createInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (int read = createInputStream.read(bArr); read != -1; read = createInputStream.read(bArr)) {
                fileOutputStream.write(bArr, 0, read);
            }
            fileOutputStream.close();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", file.getAbsolutePath());
        contentValues.put("title", "Animal ringtone1");
        contentValues.put("mime_type", "audio/mp3");
        contentValues.put("_size", Long.valueOf(file.length()));
        contentValues.put("artist", Integer.valueOf(R.string.app_name));
        contentValues.put("is_ringtone", Boolean.valueOf(true));
        contentValues.put("is_notification", Boolean.valueOf(false));
        contentValues.put("is_alarm", Boolean.valueOf(false));
        contentValues.put("is_music", Boolean.valueOf(false));
        try {
            Toast.makeText(this, new StringBuilder().append("Ringtone set successfully"), Toast.LENGTH_LONG).show();
            RingtoneManager.setActualDefaultRingtoneUri(getBaseContext(), 1, contentResolver.insert(MediaStore.Audio.Media.getContentUriForPath(file.getAbsolutePath()), contentValues));
        } catch (Throwable th) {
            Toast.makeText(this, new StringBuilder().append("Ringtone feature is not working"), Toast.LENGTH_LONG).show();
        }
    }


    //    Facebook ADS Start

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected();
        }
        return false;
    }

    public void onStart() {
        super.onStart();
        try {
            if (isConnected()) {
//                loadBannerAd();
            }
        } catch (Exception ignored) {
        }
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

        startActivity(intent);
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


//    private void loadBannerAd() {
//        try {
//            facebookad.addView(AdsConnection.getFacebookInstance(this).setFacebookBannerAD(this, new AdListener() {
//                @Override
//                public void onError(Ad ad, AdError adError) {
//                }
//
//                @Override
//                public void onAdLoaded(Ad ad) {
//
//                }
//
//                @Override
//                public void onAdClicked(Ad ad) {
//
//                }
//
//                @Override
//                public void onLoggingImpression(Ad ad) {
//
//                }
//            }));
//
//        } catch (Exception ignored) {
//        }
//    }

//    Facebook ADS Finish

}
