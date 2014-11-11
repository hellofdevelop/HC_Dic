package org.hellofdevelop.hc_dic.view;

import org.hellofdevelop.hc_dic.Const;
import org.hellofdevelop.hc_dic.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class HeroActivity extends BaseActivity {

	private static final String TAG = HeroActivity.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;

	private InterstitialAd mInterstitialAd = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hero);

		// open HeroList
		{
			Fragment fragment = new HeroListFragment();

			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();
			transaction.replace(R.id.fragment_container, fragment);
			transaction.commit();
		}

		initInterstitialAd();
	}

	@Override
	protected void onResume() {
		super.onResume();

		displayInterstitialAd();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (DEBUG) Log.v(TAG, String.format("item=%s", item));

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.action_heroes: {
			return true;
		}
		case R.id.action_video: {
			Intent intent = new Intent(this, VideoActivity.class);
			startActivity(intent);
			return true;
		}
		case R.id.action_settings: {
			return true;
		}
		case R.id.action_about: {
			return true;
		}
		default: {
			Log.e(TAG, String.format("unknown id=%d", id));
		}
		}

		return super.onOptionsItemSelected(item);
	}

	private void initInterstitialAd() {
		// load interstitial
		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId(getString(R.string.adunit_hero_resume_interstitial));
		mInterstitialAd.setAdListener(new AdListener() {

			@Override
			public void onAdLoaded() {
				if (DEBUG) Log.v(TAG, "");

			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				if (DEBUG) Log.v(TAG, String.format("errorCode=%d", errorCode));

			}

			@Override
			public void onAdOpened() {
				if (DEBUG) Log.v(TAG, "");

			}

			@Override
			public void onAdClosed() {
				if (DEBUG) Log.v(TAG, "");

			}

			@Override
			public void onAdLeftApplication() {
				if (DEBUG) Log.v(TAG, "");

			}

		});

		AdRequest adRequest = new AdRequest.Builder().build();

		mInterstitialAd.loadAd(adRequest);
	}

	private void displayInterstitialAd() {
		if (mInterstitialAd.isLoaded() == false) {
			return;
		}

		mInterstitialAd.show();
	}

}
