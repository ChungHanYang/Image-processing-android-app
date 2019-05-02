package com.example.yusheng.eee508project__2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements OnTouchListener, CvCameraViewListener2 {
    private CameraBridgeViewBase mopenCvCameraView;
    private Mat mRgba;
    Mat imgGrey, imgCanny;
    private Scalar mBlobColorRgba;
    private Scalar mBlobColorHsv;




    double x = -1;
    double y = -1;
    TextView touch_coordinates;
    TextView touch_color;


    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    mopenCvCameraView.enableView();
                    mopenCvCameraView.setOnTouchListener(MainActivity.this);
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        touch_coordinates = (TextView) findViewById(R.id.touch_coordinates);
        touch_color = (TextView) findViewById(R.id.touch_color);
        mopenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.opencv_tutorial_activity_surface_view);
        mopenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mopenCvCameraView.setCvCameraViewListener(this);
    }



    @Override
    public void onPause() {
        super.onPause();
        if (mopenCvCameraView != null)
            mopenCvCameraView.disableView();

    }

    @Override
    public void onResume()
    {
       super.onResume();
       if(!OpenCVLoader.initDebug())
       {
           OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0,this,mLoaderCallback);
       }
       else
       {
           mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
       }

    }

    @Override
    public void onDestroy()
    {
       super.onDestroy();
       if (mopenCvCameraView !=null)
           mopenCvCameraView.disableView();

    }


    @Override
    public boolean onTouch(View v, MotionEvent Event) {
        int cols = mRgba.cols();
        int rows = mRgba.rows();
        double yLow = (double)mopenCvCameraView.getHeight()*0.2401961;
        double yHigh = (double)mopenCvCameraView.getHeight()*0.7696078;
        double xScale = (double)cols/(double)mopenCvCameraView.getWidth();
        double yScale = (double)rows/(yHigh - yLow);
        x = Event.getX();
        y = Event.getY();
        y = y - yLow;
        x = x * xScale;
        y = y * yScale;
        if ((x < 0) || (y < 0) || (x > cols) ||(y > rows)) return false;
        touch_coordinates.setText("X: "+ Double.valueOf(x) + ", Y: "+ Double.valueOf(y));
        Rect touchedRect = new Rect();

        touchedRect.x = (int)x;
        touchedRect.y = (int)y;

        touchedRect.width = 8;
        touchedRect.height = 8;

        Mat touchedRegionRgba = mRgba.submat(touchedRect);

        Mat touchedRegionHsv = new Mat();
        Imgproc.cvtColor(touchedRegionRgba, touchedRegionHsv, Imgproc.COLOR_RGB2HSV);
        Imgproc.cvtColor(touchedRegionRgba, touchedRegionHsv, Imgproc.COLOR_RGB2HSV);

        mBlobColorHsv = Core.sumElems(touchedRegionHsv);
        int pointCount = touchedRect.width * touchedRect.height;
        for(int i = 0; i< mBlobColorHsv.val.length; i++)
            mBlobColorHsv.val[i] /= pointCount;

        mBlobColorRgba = convertScalarHsv2Rgba(mBlobColorHsv);

        touch_color.setText("Color: #" + String.format("%02X", (int)mBlobColorRgba.val[0])
                + String.format("%02X", (int)mBlobColorRgba.val[1])
                + String.format("%02X", (int)mBlobColorRgba.val[2]));

        touch_color.setTextColor(Color.rgb((int) mBlobColorRgba.val[0],
                (int)mBlobColorRgba.val[1],
                (int)mBlobColorRgba.val[2]));
        touch_coordinates.setTextColor(Color.rgb((int) mBlobColorRgba.val[0],
                (int)mBlobColorRgba.val[1],
                (int)mBlobColorRgba.val[2]));


        return false;
    }

    private Scalar convertScalarHsv2Rgba(Scalar hsvColor) {
        Mat pointMatRgba = new Mat();
        Mat pointMatHsv = new Mat(1, 1, CvType.CV_8UC3, hsvColor);
        Imgproc.cvtColor(pointMatHsv, pointMatRgba, Imgproc.COLOR_HSV2BGR_FULL, 4);

        return new Scalar(pointMatRgba.get(0, 0));

    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat();
        mBlobColorRgba = new Scalar(255);
        mBlobColorHsv = new Scalar(255);
        imgGrey = new Mat(height, width, CvType.CV_8UC1);
        imgCanny = new Mat(height, width, CvType.CV_8UC1);

    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();

        Imgproc.cvtColor(mRgba, imgGrey, Imgproc.COLOR_RGB2GRAY); //conert to gray scale
        Imgproc.Canny(imgGrey, imgCanny, 50, 150); //doing edge detection with canny filter

        return imgCanny;
    }


}



