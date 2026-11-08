package com.citomoltd.createyourstylishnameandbio;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Bio_two_activity extends AppCompatActivity implements View.OnClickListener {


    LinearLayout bestBagnlaCaption, banglaStatusLn, banglaCaption, fbStylishComments, banglaComments, fBStylishBio, fBAttitudeBio, fBStylishName, fBNameSymbol, symbolTextArt, tiktokStylishBIo, groupNameIdeas, fBStylishWork;

    CardView bestBagnlaCaptionText1, bestBagnlaCaptionText2, bestBagnlaCaptionText3, bestBagnlaCaptionText4, banglaStatusText1, banglaStatusText2, banglaStatusText3, banglaStatusText4, banglaCaptionText, banglaCaptionText2, banglaCaptionText3, banglaCaptionText4, fbStylishCommentsText, fbStylishCommentsText2, fbStylishCommentsText3, fbStylishCommentsText4, banglaCommentsText, banglaCommentsText2,
            fBStylishBioText, fBStylishBioText2, fBStylishBioText3, fBStylishBioText4, fBAttitudeBioText, fBAttitudeBioText2, fBAttitudeBioText3, fBAttitudeBioText4, fBStylishNameText, fBStylishNameText2, fBStylishNameText3, fBStylishNameText4, fBNameSymbolText, fBNameSymbolText2, symbolTextArtText, symbolTextArtText2, tiktokStylishBIoText, tiktokStylishBIoText2, groupNameIdeasText, groupNameIdeasText2, groupNameIdeasText3, fBStylishWorkText, fBStylishWorkText2, fBStylishWorkText3;
    TextView toolbar;
    private final Map<Integer, Pair<String, Integer>> itemMap = new HashMap<Integer, Pair<String, Integer>>() {{
        put(R.id.bestBagnlaCaptionText1, new Pair<>("মজার মজার ক্যাপশন", 1));
        put(R.id.bestBagnlaCaptionText2, new Pair<>("মজার মজার স্ট্যাটাস", 2));
        put(R.id.bestBagnlaCaptionText3, new Pair<>("Stylish ফেসবুক স্ট্যাটাস", 3));
        put(R.id.bestBagnlaCaptionText4, new Pair<>("Stylish ফেসবুক ক্যাপশন", 4));
        put(R.id.banglaStatusText1, new Pair<>("ভালোবাসার স্ট্যাটাস", 5));
        put(R.id.banglaStatusText2, new Pair<>("বন্ধু নিয়ে FB স্ট্যাটাস", 6));
        put(R.id.banglaStatusText3, new Pair<>("ইসলামিক স্ট্যাটাস", 7));
        put(R.id.banglaStatusText4, new Pair<>("জুমা মোবারক স্ট্যাটাস", 8));
        put(R.id.banglaCaptionText, new Pair<>("বাংলা শর্ট ক্যাপশন", 9));
        put(R.id.banglaCaptionText2, new Pair<>("রোমান্টিক ফেসবুক ক্যাপশন", 10));
        put(R.id.banglaCaptionText3, new Pair<>("বেস্ট বাংলা ক্যাপশন", 11));
        put(R.id.banglaCaptionText4, new Pair<>("কষ্টের ফেসবুক ক্যাপশন", 12));
        put(R.id.fbStylishCommentsText, new Pair<>("VIP Comments", 13));
        put(R.id.fbStylishCommentsText2, new Pair<>("Stylish Comments", 14));
        put(R.id.fbStylishCommentsText3, new Pair<>("FB Long Comments", 15));
        put(R.id.fbStylishCommentsText4, new Pair<>("Girls Photo Comments", 16));
        put(R.id.banglaCommentsText, new Pair<>("Bangla Comments", 17));
        put(R.id.banglaCommentsText2, new Pair<>("New Bangla Comments", 18));
        put(R.id.fBStylishBioText, new Pair<>("FB VIP Bio Style", 19));
        put(R.id.fBStylishBioText2, new Pair<>("FB VIP Long Bio", 20));
        put(R.id.fBStylishBioText3, new Pair<>("FB Islamic Bio Style", 21));
        put(R.id.fBStylishBioText4, new Pair<>("FB Bio Text Art", 22));
        put(R.id.fBAttitudeBioText, new Pair<>("FB Attitude Bio Text", 23));
        put(R.id.fBAttitudeBioText2, new Pair<>("FB Bio English", 24));
        put(R.id.fBAttitudeBioText3, new Pair<>("Bangla FB Bio Style", 25));
        put(R.id.fBAttitudeBioText4, new Pair<>("Bangla short Bio For FB", 26));
        put(R.id.fBStylishNameText, new Pair<>("FB Jungle Name", 27));
        put(R.id.fBStylishNameText2, new Pair<>("FB Emoji Name", 28));
        put(R.id.fBStylishNameText3, new Pair<>("FB Bubble Name", 29));
        put(R.id.fBStylishNameText4, new Pair<>("FB Invalid Name", 30));
        put(R.id.fBNameSymbolText, new Pair<>("FB Stylish name Symbols", 31));
        put(R.id.fBNameSymbolText2, new Pair<>("FB stylish NickName\"", 32));
        put(R.id.symbolTextArtText, new Pair<>("Stylish Art", 33));
        put(R.id.symbolTextArtText2, new Pair<>("Quotes Symbol", 34));
        put(R.id.tiktokStylishBIoText, new Pair<>("TikTok Bio Stylish", 35));
        put(R.id.tiktokStylishBIoText2, new Pair<>("Tiktok Attitude Bio", 36));
        put(R.id.groupNameIdeasText, new Pair<>("Stylish Group Name", 37));
        put(R.id.groupNameIdeasText2, new Pair<>("Messenger Group Name", 38));
        put(R.id.groupNameIdeasText3, new Pair<>("WhatsApp Group Name", 39));
        put(R.id.fBStylishWorkText, new Pair<>("FB VIP Work Symbols", 40));
        put(R.id.fBStylishWorkText2, new Pair<>("Fb Stylish Work Symbols", 41));
        put(R.id.fBStylishWorkText3, new Pair<>("FB Bangla Work", 42));
    }};

    ImageView backArrowImage;
    public static String STATUS_LIST = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bio_two);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        toolbar = findViewById(R.id.stToolbar);

        bestBagnlaCaption = findViewById(R.id.bestBagnlaCaption);
        banglaStatusLn = findViewById(R.id.banglaStatusLn);
        banglaCaption = findViewById(R.id.banglaCaption);
        fbStylishComments = findViewById(R.id.fbStylishComments);
        banglaComments = findViewById(R.id.banglaComments);
        fBStylishBio = findViewById(R.id.fBStylishBio);
        fBAttitudeBio = findViewById(R.id.fBAttitudeBio);
        fBStylishName = findViewById(R.id.fBStylishName);
        fBNameSymbol = findViewById(R.id.fBNameSymbol);
        symbolTextArt = findViewById(R.id.symbolTextArt);
        fBStylishWork = findViewById(R.id.fBStylishWork);
        groupNameIdeas = findViewById(R.id.groupNameIdeas);
        tiktokStylishBIo = findViewById(R.id.tiktokStylishBIo);

//introduction cardView
        bestBagnlaCaptionText1 = findViewById(R.id.bestBagnlaCaptionText1);
        bestBagnlaCaptionText2 = findViewById(R.id.bestBagnlaCaptionText2);
        bestBagnlaCaptionText3 = findViewById(R.id.bestBagnlaCaptionText3);
        bestBagnlaCaptionText4 = findViewById(R.id.bestBagnlaCaptionText4);
        banglaStatusText1 = findViewById(R.id.banglaStatusText1);
        banglaStatusText2 = findViewById(R.id.banglaStatusText2);
        banglaStatusText3 = findViewById(R.id.banglaStatusText3);
        banglaStatusText4 = findViewById(R.id.banglaStatusText4);
        banglaCaptionText = findViewById(R.id.banglaCaptionText);
        banglaCaptionText2 = findViewById(R.id.banglaCaptionText2);
        banglaCaptionText3 = findViewById(R.id.banglaCaptionText3);
        banglaCaptionText4 = findViewById(R.id.banglaCaptionText4);

//introduction on cardView
        fbStylishCommentsText = findViewById(R.id.fbStylishCommentsText);
        fbStylishCommentsText2 = findViewById(R.id.fbStylishCommentsText2);
        fbStylishCommentsText3 = findViewById(R.id.fbStylishCommentsText3);
        fbStylishCommentsText4 = findViewById(R.id.fbStylishCommentsText4);
        banglaCommentsText = findViewById(R.id.banglaCommentsText);
        banglaCommentsText2 = findViewById(R.id.banglaCommentsText2);
        fBStylishBioText = findViewById(R.id.fBStylishBioText);
        fBStylishBioText2 = findViewById(R.id.fBStylishBioText2);
        fBStylishBioText3 = findViewById(R.id.fBStylishBioText3);
        fBStylishBioText4 = findViewById(R.id.fBStylishBioText4);
        fBAttitudeBioText = findViewById(R.id.fBAttitudeBioText);
        fBAttitudeBioText2 = findViewById(R.id.fBAttitudeBioText2);
        fBAttitudeBioText3 = findViewById(R.id.fBAttitudeBioText3);
        fBAttitudeBioText4 = findViewById(R.id.fBAttitudeBioText4);
        fBStylishNameText = findViewById(R.id.fBStylishNameText);

        fBStylishNameText2 = findViewById(R.id.fBStylishNameText2);
        fBStylishNameText3 = findViewById(R.id.fBStylishNameText3);
        fBStylishNameText4 = findViewById(R.id.fBStylishNameText4);
        fBNameSymbolText = findViewById(R.id.fBNameSymbolText);
        fBNameSymbolText2 = findViewById(R.id.fBNameSymbolText2);
        symbolTextArtText = findViewById(R.id.symbolTextArtText);
        symbolTextArtText2 = findViewById(R.id.symbolTextArtText2);
        tiktokStylishBIoText = findViewById(R.id.tiktokStylishBIoText);
        tiktokStylishBIoText2 = findViewById(R.id.tiktokStylishBIoText2);

        groupNameIdeasText = findViewById(R.id.groupNameIdeasText);
        groupNameIdeasText2 = findViewById(R.id.groupNameIdeasText2);
        groupNameIdeasText3 = findViewById(R.id.groupNameIdeasText3);

        fBStylishWorkText = findViewById(R.id.fBStylishWorkText);
        fBStylishWorkText2 = findViewById(R.id.fBStylishWorkText2);
        fBStylishWorkText3 = findViewById(R.id.fBStylishWorkText3);

        backArrowImage = findViewById(R.id.back_arrow);


        toolbar.setText(STATUS_LIST);
        AdHelper.initializeAd(this);

        backArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int val = bundle.getInt("val", -1);
            showOnlySelectedLayout(val);
        }


//random color code

        List<CardView> cardViews = Arrays.asList(
                bestBagnlaCaptionText1, bestBagnlaCaptionText2, bestBagnlaCaptionText3, bestBagnlaCaptionText4,
                banglaStatusText1, banglaStatusText2, banglaStatusText3, banglaStatusText4,
                banglaCaptionText, banglaCaptionText2, banglaCaptionText3, banglaCaptionText4,
                fbStylishCommentsText, fbStylishCommentsText2, fbStylishCommentsText3, fbStylishCommentsText4,
                banglaCommentsText, banglaCommentsText2,
                fBStylishBioText, fBStylishBioText2, fBStylishBioText3, fBStylishBioText4,
                fBAttitudeBioText, fBAttitudeBioText2, fBAttitudeBioText3, fBAttitudeBioText4,
                fBStylishNameText, fBStylishNameText2, fBStylishNameText3, fBStylishNameText4,
                fBNameSymbolText, fBNameSymbolText2,
                symbolTextArtText, symbolTextArtText2,
                tiktokStylishBIoText, tiktokStylishBIoText2,
                groupNameIdeasText, groupNameIdeasText2, groupNameIdeasText3,
                fBStylishWorkText, fBStylishWorkText2, fBStylishWorkText3
        );
        for (CardView cardView : cardViews) {
            int color = getRandomColor();
            cardView.setCardBackgroundColor(color);
            cardView.setOnClickListener(this);
        }


        //onclick==============================================

        bestBagnlaCaptionText1.setOnClickListener(this);
        bestBagnlaCaptionText2.setOnClickListener(this);
        bestBagnlaCaptionText3.setOnClickListener(this);
        bestBagnlaCaptionText4.setOnClickListener(this);
        banglaStatusText1.setOnClickListener(this);
        banglaStatusText2.setOnClickListener(this);
        banglaStatusText3.setOnClickListener(this);
        banglaStatusText4.setOnClickListener(this);
        banglaCaptionText.setOnClickListener(this);
        banglaCaptionText2.setOnClickListener(this);
        banglaCaptionText3.setOnClickListener(this);
        banglaCaptionText4.setOnClickListener(this);
        fbStylishCommentsText.setOnClickListener(this);
        fbStylishCommentsText2.setOnClickListener(this);
        fbStylishCommentsText3.setOnClickListener(this);
        fbStylishCommentsText4.setOnClickListener(this);
        banglaCommentsText.setOnClickListener(this);
        banglaCommentsText2.setOnClickListener(this);
        fBStylishBioText.setOnClickListener(this);
        fBStylishBioText2.setOnClickListener(this);
        fBStylishBioText3.setOnClickListener(this);
        fBStylishBioText4.setOnClickListener(this);
        fBAttitudeBioText.setOnClickListener(this);
        fBAttitudeBioText2.setOnClickListener(this);
        fBAttitudeBioText3.setOnClickListener(this);
        fBAttitudeBioText4.setOnClickListener(this);
        fBStylishNameText.setOnClickListener(this);
        fBStylishNameText2.setOnClickListener(this);
        fBStylishNameText3.setOnClickListener(this);
        fBStylishNameText4.setOnClickListener(this);
        fBNameSymbolText.setOnClickListener(this);
        fBNameSymbolText2.setOnClickListener(this);
        symbolTextArtText.setOnClickListener(this);
        symbolTextArtText2.setOnClickListener(this);
        tiktokStylishBIoText.setOnClickListener(this);
        tiktokStylishBIoText2.setOnClickListener(this);
        groupNameIdeasText.setOnClickListener(this);
        groupNameIdeasText2.setOnClickListener(this);
        groupNameIdeasText3.setOnClickListener(this);
        fBStylishWorkText.setOnClickListener(this);
        fBStylishWorkText2.setOnClickListener(this);
        fBStylishWorkText3.setOnClickListener(this);


    }


    // Bio_two_activity ক্লাসের ভিতরে

    @Override
    public void onClick(View v) {
        Pair<String, Integer> data = itemMap.get(v.getId());
        if (data != null) {

            AdHelper.showAdIfNeeded(this,() -> {
                Bio_three_activity.BIO_THREE_TO0LBAR = data.first;
                Intent nextInt = new Intent(Bio_two_activity.this, Bio_three_activity.class);
                nextInt.putExtra("val", data.second);
                startActivity(nextInt);
            });

        }
    }


    private int getRandomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }


    private void showOnlySelectedLayout(int val) {
        List<View> allLayouts = Arrays.asList(
                bestBagnlaCaption, banglaStatusLn, banglaCaption,
                fbStylishComments, banglaComments, fBStylishBio,
                fBAttitudeBio, fBStylishName, fBNameSymbol,
                symbolTextArt, tiktokStylishBIo, groupNameIdeas,
                fBStylishWork
        );

        for (View layout : allLayouts) {
            layout.setVisibility(View.GONE);
        }

        switch (val) {
            case 21:
                banglaStatusLn.setVisibility(View.VISIBLE);
                break;
            case 22:
                fbStylishComments.setVisibility(View.VISIBLE);
                break;
            case 23:
                fBStylishBio.setVisibility(View.VISIBLE);
                break;
            case 24:
                fBStylishName.setVisibility(View.VISIBLE);
                break;
            case 25:
                fBStylishWork.setVisibility(View.VISIBLE);
                break;
            case 26:
                symbolTextArt.setVisibility(View.VISIBLE);
                break;
            case 27:
                tiktokStylishBIo.setVisibility(View.VISIBLE);
                break;
            case 28:
                bestBagnlaCaption.setVisibility(View.VISIBLE);
                break;
            case 29:
                fBNameSymbol.setVisibility(View.VISIBLE);
                break;
            case 30:
                fBAttitudeBio.setVisibility(View.VISIBLE);
                break;
            case 31:
                banglaComments.setVisibility(View.VISIBLE);
                break;
            case 32:
                banglaCaption.setVisibility(View.VISIBLE);
                break;
        }
    }


}