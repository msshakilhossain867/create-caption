package com.citomoltd.createyourstylishnameandbio;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.appbar.MaterialToolbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Bio_one_activity extends AppCompatActivity {

    ListView listView;
    MaterialToolbar toolbar;
    private FrameLayout adContainer;

    int imageArray[] = {R.drawable.mbg, R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5, R.drawable.bg_6, R.drawable.bg_7,
            R.drawable.bg_8, R.drawable.bg_9, R.drawable.bg_10, R.drawable.bg_11, R.drawable.bg_12, R.drawable.bg_13, R.drawable.bg_15, R.drawable.bg_16, R.drawable.bg_17, R.drawable.bg_18, R.drawable.bg_19, R.drawable.bg_20};


    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;
    public static String TOOLBAR_STATUS = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bio_one);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listViewItem1);
        adContainer = findViewById(R.id.adContainer);

        toolbar = findViewById(R.id.bioToolbar);
        toolbar.setTitle(TOOLBAR_STATUS);

        AdHelper.initializeAd(this);

        Bundle bundle = getIntent().getExtras();
        int val = bundle.getInt("val");
        if (val == 1) {
            ShortBio();
        } else if (val == 2) {
            ShortAboutBio();

        } else if (val == 3) {
            FbBio();
        } else if (val == 4) {
            FunBio();
        } else if (val == 5) {
            RomanticBio();
        } else if (val == 6) {
            ProfileBio();
        } else if (val == 7) {
            PotanorBio();
        } else if (val == 8) {
            FunFunBio();
        } else if (val == 9) {
            MySelfBio();
        } else if (val == 10) {
            CreativeBio();
        } else if (val == 18) {
            SmartFBBio();
        }


        StatusAdapter adapter = new StatusAdapter();
        listView.setAdapter(adapter);



        AdHelper.loadBannerAd(this, adContainer);


    }//on create method end========================================================

    private class StatusAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position); // এখানে null না দিয়ে আসল item রিটার্ন করা উচিত
        }

        @Override
        public long getItemId(int position) {
            return position; // সাধারণভাবে position ই id ধরে নেয়া যায়
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.list_status, parent, false);

                holder = new ViewHolder();
                holder.textView = convertView.findViewById(R.id.item_text);
                holder.copyCard = convertView.findViewById(R.id.copyButton);
                holder.download = convertView.findViewById(R.id.edit_query);
                holder.shareStatus = convertView.findViewById(R.id.shareStatus);
                holder.favorite = convertView.findViewById(R.id.favoriteId2);
                holder.randomColor = convertView.findViewById(R.id.bg_random_color);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            hashMap = arrayList.get(position);
            String valueSt = hashMap.get("status");

            holder.textView.setText(valueSt);

            // Copy to clipboard
            holder.copyCard.setOnClickListener(v -> copyToClipboard(valueSt));

            // Download as image
            holder.download.setOnClickListener(v -> {
                AdHelper.showAdIfNeeded(Bio_one_activity.this, () -> {
                    Bitmap bitmap = getBitmapFromView(holder.randomColor);

                    if (myPermissions()) {
                        saveImage(bitmap);
                    }
                });
            });


            // Share status
            holder.shareStatus.setOnClickListener(v -> {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, valueSt);
                sendIntent.setType("text/plain");
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent shareIntent = Intent.createChooser(sendIntent, "Share By");
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(shareIntent);
            });

            // Random background
            Random random = new Random();
            int imageBg = random.nextInt(imageArray.length);
            holder.randomColor.setBackgroundResource(imageArray[imageBg]);

            // Enable marquee animation if needed
            holder.shareStatus.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.shareStatus.setSelected(true);

            return convertView;
        }

        // Helper Method: View থেকে Bitmap তৈরি করার জন্য
        private Bitmap getBitmapFromView(View view) {
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmap;
        }

        class ViewHolder {
            TextView textView;
            Button copyCard, download, shareStatus;
            ImageView favorite;
            RelativeLayout randomColor;
        }
    }

    private void addStatus(String status) {
        hashMap = new HashMap<>();
        hashMap.put("status", status);
        arrayList.add(hashMap);
    }


    //FB bio===================================================1

    private void FbBio() {
        addStatus("Bio দেখে চরিত্র নির্ণয় করো না।\nএই বয়সে একটু মজা করি\nএছাড়া আর কিছুই না।");
        addStatus("অনুমতি ছাড়া প্রফালে ঘোড়া নিষেধ\nএটা কোন পার্ক না যখন তখন\nমন চাইলে ঘোরাঘুরি করবেন।");
        addStatus("কি প্রফাইল দেখতে এসেছেন?\nওরে কেউ বরণডালা নিয়ে আয়\nদেখ কে এসেছে আমার প্রফাইলে।");
        addStatus("সবার মতো হবো\nএটা কখনো হয়?\nআমার মতো আমি\nকারোর মতো নই।");
        addStatus("আমি কেরকম সেটা আমি জানি\nকেউ যদি আমাকে খারাপ ভাবে\nতাইতে আমার কিছু যায়-আসে না।");
        addStatus("বিশ্বাস করুন আমি এই পৃথিবীর\nসবচেয়ে সুদর্শন কার্টুন।");
        addStatus("এই যে এতোদিন পর\nতুমি আমার এলাকায়\nএবার দেখে ফেলেছি।");
        addStatus("Bio দেখে কারোর চরিত্র\nনির্ণয় করা যায় না।\nহয়তো বাস্তবে সে\nসবার চেয়ে আলাদা।");
        addStatus("একদিন সফল হবো ভেবে\nআজকাল বেঁচে আছি।");
        addStatus("এই চলতি জীবন ঘটনা বহুল\nদু-এক দিন ফাঁকে\nতুইতো পাবিনা আমায়\nআর আমিও পাবোনা তোকে।");
        addStatus("আমি এমনি\nহয়তো নিমিষে ভুলে যায়\nনইতো কখনোই না।");
        addStatus("আমি আমার বাবার\nরাজকন্যা\nভালবাসার মানুষটির\nরাজরানী।");
        addStatus("কে আমি?\nচিনলে ম্যাসেজ দাও\nনা চিনলে বাদ দাও।");
        addStatus("আমি কীট পতঙ্গের চেয়েও\nযথেষ্ট খারাপ\nতাই বলে কি আমার সাথে\nবন্ধুত্ব করবে না?");
        addStatus("সময়ের বন্ধনে জীবন চলে যেমন\nআমিও তার সাথে চলি তেমন।");
        addStatus("আমি জীবন যুদ্ধে\nএক অপরাজিত আহত\nসৈনিক যুদ্ধের ময়দানে\nযার কোন মূল্য নেই।");
        addStatus("ঘুঙুরের মতো বেজে চলি\nকখনো এর পায়ে\nকখনো তার পায়ে।");
        addStatus("সময় নেই তার জন্য\nযে আমায় ঘৃণা করে।\nব্যাস্ত আছি তাকে নিয়ে\nযে আমায় ভালবাসে");
        addStatus("জীবন আমার দিকে\nতাকিয়ে আছে\nআর আমি তোমার দিকে।");
        addStatus("যাহা বলিব সত্য বলিব\nবিপদে পরিলে মিথ্যা বলিব।");
    }


    //fun bio==================================2
    private void FunBio() {
        addStatus("আমাকে আর কাঁদিও না\nকারণ আমার চোখের জল\nমুছে দেওয়ার মতো কেউ নেই।");
        addStatus("বাস্তবে কারোর প্রিয়জন\nহতে পারিনি তবে প্রয়োজনে\nপ্রিয়জন হয়েছি বহুবার।");
        addStatus("চোখে জল এনে \nদেবার মানুষ পেয়েছি\nকিন্তু জল মুছে দেবার \nপায়নি কাওকে।");
        addStatus("আজ একটা গল্প বলি শোন\nআমার বিকল্প নেই কোন।");
        addStatus("লাশের শহরে আমি রাজা\nরানী আমার প্রিয় গাঁজা।");
        addStatus("পুরনো দিনের সময়\nসুযোগ পেলেই\nআমায় কাঁদায়।");
        addStatus("প্রেমে পড়ে গেলে\nপাশে থাকুন\nমনে জায়গা নাই।");
        addStatus("দয়া করিয়া পোস্ট দেখিয়া\nচরিত্র নির্ণয় করিবেন না\nএগুলো শুধু বিনোদনের জন্য।");
        addStatus("আমার চরিত্র জটিল থাকুক\nসহজ আমায় বুঝো না।");
        addStatus("প্রফাইল ঘেটে প্রেমে পড়লে\nকতৃপক্ষ দায়ী নয়।");
        addStatus("ক্রাশ তো খাবেন না\nতাহলে প্রফাইল ঘেটে\nকি হবে।");
        addStatus("একা থাকতে পারি\nতবে মাঝে মাঝে\nখুব বেশি একা লাগে।");
        addStatus("জীবনে ভাল থাকার জন্য\nহাসিটার খুব প্রয়োজন\nহোকনা সেটা অভিনয়।");
        addStatus("আমি বয়স দেখে সন্মান করি না\nআমি আচরণ দেখে সন্মান করি\nআপনার ব্যবহার আপনার পরিচয়।");
        addStatus("কারোর জন্য আশা করি না\nকারণ নতুন মানুষ পাশে পেলে\nসবাই আমাকে ভুলে যাবে।");
        addStatus("নিজের আঘাত গুলো\nকাউকে দেখাতে চায় না\nজানি আজকাল সবাই\nলবণ নিয়ে ঘোরে।");
        addStatus("কারো জন্য নয়\nনিজের জন্য\nপারফেক্ট আমি।");
        addStatus("বাপের রাজকন্যা\nমায়ের ছিচ কাঁদুনি\nভাইয়ের আল্লাদি\nওনার কলিজা\nএইতো আমি।");
        addStatus("ম্যাসেজ করে লাভ নেয়\nরিপ্লাই দিতে পারবো না।");
        addStatus("Welcome to my profile\nআসছেন যখন\nএক কাপ চা খেয়ে যাবেন।");
    }


    //romantic bio==========================================3
    private void RomanticBio() {
        addStatus("আমি হত্যা কারি\nআমার স্বপ্ন গুলোকে\nপ্রতিদিন ফেসবুকে\nহত্যা করি।");
        addStatus("যেটা চাই সেটা পাই না\nযেটা চাই না সেটাই পাই\nহুম এটাই আমার কপাল");
        addStatus("আঘাতটা সেখানেই পেয়েছি\nযেখানে তোমাকে রেখেছিলাম");
        addStatus("তোমার প্রেমে অন্ধ হতে চায়।\nকিন্তু ভয় লাগে\nযদি তুমি ভিক্ষা করতে পাঠিয়ে দাও।");
        addStatus("ক্রাশ খাবো\nমুগ্ধ হবো\nপ্রেমেও পড়বো\nতবু বলবোনা ভালবাসি।");
        addStatus("নিজের কষ্টের কথা\nচোনার লোক অনেক আছে\nকিন্তু বোঝার মত কেউ নেই।");
        addStatus("Bio দিয়ে কি হবে\nএকদিনতো মরেই জাবো।");
        addStatus("আমি ধোঁয়া তুলসীপাতা নয়,\nনিম পাতার মত তেঁতো,\nকিন্তু উপকারী।");
        addStatus("চোখের ঘুমটাতো সেই কেড়ে নিয়েছে\nযে এক সময় বলতো,\nঅনেক রাত হয়েছে বাবু\nএবার ঘুমিয়ে পড়ো।");
        addStatus("চোখের নিচে কালো দাগের রহস্য\nজানতে এসো না,\nপ্রেমে পড়ে যাবে।");
        addStatus("আমি কি জিনিস,\nজানলেই তুমি ফিনিস।\nইতি...কিউটের ডিব্বা");
        addStatus("অনেক বছর কেটে গেল\nদেখা নাইতো তার,\nকোন শহরে থাকে এখন\nআমার বাম পাঁজরের হাড়।");
        addStatus("আমি দস্যি\nআমি দুষ্টু\nআমি দজ্জাল\nআমি ঝগড়ুটে\nসুতরাং আমার থেকে দূরে থাক।");
        addStatus("কাউকে বোঝাতে পারবো না\nআমি কেমন,\nআমাকে জে যেমন মনে করে\nআমি তার কাছে তেমন।");
        addStatus("কিরে তুই আমার আইডিতে কেন\nতোর আইডি নাই\nযা ভাগ শালা।");
        addStatus("এক হাতে চোখের পানি মোছা\nআর এক হাতে ম্যাসেজ করার\nকষ্টটা তুমি বুঝবে না।");
        addStatus("অসাধারণ হতে চাই না\nসাধারনি ঠিক আছি\nকঠিন হলেও\nবাস্তব কেই ভালবাসি");
        addStatus("আমি সিঙ্গেল এতে কোন দুঃখ নেই\nকিন্তু আমার হবু বৌ\n\nএখনো কি সিঙ্গেল\nনাকি কারোর সাথে \nপ্রেম করছে।");
        addStatus("ফুচকা প্রেমি\nআইসক্রিম প্রেমি\nআর একজনের প্রেমিকা\nহুম তুমিই সেই নাম।");
        addStatus("আমার সাথে কথা বলতে চান\nতবে দরজার বাইরে\nমুখোশটা খুলে আসুন।");
    }


    //profile bio===================================
    private void ProfileBio() {
        addStatus("আমার তেমন কোন গুণ নেই\nযেটা লেখার মত,\nআমি সর্বদা চেষ্টা করি\nথাকে সকলের মনের মতো।");
        addStatus("যেচে কথা বলি বলে\nমূল্য হিন ভেবো না ।");
        addStatus("আমাকে আমার মতো\nথাকে দাউ\nআমি নিজেকে নিজের মতো\nগুছিয়ে নিয়েছি।");
        addStatus("আমার প্রেমিক বিরিয়ানি\nফুচকা আমার ভালোবাসা\nচকলেট আমার ইমোশনাল\nল্যাদ খাওয়া আমার ধর্ম।");
        addStatus("মিম ভালবাসতে ভালবাসতে\nজীবনটাই কখন মিম হয়ে গেছে\nসেটা ধরতে পারিনি।");
        addStatus("অনেক মন খারাপের মধ্যে দিয়েও\nশুধু বেঁচে আছি তোর অপেক্ষায় ।");
        addStatus("সুখ আর দুঃখ এই দুটোকে\nএডজাস্ট করার নামি জীবন।");
        addStatus("জানি স্বপ্ন সত্যি হয় না\nতবুও মন যে মানতে চায় না।");
        addStatus("আজ আমি দেখতে খারাপ বলে\nআমাকে কেউ একটুও ভালবাসে না।");
        addStatus("এখন কেউ I Love You বললে\nতাকে খুব বলতে ইচ্ছে করে\nকত দিনের জন্য অভিনয় করতে চাও।");
        addStatus("কিছু মানুষ কোন দিন\nকারোর প্রিয় হতে পারে না\nযেমন আমি।");
        addStatus("কোন অহংকার নেই\nতবে আত্মসম্মান টা\nএকটু বেশিই আছে।");
        addStatus("সত্যি তোর ভালবসাটা দারুন ছিল\nহঠাৎ শুরু হলো হঠাৎ শেষ।");
        addStatus("একা থাকাটা শিখে গেছি\nএখন শুধু ভালো থাকাটা\nশিখে নিতে হবে।");
        addStatus("ভাগ্য করে দু চারটে বন্ধু পেয়েছি\nতাদের সাথেই দিনটা\nভাল করে কেটে যায়।");
        addStatus("আমার তেমন কোন ইচ্ছে নেই\nশুধু মায়ের জন্য একটা বৌমা চায়।");
        addStatus("কারোর জন্য আশা করি না\nকারণ নতুন মানুষ পাশে পেলে\nসবাই আমাকে ভুলে যাবে।");
        addStatus("যেটা চাই সেটা পাই না\nযেটা চাই না সেটাই পাই\nহুম এটাই আমার কপাল\nহয়ত সৃষ্টিকর্তা আমাকে\nএকটু বেশিই ভালোবাসেন ।");
        addStatus("অল্প কথায় নিজের \nসম্পর্কে কিছু বললে \nসেটা অসমাপ্ত রয়ে যায়");
        addStatus("জীবন একটি সুন্দর গল্পঃ\nযেটা দুঃখ-বেদনা হাসি-আনন্দ \nআর ঘটনায় পরিপূর্ণ");
    }


    //pota nor bio++++++++++===========================

    private void PotanorBio() {
        addStatus("সৌন্দর্য আমার হৃদয়ে আছে\nprofile এ নয়");
        addStatus("অসাধারণ হতে চাই না\nসাধারণি ঠিক আছি\nকঠিন হলেও\nবাস্তব কেই ভালবাসি");
        addStatus("ফুচকা প্রেমি\nআইসক্রিম প্রেমি\nআর একজনের প্রেমিকা\nহুম তুমিই সেই নাম।");
        addStatus("আমি নিজে কে তা জানিনা,\nকারণ প্রতিবার নিজেকে \nদেখার জন্য আয়নার \nসাহায্য নিতে হয়");
        addStatus("আমি হাজার বন্ধুত্বের ইমারত চাইনা, \nআমার জন্য একটা বন্ধুই \nযথেষ্ঠ যার বাঁধন হবে অনেক শক্ত");
        addStatus("জীবন কোনো দিন থেমে থেকে না, \nযে কোনো ভাবে এটি চলতে থাকে");
        addStatus("ফেসবুকে সুন্দর বায়ো দিয়ে \nঅপরকে ইমপ্রেস করার \nকোনো ইচ্ছেই আমার নেই");
        addStatus("মাথার উপরে আকাশ,\nপায়ের নিচে মাটি,\nআর আমার মধ্যে আছি আমি");
        addStatus("আমি যতটা জানি\nতার চেয়ে কম বলতে \nবেশি পছন্দও করি");
        addStatus("ভালোবাসা দিলে তবেই \nভালোবাসা পাওয়া যায়");
        addStatus("একবার ভালোবেসে দেখো\nকথা দিচ্ছি ভুলতে পারবে না");
        addStatus("কোনো প্রত্যাশা নেই \nতাই হতাশাও নেই");
        addStatus("নতুন পরিচয় করতে\nপছন্দ করি");
        addStatus("পৃথিবীর কোনো কিছুই\nপারফেক্ট নয়");
        addStatus("শুরুটা সবসময়ই \nঅগোছালো হয়");
        addStatus("আমি আমার নিজের উপর বেশি ভরসা করি");
        addStatus("কথা বলি কম\nঅনুভব করি বেশি");
        addStatus("সব কিছু মেনে নেই কারণ আমি সরল বোকা নই");
        addStatus("আমার পরিচয় একটাই আমি মানুষ যার মান এবং হুস দুই আছে");
        addStatus("একে অপরকে ঘৃণা না করে \nএসো নতুন ভালোবাসার সৃষ্টি করি");
    }


    //fun funBio=================================
    private void FunFunBio() {
        addStatus("আমিই আমার বস\nআমাকে সাশন করার ক্ষমতা \nআমি ছাড়া কারোর নেই");
        addStatus("যারা আমাকে চেনেনা \nতাদের জন্য আমি\nভীষণ ভাবে দুঃখিত");
        addStatus("আমি নিজেকে অনেকটা \nপ্রকাশ করে ফেলেছি\nযা আমার জীবনের প্রথম ভুল");
        addStatus("এটা ঠিক যে আমার পকেটে \nবেশি টাকা থাকেনা তবে \nহৃদয়টা অনেক বড়ো \nযা টাকার কাছে হার মানে");
        addStatus("সফলতা বা ব্যর্থতার \nবিচার আমি করিনা\nসঠিক সিদ্ধান্ত স্থির করে\nএগিয়ে চলার নামই জীবন");
        addStatus("একটা নিজস্ব\nপরিচয় বানাতে চাই\nআর সেটি হলো সততা");
        addStatus("সফলতা আমার প্রাপ্তি এবং \nহেরে যাওয়া আমার শিক্ষা");
        addStatus("আমি আমার মতো করে\nনিজেকে প্রকাশ করতে \nসছন্দ্য বোধ করি\nকারণ অনুলিপির চেয়ে \nআসল বেশি মূল্যবান");
        addStatus("মিথ্যা মানুষ গুলোর \nকাছ থেকে অনেক কিছু \nসত্যির শিক্ষা পাওয়া যায়");
        addStatus("জীবনে তাকেই বিশ্বাস করো\nযে কোনো প্রতিদান চায় না");
        addStatus("প্রতিটা জিনিসকে \nপুরোপুরি গ্রহণ করুন\nতাহলেই আপনি এর থেকে \nপূর্ণ শিক্ষা নিতে পারবেন");
        addStatus("বাঘা মানুষও বিড়াল হয়ে যায়\nঅচেনা গলির কুকুরের সামনে");
        addStatus("মানুষ আগে চোখ দেখাতো\nরাগ দেখাতো কিন্তু এখনকার \nমানুষ শুধু Attitude দেখায়");
        addStatus("যখন তুমি নিজেকে \nকন্ট্রোল করতে শিখবে\nতখনই তুমি সত্যিকারের \nএকজন মানুষ হবে");
        addStatus("বালক আমি হাটিয়া বেড়াই\nঅবুঝ বালিকার খোঁজে\nকিন্তু দেখি আমিই অবুঝ\nবালিকাই বেশি বোঝে");
        addStatus("তোমার শহরে কোথাও আমি নেই\nঅথচ আমার পুরো শহরটাই তুমি \nতুমি আমার ব্যস্ততা আর\nআমি তোমার অবসর");
        addStatus("চোখের কান্না সবাই দেখে \nকিন্তু মনের কান্না কেউ দেখেনা");
        addStatus("যে অন্যকে কষ্ট দিতে পারেনা\nসে নিজেই বেশি কষ্ট পায়");
        addStatus("পরের উপকার করা ভালো\nকিন্তু নিজেকে পথে বসিয়ে নয়");
        addStatus("একজন বিশ্বাসী বন্ধু\nহাজারটা আত্মীয়ের সমান");
    }


    //my self bio=================================
    private void MySelfBio() {
        addStatus("সাধারনের বাহিরে অসাধারনের\nএকজন মানুষ Ami");
        addStatus("নিজের সম্পর্কে বলার মতো \nকোন যোগ্যতা অর্জন \nকরতে পারি নাই");
        addStatus("অতি বুদ্ধিমতি নারী\nসংসারের তরবারী");
        addStatus("যার ভিতরে ভয়\n সে বাইরে ভয় পায়।\n যার মনে মিথ্যা\n সেই মিথ্যাকে ভয় পায়।");
        addStatus("মূর্খদের মত কথা বলা থেকে\n আমি আল্লাহর আশ্রয় চাচ্ছি।");
        addStatus("আল্লাহ্\u200C, \nতুমি আমায় সিরাতুল মুস্তাকীমের\n হেদায়াত দান করো।");
        addStatus("আবারো\nশুন্য থেকে শুরু\n করতে হবে আমায়।");
        addStatus("প্রজাপতি মন খুশিতে\n এখন মেলেছেরে পাখনা");
        addStatus("বালিকা তোমার সুন্দর চুলে\n আমি যদি চুইংগাম লাগিয়ে দিই\n তুমি কি রাগ করবে?");
        addStatus("বালিকা একদিন তুমি হবে বুড়ী\n মেকাপ বক্সে থাকবে তখন\n পান আর সুপারি।।");
        addStatus("তোমার সুন্দরী মায়াবী চেহারায়\n টানা টানা পাখির মত চোখ, আর\n হাতে পায়ে কুকুরের মত নোখ।");
        addStatus("বালিকা আমি যদি তোমায়\n ঠ্যাস করে একখান চড় মারি\n তবে কি তুমি রাগ করবা?");
        addStatus("মেয়েরা সত্যি আজব\n তাকে যে ভালোবাসে\n তাকেই এরা কষ্ট দেয়।");
        addStatus("ওহে বালিকা সময় পেলে আশিস\n একদিন আমার শহরে,\n থাবরাইয়া ভালোবাসা শিখামু তোরে।");
        addStatus("বালিকা Tiktok না করে\n বাড়ির Tuktak কাজ করো\n বাড়ির লোক খুশি হবে।।");
        addStatus("মেয়েরা যখন ফুচকা খায়\n তখন মনে হয়\n একটা হাতি ১০ দিন কিছু না খেয়ে\n পুরো কলা গাছ পেয়েছে।");
        addStatus("মেয়েরা কখনো কলো ও গরীব\n ছেলেদের পছন্দ করে না।।");
        addStatus("মেয়েরা টাকার লোভে\n বুড়ো কাকুকে বিয়ে করে\n কিন্তু ছেলেরা টাকার জন্য\n বুড়ী কাকীমাকে বিয়ে করে না।");
        addStatus("বালিকা নিজেকে\n কিউটের ডিব্বা ভেবোনা\n ছোটবেলায় ডিব্বা দিয়ে\n কটকটি কিনে খেয়েছি।");
        addStatus("বালিকা হেয়ার স্টাইল করে কি হবে\n মাথায়তো সব উকুনে ভর্তি।");
    }


    //creative bio======================================

    private void CreativeBio() {
        addStatus("বালিকা আমাকে পটাতে হলে\n শুধু দুখান আইসক্রিম নিয়ে আসবে\n তাহলেই পটে যাবো।");
        addStatus("বালিকা একমুঠো ভালবাসা দিবি?\n আমি চুড়ি দিয়ে\n তোর হাত সাজিয়ে দবো।");
        addStatus("মেয়েরা মাল নয়\n প্রতিটি বাবার রাজকন্যা\n এবং ভাইয়ের কলিজ");
        addStatus("ওহে বালিকা লাল গোলাপটা যে\n শুকিয়ে গেল সেটা কখন দিবা?");
        addStatus("বালিকা বয়ফ্রেন্ড না আমি তোর\n জাস্ট ফ্রেন্ড হতে পারি কি?");
        addStatus("মেয়েরা বলে ছেলেরা বাজে\n তবে মেয়েরা কার জন্য সাজে?");
        addStatus("মেয়েরা সব কাজ সঠিক পাড়লেও\n bf নির্বাচনে ভুল করবেই।");
        addStatus("মেয়েরা বড্ড বকা\n তাইতো ঝগড়া করার সময়\n তারাই সবথেকে বেশি কাঁদে\n আর কষ্ট পায়।");
        addStatus("স্বর্গকে যে ঘরে নিয়ে আসে\n সে হলো- মেয়ে।\n আর ঘরকেই যে স্বর্গ বানিয়ে রাখে\n সে হলো- মা।");
        addStatus("নখ বড় হলে নখ \n কাটতে হয় আঙুল নয়,\n তেমনি সম্পর্কে ভুল\n বোঝাবুঝি তা সংশোধন\n করতে হয় বিচ্ছেদ নয়।");
        addStatus("শত রাগের পরেও\n যে মানুষটা আপনাকে বুঝে\n সে-ই আপনাকে প্রকৃত ভালোবাসে।");
        addStatus("কিছু গল্পের ইতি থাকেনা \n ~মাঝপথেই তার সমাপ্ত হয়...!");
        addStatus("-কিছু মানুষ!! \n -কখনো কারো আপন হতে পারে নাহ\n -যেমন আমি");
        addStatus("বিশ্বাস যখন তাঁর অস্তিত্ব \n খুঁজে বেড়ায় \n স্বপ্নরা তখন হাওয়ায় মিশে যায়");
        addStatus("আমার মাথা ব্যাথা করছে!\n সবাই ১টা করে নাপা এক্সট্রা খেয়ে\n Done লিখে দিন।");
        addStatus("");
        addStatus("৫ টাকায় ৫ জিবি\n মেয়াদ ৫ বছর\n অফার টি পেতে\n এখনই গাঁঞ্জা খান");
        addStatus("পৃথিবীতে আব্বু আম্মু একমাত্র আপন\n বাকি সবাই তো বিজ্ঞাপন");
        addStatus("মন তোরে বলি যত \n তুই চলেছিস তোরই মতো");
        addStatus("তোমার নিত্যাদিনের প্রার্থনায়, \n আমার মৃত্যু কামনা রেখো!");
        addStatus("আস্তে আস্তে ফুরিয়ে যাচ্ছে\n ~হায়াতে জিন্দগি!! \n ~হেলায় ফেলায় দিন কাটাঁচ্ছি\n ~না করে বন্দেগি");
    }


    //smart fb bio============================================
    private void SmartFBBio() {
        addStatus("বাবা মার আদর \nএর ছোট মেয়ে এন্ড\n বর এর ডঙ্গী বউ\n (pagli)");
        addStatus("আমি খুব সাধারন\n সাধা মাটা একজন \n মরতে পারি, বাচতে পারি\n কোনো দ্বীধা ছারাই");
        addStatus("গাল টেনে ছিরে দিমু \n যদি আমার জামাইরে\n কেউ মেসেজ দিস।");
        addStatus("ডিয়ার হাসবেন্ড।\n প্রতিরাতে ঘুমানোর সময়\n মাথায় হাত ভুলিয়ে দিতে হবে।");
        addStatus("এই দূরে থাকা\n এই তীব্র চাওয়া\n এই নীরব টান\n সবকিছু মিশিয়েই ভালোবাসি।");
        addStatus("থাকলে কাছে কে বা বুঝে\n হারিয়ে গেলে সবাই খোঁজে!");
        addStatus("শিকড়গুলি গভীর হলে বাতাসকে\n ভয় করার কোনও কারণ নেই।");
        addStatus("আমার রাগ গুলো = friends \n অভিমান গুলো = special someone \n ব্যথাগুলো = only me \n এটাই আমি");
        addStatus("Ex এর প্রোফাইল ঘুরে আসাও\n একধরণের শিক্ষা সফর\n আজকে Ex নাই বলে \nসেটা মিস করলাম");
        addStatus("মৃত্যু অনিবার্য,\n - তাই বাঁচতে চাই।\n অমর হলে হয়তো,\n - মরতে চাইতাম।");
        addStatus("আচ্ছা...!!! \nপ্রেমিকপুরুষ হওয়ার জন্য\n কি কি যোগ্যতা লাগে??");
        addStatus("আঁধার ভেঙ্গে আলো আসবেই\n ইনশাআল্লাহ");
        addStatus("ছেলেটি আজ হাসির পাত্র!\n কারন, তার বউ এর নাম হাসি!");
        addStatus("অতিরিক্ত রাগী মেয়েদের\n কপালে !\n - একটা শান্তশিষ্ট ধৈর্যশীল\n ছেলে থাকে !");
        addStatus("স্বপ্ন খুনের সাজা হলে\n ফেঁসে যেতাম অনেকে");
        addStatus("জীবন মানে \nঅনিশ্চিৎ ভ্রমণ");
        addStatus("দুনিয়াটা টাকা ওয়ালাদের\n হতে পারে \n কিন্তু পরকালটা ঈমানদারদের জন্য!!");
        addStatus("আমি ইচ্ছে করেই অগোছালো হই,\n - তুমি গুছিয়ে নিবে বলে।");
        addStatus("আমি ভুল করে কাঁধি\n ভুলে বাঁন্ধি ঘর\n আমার সমস্ত ভুলেরতো\n আমিই কারিগর");
        addStatus("ভাই আমার ফোনের \nএমবির চেয়ে লুঙ্গির\n গিট্টুর স্থায়িত্ব অনেক কম");
    }


    //short bio==================================================
    private void ShortBio() {
        addStatus("Forget every rule,\n enjoy life while you can");
        addStatus("I am who I am and\n that is just enough");
        addStatus("What matters is who\n I really am\n forget what you\n think you see");
        addStatus("I am neither a\n status nor a post\n you don’t have to\n like or love me");
        addStatus("Normal is overrated.\n Weird is good.");
        addStatus("I don’t move\n with negativity");
        addStatus("I don’t have to\n describe myself, do I?");
        addStatus("Always believe in yourself.\n If you don’t, who will?");
        addStatus("Whatever you do,\n be good at it.");
        addStatus("In the end,\n the love you take is equal\n to the love you make.");
        addStatus("Failure is a temporary\n detour and never a dead end");
        addStatus("While everyone was arguing,\n I did it");
        addStatus("Happy or sad,\n you are what you\n make out of life");
        addStatus("I am me,\n that’s the most\n important of it all");
        addStatus("Complicated or not,\n it’s yours to find out");
        addStatus("I only make jokes,\n don’t see me as one");
        addStatus("Having fun is key,\n obeying rules is\n just too boring");
        addStatus("You don’t have to like me\n just enjoy the space");
        addStatus("Breathe,\n that’s the only way\n to appreciate life");
        addStatus("Living life on its term\n is all that I do");
    }

    //Short about bio=====================================
    private void ShortAboutBio() {
        addStatus("Say sorry,\n it will free you on\n countless occasions");
        addStatus("No matter who\n or what you are,\n I hope you find\n peace right now");
        addStatus("Don’t let life\n knock you down,\n just breathe and believe\n it will all end well");
        addStatus("May the echoes of your\n laughter go right through\n your pains and give you\n succor from your worries");
        addStatus("I don’t believe in beliefs,\n I only accept what\n I know as values.");
        addStatus("Awesome? That I am.\n Perfect? Not at all,\n but welcome to my\n space anyway");
        addStatus("I don’t judge,\n I don’t condemn,\n I just enjoy every individual");
        addStatus("Allow me to pilot you\n through the world of\n fun and laughter");
        addStatus("I am not serious,\n take me as one\n at your own peril");
        addStatus("Let be known that nothing\n here is completely serious.\n Absolutely NOTHING");
        addStatus("If you remember me,\n I don’t care if everyone\n else forgets");
        addStatus("Sweet or sour,\n I can be whatever you like\n it’s your choice.");
        addStatus("I’m far from perfect but\n I’m a complete human.");
        addStatus("Come into the space and\n let every one of your\n terrors be erased");
        addStatus("You might want to leave\n the normal and embrace\n a bit of oddity.\n Let’s get you started");
        addStatus("Follow me first, \nyour mind can follow later");
        addStatus("You might want to follow me\n with your shock absorber.\n You’re so welcome");
        addStatus("Everyone needs a dosage \nof fun and laughter. Enjoy");
        addStatus("Don’t try hard to be serious,\n it will be disarmed here");
        addStatus("You can have it all, \n but only on here, of course");
    }


    //its new===================================

    private void copyToClipboard(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboardManager != null) {
            ClipData clipData = ClipData.newPlainText("Copied data", text);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Clipboard not available", Toast.LENGTH_SHORT).show();
        }
    }


    // share menu


//=========================================


    //permission==============================
    //permission==============================

    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permission -> {
                boolean allGranted = true;

                for (Boolean isGranted : permission.values()) {
                    if (!isGranted) {
                        allGranted = false;
                        break;
                    }
                }

                if (allGranted) {
                    // All is granted
                } else {
                    // All is not granted
                }

            });

    private boolean myPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            String[] permissions = new String[]{
                    android.Manifest.permission.READ_MEDIA_IMAGES,
            };

            List<String> permissionsTORequest = new ArrayList<>();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsTORequest.add(permission);
                }
            }

            if (!permissionsTORequest.isEmpty()) {
                String[] permissionsArray = permissionsTORequest.toArray(new String[0]);

                boolean shouldShowRationale = false;
                for (String permission : permissionsArray) {
                    if (shouldShowRequestPermissionRationale(permission)) {
                        shouldShowRationale = true;
                        break;
                    }
                }

                if (shouldShowRationale) {
                    new AlertDialog.Builder(this)
                            .setMessage("Please allow all permissions")
                            .setCancelable(false)
                            .setPositiveButton("YES", (dialogInterface, i) -> {
                                requestPermissionLauncher.launch(permissionsArray);
                            })
                            .setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss())
                            .show();
                } else {
                    requestPermissionLauncher.launch(permissionsArray);
                }

                return false; // ⛔ Permissions not granted yet

            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            };

            List<String> permissionsTORequest = new ArrayList<>();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsTORequest.add(permission);
                }
            }

            if (!permissionsTORequest.isEmpty()) {
                String[] permissionsArray = permissionsTORequest.toArray(new String[0]);

                boolean shouldShowRationale = false;
                for (String permission : permissionsArray) {
                    if (shouldShowRequestPermissionRationale(permission)) {
                        shouldShowRationale = true;
                        break;
                    }
                }

                if (shouldShowRationale) {
                    new AlertDialog.Builder(this)
                            .setMessage("Please allow all permissions")
                            .setCancelable(false)
                            .setPositiveButton("YES", (dialogInterface, i) -> {
                                requestPermissionLauncher.launch(permissionsArray);
                            })
                            .setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss())
                            .show();
                } else {
                    requestPermissionLauncher.launch(permissionsArray);
                }

                return false; // ⛔ Permissions not granted yet

            }
        }

        return true; // ✅ All permissions granted
    }
    // myPermissions end here ================


    // isStoragePermissionGranted end here ==================

    private void saveImage(Bitmap bitmap) {
        String filename = "Image-" + System.currentTimeMillis() + ".jpg";
        OutputStream fos;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+ (Scoped Storage)
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Status save");

            ContentResolver resolver = getContentResolver();
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            try {
                if (imageUri != null) {
                    fos = resolver.openOutputStream(imageUri);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    if (fos != null) fos.close();
                    Toast.makeText(this, "Download Done!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else {
            // Android 9 and below
            File directory = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES) + "/Status save");

            if (!directory.exists()) {
                directory.mkdirs();
            }

            File image = new File(directory, filename);

            try {
                fos = new FileOutputStream(image);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();

                // make image available in gallery
                MediaScannerConnection.scanFile(this, new String[]{image.getAbsolutePath()}, null, null);
                Toast.makeText(this, "Download Done!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    // saveImage end here ============


//==================================================


    @Override
    protected void onResume() {
        super.onResume();
        // Optionally, you can reload the banner ad when the activity resumes
        AdHelper.loadBannerAd(this, adContainer);
    }


}