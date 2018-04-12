package com.chandramani.alien.trelltask;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.VideoView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private float dxx;
    private float dyy;
    float starttime,endtimee;
    public int firstthumb,secondthumb;
    public  float scrolled_valuex =0;
    public HorizontalScrollView horizontalScrollView;
    public float startthumb =0, endthumb=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///////////////
        horizontalScrollView = findViewById(R.id.horizontalscrollview1);
        //toolbar
        ///////////////////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_toolbar_black);
        //thumb drawing
        /////////////////////
        ImageView imagethumbview1 = findViewById(R.id.image1thumb);
        ImageView imagethumbview2 = findViewById(R.id.image1thumb2);
        Bitmap bb1 = Bitmap.createBitmap(80,300,Bitmap.Config.ARGB_8888);
        Bitmap bb2 = Bitmap.createBitmap(80,300,Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bb1);
        canvas2.setBitmap(bb1);
        Paint paint3 =new Paint();
        paint3.setStyle(Paint.Style.FILL);
        paint3.setColor(Color.WHITE);
        paint3.setStrokeWidth(10);
        canvas2.drawCircle(imagethumbview1.getX()+40,150,40,paint3);
        canvas2.drawLine(imagethumbview1.getX()+40,300,imagethumbview1.getX()+40,0,paint3);
        canvas2.setBitmap(bb2);
        canvas2.drawCircle(imagethumbview2.getX()+40,150,40,paint3);
        canvas2.drawLine(imagethumbview2.getX()+40,300,imagethumbview2.getX()+40,0,paint3);
        imagethumbview1.setImageBitmap(bb1);
        imagethumbview2.setImageBitmap(bb2);
        //rangebar set touch listner
        //////////////////////////////
        View dragView = findViewById(R.id.image1thumb);
        firstthumb = dragView.getId();
        dragView.setOnTouchListener(this);
        View dragView2 = findViewById(R.id.image1thumb2);
        secondthumb = dragView2.getId();
        dragView2.setOnTouchListener(this);
        /////////////////////////////
        /////////////////progessss initial
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        int width1= (int)((80 * displayMetrics.density) + 0.5);
        int width2=(int)((250 * displayMetrics.density) + 0.5);
        ImageView imagepogress =findViewById(R.id.progresspluss);
        ImageView imagepogress2 =findViewById(R.id.progressminus);
        RelativeLayout.LayoutParams layoutParamsx = new RelativeLayout.LayoutParams(width1+40,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        RelativeLayout.LayoutParams layoutParamsxx = new RelativeLayout.LayoutParams(Resources.getSystem().getDisplayMetrics().widthPixels-(width2+40),
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        layoutParamsxx.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        imagepogress.setLayoutParams(layoutParamsx);                                            ///////
        imagepogress2.setLayoutParams(layoutParamsxx);                                          ///////
        startthumb = width1+40;
        endthumb =(width2+40);
                //tabhost
        ////////////////////////////////
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec tabSpec;
        tabSpec = tabHost.newTabSpec("tab1");
        tabSpec.setIndicator("FILTER");
        tabSpec.setContent(R.id.tab11);
        tabHost.addTab(tabSpec);//
        tabSpec = tabHost.newTabSpec("tab2");
        tabSpec.setIndicator("TRIM");
        tabSpec.setContent(R.id.tab13);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("tab3");
        tabSpec.setIndicator("COVER");
        tabSpec.setContent(R.id.tab12);
        tabHost.addTab(tabSpec);
        tabHost.setCurrentTab(1);
        //video frames and uri
        //////////////////////////////////
        final String uriPath = "android.resource://" + getPackageName() + "/raw/testvideo";
        final Uri uri = Uri.parse(uriPath);
        //MediaMetadataRetriver
        //////////////////////////////////
        final MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(getApplication(), uri);
       // Bitmap bitmap = retriever.getFrameAtTime(5000);
        final VideoView imageView = findViewById(R.id.Imageframes);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        final long timeInMillisec = Long.parseLong(time);
        imageView.setVideoURI(uri);
        imageView.seekTo(1000);
        imageView.requestFocus();
        imageView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                imageView.pause();
                mp.setVolume(0,0);
            }
        });
        imageView.seekTo(1000);
        ///////////////
        final FrameLayout.LayoutParams layparm1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        final FrameLayout.LayoutParams layparm2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layparm2.gravity= Gravity.CENTER;
        final ImageView imageViewPlayandPause = findViewById(R.id.playandpause);

        imageViewPlayandPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(imageView.isPlaying()){
                imageView.pause();
                imageViewPlayandPause.setAlpha(255);
               imageViewPlayandPause.setLayoutParams(layparm2);
                }
                else
                {
                    imageView.start();
                    imageViewPlayandPause.setAlpha(0);
                    imageViewPlayandPause.setLayoutParams(layparm1);
                }

            }
        });
        //for scrollview frames
        /////////////////////////////////
        final LinearLayout linearLayout = findViewById(R.id.imageviewframes2);

        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                ,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );
        final LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(200,
                LinearLayout.LayoutParams.MATCH_PARENT);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < timeInMillisec * 1000; i += 20000000) {
                    final ImageView imageView1 = new ImageButton(getBaseContext());
                    final Bitmap bitmap1 = retriever.getFrameAtTime(i);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView1.setImageBitmap(bitmap1);
                            linearLayout.addView(imageView1, layoutParams1);

                        }
                    });
                }
            }
        });
        final LinearLayout linearLayout2 = findViewById(R.id.scalevieww);
        final LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                ,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );
        int widtt = (int)((timeInMillisec*1000)/20000000)*200;
        final LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(widtt
                , LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );
        // draw scale below the video frames
        ///////////////////////////////////////////
        ImageView imageView3 = new ImageView(this);
        imageView3.setAdjustViewBounds(true);
        Bitmap bb = Bitmap.createBitmap(widtt+200,250,Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bb);
        canvas.setBitmap(bb);
        Paint paint = new Paint();
        paint.setStrokeWidth(2);
        Paint paint2 = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setTextSize(30f);
        paint2.setColor(Color.BLACK);
        paint2.setStrokeWidth(5);
        for (int i =0; i < widtt+200; i+=20) {
            if(i%200 ==0) {
                canvas.drawLine(i, 300, i, 70, paint2);
                String strr = new String();
               canvas.drawText(strr.valueOf((i/200)*20),i-20,45,paint2);
            }
            else
                canvas.drawLine(i,300,i,130,paint);
        }
        imageView3.setImageBitmap(bb);
        linearLayout2.addView(imageView3,layoutParams4);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        ImageView imagepogress =findViewById(R.id.progresspluss);
        ImageView imagepogress2 =findViewById(R.id.progressminus);
        VideoView videoView = findViewById(R.id.Imageframes);

         switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dxx = view.getX() - event.getRawX();
                dyy = view.getY() - event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                view.setX(event.getRawX() + dxx);
                //rangebar progress
                ////////////////////////////////////first thumb
                if(view.getId()== firstthumb) {
                   startthumb= (event.getRawX() + dxx+40);
                   RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int)(event.getRawX()+dxx+40),
                            RelativeLayout.LayoutParams.MATCH_PARENT
                   );
                   imagepogress.setLayoutParams(layoutParams2);
                   videoView.seekTo((int)((startthumb/200)*20000));
                }
                ///////////////////second thumb
                else if(view.getId()==secondthumb)
                {
                    endthumb = (event.getRawX()+dxx+40);
                    RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams((Resources.getSystem().getDisplayMetrics().widthPixels)-(int)(event.getRawX()+dxx+40),
                            RelativeLayout.LayoutParams.MATCH_PARENT
                    );
                    layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    imagepogress2.setLayoutParams(layoutParams3);
                    videoView.seekTo((int)((endthumb/200)*20000));
                }
                // view.setY(event.getRawY() + dyy);
                break;
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DecimalFormat df = new DecimalFormat("#.000");
        scrolled_valuex = horizontalScrollView.getScrollX();
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            starttime= ((scrolled_valuex+startthumb)/200)*20;
            String starttimes =(df.format(starttime));
            endtimee = ((scrolled_valuex+endthumb)/200)*20;
            String endtimees =(df.format(endtimee));
            Toast.makeText(getApplicationContext(),"Trim \n Start_point:"+starttimes+" Sec"+ "\n End_point:"+endtimees+" Sec",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}