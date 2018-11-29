package dpaw.com.storagetrac.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Helper class for Drawables.
 */
public class DrawableHelper {

    /**
     * Returns a drawable by name.
     * @param c context of the application
     * @param ImageName the name of the drawable
     * @return the drawable object if found
     */
    public static Drawable GetDrawable(Context c, String ImageName) {
        return c.getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));
    }

    /**
     * Converts a drawable to a bitmap, and returns that bitmap.
     * @param drawable the drawable to convert
     * @return the drawable as a bitmap
     */
    public static Bitmap DrawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
