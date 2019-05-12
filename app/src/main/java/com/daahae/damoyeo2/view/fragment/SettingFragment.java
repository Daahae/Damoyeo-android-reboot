package com.daahae.damoyeo2.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.view.VersionDialog;
import com.daahae.damoyeo2.view.activity.InterestActivity;
import com.daahae.damoyeo2.view.activity.MypageActivity;

public class SettingFragment extends PreferenceFragmentCompat {

    private final String TAG = "SETTING_FRAGMENT";

    private Preference connectedAccount;
    private Preference changeInterest;
    private Preference appVersion;

    public static SettingFragment getInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_settings);

        connectedAccount = findPreference("connectedAccount");
        connectedAccount.setOnPreferenceClickListener(onPreferenceClickListener);

        changeInterest = findPreference("changeInterest");
        changeInterest.setOnPreferenceClickListener(onPreferenceClickListener);

        appVersion = findPreference("appVersion");
        appVersion.setOnPreferenceClickListener(onPreferenceClickListener);
    }

    private Preference.OnPreferenceClickListener onPreferenceClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            switch (preference.getKey()) {
                case "connectedAccount":
                    Intent intentMypage = new Intent(getActivity(), MypageActivity.class);
                    startActivity(intentMypage);
                    break;

                case "changeInterest":
                    Intent intentInterest = new Intent(getActivity(), InterestActivity.class);
                    intentInterest.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentInterest);
                    break;

                case "appVersion":
                    VersionDialog dialog = new VersionDialog(getActivity());
                    dialog.show();
                    break;
            }
            return false;
        }
    };
}
