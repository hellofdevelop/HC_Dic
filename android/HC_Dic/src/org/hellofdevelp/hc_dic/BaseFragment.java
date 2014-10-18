package org.hellofdevelp.hc_dic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;

public class BaseFragment extends Fragment {
	
	private static final String TAG = BaseFragment.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;

	
	protected void startLoader(int loaderId, Bundle arg, LoaderManager.LoaderCallbacks<?> callback) 
	{
		LoaderManager loaderManager = getLoaderManager();
		if (loaderManager.getLoader(loaderId) == null)
		{
			loaderManager.initLoader(loaderId, arg, callback);
		}
		else
		{
			loaderManager.restartLoader(loaderId, arg, callback);
		}
	}
	
}
