package org.xapek.andiodine.tasker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.twofortyfouram.assertion.BundleAssertions;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public final class PluginBundleValues {
    public static final String TAG = PluginBundleValues.class.getSimpleName();
    public static final String BundleString = "org.xapek.andiodine.tasker";

    public static final String BUNDLE_active = BundleString + ".condition.tunnel.extra.ACTIVE";

    public static final String BUNDLE_name = BundleString + ".condition.tunnel.extra.NAME";

    public static boolean isBundleValid(@Nullable final Bundle bundle) {
        if (null == bundle) {
            return false;
        }

        try {
            BundleAssertions.assertHasBoolean(bundle, BUNDLE_active);
            BundleAssertions.assertHasString(bundle, BUNDLE_name);
        } catch (final AssertionError e) {
            Log.e(TAG, e.toString()); //$NON-NLS-1$
            return false;
        }

        return true;
    }

    @NonNull
    public static Bundle generateBundle(@NonNull final Context context, final boolean isTunnelActive, final String name) {
        final Bundle result = new Bundle();
        result.putBoolean(BUNDLE_active, isTunnelActive);
        result.putString(BUNDLE_name, name);
        return result;
    }

    public static String getName(final Bundle bundle) {
        final String bName = bundle.getString(BUNDLE_name);
        final String none = "";
        if (bName == null)
            return none;
        if (bName.isEmpty())
            return none;
        Log.v(TAG,"return name");
        return bName;
    }

    public static boolean getActive(@NonNull final Bundle bundle) {
        boolean b1 = bundle.getBoolean(BUNDLE_active, false);
        boolean b2 = bundle.getBoolean(BUNDLE_active, true);
        if (b1 == b2)
            return b1;
        throw new IllegalStateException("Bundle without BUNDLE_name");
    }
}
