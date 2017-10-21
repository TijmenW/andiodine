package org.xapek.andiodine.tasker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.twofortyfouram.assertion.BundleAssertions;

import net.jcip.annotations.ThreadSafe;

import java.security.InvalidKeyException;
import java.util.Locale;

@ThreadSafe
public final class PluginBundleValues {
    public static final String TAG = "Andiodine." + PluginBundleValues.class.getSimpleName();
    public static final String BundleString = "org.xapek.andiodine.tasker";

    public static final String BUNDLE_active = BundleString + ".condition.tunnel.extra.ACTIVE";

    public static final String BUNDLE_name = BundleString + ".condition.tunnel.extra.NAME";

    public static boolean isBundleValid(@Nullable final Bundle bundle) {
        if (null == bundle) {
            return false;
        }

        try {
            getActive(bundle);
            BundleAssertions.assertHasString(bundle, BUNDLE_name);
        } catch (final AssertionError e) {
            Log.e(TAG, e.toString()); //$NON-NLS-1$
            return false;
        } catch (final IllegalArgumentException e) {
            Log.e(TAG, e.toString()); //$NON-NLS-1$
            return false;
        }
        return true;
    }

    @NonNull
    public static Bundle generateBundle(@NonNull final Context context, final boolean isTunnelActive, final String name) {
        final Bundle result = new Bundle(2);
        result.putBoolean(BUNDLE_active, isTunnelActive);
        result.putString(BUNDLE_name, name);
        return result;
    }

    @NonNull
    public static String getName(@Nullable final Bundle bundle) {
        final String bName = (bundle == null) ? null : bundle.getString(BUNDLE_name);
        final String none = "";
        if (bName == null)
            return none;
        if (bName.isEmpty())
            return none;
        Log.v(TAG, "return name");
        return bName;
    }

    public static boolean getActive(@NonNull final Bundle bundle) throws IllegalArgumentException {
        try {
            return getBooleanInt(bundle, BUNDLE_active);
        } catch (Exception e) {
            throw new IllegalArgumentException("Bundle without BUNDLE_name: " + e.toString());
        }
    }

    public static boolean getBooleanInt(@NonNull final Bundle bundle, @NonNull final String key) throws InvalidKeyException {
        final boolean b1 = bundle.getBoolean(key, false);
        final boolean b2 = bundle.getBoolean(key, true);
        if (b1 == b2)
            return b1;
        else {
            final int bInt = bundle.getInt(key, 2);
            switch (bInt) {
                case 0:
                    return false;
                case 1:
                    return true;
                default:
                    throw new InvalidKeyException(String.format(new Locale("en", "UK"), "No boolean with key %s", key));
            }
        }
    }

    @Nullable
    public static Integer getInteger(@NonNull final Bundle bundle, @NonNull final String key) {
        int v;
        v = bundle.getInt(key, 44);  //https://xkcd.com/221/
        if (v == 44) {
            v = bundle.getInt(key, 55);
            if (v == 55) {
                final boolean b1 = bundle.getBoolean(key, false);
                final boolean b2 = bundle.getBoolean(key, true);
                if (b1 == b2)
                    return b1 ? 1 : 0;
                return null;
            }
        }
        return v;
    }
}
