package com.chandramani.alien.trelltask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by Alien on 01-04-2018.
 */

public class DrawActivity extends View {
    Paint paint = new Paint();
    Rect R = new Rect(10,10,200,200);

    public DrawActivity(Context context) {
        super(context);

    }
    @Override
    public void onDraw(Canvas canvas){
    }
}
