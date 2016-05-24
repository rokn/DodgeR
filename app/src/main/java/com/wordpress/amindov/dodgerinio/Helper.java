package com.wordpress.amindov.dodgerinio;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by Antonio Mindov on 5/23/2016.
 */
public class Helper {
    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static boolean IsIntersected(PointF circle, float radius, RectF rect)
    {
        float w = rect.width() / 2;
        float h = rect.height() / 2;

        float dx = Math.abs(circle.x - rect.centerX());
        float dy = Math.abs(circle.y - rect.centerY());

        if (dx > (radius + w) || dy > (radius + h)) return false;

        PointF circleDistance = new PointF(Math.abs(circle.x - rect.left - w), Math.abs(circle.y - rect.top - h));

        if (circleDistance.x <= (w))
        {
            return true;
        }

        if (circleDistance.y <= (h))
        {
            return true;
        }

        double cornerDistanceSq = Math.pow(circleDistance.x - w, 2) + Math.pow(circleDistance.y - h, 2);

        return (cornerDistanceSq <= (Math.pow(radius, 2)));
    }
}
