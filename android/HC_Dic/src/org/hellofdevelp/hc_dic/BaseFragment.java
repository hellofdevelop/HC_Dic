package org.hellofdevelp.hc_dic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.util.Log;

public class BaseFragment extends Fragment {
	
	private static final String TAG = BaseFragment.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;

	
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
