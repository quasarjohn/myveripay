package com.berstek.myveripy.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

public class CustomImageUtils {

    public Bitmap blurRenderScript(Bitmap smallBitmap, int radius, Context context) {

        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }

    private Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

    public void blurImage(final Activity context, final String url, final ImageView imageView, final boolean updateStatusBarColor) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap myBitmap = Glide.with(context)
                            .load(url)
                            .asBitmap()
                            .into(500, 500)
                            .get();

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(myBitmap);

                            BitmapDrawable img = (BitmapDrawable) imageView.getDrawable();
                            Bitmap bitmap = img.getBitmap();
                            Bitmap blurred = new CustomImageUtils().blurRenderScript(bitmap,
                                    15, context);
                            imageView.setImageBitmap(blurred);

                            imageView.setColorFilter(Color.rgb(123, 123, 123),
                                    android.graphics.PorterDuff.Mode.MULTIPLY);

                            if (updateStatusBarColor) {
                                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();

                                Bitmap newBitmap = Bitmap.createScaledBitmap(drawable.getBitmap(), 1, 1, true);
                                final int color = newBitmap.getPixel(0, 0);
                                newBitmap.recycle();

                                context.getWindow().setStatusBarColor(color);
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }

}
