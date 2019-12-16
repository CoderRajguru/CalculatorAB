package com.example.calculatorab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button buttonAdd;
    Button buttonSubstract;
    Button buttonMul;
    Button buttonDiv;
    Button buttonClear;
    Button buttonEqual;
    String result;
    String tmp;
    String operator;
    TextView resultTextView;

    private FirebaseRemoteConfig firebaseRemoteConfig;
    private AdView adView,adview_bottom,adview_extra;
    private InterstitialAd interstitialAd;
    private final String TAG = "Interstitial Activity";


    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControl();
        initControlListener();

        AudienceNetworkAds.initialize(this);



        Map<String,Object> map =new HashMap<>();
        map.put("banner_pos","top");
        map.put("banner_extra","no");
        map.put("Interstitial_activity","onClear");
        map.put("Interstitial_activity_placement_id","IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");

        FirebaseApp.initializeApp(this);
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build());
        firebaseRemoteConfig.setDefaults(map);


        firebaseRemoteConfig.fetch(0).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){


                    firebaseRemoteConfig.activateFetched();

                    Log.d("banner_pos", firebaseRemoteConfig.getString("banner_pos"));

                }
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        Log.d("IID_TOKEN", task.getResult().getToken());
                    }
                });


        Log.d("banner_pos", firebaseRemoteConfig.getString("banner_pos"));
        //Log.d("banner_pos", firebaseRemoteConfig.getString("banner_pos"));



        interstitialAd = new InterstitialAd(this, firebaseRemoteConfig.getString("Interstitial_activity_placement_id"));




        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
                interstitialAd.loadAd();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }

        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();





        if(firebaseRemoteConfig.getString("banner_pos").equals("top")){

            Log.d("check_ban","inside if_top");

            adView = new AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);
            LinearLayout adContainer_bottom = (LinearLayout) findViewById(R.id.banner_container);
            adContainer_bottom.addView(adView);
            adView.loadAd();
        }



        if(firebaseRemoteConfig.getString("banner_pos").equals("bottom")){

            Log.d("check_ban","inside if_bot");

            adview_bottom = new AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);
            LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container_bottom);
            adContainer.addView(adview_bottom);
            adview_bottom.loadAd();

            // dmVDcrDTw-Q:APA91bFnMaV31qUtsgeGXt0zrqMx01cd0NBTPzmLlbufCgzkIrVL_onH_jHySZ2mS0y0oU9Slai4FfhevKfQ_yTHC0BCUQiXeRX5t5lx1aSRjpavNzo9fSV4ryfpp38dcBjSwv4n-O2_ : pixel
            //APA91bG3Em7p6Qa-phGVlxa8WTZ-JjrS4KKrleBefOQwTOX9N0Gzrh06x0irvR0hmcwMC_wwI7ukB4xpZ42XHpYGzhZ5ukLyI-dOZLKKdsWjJtPKCiB5LVndQ0sWHcneK1m8wVpTPOkn : samsung sarah
            // fezK_pm-Sfs:APA91bG3Em7p6Qa-phGVlxa8WTZ-JjrS4KKrleBefOQwTOX9N0Gzrh06x0irvR0hmcwMC_wwI7ukB4xpZ42XHpYGzhZ5ukLyI-dOZLKKdsWjJtPKCiB5LVndQ0sWHcneK1m8wVpTPOkn : samsung DK

        }

        if(firebaseRemoteConfig.getString("banner_extra").equals("yes")){

            adview_extra = new AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.RECTANGLE_HEIGHT_250);
            LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container_extra);
            adContainer.addView(adview_extra);
            adview_extra.loadAd();

            // dmVDcrDTw-Q:APA91bFnMaV31qUtsgeGXt0zrqMx01cd0NBTPzmLlbufCgzkIrVL_onH_jHySZ2mS0y0oU9Slai4FfhevKfQ_yTHC0BCUQiXeRX5t5lx1aSRjpavNzo9fSV4ryfpp38dcBjSwv4n-O2_ : pixel (cg)
            //APA91bG3Em7p6Qa-phGVlxa8WTZ-JjrS4KKrleBefOQwTOX9N0Gzrh06x0irvR0hmcwMC_wwI7ukB4xpZ42XHpYGzhZ5ukLyI-dOZLKKdsWjJtPKCiB5LVndQ0sWHcneK1m8wVpTPOkn : samsung sarah(V-A)
            // fezK_pm-Sfs:APA91bG3Em7p6Qa-phGVlxa8WTZ-JjrS4KKrleBefOQwTOX9N0Gzrh06x0irvR0hmcwMC_wwI7ukB4xpZ42XHpYGzhZ5ukLyI-dOZLKKdsWjJtPKCiB5LVndQ0sWHcneK1m8wVpTPOkn : samsung DK (cg)

        }


    }

    private void initControlListener() {
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("0");
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("1");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("2");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("3");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("4");
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("5");
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("6");
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("7");
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("8");
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("9");
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClearButtonClicked();
                if(firebaseRemoteConfig.getString("Interstitial_activity").equals("onClear")){
                    interstitialAd.show();
                }
            }
        });
        buttonSubstract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onOperatorButtonClicked("-");
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOperatorButtonClicked("+");
            }
        });
        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOperatorButtonClicked("X");
            }
        });
        buttonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOperatorButtonClicked("/");
            }
        });
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqualButtonClicked();
            }
        });

    }

    private void onEqualButtonClicked() {
        int res = 0;
        try {
            int number = Integer.valueOf(tmp);
            int number2 = Integer.valueOf(resultTextView.getText().toString());
            switch (operator) {
                case "+":
                    res = number + number2;
                    break;
                case "/":
                    res = number / number2;
                    break;
                case "-":
                    res = number - number2;
                    break;
                case "X":
                    res = number * number2;
                    break;
            }
            result = String.valueOf(res);
            resultTextView.setText(result);
            if(firebaseRemoteConfig.getString("Interstitial_activity").equals("onExit")){
                interstitialAd.show();




            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onOperatorButtonClicked(String operator) {
        tmp = resultTextView.getText().toString();
        onClearButtonClicked();
        this.operator = operator;
    }

    private void onClearButtonClicked() {
        result = "";
        resultTextView.setText("");

    }

    private void onNumberButtonClicked(String pos) {
        result = resultTextView.getText().toString();
        result = result + pos;
        resultTextView.setText(result);
    }

    private void initControl() {
        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);
        button7 = (Button)findViewById(R.id.button7);
        button8 = (Button)findViewById(R.id.button8);
        button9 = (Button)findViewById(R.id.button9);

        buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonClear = (Button)findViewById(R.id.buttonClear);
        buttonSubstract = (Button)findViewById(R.id.buttonSub);
        buttonMul = (Button)findViewById(R.id.buttonMul);
        buttonDiv = (Button)findViewById(R.id.buttonDiv);
        buttonEqual = (Button)findViewById(R.id.buttonEqual);

        resultTextView = (TextView)findViewById(R.id.text_view_result);
    }
}
