package uz.zokirbekov.e_eye.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class BitmapConverter {
    private static BitmapConverter instance;

    public static BitmapConverter getInstance()
    {
        if (instance == null)
            instance = new BitmapConverter();
        return instance;
    }

    public Bitmap byteArrayToBitmap(byte[] array)
    {
        if (array == null)
            return null;

        Bitmap decodedByte = BitmapFactory.decodeByteArray(array, 0, array.length);
        return decodedByte;
    }

    public byte[] bitmapToByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return byteArray;
    }

}
