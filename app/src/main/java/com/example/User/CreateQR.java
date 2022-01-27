package com.example.User;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class CreateQR {

    public void create(WindowManager win, ImageView view, String id) {
        if (id.isEmpty()) {
        } else {
            // initializing a variable for default display.
            Display display = win.getDefaultDisplay();

            // creating a variable for point which
            // is to be displayed in QR Code.
            Point point = new Point();
            display.getSize(point);

            // getting width and
            // height of a point
            int width = point.x;
            int height = point.y;

            // generating dimension from width and height.
            int dimen = width == height ? width : height;
            //dimen = dimen * 3 / 4;

            // setting this dimensions inside our qr code
            // encoder to generate our qr code.
            QRGEncoder qrgEncoder = new QRGEncoder(String.valueOf(id), null, QRGContents.Type.TEXT, dimen);
            try {
                // getting our qrcode in the form of bitmap.
                Bitmap bitmap = qrgEncoder.encodeAsBitmap();
                // the bitmap is set inside our image
                // view using .setimagebitmap method.
                view.setImageBitmap(bitmap);

            } catch (WriterException e) {
                // this method is called for
                // exception handling.
                Log.e("Tag", e.toString());
            }
        }
    }
}
