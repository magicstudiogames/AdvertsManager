package com.magicstudiogames.advertsmanager.adverts;

import static com.google.android.gms.ads.MobileAds.getInitializationStatus;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Objects;

public class AdmobManager {
    protected Context context;

    protected String placementInterstitial;
    protected String placementRewarded;

    private OnAdmobManagerListener onAdmobManagerListener;
    private OnBannerListener onBannerListener;
    private OnRewardedListener onRewardedListener;
    private OnInterstitialListener onInterstitialListener;


    private boolean admobManageIsLoaded = false;
    private boolean bannerIsLoaded;
    private boolean interstitialIsLoaded;
    private boolean rewardedIsLoaded;

    public interface OnAdmobManagerListener {
        void onLoaded(boolean status);
    }

    public interface OnBannerListener {
        void onAdClicked();

        void onAdClosed();

        void onAdLoaded();

        void onAdImpression();

        void onAdFailedToLoad(@NonNull LoadAdError adError);

        void onAdOpened();

    }

    public interface OnInterstitialListener {

        void onInterstitialLoaded(@NonNull InterstitialAd interstitialAd);

        void onInterstitialShowComplete(@NonNull InterstitialAd interstitialAd);

        void onInterstitialUncompleted(@NonNull String message);

        void onInterstitialFailedToLoad(@NonNull LoadAdError loadAdError);
    }

    public interface OnRewardedListener {

        void onRewardedLoaded(@NonNull RewardedAd rewardedAd);

        void onRewardedShowComplete(@NonNull RewardedAd rewardedAd);

        void onRewardedDismissComplete();

        void onRewardedUncompleted(@NonNull String message);

        void onRewardedFailedToLoad(@NonNull LoadAdError loadAdError);
    }

    public AdmobManager(Context context, String placementInterstitial) {
        this.context = context;
        this.placementInterstitial = placementInterstitial;
    }

    public AdmobManager(Context context, String placementInterstitial, String placementRewarded) {
        this.context = context;
        this.placementInterstitial = placementInterstitial;
        this.placementRewarded = placementRewarded;
    }

    public void setOnAdmobManagerListener(OnAdmobManagerListener onAdmobManagerListener) {
        this.onAdmobManagerListener = onAdmobManagerListener;
    }

    public void setOnBannerListener(OnBannerListener onBannerListener) {
        this.onBannerListener = onBannerListener;
    }

    public void setOnInterstitialListener(OnInterstitialListener onInterstitialListener) {
        this.onInterstitialListener = onInterstitialListener;
    }

    public void setOnRewardedListener(OnRewardedListener onRewardedListener) {
        this.onRewardedListener = onRewardedListener;
    }

    public void initializeAdmobManager() {
        MobileAds.initialize(context, initializationStatus -> {
            if (onAdmobManagerListener != null) {
                onAdmobManagerListener.onLoaded(true);
            }
            admobManageIsLoaded = true;
        });
    }

    public void initializeBanner(AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                if (onBannerListener != null) {
                    onBannerListener.onAdClicked();
                }
            }

            @Override
            public void onAdClosed() {
                if (onBannerListener != null) {
                    onBannerListener.onAdClosed();
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                bannerIsLoaded = false;
                if (onBannerListener != null) {
                    onBannerListener.onAdFailedToLoad(adError);
                }
            }

            @Override
            public void onAdImpression() {
                if (onBannerListener != null) {
                    onBannerListener.onAdImpression();
                }
            }

            @Override

            public void onAdLoaded() {
                bannerIsLoaded = true;
                if (onBannerListener != null) {
                    onBannerListener.onAdLoaded();
                }
            }

            @Override
            public void onAdOpened() {
                if (onBannerListener != null) {
                    onBannerListener.onAdOpened();
                }
            }
        });
    }

    public void initializeInterstitial() {
        InterstitialAd.load(context, placementInterstitial, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                interstitialIsLoaded = true;
                if (onInterstitialListener != null) {
                    onInterstitialListener.onInterstitialLoaded(interstitialAd);
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                interstitialIsLoaded = false;
                if (onInterstitialListener != null) {
                    onInterstitialListener.onInterstitialFailedToLoad(loadAdError);
                }
            }
        });
    }

    public void showInterstitial() {
        if (interstitialIsLoaded) {
            InterstitialAd.load(context, placementInterstitial, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    interstitialAd.show((Activity) context);
                    if (onInterstitialListener != null) {
                        onInterstitialListener.onInterstitialShowComplete(interstitialAd);
                    }
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    if (onInterstitialListener != null) {
                        onInterstitialListener.onInterstitialFailedToLoad(loadAdError);
                    }
                }
            });
        } else {
            if (onInterstitialListener != null) {
                onInterstitialListener.onInterstitialUncompleted("Incomplete interstitial loading");
            }
        }
    }

    public void initializeRewarded() {
        RewardedAd.load(context, placementRewarded, new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                rewardedIsLoaded = true;
                if (onRewardedListener != null) {
                    onRewardedListener.onRewardedLoaded(rewardedAd);
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                rewardedIsLoaded = false;
                if (onRewardedListener != null) {
                    onRewardedListener.onRewardedFailedToLoad(loadAdError);
                }
            }
        });
    }

    public void showRewarded() {
        if (rewardedIsLoaded) {
            RewardedAd.load(context, placementRewarded, new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                    rewardedAd.show((Activity) context, rewardItem -> {
                        if (onRewardedListener != null) {
                            onRewardedListener.onRewardedShowComplete(rewardedAd);
                        }
                    });

                    rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            if (onRewardedListener != null) {
                                onRewardedListener.onRewardedDismissComplete();
                            }
                        }
                    });

                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    if (onRewardedListener != null) {
                        onRewardedListener.onRewardedFailedToLoad(loadAdError);
                    }
                }

            });
        } else {
            if (onRewardedListener != null) {
                onRewardedListener.onRewardedUncompleted("Incomplete rewarded loading");
            }
        }
    }

    public boolean admobManageIsLoaded() {
        return admobManageIsLoaded;
    }

    public boolean bannerIsLoaded() {
        return bannerIsLoaded;
    }

    public boolean interstitialIsLoaded() {
        return interstitialIsLoaded;
    }

    public boolean rewardedIsLoaded() {
        return rewardedIsLoaded;
    }
}

