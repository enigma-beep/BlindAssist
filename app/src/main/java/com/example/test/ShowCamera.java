package com.example.test;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {

    Camera camera;
    SurfaceHolder holder;
    public ShowCamera(Context context,Camera camera) {
        super(context);
        this.camera=camera;
        holder=getHolder();
        holder.addCallback(this);

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        Camera.Parameters params=camera.getParameters();

        //change camera orientation

        List<Camera.Size> sizes=params.getSupportedPictureSizes();

        if(this.getResources().getConfiguration().orientation!= Configuration.ORIENTATION_LANDSCAPE)
        {
            params.set("orientation","portrait");
            camera.setDisplayOrientation(90);
            params.setRotation(90);
        }
        else {
            params.set("orientation","landscape");
            camera.setDisplayOrientation(0);
            params.setRotation(0);
        }
        //params.setPictureSize(mSize.width,mSize.height);
        camera.setParameters(params);
        try{
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.setPreviewCallback(null);
        //camera.release();
    }
}
