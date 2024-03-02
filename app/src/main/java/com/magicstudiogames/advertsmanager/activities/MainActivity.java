package com.magicstudiogames.advertsmanager.activities;

import static com.magicstudiogames.advertsmanager.databinding.ActivityMainBinding.inflate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.material.button.MaterialButton;
import com.magicstudiogames.advertsmanager.R;
import com.magicstudiogames.advertsmanager.adverts.AdmobManager;
import com.magicstudiogames.advertsmanager.adverts.UnityManager;
import com.magicstudiogames.advertsmanager.databinding.ActivityMainBinding;
import com.unity3d.ads.UnityAds;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AdmobManager admobManager;
    private UnityManager unityManager;

    private MaterialButton admobInterstitialLoad, admobInterstitialShow;
    private MaterialButton admobRewardedLoad, admobRewardedShow;
    private MaterialButton unityInterstitialLoad, unityInterstitialShow;
    private MaterialButton unityRewardedLoad, unityRewardedShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());
        initializeLayout();
        initializeAdmob();
        initializeUnity();
    }

    private void initializeLayout() {
        admobInterstitialLoad = binding.admobInterstitialAdLoad;
        admobInterstitialShow = binding.admobInterstitialAdShow;
        admobRewardedLoad = binding.admobRewardedAdLoad;
        admobRewardedShow = binding.admobRewardedAdShow;

        unityInterstitialLoad = binding.unityInterstitialAdLoad;
        unityInterstitialShow = binding.unityInterstitialAdShow;
        unityRewardedLoad = binding.unityRewardedAdLoad;
        unityRewardedShow = binding.unityRewardedAdShow;

        admobInterstitialLoad.setOnClickListener(v -> {
            if (admobManager.admobManageIsLoaded()) {
                admobManager.initializeInterstitial();
            }
        });

        admobInterstitialShow.setOnClickListener(v -> {
            if (admobManager.admobManageIsLoaded() && admobManager.interstitialIsLoaded()) {
                admobManager.showInterstitial();
            }
        });

        admobRewardedLoad.setOnClickListener(v -> {
            if (admobManager.admobManageIsLoaded()) {
                admobManager.initializeRewarded();
            }
        });

        admobRewardedShow.setOnClickListener(v -> {
            if (admobManager.admobManageIsLoaded() && admobManager.rewardedIsLoaded()) {
                admobManager.showRewarded();
            }
        });

        unityInterstitialLoad.setOnClickListener(v -> {
            if (unityManager.unityManagerIsLoaded()) {
                unityManager.initializeInterstitial();
            }
        });

        unityInterstitialShow.setOnClickListener(v -> {
            if (unityManager.unityManagerIsLoaded() && unityManager.interstitialIsLoaded()) {
                unityManager.showInterstitial();
            }
        });

        unityRewardedLoad.setOnClickListener(v -> {
            if (unityManager.unityManagerIsLoaded()) {
                unityManager.initializeRewarded();
            }
        });

        unityRewardedShow.setOnClickListener(v -> {
            if (unityManager.unityManagerIsLoaded() && unityManager.rewardedIsLoaded()) {
                unityManager.showRewarded();
            }
        });
    }

    private void initializeAdmob() {
        admobManager = new AdmobManager(this, getString(R.string.admob_interstitial), getString(R.string.admob_awarded));
        admobManager.initializeAdmobManager();
        admobManager.setOnAdmobManagerListener(status -> {
            admobInterstitialLoad.setEnabled(status);
            admobRewardedLoad.setEnabled(status);
        });

        admobManager.initializeBanner(findViewById(R.id.ad_view_admob));
        admobManager.setOnBannerListener(new AdmobManager.OnBannerListener() {
            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdClosed() {

            }

            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdImpression() {

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {

            }

            @Override
            public void onAdOpened() {

            }
        });

        admobManager.setOnInterstitialListener(new AdmobManager.OnInterstitialListener() {
            @Override
            public void onInterstitialLoaded(@NonNull InterstitialAd interstitialAd) {
                admobInterstitialShow.setEnabled(true);
            }

            @Override
            public void onInterstitialShowComplete(@NonNull InterstitialAd interstitialAd) {
                admobInterstitialShow.setEnabled(false);
            }

            @Override
            public void onInterstitialUncompleted(@NonNull String message) {
                admobInterstitialShow.setEnabled(false);
            }

            @Override
            public void onInterstitialFailedToLoad(@NonNull LoadAdError loadAdError) {
                admobInterstitialShow.setEnabled(false);
            }
        });

        admobManager.setOnRewardedListener(new AdmobManager.OnRewardedListener() {
            @Override
            public void onRewardedLoaded(@NonNull RewardedAd rewardedAd) {
                admobRewardedShow.setEnabled(true);
            }

            @Override
            public void onRewardedShowComplete(@NonNull RewardedAd rewardedAd) {
                admobRewardedShow.setEnabled(false);
            }

            @Override
            public void onRewardedDismissComplete() {
                admobRewardedShow.setEnabled(false);
            }

            @Override
            public void onRewardedUncompleted(@NonNull String message) {
                admobRewardedShow.setEnabled(false);
            }

            @Override
            public void onRewardedFailedToLoad(@NonNull LoadAdError loadAdError) {
                admobRewardedShow.setEnabled(false);
            }
        });
    }

    private void initializeUnity() {
        unityManager = new UnityManager(this, getString(R.string.unity_game_app), getString(R.string.unity_game_interstitial), getString(R.string.unity_game_rewarded));
        unityManager.initializeUnity();

        unityManager.setOnUnityManagerListener(status -> {
            unityInterstitialLoad.setEnabled(status);
            unityRewardedLoad.setEnabled(status);
        });

        unityManager.initializeBanner(getString(R.string.unity_game_banner), findViewById(R.id.ad_view_unity));
        unityManager.setOnInterstitialListener(new UnityManager.OnInterstitialListener() {
            @Override
            public void onUnityInterstitialShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                unityInterstitialShow.setEnabled(false);
            }

            @Override
            public void onUnityInterstitialLoaded(String placementId) {
                unityInterstitialShow.setEnabled(true);
            }

            @Override
            public void onUnityInterstitialShowComplete(UnityAds.UnityAdsShowCompletionState state) {
                unityInterstitialShow.setEnabled(false);
            }

            @Override
            public void onUnityInterstitialUncompleted(String message) {
                unityInterstitialShow.setEnabled(false);
            }

            @Override
            public void onUnityInterstitialFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                unityInterstitialShow.setEnabled(false);
            }
        });

        unityManager.setOnRewardedListener(new UnityManager.OnRewardedListener() {
            @Override
            public void onUnityRewardedShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                unityRewardedShow.setEnabled(false);
            }

            @Override
            public void onUnityRewardedLoaded(String placementId) {
                unityRewardedShow.setEnabled(true);
            }

            @Override
            public void onUnityRewardedShowComplete(UnityAds.UnityAdsShowCompletionState state) {
                unityRewardedShow.setEnabled(false);
            }

            @Override
            public void onUnityRewardedUncompleted(String message) {
                unityRewardedShow.setEnabled(false);
            }

            @Override
            public void onUnityRewardedFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                unityRewardedShow.setEnabled(false);
            }
        });
    }
}