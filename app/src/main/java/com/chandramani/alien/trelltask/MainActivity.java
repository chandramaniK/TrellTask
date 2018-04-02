package com.chandramani.alien.trelltask;

import android.content.Context;
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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.InflateException;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private float dxx;
    private float dyy;
    float valueofxx = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        View dragView = findViewById(R.id.image1thumb);
        dragView.setOnTouchListener(this);
        View dragView2 = findViewById(R.id.image1thumb2);
        dragView2.setOnTouchListener(this);

        //tabhostt
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

        final String uriPath = "android.resource://" + getPackageName() + "/raw/videoplayback";
        final Uri uri = Uri.parse(uriPath);
        final MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(getApplication(), uri);
        //  retriever.setDataSource(this.path);
        Bitmap bitmap = retriever.getFrameAtTime(5000);
        final VideoView imageView = findViewById(R.id.Imageframes);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        final long timeInMillisec = Long.parseLong(time);
        imageView.setVideoURI(uri);
        imageView.seekTo(1000);

        imageView.requestFocus();
        imageView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                imageView.pause();
            }
        });
        //for progresss

        //for scrollview frames
        final LinearLayout linearLayout = findViewById(R.id.imageviewframes2);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                ,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );
        final LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(100,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < timeInMillisec * 1000; i += 2000000) {
                    final ImageView imageView1 = new ImageButton(getBaseContext());
                    final Bitmap bitmap1 = retriever.getFrameAtTime(i);


                    //imageView1.setLayoutParams(layoutParams);
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
        final LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                ,LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );
        ImageView imageView3 = new ImageView(this);
        imageView3.setAdjustViewBounds(true);
          Bitmap bb = Bitmap.createBitmap(15000,200,Bitmap.Config.ARGB_8888);
          Canvas canvas=new Canvas(bb);
        canvas.setBitmap(bb);
        Paint paint = new Paint();
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        for (int i = 0; i < timeInMillisec; i+=20) {
            if(i%100 ==0 || i==0) {
                canvas.drawLine(i, 300, i, 50, paint);
                String strr = new String();

                canvas.drawText(strr.valueOf(i),i-5,30,paint);
                Log.d("testt11","text");
            }
            else
                canvas.drawLine(i,300,i,100,paint);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),"Trim \n Start_point:"+(valueofxx)*20+ "\n End_point:"+(valueofxx)*20,Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dxx = view.getX() - event.getRawX();
                dyy = view.getY() - event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                view.setX(event.getRawX() + dxx);
                valueofxx = view.getScrollX();
                // view.setY(event.getRawY() + dyy);
                break;


        }
        return true;
    }

}