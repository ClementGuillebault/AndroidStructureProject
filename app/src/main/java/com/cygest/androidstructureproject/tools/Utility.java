package com.cygest.androidstructureproject.tools;

import android.content.Context;
import android.content.res.ColorStateList;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome;

public class Utility {

    public static IconicsDrawable iconToDrawable(Context context, FontAwesome.Icon i, int sizePx, int color) {
        IconicsDrawable icon = new IconicsDrawable(context, i);

        icon.setSizeXPx(sizePx);
        icon.setSizeYPx(sizePx);

        icon.setColorList(ColorStateList.valueOf(context.getColor(color)));

        return icon;
    }
}
