package org.xapek.andiodine.tasker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.twofortyfouram.assertion.BundleAssertions;
import com.twofortyfouram.locale.sdk.client.ui.activity.AbstractPluginActivity;

import net.jcip.annotations.ThreadSafe;

/**
 * Created by tmw on 14-10-17.
 */

public class EditActivity extends AbstractPluginActivity {

    @Override
    public boolean isBundleValid(@NonNull Bundle bundle) {
        return PluginBundleValues.isBundleValid(bundle);
    }

    @Override
    public void onPostCreateWithPreviousResult(@NonNull Bundle bundle, @NonNull String s) {

    }

    @Nullable
    @Override
    public Bundle getResultBundle() {
        //return PluginBundleValues.generateBundle(this,...,...);
        return null;
    }

    @NonNull
    @Override
    public String getResultBlurb(@NonNull Bundle bundle) {
        return null;
    }
}

