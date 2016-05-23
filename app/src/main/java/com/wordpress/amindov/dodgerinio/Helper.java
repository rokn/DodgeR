package com.wordpress.amindov.dodgerinio;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Antonio Mindov on 5/23/2016.
 */
public class Helper {
    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
