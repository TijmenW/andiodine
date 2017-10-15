package org.xapek.andiodine.preferences;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

public abstract class AbstractPreference {
    public static final String TAG = "Preference";
    private final String mKey;
    private final ContentValuesStore mPreferenceActivity;
    private final String mTitle;
    private final int mHelpMsgId;

    public AbstractPreference(ContentValuesStore preferenceActivity, String title, int helpResId, String key) {
        mPreferenceActivity = preferenceActivity;
        mTitle = title;
        mHelpMsgId = helpResId;
        mKey = key;
    }

    public abstract @NonNull View getListItemView(Context context);

    //public abstract void updateValueInView();

    public void persist(final String value) {
        Log.d(TAG, String.format("persist String %s -> %s", mKey, value));
        mPreferenceActivity.CVput(mKey, value);
    }

    public void persist(final boolean value) {
        Log.d(TAG, String.format("persist boolean %s -> %s", mKey, "" + value));
        mPreferenceActivity.CVput(mKey, value ? 1 : 0);
    }

    public boolean getAsBoolean() {
        return mPreferenceActivity.CVgetAsInteger(mKey) != null
                && mPreferenceActivity.CVgetAsInteger(mKey) == 1;
    }

    public String getAsString() {
        return mPreferenceActivity.CVgetAsString(mKey);
    }

    public String getTitle() {
        return mTitle;
    }

    public String getMessage() {
        return mPreferenceActivity.getResources().getString(mHelpMsgId);
    }


}
