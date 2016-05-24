package com.wordpress.amindov.dodgerinio;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio Mindov on 5/23/2016.
 */
public class NAABB {
    private List<PointF> polyA, polyB;

    public void setA(List<PointF> poly) {
        polyA = poly;
    }

    public void setA(RectF rect) {
        polyA = new ArrayList<>(4);
        polyA.add(new PointF(rect.left, rect.top));
        polyA.add(new PointF(rect.left, rect.bottom));
        polyA.add(new PointF(rect.right, rect.top));
        polyA.add(new PointF(rect.right, rect.bottom));
    }

    public void setB(List<PointF> poly) {
        polyB = poly;
    }

    public void setB(RectF rect) {
        polyB = new ArrayList<>(4);
        polyB.add(new PointF(rect.left, rect.top));
        polyB.add(new PointF(rect.left, rect.bottom));
        polyB.add(new PointF(rect.right, rect.top));
        polyB.add(new PointF(rect.right, rect.bottom));
    }

    boolean isPolygonsIntersecting() {
        for (int x=0; x<2; x++) {
            List<PointF> polygon = (x==0) ? polyA : polyB;

            for (int i1=0; i1<polygon.size(); i1++) {
                int   i2 = (i1 + 1) % polygon.size();
                PointF p1 = polygon.get(i1);
                PointF p2 = polygon.get(i2);

                PointF normal = new PointF(p2.y - p1.y, p1.x - p2.x);

                double minA = Double.POSITIVE_INFINITY;
                double maxA = Double.NEGATIVE_INFINITY;

                for (PointF p : polyA) {
                    double projected = normal.x * p.x + normal.y * p.y;

                    if (projected < minA) {
                        minA = projected;
                    }

                    if (projected > maxA) {
                        maxA = projected;
                    }
                }

                double minB = Double.POSITIVE_INFINITY;
                double maxB = Double.NEGATIVE_INFINITY;

                for (PointF p : polyB)
                {
                    double projected = normal.x * p.x + normal.y * p.y;

                    if (projected < minB)
                        minB = projected;
                    if (projected > maxB)
                        maxB = projected;
                }

                if (maxA < minB || maxB < minA)
                    return false;
            }
        }

        return true;
    }
}
