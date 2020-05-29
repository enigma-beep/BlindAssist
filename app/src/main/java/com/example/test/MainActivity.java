package com.example.test;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;


import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    GestureDetector gestureDetector;

    String toSpeak;
    Camera camera;
    FrameLayout framelayout;
    ShowCamera showCamera;
    display d = new display();
    TextToSpeech t1;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });


        gestureDetector = new GestureDetector(MainActivity.this, MainActivity.this);

        //   Text to speech initialization


        framelayout = (FrameLayout) findViewById(R.id.frameLayout);

        //open
        camera = Camera.open();


        showCamera = new ShowCamera(this, camera);
        framelayout.addView(showCamera);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                performClick();
            }
        }, 1000);

    }

    public void performClick() {
        toSpeak = "Identification mode";
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {

        View v = framelayout.getRootView();
        captureImage(v);
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {


        Intent i = new Intent(MainActivity.this, distance.class);
        startActivity(i);
        return false;
    }


    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            File picture_file = getOutputMediaFile();
            if (picture_file == null) {
                return;
            } else {
                try {
                    FileOutputStream fos = new FileOutputStream(picture_file);
                    fos.write(bytes);
                    fos.close();

                    Intent i = new Intent(MainActivity.this, display.class);
                    startActivity(i);
                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    };

    private File getOutputMediaFile() {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        } else {
            File folder_gui = new File(Environment.getExternalStorageDirectory() + File.separator + "GUI");
            if (!folder_gui.exists()) {
                folder_gui.mkdirs();
            }
            File outputFile = new File(folder_gui, "temp.jpg");
            return outputFile;
        }
    }


    public void captureImage(View v) {
        if (camera != null) {
            camera.takePicture(null, null, mPictureCallback);
        }
    }


    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {

        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
