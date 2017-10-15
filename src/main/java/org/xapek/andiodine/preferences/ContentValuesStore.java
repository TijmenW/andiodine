package org.xapek.andiodine.preferences;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by tmw on 15-10-17.
 */

public interface ContentValuesStore {
    //public void setContentValues(final ContentValues contentValues);

    //public ContentValues getContentValues();

    public int CVgetAsInt(final String key);

    @Nullable
    public Integer CVgetAsInteger(@NonNull final String key);


    @Nullable
    public String CVgetAsString(@NonNull final String key);


    public void CVput(@NonNull String key, String value);

    public void CVput(@NonNull String key, int value);

    @NonNull
    public Resources getResources();
}
