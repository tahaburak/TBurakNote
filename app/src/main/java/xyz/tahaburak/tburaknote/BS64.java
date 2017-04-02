package xyz.tahaburak.tburaknote;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

/**
 * Created by tahaburaks on 02/04/2017.
 */

public class BS64 {
    public static String encode(String text) {// convert to base64
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        return base64;

    }

    public static String decode(String base64) {// convert from base64

        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        String text = new String(data, StandardCharsets.UTF_8);
        return text;
    }
}
