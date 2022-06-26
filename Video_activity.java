

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.vending.billing.IInAppBillingService;
import com.artemuzunov.darbukarhythms.R;
import com.artemuzunov.darbukarhythms.other.OnTaskComplete;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;
import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
import static android.webkit.WebSettings.LOAD_NO_CACHE;


public class Video_activity extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    //ui
    private static RecyclerView recyclerView;
    private static RecyclerView overlayrecView;
    private static Toolbar toolbar;
    private static SwipeRefreshLayout srf;
    //private static TextView cat_description;
    private static TextView vid_name;
    private static VideoPlayer videoPlayer;
    private static VideoController vcontr;
    private static RelativeLayout v_fr;
    private static RelativeLayout list_frame;
    private static ProgressBar vidp_bar;
    private static ProgressBar teacherp_bar;
    private static WebView teacher_wv;
    private static AppBarLayout appBarLayout;
    private static Purch_dialog purch_dialog;
    private static View root_layout;
    private static LinearLayout vid_item;
    private static CoordinatorLayout frameLayoutMainContent;
    //other
    private static int height;
    private static boolean overlay_bar = false;
    private static final String CATALOG = "catalog";
    private static final String VIDEO = "video";
    private static final String TEACHER = "teacher";
    private static String status_view = CATALOG;
    private static String prev_status_view = "";
    private static int curr_catID;
    private static ArrayList<Subscription> curr_sublist;
    private static String curr_catDes;
    private static int catPos;
    public static boolean bought = false;
    private static int vid_id;
    private static boolean video_flag = false;
    private static boolean overlay_flag = false;
    private static boolean clickable = false;
    private static Data_Adapter_Cat_List catAdapter;
    private static Data_Adapter_Video_List vidAdapter;
    private static ArrayList<Video> videos = new ArrayList<>();
    public static ArrayList<Category> categories = new ArrayList<>();
    private static ArrayList<String> user_subscriptions = new ArrayList<>();
    private static String teacher;
    private static String videopath = API_String.api_str + "FileModels/";
    private static String[] arr_qualities;
    private static int mCurrentPosition;
    private static int teacher_id;
    private static String encodedteacherHtml;
    private static Bundle prices = new Bundle();
    private static Bundle titles = new Bundle();
    private static Bundle descrs = new Bundle();
    private static ArrayList<Subscription> subscriptions;
    private static IInAppBillingService mService;
    private static ArrayList<String> responseList;
    private static ServiceConnection mServiceConn;
    final String ID = "user_id";
    private static String buf_status_view;
    private static boolean purchased = false;
    private int response;
    private int currMarg;
    private static boolean binded = false;
    private static ArrayList<Video> buf_list = new ArrayList<>();
    static int prev_pos = 0;
    private static boolean boughtOverlay = false;
    private static boolean boughtList = false;
    private static int overlayCatPos;
    private static int overlaycatID;
    private static String overlaycatDes;
    private static ArrayList<Subscription> overlaysublist = new ArrayList<>();
    private static boolean overlayClicked = false;
    private static boolean logged = false;

    private static ArrayList<String> blocked_user_subscriptions = new ArrayList<>();
    private static String boughtID = "";
    public static boolean inited = false;
    public static boolean get_flag = false;


    public void setMainView(CoordinatorLayout mainView) {
        frameLayoutMainContent = mainView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
        if (videoPlayer != null) {
            if (videoPlayer.isPlaying()) {
                overlay_flag = true;
                closePlayer();
            }
        }
        videos.clear();
        categories.clear();

        ((MainActivity) getContext()).setOnBackPressed(new MainActivity.OnBackPressedListener() {
            @Override
            public void onBack() {
                onBackPressed();
            }
        });

        ((MainActivity) getContext()).setOnLogInListener(new MainActivity.onLogInListener() {
            @Override
            public void onLogIn() {
                if ((status_view.equals(CATALOG)) || ((video_flag && !overlay_flag))) {
                    categories.clear();
                    videos.clear();
                    logged = true;
                    inited = false;
                    user_subscriptions.clear();
                    subscriptions.clear();
                    get_flag = false;
                    gettingSubInfo();
                } else {
                    //if(){
                    categories.clear();
                    videos.clear();
                    logged = true;
                    inited = false;
                    user_subscriptions.clear();
                    subscriptions.clear();
                    get_flag = false;
                    gettingSubInfo();
                    initVideos();
                }
            }
        });

        ((MainActivity) getContext()).setonLogOutListener(new MainActivity.onLogOutListener() {
            @Override
            public void onLogOut() {
                //status_view = CATALOG;
                categories.clear();
                videos.clear();
                bought = false;
                boughtList = true;
                boughtOverlay = true;

                user_subscriptions.clear();
                subscriptions.clear();
                inited = false;
                get_flag = false;

                //initCatalogs();

                overlay_flag = false;

                gettingSubInfo();
                if (video_flag) {
                    video_flag = false;
                    videoPlayer.setMediaController(null);
                    Animation mDeleteAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.delete);
                    mDeleteAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            v_fr.setVisibility(View.GONE);
                            videoPlayer.stopPlayback();
                            videoPlayer.setOnPreparedListener(null);
                            videoPlayer = null;
                        }
                    });
                    v_fr.startAnimation(mDeleteAnimation);
                }
            }
        });


        ((MainActivity) getContext()).setonPurchListener(new MainActivity.onPurchListener() {
            @Override
            public void onPurch(final String subID) {
                FetchSubData fetcher = new FetchSubData(subID, new OnTaskComplete() {
                    @Override
                    public void onTaskCompleted(String res) {
                        boughtID = subID;
                        if (overlayClicked) {
                            categories.get(overlayCatPos).setBought(true);
                        }
                        if (user_subscriptions != null)
                            user_subscriptions.add(boughtID);
                        if (!overlayClicked) {
                            boughtList = true;
                        } else {
                            boughtOverlay = true;
                        }
                        bought = true;
                        purchased = true;

                        onRefresh();
                    }
                });
                fetcher.execute();
            }
        });

        root_layout = inflater.inflate(R.layout.video_activity, container, false);
        srf = root_layout.findViewById(R.id.refresh);
        srf.setOnRefreshListener(this);
        CoordinatorLayout main_view = root_layout.findViewById(R.id.main);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) main_view.getLayoutParams();
        currMarg = params.topMargin;
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar = root_layout.findViewById(R.id.toolbar);
        recyclerView = root_layout.findViewById(R.id.univ_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // TODO Auto-generated method stub
                super.onScrolled(recyclerView, dx, dy);

            }

            int cur_pos = 0;


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.d("pos", String.valueOf(cur_pos));
                boolean req;
                req = linearLayoutManager.findFirstCompletelyVisibleItemPosition() <= 0;
                Log.d("pos", String.valueOf(req));
                srf.setEnabled(req);
            }
        });
        overlayrecView = frameLayoutMainContent.findViewById(R.id.overlay_list);
        videoPlayer = frameLayoutMainContent.findViewById(R.id.videoPlayer);
        videoPlayer.setGettingMetricsObject(new VideoPlayer.gettingMetrics() {
            @Override
            public void getMetrics(DisplayMetrics dm) {
                Video_activity.this.getMetrics(dm);
            }
        });

        list_frame = frameLayoutMainContent.findViewById(R.id.listframe);
        vidp_bar = frameLayoutMainContent.findViewById(R.id.videoProgressBar);
        v_fr = frameLayoutMainContent.findViewById(R.id.vframe);
        vid_name = frameLayoutMainContent.findViewById(R.id.video_title);
        get_flag = false;
        gettingSubInfo();
        //initCatalogs();

        }catch(Exception e){

            Log.e("Error_Darbuka_video",Log.getStackTraceString(e));
        }
        return root_layout;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState){
        try {
            super.onCreate(savedInstanceState);
            if (isOnline()) {
                video_flag = false;
                overlay_flag = false;
                status_view = CATALOG;

                ((MainActivity) getContext()).updateUI(6);
                get_flag = false;
                gettingSubInfo();

            } else {
                Toast.makeText(getActivity(), R.string.conn_err, Toast.LENGTH_LONG).show();
                ((MainActivity) getContext()).rhythms_show();
            }
        }catch(Exception e){
            Log.e("Error_Darbuka_video",Log.getStackTraceString(e));

        }
    }


    public void setAppBarLayout(AppBarLayout appBarLayout) {
        this.appBarLayout = appBarLayout;
    }


    private void gettingSubInfo() {
        if (isOnline()) {
            final ReceiveSubData sub_receiver = new ReceiveSubData(new OnTaskComplete() {
                @Override
                public void onTaskCompleted(String res) {
                    Log.d("1", "task completed");

                    mServiceConn = new ServiceConnection() {
                        @Override
                        public void onServiceConnected(ComponentName name, IBinder service) {
                            if (!get_flag) {
                                prices.clear();
                                get_flag = true;
                                Log.d("1", "purch service connected");
                                binded = true;

                                mService = IInAppBillingService.Stub.asInterface(service);
                                Bundle querySku = new Bundle();
                                ArrayList<String> skuList = new ArrayList<>();


                                for (Subscription sub : subscriptions) {
                                    if (!skuList.contains(sub.getSubscriptionId())) {
                                        skuList.add(sub.getSubscriptionId());
                                    }
                                }
                                if (skuList.size() != 0) {
                                    querySku.putStringArrayList("ITEM_ID_LIST", skuList);
                                } else {
                                    get_flag = false;
                                    gettingSubInfo();
                                }
                                Bundle skuDetails;

                                try {
                                    if (querySku.size() != 0) {
                                        skuDetails = mService.getSkuDetails(3, getContext().getPackageName(), "subs", querySku);
                                        response = skuDetails.getInt("RESPONSE_CODE");
                                        if (response == 0) {

                                            responseList = skuDetails.getStringArrayList("DETAILS_LIST");

                                            for (String thisResponse : responseList) {
                                                JSONObject object = new JSONObject(thisResponse);
                                                String sku = object.getString("productId");
                                                String price = object.getString("price");
                                                String title = object.getString("title");
                                                String descr = object.getString("description");
                                                prices.putString(sku, price);
                                                titles.putString(sku, title);
                                                descrs.putString(sku, descr);
                                            }
                                            if ((status_view.equals(CATALOG)) && (!inited)) {
                                                inited = true;
                                                categories.clear();
                                                initCatalogs();
                                            } else {
                                                checkSubs();
                                                ReceiveCatalogsData receiver = new ReceiveCatalogsData(new OnTaskComplete() {
                                                    @Override
                                                    public void onTaskCompleted(String response) {
                                                        if (user_subscriptions.size() != 0) {
                                                            for (int i = 0; i < categories.size(); i++) {
                                                                ArrayList<Subscription> subs = categories.get(i).getSubscriptions();
                                                                for (int j = 0; j < subs.size(); j++) {
                                                                    if (user_subscriptions.contains(subs.get(j).getSubscriptionId())) {
                                                                        categories.get(i).setBought(true);
                                                                        break;
                                                                    } else {
                                                                        categories.get(i).setBought(false);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                });
                                                receiver.execute();
                                            }
                                        }
                                    } else {
                                        get_flag = false;
                                        gettingSubInfo();
                                    }
                                } catch (Exception e) {
                                    Log.d("1", "Error!");
                                    skuDetails = null;
                                }

                            } else {
                                if ((status_view.equals(CATALOG)) && (!inited)) {
                                    inited = true;
                                    categories.clear();
                                    initCatalogs();
                                }
                            }
                        }

                        @Override
                        public void onServiceDisconnected(ComponentName name) {
                            mService = null;
                            Log.d("1", "service disconnected");
                            if (status_view.equals(CATALOG))
                                initCatalogs();
                        }

                        @Override
                        public void onBindingDied(ComponentName name) {
                        }

                    };
                    Thread threadLoadPurchase = new Thread() {
                        public void run() {

                            setPriority(Thread.MAX_PRIORITY);
                            Intent serviceIntent =
                                    new Intent("com.android.vending.billing.InAppBillingService.BIND");
                            serviceIntent.setPackage("com.android.vending");
                            getActivity().bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

                            Log.d("1", "service connection");
                        }
                    };
                    threadLoadPurchase.start();

                }
            });
            sub_receiver.execute();

        } else

        {
            Toast.makeText(getActivity(), R.string.conn_err, Toast.LENGTH_LONG).show();
            ((MainActivity) getContext()).rhythms_show();
        }
    }


    protected boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        } else {
            return true;
        }
    }


    public void onBackPressed() {


        if (video_flag && !overlay_flag) {
            shrinkPlayer();
            return;
        }


        if ((!video_flag) || ((video_flag) && (overlay_flag))) {
            if ((status_view == null) || (status_view.equals(CATALOG))) {
                if (getActivity() != null)
                    if (videos != null)
                        if (categories != null) {
                            if (videoPlayer != null) {
                                if (videoPlayer.isPlaying()) {
                                    closePlayer();
                                }
                            }
                            ((MainActivity) getContext()).rhythms_show();
                        }

            }

            if ((prev_status_view.equals(CATALOG)) && (status_view.equals(VIDEO))) {
                ((MainActivity) getContext()).setDrawerState(true);
                if (videos != null)
                    status_view = CATALOG;
                prev_status_view = "";
                initCatalogs();
                MainActivity.setToolbarTitle(R.string.title_activity_video);
            }
            if ((prev_status_view.equals(CATALOG) && (status_view.equals(TEACHER)))) {
                status_view = CATALOG;
                recyclerView.setVisibility(View.VISIBLE);
                teacher_wv.setVisibility(View.GONE);
                MainActivity.setToolbarTitle(R.string.title_activity_video);
            }

            if ((prev_status_view.equals(VIDEO) && (status_view.equals(TEACHER)))) {
                status_view = VIDEO;
                prev_status_view = CATALOG;
                recyclerView.setVisibility(View.VISIBLE);
                teacher_wv.setVisibility(View.GONE);
                MainActivity.setToolbarTitle(R.string.title_activity_video);
            }


        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onPause() {
        if (video_flag) {
            Log.d("pause", "paused");
            videoPlayer.pause();
            mCurrentPosition = videoPlayer.getCurrentPosition();
            videoPlayer.setVisibility(View.GONE);
            videoPlayer.stopPlayback();
        }
        super.onPause();
    }


    @Override
    public void onResume() {
        if (video_flag) {
            if (v_fr == null)
                v_fr = frameLayoutMainContent.findViewById(R.id.vframe);
            if (videoPlayer == null)
                videoPlayer = frameLayoutMainContent.findViewById(R.id.videoPlayer);

            v_fr.setVisibility(View.VISIBLE);
            videoPlayer.seekTo(mCurrentPosition);
            final ProgressBar p_bar = frameLayoutMainContent.findViewById(R.id.videoProgressBar);
            p_bar.setVisibility(View.VISIBLE);
            videoPlayer.setVisibility(View.VISIBLE);
            videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    p_bar.setVisibility(View.GONE);
                    Log.d("pause", "resume");
                    if (overlay_flag) {
                        videoPlayer.setMediaController(null);
                    }
                    videoPlayer.start();
                }
            });
        }
        super.onResume();
    }


    private void checkSubs() {
        SharedPreferences auth_info = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userID = auth_info.getString(ID, "");
        if ((isOnline()) && (!userID.equals(""))) {
            user_subscriptions.clear();
            if (mService != null) {
                try {
                    Bundle ownedItems = mService.getPurchases(3, getActivity().getPackageName(), "subs", null);
                    response = ownedItems.getInt("RESPONSE_CODE");
                    if (response == 0) {
                        ArrayList<String> ownedSkus =
                                ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                        ArrayList<String> purchaseDataList =
                                ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                        ArrayList<String> signatureList =
                                ownedItems.getStringArrayList("INAPP_DATA_SIGNATURE");
                        String continuationToken =
                                ownedItems.getString("INAPP_CONTINUATION_TOKEN");

                        for (int i = 0; i < purchaseDataList.size(); ++i) {
                            user_subscriptions.add(ownedSkus.get(i));
                        }

                    }
                    if (user_subscriptions.size() != 0) {
                        ReceiveUserData receiver = new ReceiveUserData(new OnTaskComplete() {
                            @Override
                            public void onTaskCompleted(String response) {

                            }
                        });
                        receiver.execute();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private void initCatalogs() {
        final ProgressBar progressBar = root_layout.findViewById(R.id.progressBarList);
        if (isOnline()) {
            if (categories.size() == 0) {
                checkSubs();
                ReceiveCatalogsData receiver = new ReceiveCatalogsData(new OnTaskComplete() {
                    @Override
                    public void onTaskCompleted(String response) {
                        progressBar.setVisibility(View.GONE);
                        DisplayMetrics dm = new DisplayMetrics();
                        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                        if (user_subscriptions.size() != 0) {
                            for (int i = 0; i < categories.size(); i++) {
                                ArrayList<Subscription> subs = categories.get(i).getSubscriptions();
                                for (int j = 0; j < subs.size(); j++) {
                                    if (user_subscriptions.contains(subs.get(j).getSubscriptionId())) {
                                        categories.get(i).setBought(true);
                                        break;
                                    } else {
                                        categories.get(i).setBought(false);
                                    }
                                }
                            }
                        } else {
                            for (int i = 0; i < categories.size(); i++) {
                                categories.get(i).setBought(false);
                            }
                        }
                        catAdapter = new Data_Adapter_Cat_List(getActivity(), categories, dm);
                        if (!video_flag || overlay_flag) {
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(catAdapter);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                    LinearLayoutManager.VERTICAL);
                            dividerItemDecoration.setDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.divider));
                            recyclerView.addItemDecoration(dividerItemDecoration);

                        } else {
                            overlayrecView.setVisibility(View.VISIBLE);
                            overlayrecView.setAdapter(catAdapter);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(overlayrecView.getContext(),
                                    LinearLayoutManager.VERTICAL);
                            dividerItemDecoration.setDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.divider));
                            overlayrecView.addItemDecoration(dividerItemDecoration);
                        }
                        catAdapter.setOnPreparedListener(new Data_Adapter_Cat_List.OnPreparedListener() {
                            @Override
                            public void onPrepare() {
                                vid_item = (LinearLayout) catAdapter.getView();
                                vid_item.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onItemClick(v);
                                    }
                                });
                            }
                        });
                    }
                });
                receiver.execute();
            } else {
                catAdapter = null;
                progressBar.setVisibility(View.GONE);
                DisplayMetrics dm = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                catAdapter = new Data_Adapter_Cat_List(getActivity(), categories, dm);
                if (!video_flag || overlay_flag) {
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(catAdapter);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                            LinearLayoutManager.VERTICAL);
                    dividerItemDecoration.setDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.divider));
                    recyclerView.addItemDecoration(dividerItemDecoration);
                } else {
                    overlayrecView.setVisibility(View.VISIBLE);
                    overlayrecView.setAdapter(catAdapter);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(overlayrecView.getContext(),
                            LinearLayoutManager.VERTICAL);
                    dividerItemDecoration.setDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.divider));
                    overlayrecView.addItemDecoration(dividerItemDecoration);
                }
                catAdapter.setOnPreparedListener(new Data_Adapter_Cat_List.OnPreparedListener() {
                    @Override
                    public void onPrepare() {
                        vid_item = (LinearLayout) catAdapter.getView();
                        vid_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onItemClick(v);
                            }
                        });
                    }
                });
            }
        } else {
            Toast.makeText(getActivity(), R.string.conn_err, Toast.LENGTH_LONG).show();
            ((MainActivity) getContext()).rhythms_show();
        }
    }

    private void initVideos() {
        if (isOnline()) {
            recyclerView.setVisibility(View.GONE);
            final ProgressBar progressBar = root_layout.findViewById(R.id.progressBarList);
            progressBar.setVisibility(View.VISIBLE);
            if (videos.size() == 0) {
                ReceiveVideosData receiver = new ReceiveVideosData(new OnTaskComplete() {
                    @Override
                    public void onTaskCompleted(String response) {
                        progressBar.setVisibility(View.GONE);
                        DisplayMetrics metrics = new DisplayMetrics();
                        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

                        vidAdapter = new Data_Adapter_Video_List(getActivity(), videos, metrics, bought, false, Parser.parsingManager(curr_catDes, getActivity()));
                        recyclerView.setAdapter(vidAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                LinearLayoutManager.VERTICAL);
                        dividerItemDecoration.setDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.divider));
                        recyclerView.addItemDecoration(dividerItemDecoration);
                        clickable = true;
                        vidAdapter.setOnPreparedListener(new Data_Adapter_Video_List.OnPreparedListener() {
                            @Override
                            public void onPrepare() {
                                vid_item = (LinearLayout) vidAdapter.getView();
                                vid_item.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onItemClick(v);
                                    }
                                });
                            }
                        });
                    }
                });
                receiver.execute();
            } else {
                progressBar.setVisibility(View.GONE);
                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                vidAdapter = new Data_Adapter_Video_List(getActivity(), videos, metrics, bought, false, Parser.parsingManager(curr_catDes, getActivity()));
                recyclerView.setAdapter(vidAdapter);
                recyclerView.setVisibility(View.VISIBLE);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        LinearLayoutManager.VERTICAL);
                dividerItemDecoration.setDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.divider));
                recyclerView.addItemDecoration(dividerItemDecoration);
                vidAdapter.setOnPreparedListener(new Data_Adapter_Video_List.OnPreparedListener() {
                    @Override
                    public void onPrepare() {
                        vid_item = (LinearLayout) vidAdapter.getView();
                        vid_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onItemClick(v);
                            }
                        });
                    }
                });
            }
        } else {
            Toast.makeText(getActivity(), R.string.conn_err, Toast.LENGTH_LONG).show();
            ((MainActivity) getContext()).rhythms_show();
        }
    }

    public static void dialogDismiss() {
        if (purch_dialog != null)
            purch_dialog.dismiss();
    }

    @SuppressLint("ClickableViewAccessibility")
    @SuppressWarnings("unchecked")
    public void onItemClick(View view) {
        switch (status_view) {
            case CATALOG: {
                ((MainActivity) getContext()).setDrawerState(false);
                //((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                MainActivity.setExpanded(true, true);
                ((MainActivity) getActivity()).getSupportActionBar().show();
                if ((!video_flag) || (overlay_flag)) {
                    ((MainActivity) getContext()).setDrawerState(false);
                    MainActivity.setExpanded(true, true);
                    ((MainActivity) getActivity()).getSupportActionBar().show();
                    status_view = VIDEO;
                    videos.clear();
                    status_view = VIDEO;
                    prev_status_view = CATALOG;
                    Data_Adapter_Cat_List.ViewHolder vh = (Data_Adapter_Cat_List.ViewHolder) recyclerView.getChildViewHolder(view);
                    curr_catID = vh.id;
                    //curr_subID = vh.subID;
                    curr_sublist = vh.subscriptions;
                    curr_catDes = vh.description;
                    //((MainActivity)getActivity()).getSupportActionBar().setTitle(vh.cat_name);
                    MainActivity.setToolbarTitle(vh.cat_name);
                    catPos = vh.position;
                    boughtList = vh.bought;
                    bought = boughtList;
                    clickable = false;
                    initVideos();
                } else {
                    onItemClick(view);
                    status_view = CATALOG;
                }

            }
            break;
            case VIDEO: {
                if (isOnline()) {
                    Data_Adapter_Video_List.ViewHolder holder;
                    Log.d("1", "clickable=" + clickable);
                    if (clickable) {
                        if ((video_flag) && (!overlay_flag)) {
                            bought = boughtOverlay;
                            overlayClicked = true;
                            holder = (Data_Adapter_Video_List.ViewHolder) overlayrecView.getChildViewHolder(view);

                        } else {
                            overlayClicked = false;
                            buf_status_view = status_view;
                            status_view = VIDEO;

                            holder = (Data_Adapter_Video_List.ViewHolder) recyclerView.getChildViewHolder(view);
                        }
                        if (holder.clickable) {
                            CoordinatorLayout main_view = root_layout.findViewById(R.id.main);
                            FrameLayout.LayoutParams pars = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
                            pars.topMargin = 0;
                            main_view.setLayoutParams(pars);
                            videoPlayer = frameLayoutMainContent.findViewById(R.id.videoPlayer);
                            if ((holder.free) || (bought)) {
                                if (!overlayClicked) {
                                    if (overlayCatPos == catPos)
                                        overlaysublist.addAll(curr_sublist);
                                    buf_list.clear();
                                    buf_list.addAll(videos);
                                    boughtOverlay = bought;
                                    overlaycatDes = curr_catDes;
                                    overlaycatID = curr_catID;
                                    overlayCatPos = catPos;
                                }

                                video_flag = true;
                                MainActivity.setExpanded(false, true);
                                ((MainActivity) getActivity()).getSupportActionBar().hide();
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                                MainActivity.setExpanded(false, true);
                                ((MainActivity) getActivity()).getSupportActionBar().hide();
                                status_view = VIDEO;
                                recyclerView.setVisibility(View.GONE);
                                vid_name.setText(holder.title);
                                videoPlayer.stopPlayback();
                                videoPlayer.setVisibility(View.GONE);

                                clickable = false;
                                overlay_flag = false;
                                vid_id = holder.id;
                                //RelativeLayout rl = root_layout.findViewById(R.id.vp_fr);
                                RelativeLayout rl = frameLayoutMainContent.findViewById(R.id.vp_fr);
                                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.MATCH_PARENT,
                                        RelativeLayout.LayoutParams.WRAP_CONTENT
                                );
                                rl.setLayoutParams(p);
                                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.MATCH_PARENT,
                                        RelativeLayout.LayoutParams.MATCH_PARENT
                                );

                                videoPlayer.setSystemUiVisibility(0);

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                                    layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
                                    layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                                    layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                } else {
                                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                                }
                                videoPlayer.setLayoutParams(layoutParams);

                                arr_qualities = holder.qualities;
                                vcontr = new VideoController(getActivity(), arr_qualities);

                                VideoController.choise = 0;
                                String q = arr_qualities[0];
                                videoPlayer.setVideoPath(videopath + vid_id + "/q/" + q);
                                CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.gravity = Gravity.TOP;
                                v_fr.setLayoutParams(params);

                                //final ProgressBar p_bar = root_layout.findViewById(R.id.videoProgressBar);
                                final ProgressBar p_bar = frameLayoutMainContent.findViewById(R.id.videoProgressBar);

                                v_fr.setVisibility(View.VISIBLE);
                                list_frame.setVisibility(View.VISIBLE);

                                final DisplayMetrics metrics = new DisplayMetrics();
                                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                                final Data_Adapter_Video_List ovvidAdapter;
                                if (buf_list.size() != 1) {
                                    ArrayList<Video> buf = new ArrayList<>();
                                    for (int i = 0; i < buf_list.size(); i++) {
                                        if ((buf_list.get(i) != null) && (buf_list.get(i).getId() != vid_id))
                                            buf.add(buf_list.get(i));
                                    }
                                    ovvidAdapter = new Data_Adapter_Video_List(getActivity(), buf, metrics, bought, true, null);
                                } else {
                                    ovvidAdapter = new Data_Adapter_Video_List(getActivity(), buf_list, metrics, bought, true, null);
                                }
                                overlayrecView.setAdapter(ovvidAdapter);
                                RelativeLayout.LayoutParams parameters = (RelativeLayout.LayoutParams) overlayrecView.getLayoutParams();

                                overlayrecView.setVisibility(View.VISIBLE);
                                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(overlayrecView.getContext(),
                                        LinearLayoutManager.VERTICAL);
                                dividerItemDecoration.setDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.divider));
                                overlayrecView.addItemDecoration(dividerItemDecoration);

                                ovvidAdapter.setOnPreparedListener(new Data_Adapter_Video_List.OnPreparedListener() {
                                    @Override
                                    public void onPrepare() {
                                        vid_item = (LinearLayout) ovvidAdapter.getView();
                                        vid_item.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                onItemClick(v);
                                            }
                                        });
                                    }
                                });
                                p_bar.setVisibility(View.VISIBLE);
                                videoPlayer.setVisibility(View.VISIBLE);

                                videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        clickable = true;
                                        videoPlayer.setDm(metrics);
                                        ImageView iv = frameLayoutMainContent.findViewById(R.id.iv);
                                        videoPlayer.setIv(iv);
                                        videoPlayer.setAlpha(1.0f);
                                        videoPlayer.setBackgroundColor(Color.TRANSPARENT);
                                        videoPlayer.setMediaController(vcontr);
                                        videoPlayer.setZOrderOnTop(false);
                                        videoPlayer.start();
                                        p_bar.setVisibility(View.GONE);
                                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                                        onTouchListenerSet();
                                    }
                                });
                            } else {
                                SharedPreferences auth_info = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                String userID = auth_info.getString(ID, "");
                                //if (!userID.equals("")) {
                                if ((prices.size() != 0) && (descrs.size() != 0) && (titles.size() != 0)) {
                                    purch_dialog = new Purch_dialog();
                                    Bundle bundle = new Bundle();
                                    if (!overlayClicked) {
                                        bundle.putParcelableArrayList("subs", categories.get(catPos).getSubscriptions());
                                    } else {
                                        bundle.putParcelableArrayList("subs", categories.get(overlayCatPos).getSubscriptions());
                                    }
                                    bundle.putString("status", "buy");
                                    bundle.putBoolean("video_dialog", true);
                                    purch_dialog.setOnlogInFinishedListener(new Purch_dialog.OnlogInFinishedListener() {
                                        @Override
                                        public void success(boolean isLog, ArrayList<String> subscriptions) {
                                            if (isLog) {
                                                logged = true;
                                                if (!overlayClicked) {
                                                    boughtList = true;
                                                } else {
                                                    boughtOverlay = true;
                                                }
                                                for (int i = 0; i < categories.size(); i++) {
                                                    Category category = categories.get(i);
                                                    for (int j = 0; j < category.getSubscriptions().size(); j++) {
                                                        if (!blocked_user_subscriptions.contains(category.getSubscriptions().get(j).getSubscriptionId())) {
                                                            categories.get(i).setBought(true);
                                                            break;
                                                        }
                                                    }
                                                }
                                                blocked_user_subscriptions.clear();
                                                blocked_user_subscriptions.addAll(subscriptions);
                                                bought = true;
                                                onRefresh();
                                                purch_dialog.dismiss();
                                            }
                                        }
                                    });
                                    purch_dialog.setArguments(bundle);

                                    purch_dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

                                    purch_dialog.show(getActivity().getSupportFragmentManager(), "purch_dialog");
                                } else {
                                    AlertDialog.Builder builder;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                                    } else {
                                        builder = new AlertDialog.Builder(getActivity());
                                    }
                                    builder.setTitle(R.string.google_error_title)
                                            .setMessage(R.string.google_play_auth_needed)
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // continue with delete
                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.conn_err, Toast.LENGTH_LONG).show();
                    //getActivity().finish();
                    ((MainActivity) getContext()).rhythms_show();
                }
            }
            break;
        }
    }

    public static void stopPlayer() {
        if (status_view.equals(VIDEO) || (video_flag)) {
            video_flag = false;
            overlay_flag = false;
            if (videoPlayer.isPlaying()) {
                videoPlayer.stopPlayback();
                videoPlayer.setVisibility(View.GONE);
            }
        }
    }


    private void shrinkPlayer() {
        status_view = buf_status_view;
        final DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        overlay_flag = true;
        list_frame.setVisibility(View.GONE);
        videoPlayer.setMediaController(null);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Animation mShrinkAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shrink);
        mShrinkAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                vcontr.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getActivity().getWindow().getDecorView().setSystemUiVisibility(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                MainActivity.setExpanded(true);
                ((MainActivity) getActivity()).getSupportActionBar().show();
                recyclerView.setVisibility(View.VISIBLE);
                CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.BOTTOM | Gravity.END;
                if (!(metrics.widthPixels > metrics.heightPixels)) {
                    params.rightMargin = 20;
                    params.bottomMargin = 50;
                    params.width = (int) (metrics.widthPixels * .5);
                    params.height = (int) (metrics.heightPixels * .2);
                } else {
                    params.rightMargin = 20;
                    params.bottomMargin = 50;
                    double pers_w = metrics.widthPixels * 0.7;
                    double pers_h = metrics.heightPixels * 0.7;
                    params.width = (int) (metrics.widthPixels - pers_w);
                    params.height = (int) (metrics.heightPixels - pers_h);
                }
                v_fr.setLayoutParams(params);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v_fr.startAnimation(mShrinkAnimation);

    }


    @SuppressLint("ClickableViewAccessibility")
    private void onTouchListenerSet() {
        videoPlayer.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            @Override
            public void onSwipeBottom() {
                final DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                if (!overlay_flag && (metrics.widthPixels < metrics.heightPixels)) {
                    bought = boughtList;
                    shrinkPlayer();
                    if (purchased) {
                        if (status_view.equals(VIDEO)) {
                            for (int i = 0; i < categories.get(catPos).getSubscriptions().size(); i++) {
                                if (((user_subscriptions.contains(categories.get(catPos).getSubscriptions().get(i).getSubscriptionId())))) {
                                    bought = true;
                                    boughtList = true;
                                    onRefresh();
                                    purchased = false;
                                    break;
                                }
                            }
                            onRefresh();
                            purchased = false;
                        }else{
                        onRefresh();
                        purchased = false;
                        }
                    }


                    if (logged) {
                        if (status_view.equals(VIDEO)) {
                            for (int i = 0; i < categories.get(catPos).getSubscriptions().size(); i++) {
                                if (((user_subscriptions.contains(categories.get(catPos).getSubscriptions().get(i).getSubscriptionId())))) {
                                    bought = true;
                                    boughtList = true;
                                    onRefresh();
                                    logged = false;
                                    break;
                                }
                            }
                            onRefresh();
                            logged = false;
                        } else {
                            onRefresh();
                            logged = false;
                        }
                    }
                }
            }

            @Override
            public void onSwipeTop() {

                if (overlay_flag) {

                    overlay_flag = false;
                    buf_status_view = status_view;
                    status_view = VIDEO;
                    bought = boughtOverlay;
                    if (purchased) {
                        for (int i = 0; i < categories.get(overlayCatPos).getSubscriptions().size(); i++) {
                            if (((user_subscriptions.contains(categories.get(overlayCatPos).getSubscriptions().get(i).getSubscriptionId())))) {
                                bought = true;
                                boughtOverlay = true;
                                onRefresh();
                                purchased = false;
                                break;
                            }

                        }
                        onRefresh();
                        purchased = false;
                    }

                    if (logged) {
                        for (int i = 0; i < categories.get(overlayCatPos).getSubscriptions().size(); i++) {
                            if (((user_subscriptions.contains(categories.get(overlayCatPos).getSubscriptions().get(i).getSubscriptionId())))) {
                                bought = true;
                                boughtOverlay = true;
                                onRefresh();
                                logged = false;
                                break;
                            }
                        }
                        onRefresh();
                        logged = false;
                    }

                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    Animation mExpandAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.expand);
                    mExpandAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                            vcontr.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            MainActivity.setExpanded(false);
                            ((MainActivity) getActivity()).getSupportActionBar().hide();
                            CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            v_fr.setLayoutParams(params);
                            videoPlayer.setMediaController(vcontr);
                            list_frame.setVisibility(View.VISIBLE);
                        }
                    });
                    v_fr.startAnimation(mExpandAnimation);
                }
            }

            @Override
            public void onSwipeLeft() {
                closePlayer();
            }
        });
    }

    private void closePlayer() {
        if (overlay_flag) {
            video_flag = false;
            overlay_flag = false;
            boughtOverlay = false;
            videoPlayer.setMediaController(null);
            Animation mDeleteAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.delete);
            mDeleteAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    v_fr.setVisibility(View.GONE);
                    videoPlayer.stopPlayback();
                    videoPlayer.setOnPreparedListener(null);
                    videoPlayer = null;

                }
            });
            v_fr.startAnimation(mDeleteAnimation);
        }
    }


    public void getMetrics(DisplayMetrics dm) {
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //RelativeLayout rl = root_layout.findViewById(R.id.vp_fr);
        RelativeLayout rl = frameLayoutMainContent.findViewById(R.id.vp_fr);
        DisplayMetrics m = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(m);
        videoPlayer.setDm(m);
        if (newConfig.orientation == ORIENTATION_PORTRAIT) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            vcontr.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            rl.setLayoutParams(p);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
                layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            } else {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            }
            videoPlayer.setLayoutParams(layoutParams);
            list_frame.setVisibility(View.VISIBLE);
        } else {
            if (vcontr.isShowing()) {
                vcontr.hide();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_IMMERSIVE
                                // Set the content to appear under the system bars so that the
                                // content doesn't resize when the system bars hide and show.
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                // Hide the nav bar and status bar
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN);

            } else {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                // Hide the nav bar and status bar
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN);

            }

            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );
            rl.setLayoutParams(p);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            videoPlayer.setLayoutParams(layoutParams);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
            list_frame.setVisibility(View.GONE);
        }
    }


    @Override
    public void onRefresh() {
        if (isOnline()) {
            while (recyclerView.getItemDecorationCount() > 0) {
                recyclerView.removeItemDecorationAt(0);
            }
            switch (status_view) {
                case CATALOG: {
                    categories.clear();
                    checkSubs();
                    ReceiveCatalogsData receiver = new ReceiveCatalogsData(new OnTaskComplete() {
                        @Override
                        public void onTaskCompleted(String response) {
                            srf.setRefreshing(false);
                            DisplayMetrics dm = new DisplayMetrics();
                            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                            if (user_subscriptions.size() != 0) {
                                for (int i = 0; i < categories.size(); i++) {
                                    ArrayList<Subscription> subs = categories.get(i).getSubscriptions();
                                    for (int j = 0; j < subs.size(); j++) {
                                        if (user_subscriptions.contains(subs.get(j).getSubscriptionId())) {
                                            categories.get(i).setBought(true);
                                            break;
                                        } else {
                                            categories.get(i).setBought(false);
                                        }
                                    }
                                }
                            }
                            catAdapter = new Data_Adapter_Cat_List(getActivity(), categories, dm);
                            if (recyclerView != null) {
                                recyclerView.setVisibility(View.VISIBLE);
                                recyclerView.setAdapter(catAdapter);
                                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                        LinearLayoutManager.VERTICAL);
                                dividerItemDecoration.setDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.divider));
                                recyclerView.addItemDecoration(dividerItemDecoration);
                                catAdapter.setOnPreparedListener(new Data_Adapter_Cat_List.OnPreparedListener() {
                                    @Override
                                    public void onPrepare() {
                                        vid_item = (LinearLayout) catAdapter.getView();
                                        vid_item.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                onItemClick(v);
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
                    receiver.execute();
                }
                break;
                case VIDEO: {
                    if (video_flag && !overlay_flag) {
                        final DisplayMetrics metrics = new DisplayMetrics();
                        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                        final Data_Adapter_Video_List ovvidAdapter;
                        if (buf_list.size() != 1) {
                            ArrayList<Video> buf = new ArrayList<>();
                            for (int i = 0; i < buf_list.size(); i++) {
                                if ((buf_list.get(i) != null) && (buf_list.get(i).getId() != vid_id))
                                    buf.add(buf_list.get(i));
                            }
                            ovvidAdapter = new Data_Adapter_Video_List(getActivity(), buf, metrics, bought, true, null);
                        } else {
                            ovvidAdapter = new Data_Adapter_Video_List(getActivity(), buf_list, metrics, bought, true, null);
                        }
                        overlayrecView.setAdapter(ovvidAdapter);
                        RelativeLayout.LayoutParams parameters = (RelativeLayout.LayoutParams) overlayrecView.getLayoutParams();

                        overlayrecView.setVisibility(View.VISIBLE);
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(overlayrecView.getContext(),
                                LinearLayoutManager.VERTICAL);
                        dividerItemDecoration.setDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.divider));
                        overlayrecView.addItemDecoration(dividerItemDecoration);

                        ovvidAdapter.setOnPreparedListener(new Data_Adapter_Video_List.OnPreparedListener() {
                            @Override
                            public void onPrepare() {
                                vid_item = (LinearLayout) ovvidAdapter.getView();
                                vid_item.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onItemClick(v);
                                    }
                                });
                            }
                        });
                    } else {
                        if ((!overlayClicked) || (catPos == overlayCatPos) || (logged)) {
                            videos.clear();
                            ReceiveVideosData receiver = new ReceiveVideosData(new OnTaskComplete() {
                                @Override
                                public void onTaskCompleted(String response) {
                                    srf.setRefreshing(false);
                                    DisplayMetrics metrics = new DisplayMetrics();
                                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                                    vidAdapter = new Data_Adapter_Video_List(getActivity(), videos, metrics, bought, false, Parser.parsingManager(curr_catDes, getActivity()));
                                    recyclerView.setAdapter(vidAdapter);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                            LinearLayoutManager.VERTICAL);
                                    dividerItemDecoration.setDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.divider));
                                    recyclerView.addItemDecoration(dividerItemDecoration);
                                    vidAdapter.setOnPreparedListener(new Data_Adapter_Video_List.OnPreparedListener() {
                                        @Override
                                        public void onPrepare() {
                                            vid_item = (LinearLayout) vidAdapter.getView();
                                            vid_item.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    onItemClick(v);
                                                }
                                            });
                                        }
                                    });
                                }

                            });
                            receiver.execute();
                        } else {
                            DisplayMetrics metrics = new DisplayMetrics();
                            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                            vidAdapter = new Data_Adapter_Video_List(getActivity(), videos, metrics, bought, false, Parser.parsingManager(curr_catDes, getActivity()));
                            recyclerView.setAdapter(vidAdapter);
                            recyclerView.setVisibility(View.VISIBLE);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                    LinearLayoutManager.VERTICAL);
                            dividerItemDecoration.setDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.divider));
                            recyclerView.addItemDecoration(dividerItemDecoration);
                            vidAdapter.setOnPreparedListener(new Data_Adapter_Video_List.OnPreparedListener() {
                                @Override
                                public void onPrepare() {
                                    vid_item = (LinearLayout) vidAdapter.getView();
                                    vid_item.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            onItemClick(v);
                                        }
                                    });
                                }
                            });
                        }
                    }
                }
                break;
            }
        } else {
            Toast.makeText(getActivity(), R.string.conn_err, Toast.LENGTH_LONG).show();
            //getActivity().finish();
            ((MainActivity) getContext()).rhythms_show();
        }
    }


    public static void setQuality(final Context context, String quality_flag) {
        mCurrentPosition = videoPlayer.getCurrentPosition();
        videoPlayer.setVisibility(View.INVISIBLE);
        videoPlayer.setVisibility(View.VISIBLE);
        vcontr = new VideoController(context, arr_qualities);
        videoPlayer.setVideoPath(videopath + vid_id + "/q/" + quality_flag);
        vidp_bar.setVisibility(View.VISIBLE);
        videoPlayer.seekTo(mCurrentPosition);
        videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                vidp_bar.setVisibility(View.GONE);
                videoPlayer.setAlpha(1.0f);
                videoPlayer.setZOrderOnTop(false);
                videoPlayer.setBackgroundColor(Color.TRANSPARENT);
                videoPlayer.setMediaController(vcontr);
                videoPlayer.start();
            }
        });
    }

    private static class ReceiveVideosData extends AsyncTask<Void, Void, String> {

        private OnTaskComplete mCallBack;

        ReceiveVideosData(OnTaskComplete callback) {
            mCallBack = callback;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                int responseCode = conn.getResponseCode();
                videos.add(null);
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String response = "";
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        response += line + "\n";
                    }
                    JSONArray files = new JSONArray(response);
                    for (int i = 0; i < files.length(); i++) {
                        Integer id = Integer.parseInt(files.getJSONObject(i).getString("Id"));
                        String name = files.getJSONObject(i).getString("Name");
                        String description = files.getJSONObject(i).getString("Description");
                        if (description.equals("null")) {
                            description = "";
                        }
                        boolean free = files.getJSONObject(i).getBoolean("Free");
                        JSONArray qualities = files.getJSONObject(i).getJSONArray("Qualities");
                        String teacher_name = files.getJSONObject(i).getString("TeacherName");
                        Integer teacher_id = -1;
                        if (!files.getJSONObject(i).getString("TeacherId").equals("null")) {
                            teacher_id = files.getJSONObject(i).getInt("TeacherId");
                        }
                        if (!teacher_name.equals(teacher)) {
                            teacher = teacher_name;
                        }
                        arr_qualities = new String[qualities.length()];
                        for (int j = 0; j < qualities.length(); j++) {
                            arr_qualities[j] = qualities.getString(j);
                        }
                        String duration = files.getJSONObject(i).getString("Duration");
                        String image = API_String.api_str + "FileModels/preview/" + id;
                        videos.add(new Video(id, name, image, description, free, arr_qualities, teacher, teacher_id, duration));
                    }
                    return new String("");
                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if ((mCallBack != null) && (videos.size() > 0)) {
                mCallBack.onTaskCompleted(result);
            }
        }
    }


    private class ReceiveCatalogsData extends AsyncTask<Void, Void, String> {

        private OnTaskComplete mCallBack;


        ReceiveCatalogsData(OnTaskComplete callback) {
            mCallBack = callback;
        }


        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String response = "";
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        response += line + "\n";
                    }
                    JSONArray json_arr = new JSONArray(response);
                    for (int i = 0; i < json_arr.length(); i++) {
                        boolean parent_flag = false;
                        Integer id = Integer.parseInt(json_arr.getJSONObject(i).getString("Id"));
                        String name = json_arr.getJSONObject(i).getString("Name");
                        Integer parentid = Integer.parseInt(json_arr.getJSONObject(i).getString("ParentId"));
                        String teacherName = json_arr.getJSONObject(i).getString("teacherName");
                        String description = json_arr.getJSONObject(i).getString("Description");
                        if (description.equals("null")) {
                            description = "";
                        }
                        String buf = json_arr.getJSONObject(i).getString("TeacherId");
                        Integer teacherID = -1;
                        if (!buf.equals("null")) {
                            teacherID = Integer.parseInt(buf);
                        }

                        JSONArray subs = json_arr.getJSONObject(i).getJSONArray("Subscriptions");
                        ArrayList<Subscription> subscriptions = new ArrayList<>();
                        for (int k = 0; k < subs.length(); k++) {
                            subscriptions.add(new Subscription(subs.getJSONObject(k).getString("SubscriptionId"), Parser.parsingManager(subs.getJSONObject(k).getString("Description"), getActivity())));
                            if ((prices.size() != 0) && (descrs.size() != 0) && (titles.size() != 0)) {
                                subscriptions.get(subscriptions.size() - 1).setPrice((String) prices.get(subs.getJSONObject(k).getString("SubscriptionId")));
                                subscriptions.get(subscriptions.size() - 1).setName((String) titles.get(subs.getJSONObject(k).getString("SubscriptionId")));
                            }
                        }

                        //String subID = json_arr.getJSONObject(i).getString("SubscriptionId");
                        for (int j = 0; j < json_arr.length(); j++) {
                            if (id == Integer.parseInt(json_arr.getJSONObject(j).getString("ParentId"))) {
                                parent_flag = true;
                                break;
                            }
                        }
                        if (!parent_flag) {
                            categories.add(new Category(id, name, teacherName, teacherID, description, subscriptions));
                        }
                    }
                    return new String("");
                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if ((mCallBack != null) && (categories.size() > 0)) {
                mCallBack.onTaskCompleted(result);
            }
        }
    }


    private class ReceiveUserData extends AsyncTask<Void, Void, String> {
        private OnTaskComplete mCallBack;


        ReceiveUserData(OnTaskComplete callback) {
            mCallBack = callback;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                SharedPreferences auth_info = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String userID = auth_info.getString(ID, "");
                if (userID.equals("")) {
                    userID = "null";
                }
                URL url = new URL(link + userID);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String response = "";
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        response += line + "\n";
                    }
                    JSONArray json_arr = new JSONArray(response);
                    for (int i = 0; i < json_arr.length(); i++) {
                        Log.d("1", "loading");
                        if (userID.equals(json_arr.getJSONObject(i).getString("UserId"))) {
                            if (user_subscriptions.contains(json_arr.getJSONObject(i).getString("SubscriptionId"))) {
                                user_subscriptions.add(json_arr.getJSONObject(i).getString("SubscriptionId"));
                                //Log.d("1",user_subscriptions.get(i));
                            }
                        }
                    }
                    return new String("");
                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (mCallBack != null) {
                mCallBack.onTaskCompleted(result);
            }
        }
    }

    private static class ReceiveTeacherData extends AsyncTask<Void, Void, String> {
        int id;
        String link;

        private OnTaskComplete mCallBack;

        ReceiveTeacherData(int id, OnTaskComplete callback) {
            this.id = id;
            mCallBack = callback;

        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String response = "";
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        response += line + "\n";
                    }
                    JSONObject teacher = new JSONObject(response);

                    String name = teacher.getString("Name");
                    encodedteacherHtml = teacher.getString("HtmlString");
                    return new String("");
                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if ((mCallBack != null) && (encodedteacherHtml.length() != 0)) {
                Log.d("html", encodedteacherHtml);
                mCallBack.onTaskCompleted(result);
            }
        }
    }

    private class ReceiveSubData extends AsyncTask<Void, Void, String> {
        private OnTaskComplete mCallBack;

        ReceiveSubData(OnTaskComplete callback) {
            mCallBack = callback;
        }

        @Override
        protected String doInBackground(Void... params) {


            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String response = "";
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        response += line + "\n";
                    }
                    subscriptions = new ArrayList<>();
                    JSONArray json_arr = new JSONArray(response);
                    for (int i = 0; i < json_arr.length(); i++) {
                        JSONArray subs = json_arr.getJSONObject(i).getJSONArray("Subscriptions");

                        for (int k = 0; k < subs.length(); k++) {
                            subscriptions.add(new Subscription(subs.getJSONObject(k).getString("SubscriptionId"), Parser.parsingManager(subs.getJSONObject(k).getString("Description"), getActivity())));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return new String("");
        }

        @Override
        protected void onPostExecute(String result) {
            if ((mCallBack != null) && (subscriptions.size() > 0)) {
                mCallBack.onTaskCompleted(result);
            }
        }
    }

    private class FetchSubData extends AsyncTask<Void, Void, String> {
        private String subID = "";
        private OnTaskComplete mCallBack;
        int responseCode = 0;

        FetchSubData(String subID, OnTaskComplete mCallBack) {
            this.subID = subID;
            this.mCallBack = mCallBack;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                SharedPreferences auth_info = PreferenceManager.getDefaultSharedPreferences(getContext());
                String userID = auth_info.getString(ID, "");
                if (!userID.equals("")) {
                    String link = API_String.api_str + "UsersCatalogs";
                    URL url = new URL(link);
                    JSONObject postDataParams = new JSONObject();

                    postDataParams.put("UserId", userID);
                    postDataParams.put("SubscriptionId", subID);
                    Log.e("data", postDataParams.toString());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    conn.setRequestProperty("Content-Type", " application/json;charset=utf-8");
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(postDataParams.toString());

                    writer.flush();
                    writer.close();
                    os.close();
                    conn.connect();
                    responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {

                        BufferedReader in = new BufferedReader(new
                                InputStreamReader(
                                conn.getInputStream()));

                        StringBuffer sb = new StringBuffer("");
                        String line = "";

                        while ((line = in.readLine()) != null) {

                            sb.append(line);
                            break;
                        }

                        in.close();
                        return sb.toString();

                    } else {
                        return new String("false : " + responseCode);
                    }
                } else {
                    return new String("");
                }

            } catch (MalformedURLException e) {
                Log.d("err", e.getMessage());
                return e.getMessage();
            } catch (JSONException e) {
                Log.d("err", e.getMessage());
                return e.getMessage();
            } catch (ProtocolException e) {
                Log.d("err", e.getMessage());
                return e.getMessage();
            } catch (IOException e) {
                Log.d("err", e.getMessage());
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (mCallBack != null) {
                mCallBack.onTaskCompleted(result);
            }
        }
    }
}