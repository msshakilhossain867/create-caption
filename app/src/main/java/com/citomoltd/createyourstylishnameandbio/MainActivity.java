package com.citomoltd.createyourstylishnameandbio;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    GridView gridView;

    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    NavigationView navigationView;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        gridView = findViewById(R.id.recyclerview);
        drawerLayout = findViewById(R.id.drawerLayout);
        materialToolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);

        myPermissions();
        AdHelper.initializeAd(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (MainActivity.this, drawerLayout, materialToolbar, R.string.drawerLayout_close, R.string.drawerLayout_open);
        drawerLayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.homeId) {
                    Toast.makeText(MainActivity.this, "home............", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.privacyIdMenu) {
                    //code here-------------------------------------------
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/stylish-status-maker-privacy"));
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);

                } else if (item.getItemId() == R.id.shareMenu) {
                    // code here
                    ShareApp(MainActivity.this);
                    drawerLayout.closeDrawer(GravityCompat.START);

                } else if (item.getItemId() == R.id.moreApp) {
                    // code here==============
                    String developerName = "https://play.google.com/store/apps/developer?id=citomoltd";
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(developerName)));

                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(developerName)));

                    }
                    drawerLayout.closeDrawer(GravityCompat.START);

                } else if (item.getItemId() == R.id.ratingMenu) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);


                }
                return true;
            }
        });

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId()==R.id.ratingTool) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                    }

                }
                return true;
            }
        });
        setHashMap();

        MyAdapter adapter = new MyAdapter();
        gridView.setAdapter(adapter);

        // Handle back press using OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirm Exit")
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish(); // Close activity
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Do nothing
                            }
                        })
                        .show();
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View mView = inflater.inflate(R.layout.item_design, parent, false);

            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            LinearLayout cardView = mView.findViewById(R.id.cardView);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            TextView textView = mView.findViewById(R.id.myN);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            CardView mainBg = mView.findViewById(R.id.mainBg);

            hashMap = arrayList.get(position);
            String tittle = hashMap.get("tittle");
            textView.setText(tittle);


            //random color code
            //random color code
            Random random = new Random();
            int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
            mainBg.setCardBackgroundColor(color);


          /*  cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position==0){
                        //banglas stutatus
                        Bio_two_activity.STATUS_LIST=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_two_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",21);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);


                    }
                    else if (position==1) {
                        //short bio

                        Bio_one_activity.TOOLBAR_STATUS=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_one_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",1);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);

                    }
                    else if (position==2) {
                        //fb stylish  comments
                        Bio_two_activity.STATUS_LIST=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_two_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",22);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);
                    }
                    else if (position==3) {
                        //fb stylish bio
                        Bio_two_activity.STATUS_LIST=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_two_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",23);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);

                    }
                    else if (position==4) {
                        //fb stylish name
                        Bio_two_activity.STATUS_LIST=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_two_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",24);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);

                    }

                    else if (position==5) {
                        //fb stylish work5
                        Bio_two_activity.STATUS_LIST=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_two_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",25);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);

                    }
                    else if (position==6) {
                        //symbol txt art
                        Bio_two_activity.STATUS_LIST=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_two_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",26);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);

                    }
                    else if (position==7) {
                        //tiktok stylish bio
                        Bio_two_activity.STATUS_LIST=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_two_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",27);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);

                    } else if (position==8) {
                        //short about bio
                        Bio_one_activity.TOOLBAR_STATUS=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_one_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",2);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);


                    } else if (position==9) {
                        //FB বায়ো
                        Bio_one_activity.TOOLBAR_STATUS=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_one_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",3);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);


                    } else if (position==10) {
                        //েরা মজার বায়ো
                        Bio_one_activity.TOOLBAR_STATUS=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_one_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",4);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);


                    } else if (position==11) {
                        //রোমান্টিক সেরা বায়ো
                        Bio_one_activity.TOOLBAR_STATUS=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_one_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",5);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);


                    } else if (position==12) {
                        //প্রোফাইল বায়ো
                        Bio_one_activity.TOOLBAR_STATUS=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_one_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",6);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);


                    } else if (position==13) {
                        //পটানোর মতো বায়ো
                        Bio_one_activity.TOOLBAR_STATUS=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_one_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",7);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);


                    } else if (position==14) {
                        //মজার মজার বায়ো
                        Bio_one_activity.TOOLBAR_STATUS=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_one_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",8);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);


                    } else if (position==15) {
                        //জের সম্প্ররকে বায়ো
                        Bio_one_activity.TOOLBAR_STATUS=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_one_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",9);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);


                    } else if (position==16) {
                        //্রিয়েটিব বায়ো
                        Bio_one_activity.TOOLBAR_STATUS=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_one_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",10);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);


                    } else if (position==17) {
                        //স্মার্ট FB বায়ো
                        Bio_one_activity.TOOLBAR_STATUS=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_one_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",18);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);


                    } else if (position==18) {
                        //best bangla caption
                        Bio_two_activity.STATUS_LIST=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_two_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",28);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);

                    } else if (position==19) {
                        //Fb name symbols
                        Bio_two_activity.STATUS_LIST=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_two_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",29);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);

                    } else if (position==20) {
                        //Fb Attitude bio
                        Bio_two_activity.STATUS_LIST=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_two_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",30);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);

                    } else if (position==21) {
                        //bangla comments
                        Bio_two_activity.STATUS_LIST=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_two_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",31);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);

                    } else if (position==22) {
                        // bangla captions
                        Bio_two_activity.STATUS_LIST=tittle;
                        Intent nextInt=new Intent(MainActivity.this,Bio_two_activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("val",32);
                        nextInt.putExtras(bundle);
                        startActivity(nextInt);

                    }
                }
            });*/

            cardView.setOnClickListener(v ->{
                        AdHelper.showAdIfNeeded(MainActivity.this, new AdHelper.AdListener() {
                            @Override
                            public void onAdClosed() {
                                handleGridClick(position, tittle);
                                  }
                        });
                    }
                    );


            return mView;
        }
    }


    //hash map called-------------------------------
    //hash map called-------------------------------

    private void setHashMap() {
        String[] titles = {
                "Bangla Status", "Short Bio", "FB Stylish comments", "FB Stylish Bio",
                "FB Stylish name", "FB Stylish Work", "Symbol Text Art", "Tiktok Stylish Bio",
                "Short About Bio", "FB বায়ো", "সেরা মজার বায়ো", "রোমান্টিক সেরা বায়ো",
                "প্রোফাইল বায়ো", "পটানোর মতো বায়ো", "মজার মজার বায়ো", "নিজের সম্পর্কে বায়ো",
                "ক্রিয়েটিব বায়ো", "স্মার্ট FB বায়ো", "Best Bangla Caption", "FB Name Symbol ",
                "Fb Attitude Bio", "Bangla Comments", "Bangla Captions"
        };

        for (String title : titles) {
            HashMap<String, String> map = new HashMap<>();
            map.put("tittle", title);
            arrayList.add(map);
        }
    }

    private void handleGridClick(int position, String title) {
        Intent intent = null;
        Bundle bundle = new Bundle();

        switch (position) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                Bio_two_activity.STATUS_LIST = title;
                intent = new Intent(this, Bio_two_activity.class);
                bundle.putInt("val", getValForPosition(position));
                break;

            case 1:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                Bio_one_activity.TOOLBAR_STATUS = title;
                intent = new Intent(this, Bio_one_activity.class);
                bundle.putInt("val", getValForPosition(position));
                break;

            default:
                return;
        }

        if (intent != null) {
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


    private int getValForPosition(int position) {
        int[] values = {
                21, 1, 22, 23, 24, 25, 26, 27,
                2, 3, 4, 5, 6, 7, 8, 9,
                10, 18, 28, 29, 30, 31, 32
        };
        return values[position];
    }


    //share method
    private void ShareApp(Context context) {
        // code here======
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Download now : https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }






    /// ==============================================================
    //=========================================

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

            if (permissionsTORequest.isEmpty()) {
                // All permissions are already granted
                Toast.makeText(this, "All permissions are already granted", Toast.LENGTH_SHORT).show();


            } else {
                String[] permissionsArray = permissionsTORequest.toArray(new String[0]);
                boolean shouldShowRationale = false;

                for (String permission : permissionsArray) {
                    if (shouldShowRequestPermissionRationale(permission)) {
                        shouldShowRationale = true;
                        break;
                    }
                }

                if (shouldShowRationale) {
                    new androidx.appcompat.app.AlertDialog.Builder(this)
                            .setMessage("Please allow all permissions")
                            .setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    requestPermissionLauncher.launch(permissionsArray);
                                }
                            })

                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();

                } else {
                    requestPermissionLauncher.launch(permissionsArray);
                }


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

            if (permissionsTORequest.isEmpty()) {
                // All permissions are already granted
                Toast.makeText(this, "All permissions are already granted", Toast.LENGTH_SHORT).show();


            } else {
                String[] permissionsArray = permissionsTORequest.toArray(new String[0]);
                boolean shouldShowRationale = false;

                for (String permission : permissionsArray) {
                    if (shouldShowRequestPermissionRationale(permission)) {
                        shouldShowRationale = true;
                        break;
                    }
                }

                if (shouldShowRationale) {
                    new androidx.appcompat.app.AlertDialog.Builder(this)
                            .setMessage("Please allow all permissions")
                            .setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    requestPermissionLauncher.launch(permissionsArray);
                                }
                            })

                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();

                } else {
                    requestPermissionLauncher.launch(permissionsArray);
                }


            }


        }


        return true;
    } // myPermissions end here ================


    // isStoragePermissionGranted end here ==================
}