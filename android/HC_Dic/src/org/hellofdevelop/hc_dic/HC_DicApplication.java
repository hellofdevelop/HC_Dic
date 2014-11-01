package org.hellofdevelop.hc_dic;

import java.util.HashMap;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class HC_DicApplication extends Application {
	
	private static final String TAG = HC_DicApplication.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		initGA();
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		
		
	}
	
	
	public enum TrackerName {
		//GLOBAL_TRACKER,
		APP_TRACKER,
	}
	
	private HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
	private int[] mSyncForTrackers = new int[1];
	
	private void initGA() {
		getTracker(TrackerName.APP_TRACKER);
	}

	public Tracker getTracker(TrackerName trackerName) {
		synchronized (mSyncForTrackers) {
			if (mTrackers.containsKey(trackerName) == false) {
				GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
				Tracker t = null;
				
				switch (trackerName) {
				case APP_TRACKER:
					t = analytics.newTracker(R.string.ga_app_trackingId);
					break;
					
				default:
					Log.e(TAG, String.format("unknown trackerName=%s", trackerName));
					return null;
					//break;
				}
				
				mTrackers.put(trackerName, t);
			}
			
			return mTrackers.get(trackerName);
		}
	}
	
}
