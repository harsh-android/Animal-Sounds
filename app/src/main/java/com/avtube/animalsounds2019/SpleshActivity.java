package com.avtube.animalsounds2019;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.avtube.entertainment.animalsounds.R;
import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

import static com.avtube.animalsounds2019.AllJSONAPI.BackgroundMusic;
import static com.avtube.animalsounds2019.AllJSONAPI.CategoryJsonParse;
import static com.avtube.animalsounds2019.AllJSONAPI.SliderImagesJsonParse;
import static com.avtube.animalsounds2019.AllJSONAPI.bgMusic;
import static com.avtube.animalsounds2019.AllJSONAPI.mainCatData;
import static com.avtube.animalsounds2019.AllJSONAPI.animSliderImgModels;

public class SpleshActivity extends AppCompatActivity {

    private ImageView Imageview;
    private Dialog dialog;
    private Button refreshButton,openDataButton;
    private ImageView nointernet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
//        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splesh);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        BackgroundMusic(SpleshActivity.this);
        bgMusic.start();
        bgMusic.setLooping(true);


        CategoryJsonParse(SpleshActivity.this);

        SliderImagesJsonParse(SpleshActivity.this);

        Imageview=(ImageView)findViewById(R.id.Imageview);

        Glide.with(SpleshActivity.this).load(R.drawable.s6).into(Imageview);


        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startMainActivity();
            }
        },2500);

    }


    private void startMainActivity() {

        if (mainCatData.isEmpty() || animSliderImgModels.isEmpty()) {

            CategoryJsonParse(SpleshActivity.this);
            SliderImagesJsonParse(SpleshActivity.this);
        }

        dialog = new Dialog(SpleshActivity.this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.nointernet_dilog_item);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        refreshButton = (Button) dialog.findViewById(R.id.refreshButton);
        openDataButton = (Button) dialog.findViewById(R.id.openDataButton);

        nointernet=(ImageView)dialog.findViewById(R.id.nointernet);

        Glide.with(SpleshActivity.this).load(R.drawable.nointernet).into(nointernet);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(SpleshActivity.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {

            if(dialog.isShowing())
            {
                dialog.dismiss();
            }
            bgMusic.stop();
            startActivity(new Intent(SpleshActivity.this, MainActivity.class));
            finish();

        } else {

//            Toast.makeText(this, "Heloo", Toast.LENGTH_SHORT).show();
            dialog.show();

            refreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startMainActivity();
                    dialog.dismiss();
                }
            });

            openDataButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        bgMusic.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bgMusic.stop();
        bgMusic.seekTo(0);
    }

    protected void onStop() {
        super.onStop();

        bgMusic.pause();
    }


}
