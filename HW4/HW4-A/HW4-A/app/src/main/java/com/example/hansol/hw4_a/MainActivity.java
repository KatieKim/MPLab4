package com.example.hansol.hw4_a;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.os.Bundle;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Bitmap mbok;
    private Bitmap mbokbok;
    private Paint mPaint;
    private boolean[][] check=  new boolean[8][6];
    private int[][] mode = new int[8][6];
    private float swidth;
    int windowHeight;
    int windowWidth;
    int imgwidth;
    int imgheight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView vw = new MyView(this);
        setContentView(vw);
    }
    protected class MyView extends View {

        public MyView(Context context) {
            super(context);
            init();
        }
        public void init() {
            for(int i = 0 ; i < 8 ; i++){
                for(int  j = 0 ; j < 6 ; j++){
                    check[i][j] = true;
                    mode[i][j] = 1;
                }
            }
            mPaint = new Paint();
            //mbok is before it pop, and mbokbok is after it poped.
            Resources res = getResources();
            mbok = BitmapFactory.decodeResource(res, R.drawable.bok);
            mbokbok = BitmapFactory.decodeResource(res, R.drawable.bokbok);

            imgwidth = mbok.getWidth();
            imgheight = mbok.getHeight();

            DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
            int statusBarheight = getStatusBarHeight();

            //get right windowsize for windowWidth/6 and show 8 size and 6 size for whole screen
            windowHeight= dm.heightPixels - statusBarheight;
            windowWidth = dm.widthPixels;
            swidth = (float)windowWidth/6;

            Log.i("TAG11",windowWidth +" "+windowHeight );
        }
        public boolean onTouchEvent(MotionEvent event) {
            int endcheck = 0 ;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // get the coordinates
                float x = event.getX();
                float y = event.getY();
                //change state for already poped, if it;s poped already, finish the loop
                for(int i =0 ; i< 8 ; i++){
                    for(int j = 0 ; j < 6 ; j++){
                        if ((int)((swidth*j + swidth/2)-x)*(int)((swidth*j + swidth/2)-x) + (int)((swidth*i + swidth/2)-y)* (int)((swidth*i + swidth/2)-y) <(int)((swidth/2) * (swidth/2)) ) {
                            if(check[i][j] == true) {
                                check[i][j] = false;
                                int r = (int) (Math.random() * 4);
                                mode[i][j] = r;
                                endcheck = 1;
                                invalidate();
                                break;
                            }
                        }
                    }
                    if(endcheck == 1){
                        endcheck = 0;
                        break;
                    }
                }

            }
            // indicates that the event was handled
            return true;
        } // end of onTouchEvent

        public void onDraw(Canvas canvas) {
            canvas.drawColor(0xffA1C8DF);
            for(int i = 0 ;i  < 8 ; i++){
                for(int j = 0 ; j < 6 ; j++){
                    if(check[i][j])
                        canvas.drawBitmap(mbok, new Rect(0, 0, imgwidth, imgheight), new Rect((int)swidth*j,(int)swidth*i ,(int)(swidth*j+swidth), (int)(swidth*i +swidth)), mPaint);
                    else {
                        canvas.drawBitmap(mbokbok, new Rect(0, 0, imgwidth, imgheight), new Rect((int) swidth * j, (int) swidth * i, (int) (swidth * j + swidth), (int) (swidth * i + swidth)), mPaint);
                    }
                }
            }
        }
    }
    public int getStatusBarHeight(){
        int statusHeight = 0;
        int screenSizeType = (getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK);
        if(screenSizeType != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            int resourceId = getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusHeight = getApplicationContext().getResources().getDimensionPixelSize(resourceId);
            }
        }
        return statusHeight;
    }
}
