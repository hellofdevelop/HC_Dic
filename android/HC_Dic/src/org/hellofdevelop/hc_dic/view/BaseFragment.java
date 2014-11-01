package org.hellofdevelop.hc_dic.view;

import org.hellofdevelop.hc_dic.Const;
import org.hellofdevelop.hc_dic.HC_DicApplication;
import org.hellofdevelop.hc_dic.HC_DicApplication.TrackerName;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class BaseFragment extends Fragment {
	
	private static final String TAG = BaseFragment.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;


	@Override
	public void onResume() {
		super.onResume();

		{
			Tracker t = ((HC_DicApplication) this.getActivity().getApplication()).getTracker(TrackerName.APP_TRACKER);
			
			t.setScreenName(this.getClass().getSimpleName());
			
			t.send(new HitBuilders.AppViewBuilder().build());
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		
	}
	
	protected void startLoader(int loaderId, Bundle args, LoaderManager.LoaderCallbacks<?> callback) 
	{
		if (DEBUG) Log.v(TAG, String.format("loaderId=%d args=%s callback=%s", loaderId, args, callback));
		
		LoaderManager loaderManager = getLoaderManager();
		if (loaderManager.getLoader(loaderId) == null)
		{
			loaderManager.initLoader(loaderId, args, callback);
		}
		else
		{
			loaderManager.restartLoader(loaderId, args, callback);
		}
	}
	
}
