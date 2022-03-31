package com.avtube.animalsounds2019;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avtube.entertainment.animalsounds.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import static com.avtube.animalsounds2019.AllJSONAPI.CategorySubJsonParse;
import static com.avtube.animalsounds2019.AllJSONAPI.subCatData;
import static com.avtube.animalsounds2019.MainActivity.CategoryName;
import static com.avtube.animalsounds2019.adsid.bannerads_load;
import static com.avtube.animalsounds2019.adsid.interstitial_load;

public class SubCategoryActivity extends AppCompatActivity {

    private RecyclerView SubCategoryRecyclerView;
    private SubCategoryAdapter subcategoryAdapter;
    private Thread thread;
    public static int SelectAnimalposition;
    public static int TotalImages;
    public static String selectItemId;
    private Intent intent;
    private TextView CategroyHeadTextview;
    private InterstitialAd mInterstitialAd2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MobileAds.initialize(this,String.valueOf(R.string.appid));
        //ADMOB BANNER ADS
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.adView);
        String id = getString(R.string.bannerid1);
        bannerads_load(SubCategoryActivity.this, relativeLayout, id);

        //ADMOB INTERSTITIAL ADS
        String InterstiId = getString(R.string.instatid1);
        mInterstitialAd2 = new InterstitialAd(SubCategoryActivity.this);
        interstitial_load(SubCategoryActivity.this, mInterstitialAd2, InterstiId);

        mInterstitialAd2.setAdListener(new AdListener() {

            public void onAdClosed() {
                // Load the next interstitial.
                startActivity(intent);
                mInterstitialAd2.loadAd(new AdRequest.Builder().build());
            }

        });

        CategroyHeadTextview = (TextView) findViewById(R.id.CategroyHeadTextview);
        CategroyHeadTextview.setText(CategoryName);

        SubCategoryRecyclerView = (RecyclerView) findViewById(R.id.SubCategoryRecyclerView);
        RecyclerView.LayoutManager categoryManager = new GridLayoutManager(SubCategoryActivity.this, 2, GridLayoutManager.VERTICAL, false);
        subcategoryAdapter = new SubCategoryAdapter();
        SubCategoryRecyclerView.setLayoutManager(categoryManager);
        SubCategoryRecyclerView.setAdapter(subcategoryAdapter);


        final Dialog dialog = new Dialog(SubCategoryActivity.this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.progrees_dilog_item);
        dialog.setCanceledOnTouchOutside(false);
        ImageView loader = (ImageView) dialog.findViewById(R.id.loader);
        Glide.with(SubCategoryActivity.this).load(R.drawable.loadingloder).into(loader);

        dialog.show();

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        if (!subCatData.isEmpty()) {
            dialog.dismiss();
        }

        thread = new Thread() {
            @Override
            public void run() {
                try {

                    while (subCatData.isEmpty()) {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                subcategoryAdapter.notifyDataSetChanged();

//                                Toast.makeText(SubCategoryActivity.this, "yess", Toast.LENGTH_SHORT).show();
                                if (!subCatData.isEmpty()) {
                                    dialog.dismiss();

                                    //Bakground Sound
//                                    if (bgMusic.isPlaying() == false) {
//
//                                        bgMusic.start();
//                                        bgMusic.setLooping(true);
//
//                                    }

                                } else {
                                    CategorySubJsonParse(SubCategoryActivity.this);
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


    /* 21/6/2019 CategoryAdapter CODE Start */

    private class SubCategoryAdapter extends RecyclerView.Adapter<ViewHolder> {
        @NonNull

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = getLayoutInflater().inflate(R.layout.animal_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

            viewHolder.animaNameTextview.setText(subCatData.get(i).getAnimalName());
            Glide.with(SubCategoryActivity.this).load(subCatData.get(i).getAnimalThumbhImage()).into(viewHolder.animalImageview);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    SelectAnimalposition = i;
                    selectItemId = subCatData.get(i).getId();
                    TotalImages = subCatData.size();
                    intent = new Intent(SubCategoryActivity.this, ViewAnimalActivity.class);

                    if (i % 2 == 0) {


                        if (mInterstitialAd2.isLoaded()) {
                            mInterstitialAd2.show();
                        } else {
                            startActivity(intent);
                        }
                    } else {
                        startActivity(intent);
                    }

                    /*Facebook Inter ADS Finish*/


                }
            });
        }

        @Override
        public int getItemCount() {
            return subCatData.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView animalImageview;
        private final TextView animaNameTextview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            animalImageview = (ImageView) itemView.findViewById(R.id.animalImageview);
            animaNameTextview = (TextView) itemView.findViewById(R.id.animaNameTextview);
        }
    }

    /* 21/6/2019 CategoryAdapter CODE finish */


    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected();
        }
        return false;
    }


}
