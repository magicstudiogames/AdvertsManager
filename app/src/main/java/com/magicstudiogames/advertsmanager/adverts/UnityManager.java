package com.magicstudiogames.advertsmanager.adverts;

import android.app.Activity;
import android.content.Context;

import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class UnityManager {

    private final Context context;
    private final String gameId;
    private String interstitialPlacementId;
    private String rewardsPlacementId;

    private OnUnityManagerListener onUnityManagerListener;
    private OnInterstitialListener onInterstitialListener;
    private OnRewardedListener onRewardedListener;

    private boolean unityManagerIsLoaded;
    private boolean completedInterstitialLoaded;
    private boolean completedRewardedLoaded;


    public interface OnUnityManagerListener {
        void onLoaded(boolean status);
    }

    public interface OnInterstitialListener {
        void onUnityInterstitialShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message);

        void onUnityInterstitialLoaded(String placementId);

        void onUnityInterstitialShowComplete(UnityAds.UnityAdsShowCompletionState state);

        void onUnityInterstitialUncompleted(String message);

        void onUnityInterstitialFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message);
    }

    public interface OnRewardedListener {
        void onUnityRewardedShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message);

        void onUnityRewardedLoaded(String placementId);

        void onUnityRewardedShowComplete(UnityAds.UnityAdsShowCompletionState state);

        void onUnityRewardedUncompleted(String message);

        void onUnityRewardedFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message);
    }

    public UnityManager(Context context, String gameId, String interstitialPlacementId, String rewardsPlacementId) {
        this.context = context;
        this.gameId = gameId;
        this.interstitialPlacementId = interstitialPlacementId;
        this.rewardsPlacementId = rewardsPlacementId;
    }

    public void setOnUnityManagerListener(OnUnityManagerListener onUnityManagerListener) {
        this.onUnityManagerListener = onUnityManagerListener;
    }

    public void setOnInterstitialListener(OnInterstitialListener onInterstitialListener) {
        this.onInterstitialListener = onInterstitialListener;
    }

    public void setOnRewardedListener(OnRewardedListener onRewardedListener) {
        this.onRewardedListener = onRewardedListener;
    }

    public void initializeUnity() {
        UnityAds.initialize(context, gameId, false, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                unityManagerIsLoaded = true;
                if (onUnityManagerListener != null) {
                    onUnityManagerListener.onLoaded(true);
                }
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                unityManagerIsLoaded = false;
                if (onUnityManagerListener != null) {
                    onUnityManagerListener.onLoaded(false);
                }
            }
        });
    }

    public void initializeBanner(String bannerPlacementId, AdView adView) {
        UnityBannerSize bannerSize = new UnityBannerSize(320, 50);
        BannerView bannerView = new BannerView((Activity) context, bannerPlacementId, bannerSize);
        adView.setView(bannerView);
        bannerView.setListener(new BannerView.IListener() {
            @Override
            public void onBannerLoaded(BannerView bannerAdView) {
            }

            @Override
            public void onBannerShown(BannerView bannerAdView) {

            }

            @Override
            public void onBannerClick(BannerView bannerAdView) {

            }

            @Override
            public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {

            }

            @Override
            public void onBannerLeftApplication(BannerView bannerView) {

            }

        });
        bannerView.load();
    }

    public void initializeInterstitial() {
        UnityAds.load(interstitialPlacementId, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                completedInterstitialLoaded = true;
                if (onInterstitialListener != null) {
                    onInterstitialListener.onUnityInterstitialLoaded(placementId);
                }
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                completedInterstitialLoaded = false;
                if (onInterstitialListener != null) {
                    onInterstitialListener.onUnityInterstitialFailedToLoad(placementId, error, message);
                }
            }
        });
    }

    public void showInterstitial() {
        if (completedInterstitialLoaded) {
            UnityAds.show((Activity) context, interstitialPlacementId, new IUnityAdsShowListener() {
                @Override
                public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                    if (onInterstitialListener != null) {
                        onInterstitialListener.onUnityInterstitialShowFailure(placementId, error, message);
                    }
                }

                @Override
                public void onUnityAdsShowStart(String placementId) {
                    // Interstitial ad show start
                }

                @Override
                public void onUnityAdsShowClick(String placementId) {
                    // Interstitial ad clicked
                }

                @Override
                public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                    if (onInterstitialListener != null) {
                        onInterstitialListener.onUnityInterstitialShowComplete(state);
                    }
                }
            });
        } else {
            if (onInterstitialListener != null) {
                onInterstitialListener.onUnityInterstitialUncompleted("Incomplete interstitial loading");
            }
        }
    }

    public void initializeRewarded() {
        UnityAds.load(rewardsPlacementId, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                completedRewardedLoaded = true;
                if (onRewardedListener != null) {
                    onRewardedListener.onUnityRewardedLoaded(placementId);
                }
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                completedRewardedLoaded = false;
                if (onRewardedListener != null) {
                    onRewardedListener.onUnityRewardedFailedToLoad(placementId, error, message);
                }
            }
        });
    }

    public void showRewarded() {
        if (completedRewardedLoaded) {
            UnityAds.show((Activity) context, rewardsPlacementId, new IUnityAdsShowListener() {
                @Override
                public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                    if (onRewardedListener != null) {
                        onRewardedListener.onUnityRewardedShowFailure(placementId, error, message);
                    }
                }

                @Override
                public void onUnityAdsShowStart(String placementId) {
                    // Reward ad show start
                }

                @Override
                public void onUnityAdsShowClick(String placementId) {
                    // Reward ad clicked
                }

                @Override
                public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                    if (onRewardedListener != null) {
                        onRewardedListener.onUnityRewardedShowComplete(state);
                    }
                }
            });
        } else {
            if (onRewardedListener != null) {
                onRewardedListener.onUnityRewardedUncompleted("incomplete rewarded loading");
            }
        }
    }

    public boolean unityManagerIsLoaded() {
        return unityManagerIsLoaded;
    }

    public boolean interstitialIsLoaded() {
        return completedInterstitialLoaded;
    }

    public boolean rewardedIsLoaded() {
        return completedRewardedLoaded;
    }
}