package com.example.dashboard;

import android.graphics.Bitmap;

public class ImageData {

    private static Bitmap image;

    private static ImageData imageData;

    private ImageData(Bitmap image){
        ImageData.image = image;
    }

    public static ImageData getInstantce(){
        if (imageData == null){
            imageData = new ImageData(null);
        }
        return imageData;
    }

    public void setImage(Bitmap image) {
        ImageData.image = image;
    }

    public Bitmap getImage() {
        return image;
    }
}
