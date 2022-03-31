package com.avtube.animalsounds2019;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.avtube.entertainment.animalsounds.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.avtube.animalsounds2019.MainActivity.CategoryName;
import static com.avtube.entertainment.animalsounds.BuildConfig.APIPATH;


public class AllJSONAPI {

    public static int BirdSound[] = new int[]{R.raw.canary, R.raw.cardinal, R.raw.chaffinch, R.raw.crow, R.raw.dove, R.raw.duck, R.raw.eagle, R.raw.emu, R.raw.flamingo, R.raw.geese, R.raw.hawk, R.raw.hen, R.raw.hoopoe, R.raw.kingfisher, R.raw.loon,R.raw.nightingale, R.raw.ostrich,R.raw.owl, R.raw.parrot, R.raw.peacock, R.raw.quail, R.raw.rhea, R.raw.rooster, R.raw.seagull, R.raw.sparrow, R.raw.swan, R.raw.turkey};
    public static int DianosureSound[] = new int[]{R.raw.abelisaurus, R.raw.achelousaurus, R.raw.albertosaurus, R.raw.ankylosaurus, R.raw.brachiosaurus, R.raw.compsognathus, R.raw.irritator, R.raw.pterodactyl, R.raw.spinosaurus, R.raw.tyrannosaurus};
    public static int InsectSound[] = new int[]{R.raw.bees, R.raw.fly, R.raw.grasshopper, R.raw.mosquito, R.raw.wasp};
    public static int petSound[] = new int[]{R.raw.buffalo, R.raw.bull, R.raw.camel, R.raw.cat, R.raw.cow, R.raw.dog, R.raw.donkey, R.raw.goat, R.raw.hamster, R.raw.horse, R.raw.lama, R.raw.pig, R.raw.rabbit, R.raw.sheep, R.raw.yak};
    public static int waterSound[] = new int[]{R.raw.crocodile, R.raw.dolphin, R.raw.frog, R.raw.hippopotamus, R.raw.orca, R.raw.penquin, R.raw.sealion, R.raw.turtle, R.raw.walrus, R.raw.whale};
    public static int wildSound[] = new int[]{R.raw.anteater, R.raw.antelope, R.raw.armadillo, R.raw.baboon, R.raw.bear, R.raw.beaver, R.raw.bison, R.raw.boar, R.raw.capybara, R.raw.chameleon, R.raw.cheetah, R.raw.chimpanzee, R.raw.coyote, R.raw.deer, R.raw.elephant, R.raw.ferret, R.raw.fox, R.raw.giantpanda, R.raw.gibbon, R.raw.giraffe, R.raw.gorilla, R.raw.hyena, R.raw.kangaroo, R.raw.koala, R.raw.lemur, R.raw.lion, R.raw.lynx, R.raw.meerkat, R.raw.monkey, R.raw.moose, R.raw.polarbear, R.raw.puma, R.raw.raccoon,R.raw.reindeer,R.raw.rhinoceros,R.raw.roe, R.raw.sloth, R.raw.snake, R.raw.squirrel, R.raw.tiger, R.raw.wolf, R.raw.zebra};

    public static List<MainCatData> mainCatData = new ArrayList<>();
    public static List<SubCatData> subCatData = new ArrayList<>();
    public static List<AnimSliderImgModel> animSliderImgModels = new ArrayList<>();


//    public static String APIPATH = "http://animalsounds.appvision.in/";
    private static String url;
    private static String JsonArryName;
    public static MediaPlayer bgMusic;
    public static int position;


    //Background Sound Code....
    public  static void   BackgroundMusic(Activity activity)
    {
        bgMusic = MediaPlayer.create(activity, R.raw.lion);
    }

    // Volley Code JsonParse All Data Callect here 21/6/2019
    public static void CategoryJsonParse(final Activity activity) {

        String url = "JSONAnimalMainCat.php";

        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, APIPATH + "JSONAnimalMainCat.php", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("tag", "onResponse: " + response);

                        mainCatData.clear();

                        try {
                            JSONArray jsonArray = response.getJSONArray("AnimalMainCat");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject textQuiz = jsonArray.getJSONObject(i);


                                String id = textQuiz.getString("id");
                                String categoryName = textQuiz.getString("mcname");
                                String CategoryImages = textQuiz.getString("mcimage");

//                                Log.e("Category", "onResponse:" + id + " " + categoryName + "  " + CategoryImages);

                                MainCatData data = new MainCatData();
                                data.setId(id);
                                data.setCategoryName(categoryName);
                                data.setCategoryImages(CategoryImages);


                                mainCatData.add(data);

                            }
//                            Toast.makeText(activity, "sizeQ" + mainCatData.size(), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }


    public static void CategorySubJsonParse(final Activity activity) {


        if (CategoryName == "Wild Animal") {
            url = "JSONAnimalWild.php";
            JsonArryName = "AnimalWild";
        }
        if (CategoryName == "Bird") {
            url = "JSONAnimalBird.php";
            JsonArryName = "AnimalBird";
        }

        if (CategoryName == "Dianosure") {
            url = "JSONAnimalDino.php";
            JsonArryName = "AnimalDino";
        }

        if (CategoryName == "Pet Animals") {
            url = "JSONAnimalPet.php";
            JsonArryName = "AnimalPet";
        }

        if (CategoryName == "Water Animas") {
            url = "JSONAnimalWA.php";
            JsonArryName = "AnimalWA";
        }

        if (CategoryName == "Insect") {
            url = "JSONAnimalInsect.php";
            JsonArryName = "AnimalInsect";
        }

        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, APIPATH + url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("tag", "onResponse: " + response);

                        subCatData.clear();

                        try {
                            JSONArray jsonArray = response.getJSONArray(JsonArryName);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject textQuiz = jsonArray.getJSONObject(i);

                                String id = textQuiz.getString("id");
                                String MainCategoryid = textQuiz.getString("mcid");
                                String img1 = textQuiz.getString("img1");
                                String img2 = textQuiz.getString("img2");
                                String img3 = textQuiz.getString("img3");
                                String img4 = textQuiz.getString("img4");
                                String img5 = textQuiz.getString("img5");
                                String img6 = textQuiz.getString("img6");
                                String img7 = textQuiz.getString("img7");
                                String img8 = textQuiz.getString("img8");
                                String img9 = textQuiz.getString("img9");
                                String img10 = textQuiz.getString("img10");
                                String animalThumbhImages = textQuiz.getString("scmimg");
                                String animalPanoraImages = textQuiz.getString("scview");
                                String animalName = textQuiz.getString("scname");
                                String ENamej = textQuiz.getString("scname");
                                String EDiscriptionj = textQuiz.getString("eadesc");
                                String EScientificallyNamej = textQuiz.getString("esciname");
                                String EGestationPeriodj = textQuiz.getString("egp");
                                String ELifeSpanj = textQuiz.getString("elife");
                                String EMassj = textQuiz.getString("emass");
                                String ETropicalLevelj = textQuiz.getString("etl");
                                String EDoYouKnowj = textQuiz.getString("edyk");
                                String GNamej = textQuiz.getString("ganame");
                                String GDiscriptionj = textQuiz.getString("gadesc");
                                String GScientificallyNamej = textQuiz.getString("gsciname");
                                String GGestationPeriodj = textQuiz.getString("ggp");
                                String GLifeSpanj = textQuiz.getString("glife");
                                String GMassj = textQuiz.getString("gmass");
                                String GTropicalLevelj = textQuiz.getString("gtl");
                                String GDoYouKnowj = textQuiz.getString("gdyk");

//                                Log.e("Category", "onResponse:" + id + " " + animalName + "  " + animalPanoraImages);

                                SubCatData data = new SubCatData();

                                data.setId(id);
                                data.setMainCategoryid(MainCategoryid);
                                data.setImg1(img1);
                                data.setImg2(img2);
                                data.setImg3(img3);
                                data.setImg4(img4);
                                data.setImg5(img5);
                                data.setImg6(img6);
                                data.setImg7(img7);
                                data.setImg8(img8);
                                data.setImg9(img9);
                                data.setImg10(img10);
                                data.setAnimalThumbhImage(animalThumbhImages);
                                data.setAnimalPanoraImage(animalPanoraImages);
                                data.setAnimalName(animalName);
                                data.setEName(ENamej);
                                data.setEDiscription(EDiscriptionj);
                                data.setEScientificallyName(EScientificallyNamej);
                                data.setEGestationPeriod(EGestationPeriodj);
                                data.setELifeSpan(ELifeSpanj);
                                data.setEMass(EMassj);
                                data.setETropicalLevel(ETropicalLevelj);
                                data.setEDoYouKnow(EDoYouKnowj);
                                data.setGName(GNamej);
                                data.setGDiscription(GDiscriptionj);
                                data.setGScientificallyName(GScientificallyNamej);
                                data.setGGestationPeriod(GGestationPeriodj);
                                data.setGLifeSpan(GLifeSpanj);
                                data.setGMass(GMassj);
                                data.setGTropicalLevel(GTropicalLevelj);
                                data.setGDoYouKnow(GDoYouKnowj);

                                subCatData.add(data);

                            }
//                            Toast.makeText(activity, "sizeQ" + subCatData.size(), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    public static void SliderImagesJsonParse(Activity activity) {

        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        String url = "JSONSliderImg.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, APIPATH + "JSONSliderImg.php", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("tag", "onResponse: " + response);

                        animSliderImgModels.clear();

                        try {
                            JSONArray jsonArray = response.getJSONArray("SliderImg");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject SilderImagesObject = jsonArray.getJSONObject(i);

                                String id = SilderImagesObject.getString("id");
                                String SImages = SilderImagesObject.getString("simage");
                                String SName = SilderImagesObject.getString("sname");

//                                Log.e("imagesss", "onResponse:" + id + " " + SImages + "  " + SName);

                                AnimSliderImgModel data1 = new AnimSliderImgModel();
                                data1.setId(id);
                                data1.setImages(SImages);
                                data1.setName(SName);

                                animSliderImgModels.add(data1);

                            }

//                            Toast.makeText(activity, "sizeT" + animSliderImgModels.size(), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }


}
