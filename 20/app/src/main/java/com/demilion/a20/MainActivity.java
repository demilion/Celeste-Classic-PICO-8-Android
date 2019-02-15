package com.demilion.a20;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.AndroidException;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.android.vending.billing.IInAppBillingService;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements RewardedVideoAdListener {//extends AppCompatActivity {//implements MoPubInterstitial.InterstitialAdListener

    private WebView myWebView;
    private RewardedVideoAd mRewardedVideoAd;
    IInAppBillingService mService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//-------------------
        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
//-------------------
        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.setWebViewClient(new WebViewClient());
        // Enable JavaScript
        WebSettings settings = myWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient());
        //myWebView.setWebChromeClient(new WebChromeClient());
        //myWebView.setVerticalScrollBarEnabled(false);
        //myWebView.setHorizontalScrollBarEnabled(false);

        //MobileAds.initialize(this, "");//Bonificado production
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");//test

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        myWebView.addJavascriptInterface(new MyJavaScriptInterface(this), "Android");
        myWebView.loadUrl("file:///android_asset/index.html");
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",new AdRequest.Builder().build());

    }

    public class MyJavaScriptInterface
    {

        private Context context;

        public MyJavaScriptInterface(Context context){
            this.context = context;
        }

        @JavascriptInterface // this annotation is import
        public void startRewardVideoAndroidFunction(String paramFromJS) {

            //here you need to start showing reward movie
            //because this function will be called after webView button click.

            // if (text.equals("ok")) {

            //if (mRewardedVideoAd.isLoaded()) {
            Toast.makeText(context , "PAUSE",Toast.LENGTH_SHORT).show();
            //mRewardedVideoAd.show();
            //}

            runOnUiThread(new Runnable() {
                public void run() {
                    //if (mRewardedVideoAd.isLoaded()) {
                    //    mRewardedVideoAd.show();
                    //}


                    if (mRewardedVideoAd.isLoaded()) {
                    //-----------------------------------------------
                        //TODO something when floating action menu first item clicked

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("HAVING FUN?\n" + "SHOW US SOME LOVE \n" + "RATE OUR GAME OR\n" + "WATCH A VIDEO!!!")
                                .setCancelable(false)
                                .setPositiveButton("Rate Now", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.demilion.a20") ) );
                                    }
                                })

                                .setNeutralButton("Video",

                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //if (mRewardedVideoAd.isLoaded()) {
                                                    mRewardedVideoAd.show();
                                                //}

                                            }
                                        })

                                .setNegativeButton("No, Thanks", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        builder.create()
                                .show();

                    //-----------------------------------------------
                }else
                 {
                        //-----------------------------------------------
                        //TODO something when floating action menu first item clicked

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("HAVING FUN?\n" + "SHOW US SOME LOVE \n" + "RATE OUR GAME OR\n" + "DONATE!!!")
                                .setCancelable(false)
                                .setPositiveButton("Rate Now", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.demilion.a20") ) );
                                    }
                                })

                                .setNeutralButton("DONATE",

                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //if (mRewardedVideoAd.isLoaded()) {
                                                //mRewardedVideoAd.show();
                                                //}
// comprar servicio
                                                try {
                                                    comprar("donation", "inapp", 1002);
                                                } catch (AndroidException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        })

                                .setNegativeButton("No, Thanks", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        builder.create()
                                .show();
                    }
                }

            });


        }
    }



    @Override
    public void onRewardedVideoAdClosed() {
        // Load the next rewarded video ad.

        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem reward) {
        // Reward the user.

        // Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
        //         reward.getAmount(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Thanks a lot,  " + reward.getType() + "  +: " +
                reward.getAmount()+ " seg", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        // Toast.makeText(this, "onRewardedVideoAdLeftApplication",
        //         Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        //  Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        //  Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdOpened() {
        //   Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        //  Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    // @Override
    public void onRewardedVideoCompleted() {
        // Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
    }

    private void loadRewardedVideoAd() {
        // mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", //Test
        // new AdRequest.Builder().build());

        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConn);
        }
    }

    //Este codigo ponlo al final de tu proyecto, son los metodos del codigo anterior
    //---------------
    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
        }
    };


    // lanzo petición de compra1<br>comprar("android.test.purchased", "inapp", 1001);


    // METODO PARA REALIZAR LAS COMPRAS. Se pasa parametro SKU, TIPO y codigo de peticion:
    public void comprar(String SKU, String TYPE, int reqcode) throws AndroidException {

        // codigo venta, genero la peticion de la liecencia
        Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(),
                SKU, TYPE, "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");

        // genero el pendig intent
        PendingIntent pendingIntent = buyIntentBundle
                .getParcelable("BUY_INTENT");

        // compruebo que la respuesta es 0, si no no se puede comprar
        if (buyIntentBundle.getInt("RESPONSE_CODE") == 0) {
            startIntentSenderForResult(pendingIntent.getIntentSender(),
                    reqcode, new Intent(), Integer.valueOf(0),
                    Integer.valueOf(0), Integer.valueOf(0));
        }

    }

    //Metodo que recibe el resultado del intent de la compra

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    Toast.makeText(this, "You have bought the " + sku +
                            ". Excellent choice,adventurer!", Toast.LENGTH_LONG)
                            .show();

                }
                catch (JSONException e) {
                    Toast.makeText(this, "Failed to parse purchase data.", Toast.LENGTH_LONG);

                    e.printStackTrace();
                }
            }
        }
    }    //---------------

}

