package com.citomoltd.createyourstylishnameandbio;

import android.app.Activity;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AdHelper {

    private static long lastAdTime = 0;
    private static final long AD_INTERVAL = 3 * 60 * 1000; // 3 minutes interval

    private static InterstitialAd mInterstitialAd;
    private static boolean isAdLoading = false;

    private static AdView mAdView;
    private static boolean isBannerAdLoading = false;

    public interface AdListener {
        void onAdClosed();
    }

    public static void initializeAd(Activity activity) {
        loadNewAd(activity);
    }

    public static void showAdIfNeeded(Activity activity, AdListener listener) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastAdTime > AD_INTERVAL) {
            if (mInterstitialAd != null) {
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Log.d("AdHelper", "Ad dismissed.");
                        lastAdTime = System.currentTimeMillis();
                        if (listener != null) listener.onAdClosed();
                        // Pre-load the next interstitial ad
                        loadNewAd(activity);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d("AdHelper", "Ad failed to show: " + adError.getMessage());
                        if (listener != null) listener.onAdClosed();
                        // Try reloading ad
                        loadNewAd(activity);
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        Log.d("AdHelper", "Ad showed.");
                        mInterstitialAd = null; // Important: prevent showing the same ad again
                    }
                });

                mInterstitialAd.show(activity);
            } else {
                Log.d("AdHelper", "Interstitial ad not ready, loading new one...");
                loadNewAd(activity);
                if (listener != null) listener.onAdClosed();
            }
        } else {
            if (listener != null) listener.onAdClosed();
        }
    }

    private static void loadNewAd(Activity activity) {
        if (isAdLoading) {
            Log.d("AdHelper", "Ad is already loading.");
            return;
        }

        isAdLoading = true;
        AdRequest adRequest = new AdRequest.Builder().build();
        //test add ca-app-pub-3940256099942544/1033173712

        InterstitialAd.load(activity, "ca-app-pub-5488667449529842/2935254054", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(InterstitialAd interstitialAd) {
                        Log.d("AdHelper", "Interstitial ad loaded.");
                        mInterstitialAd = interstitialAd;
                        isAdLoading = false;
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Log.d("AdHelper", "Failed to load ad: " + loadAdError.getMessage());
                        mInterstitialAd = null;
                        isAdLoading = false;
                    }
                });
    }

    public static void loadBannerAd(Activity activity, FrameLayout adContainer) {
        if (isBannerAdLoading) {
            return;
        }

        isBannerAdLoading = true;

        mAdView = new AdView(activity);
        mAdView.setAdSize(AdSize.BANNER);
        //test ads ca-app-pub-3940256099942544/6300978111
        mAdView.setAdUnitId("ca-app-pub-5488667449529842/5984907461");
        adContainer.removeAllViews();
        adContainer.addView(mAdView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                isBannerAdLoading = false;
                Log.d("AdHelper", "Banner Ad Loaded");
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                isBannerAdLoading = false;
                Log.d("AdHelper", "Banner Ad failed to load: " + loadAdError.getMessage());
            }
        });
    }

    public static void clearBannerAd() {
        if (mAdView != null) {
            mAdView.destroy();
            mAdView = null;
        }
    }
}
