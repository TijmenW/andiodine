package org.xapek.andiodine.tasker;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.twofortyfouram.locale.sdk.client.ui.activity.AbstractPluginActivity;

import org.xapek.andiodine.R;
import org.xapek.andiodine.config.ConfigDatabase;
import org.xapek.andiodine.config.IodineConfiguration;
import org.xapek.andiodine.preferences.BooleanPreference;
import org.xapek.andiodine.preferences.ContentValuesStore;
import org.xapek.andiodine.preferences.SpinnerPreference;
import org.xapek.andiodine.preferences.AbstractPreference;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.xapek.andiodine.tasker.PluginBundleValues.*;

/**
 * Created by tmw on 14-10-17.
 */

public class EditActivity extends AbstractPluginActivity implements ContentValuesStore {

    final public String TAG = this.getClass().getSimpleName();
    private ListView mList;
    private ConfigDatabase database;
    private List<IodineConfiguration> iodineConfigurations;
    private ArrayList<String> databaseNames;
    private LayoutInflater mInflater;
    private Spinner databaseSpinner;
    private ArrayAdapter<AbstractPreference> aAdapter;
    private ArrayAdapter<IodineConfiguration> ICAdapter;
    private Bundle mBundle;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isBundleValid(savedInstanceState))
            mBundle = savedInstanceState;
        else mBundle = generateBundle(this,true,"");

        setContentView(R.layout.pref);

        mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        database = new ConfigDatabase(this);
        iodineConfigurations = database.selectAll();
        databaseNames = new ArrayList<String>();
        for (IodineConfiguration iodineConfiguration : iodineConfigurations) {
            databaseNames.add(iodineConfiguration.getName());
        }

        createUI();
        /*databaseSpinner = new Spinner(this);

        ICAdapter = new ArrayAdapter<IodineConfiguration>(this,
                android.R.layout.simple_list_item_single_choice, android.R.id.text1,
                iodineConfigurations);
        //todo: string

        databaseSpinner.setAdapter(ICAdapter);

        final List<View> views = new ArrayList<View>();
        views.add(databaseSpinner);

        aAdapter = new ArrayAdapter<View>(this,
                R.layout.rowlayout,
                views) {
            @Override
            @NonNull
            public View getView(int position,
                                View convertView,
                                @NonNull ViewGroup parent) {
                final View item = this.getItem(position);
                return item;
            }
        };

        //android.R.layout.simple_spinner_dropdown_item;
        mList = ((ListView) findViewById(R.id.pref_list));
        mList.setAdapter(aAdapter
        );*/

    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }

    @Override
    public boolean isBundleValid(@NonNull Bundle bundle) {
        return PluginBundleValues.isBundleValid(bundle);
    }

    @Override
    public void onPostCreateWithPreviousResult(@NonNull final Bundle PreviousBundle, @NonNull final String s) {
        Log.i(TAG, "onPostCreateWithPreviousResult: string: " + s + "\nbundle:" + PreviousBundle.toString());
        if (isBundleValid(PreviousBundle)) {
            /*final String bn = getName(PreviousBundle);
            final int size = databaseNames.size();
            for (int i = 0; i < size; i++) {
                if (bn.equals(databaseNames.get(i))) {
                    mList.setItemChecked(i, true);
                    break;
                }
            }*/
            mBundle = PreviousBundle;
            createUI();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (!Objects.equals(getResultBlurb(PreviousBundle), s)) {
                    Log.e(TAG, "Bundle not equal to Blurb\nBundle:" + PreviousBundle.toString() + "\nBlurb" + s);
                }
            }
        } else Log.e(TAG, "Bundle invalid: " + PreviousBundle.toString());
    }

    private void createUI() {
        mList = ((ListView) findViewById(R.id.pref_list));
        final List<AbstractPreference> vl = new ArrayList<>();
        vl.add(new SpinnerPreference(this, BUNDLE_name, getResources().getString(R.string.pref_text_name_label), 0, databaseNames.toArray(new String[0])));
        vl.add(new BooleanPreference(this, getResources().getString(R.string.active), 0, BUNDLE_active));
        aAdapter = new ArrayAdapter<AbstractPreference>(this,
                R.layout.rowlayout,
                vl) {
            @Override
            @NonNull
            public View getView(int position,
                                View convertView,
                                @NonNull ViewGroup parent) {
                final View item = this.getItem(position).getListItemView(this.getContext());
                return item;
            }
        };
        mList.setAdapter(aAdapter);
    }

    ;

    @NonNull
    @Override
    public Bundle getResultBundle() {
//IodineConfiguration config = TunnelStatus.GetOne();
//if (config == null) return PluginBundleValues.generateBundle(this, false, "");
//return PluginBundleValues.generateBundle(this, true, config.getName());
        return (Bundle) mBundle.clone();
    }

    @NonNull
    @Override
    public String getResultBlurb(@NonNull Bundle bundle) {
        return getResources().getString(getActive(bundle)?R.string.active:R.string.inactive) + ':' + PluginBundleValues.getName(bundle);
    }

    @Override
    public int CVgetAsInt(String key) {
        return CVgetAsInteger(key);
    }

    @Nullable
    @Override
    public Integer CVgetAsInteger(String key) {
        return getInteger(mBundle,key);
    }

    @Override
    public String CVgetAsString(String key) {
        Object v = mBundle.get(key);
        if (v == null)
            return null;
        return v.toString();
    }

    @Override
    public void CVput(String key, String value) {
        mBundle.putString(key, value);
    }

    @Override
    public void CVput(String key, int value) {
        mBundle.putInt(key, value);
    }
}

