package main.dettrans.hacks.kwasi.com.detroithealthtransys;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

/**
 * Created by kwas7493 on 1/31/2017.
 */

public class CircularTransform implements Transformation{
    @Override
    public Bitmap transform(Bitmap source) {

        int minImageSize = Math.min(source.getWidth(), source.getHeight());

        int xPoint = (source.getWidth() - minImageSize)/2;
        int yPoint = (source.getHeight() - minImageSize)/2;

        Bitmap createBitmap = Bitmap.createBitmap(source, xPoint, yPoint, minImageSize, minImageSize);

        //clear reference to data if the source and created bitmap are not equal

        if(source != createBitmap)
            source.recycle();

        Bitmap bitmap = Bitmap.createBitmap(minImageSize, minImageSize, source.getConfig());

        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        BitmapShader bitmapShader = new BitmapShader(createBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
        paint.setAntiAlias(true);

        float radius = minImageSize/2;
        canvas.drawCircle(radius, radius, radius, paint);

        createBitmap.recycle();
        return bitmap;
    }



    @Override
    public String key() {
        return "Circle";
    }
}
