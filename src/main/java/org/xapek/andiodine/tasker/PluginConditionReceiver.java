package org.xapek.andiodine.tasker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;


import com.twofortyfouram.locale.sdk.client.receiver.AbstractPluginConditionReceiver;

import org.xapek.andiodine.config.IodineConfiguration;

import static com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_SATISFIED;
import static com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_UNSATISFIED;
import static org.xapek.andiodine.tasker.TunnelStatus.GetAll;

/**
 * Created by tmw on 14-10-17.
 */

public class PluginConditionReceiver extends AbstractPluginConditionReceiver {
    @Override
    protected boolean isBundleValid(@NonNull Bundle bundle) {
        return PluginBundleValues.isBundleValid(bundle);
    }

    @Override
    protected boolean isAsync() {
        //todo: find out what it means
        return true;
    }

    @Override
    protected int getPluginConditionResult(@NonNull Context context, @NonNull Bundle bundle) {
        Log.e("I tasker","Read plugin");
        final String n = PluginBundleValues.getName(bundle);
        final boolean ShouldBeActive = PluginBundleValues.getActive(bundle);
        for (final IodineConfiguration a :
                GetAll()) {
            if (n.isEmpty()) {
                return boolToLocaleCondition(true, ShouldBeActive);
            } else if ((a != null)) {
                if (a.getName() != null) {
                    if (n.equals(a.getName())) {
                        return boolToLocaleCondition(true, ShouldBeActive);
                    }
                }
            }
        }
        return boolToLocaleCondition(false, ShouldBeActive);
    }

    int boolToLocaleCondition(boolean condition, boolean notInverse) {
        return (condition == notInverse) ? RESULT_CONDITION_SATISFIED : RESULT_CONDITION_UNSATISFIED;
    }
}
