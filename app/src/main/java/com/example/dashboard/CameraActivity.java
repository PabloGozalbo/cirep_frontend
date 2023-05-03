package com.example.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.Recorder;
import androidx.camera.video.Recording;
import androidx.camera.video.VideoCapture;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.cirep_frontend.R;
import com.example.cirep_frontend.databinding.ActivityCameraBinding;
import com.google.common.util.concurrent.ListenableFuture;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraActivity extends AppCompatActivity {

    private Button shutter;
    private ActivityCameraBinding viewBinding;
    private ImageView imageCapture;
    private ExecutorService cameraExecutor;
    private PreviewView mPreviewView;
    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        mPreviewView = findViewById(R.id.camera);
        imageCapture = findViewById(R.id.captureImg);
        shutter = findViewById(R.id.imageCapture);

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
        cameraExecutor = Executors.newSingleThreadExecutor();
    }

    private void startCamera() {
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try{
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);
                } catch(ExecutionException | InterruptedException e) {
                    //No se deberia llegar aquí y no deberia haber excepciones, si las hay es un error.
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    void  bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();

        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().build();

        ImageCapture.Builder builder = new ImageCapture.Builder();

        final ImageCapture imageCapture = builder.setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation()).build();

        preview.setSurfaceProvider(mPreviewView.getSurfaceProvider()); //Cuidao

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageAnalysis, imageCapture);

        shutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageCapture.takePicture(cameraExecutor, new ImageCapture.OnImageCapturedCallback(){
                    @Override
                    public void onCaptureSuccess(@NotNull ImageProxy imageProxy) {
                        @SuppressLint("UnsafeOptInUsageError")
                        Image image = imageProxy.getImage();
                        // TODO hacer las cosas con la imagen.
                        imageProxy.close();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        exception.printStackTrace();
                    }
                });
            }
        });
    }

    private boolean allPermissionsGranted() {
        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    public String getBatchDirectoryName() {

        String app_folder_path = "";
        app_folder_path = Environment.getExternalStorageDirectory().toString() + "/images";
        File dir = new File(app_folder_path);
        if (!dir.exists() && !dir.mkdirs()) {

        }
        return app_folder_path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }

}