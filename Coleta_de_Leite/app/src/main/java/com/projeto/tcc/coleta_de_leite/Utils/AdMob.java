package com.projeto.tcc.coleta_de_leite.Utils;

import android.content.Context;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite.R;

public class AdMob {
    public boolean mAdmob(Context context ,AdView adView){
        MobileAds.initialize(context, "ca-app-pub-7740037973360371/2579306449");
        adView = (AdView) adView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("ca-app-pub-7740037973360371/2579306449").build();
        adView.loadAd(adRequest);
        return true;

    }
}
