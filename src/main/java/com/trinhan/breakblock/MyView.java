package com.trinhan.breakblock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xuanvu.breakblock.R;

import java.util.ArrayList;

public class MyView extends View implements Runnable {
    private Bitmap ball;
    private Bitmap ballResize;

    private Bitmap thanhngang;
    private Bitmap thanhngangsize;
    private Bitmap gameover;
    private Bitmap win;

    private int x1 = 500, y1 = 500, dx1 = 5, dy1 = 5;
    private int x2 = 500, y2 = 500, dx2 = 10, dy2 = 10;

    private int x3 = 400 - getWidth(), y3 = 1480 - getHeight();

    int point = 0;


    ArrayList<Brick> lists;

    private SoundManager soundManager;


    public MyView(Context context, @Nullable AttributeSet attrs) {
        super( context, attrs );

        thanhngang = BitmapFactory.decodeResource( getResources(), R.drawable.thanhngang );
        thanhngangsize = Bitmap.createScaledBitmap( thanhngang, 240, 150, false );

        gameover = BitmapFactory.decodeResource( getResources(), R.drawable.game_over );
        win = BitmapFactory.decodeResource( getResources(), R.drawable.youwin );

        ball = BitmapFactory.decodeResource( getResources(), R.drawable.ball );
        ballResize = Bitmap.createScaledBitmap( ball, 50, 50, false );

        lists = new ArrayList<Brick>();

        for (int i = 0; i < 9; i++) {
            Brick brick = new Brick( 110 * i + 50, 10, 105, 70 );
            Brick brick2 = new Brick( 110 * i + 50, 85, 105, 70 );
            Brick brick3 = new Brick( 110 * i + 50, 160, 105, 70 );
            Brick brick4 = new Brick( 110 * i + 50, 235, 105, 70 );
            Brick brick5 = new Brick( 110 * i + 50, 310, 105, 70 );
           /* Brick brick6 = new Brick(110 * i + 50, 385, 105, 70);*/

            lists.add( brick );
            lists.add( brick2 );
            lists.add( brick3 );
            lists.add( brick4 );
            lists.add( brick5 );
            /* lists.add( brick6 );*/
        }
           /* for (int j = 0; j < 4; j++) {
                Brick brick4 = new Brick(110 * j + 50, 235, 105, 70);
                Brick brick5 = new Brick( 110 * j + 600, 235, 105, 70 );

                lists.add( brick4 );
                lists.add( brick5 );
            }

            for (int k = 0; k < 2; k++) {
                Brick brick4 = new Brick(110 * k + 50, 310, 105, 70);
                Brick brick5 = new Brick( 110 * k + 820, 310, 105, 70 );

                lists.add( brick4 );
                lists.add( brick5 );
            }*/


        soundManager = SoundManager.getInstance();
        soundManager.init( context );

    }


    @Override

    protected void onDraw(Canvas canvas) {

        // TODO Auto-generated method stub

        super.onDraw( canvas );


        int x = getWidth();

        int y = getHeight();

        int radius;

        radius = 100;

        Paint paint = new Paint();

        paint.setStyle( Paint.Style.FILL );

        paint.setColor( Color.WHITE );

        canvas.drawPaint( paint );

        paint.setColor( Color.parseColor( "#CD5C5C" ) );

       /* canvas.drawCircle( x1, y1, 20, paint );
*/

        canvas.drawBitmap( ballResize, x2, y2, null );

        canvas.drawBitmap( thanhngangsize, x3, y3, null );


        Paint paint2 = new Paint();
        paint2.setColor( Color.GREEN );
        paint2.setTextSize( 120 );
        canvas.drawText( String.valueOf( point ), 480, 590, paint2 );

        if (y2 > y3 + 30) {
            if (x2 > x3 - 20 && x2 < x3 + 250) {
                soundManager.playSound( R.raw.sound2 );
                dy2 = -dy2;
            } else {
                canvas.drawText( "Scores ", 385, 430, paint2 );
                canvas.drawBitmap( gameover, 220, 600, null );
                /*soundManager.playSound( R.raw.gameover );*/
                return;

            }
        }

        for (Brick element : lists) {
            element.drawBrick( canvas, paint );
            if (element.getVisibility()) {
                //kiểm tra ball va chạm với gạch
                // viên nào bể thì set visible = false

                if (y2 < element.getY() + element.getHeight()) {
                    if (x2 > element.getX() && x2 < (element.getX() + element.getWidth())) {
                        element.setInVisible();
                        soundManager.playSound( R.raw.sound1 );
                        point += 10;
                        dy2 = -dy2;

                    }
                }

            }
            if (point == 450){
                canvas.drawText( "YOU WIN ", 360, 360, paint2 );
                canvas.drawText( "Scores ", 385, 430, paint2 );
               /* canvas.drawBitmap( win, 220, 600, null );*/
                return;
            }

        }


        update();
        invalidate();


    }


    @Override
    public void run() {

    }


    void update() {

        x1 += dx1;
        y1 += dy1;


        if (x1 > this.getWidth() || x1 < 0) dx1 = -dx1;
        if (y1 > this.getHeight() || y1 < 0) dy1 = -dy1;

        // ball two
        x2 += dx2;
        y2 += dy2;

        if (x2 > this.getWidth() || x2 < 0) dx2 = -dx2;
        if (y2 > this.getHeight() || y2 < 0) dy2 = -dy2;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean handled = false;

        int xTouch;
        int yTouch;
        int actionIndex = event.getActionIndex();


        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                xTouch = (int) event.getX( 0 );
                yTouch = (int) event.getY( 0 );


                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                xTouch = (int) event.getX( actionIndex );
                yTouch = (int) event.getY( actionIndex );


                handled = true;
                break;

            case MotionEvent.ACTION_MOVE:
                final int pointerCount = event.getPointerCount();

                for (actionIndex = 0; actionIndex < pointerCount; actionIndex++) {

                    xTouch = (int) event.getX( actionIndex );
                    yTouch = (int) event.getY( actionIndex );

                    x3 = xTouch;
                }


                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_UP:

                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_UP:

                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_CANCEL:

                handled = true;
                break;

            default:
                // do nothing
                break;
        }

        return super.onTouchEvent( event ) || handled;
    }


}