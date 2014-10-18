package org.hellofdevelp.hc_dic;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HeroListFragment extends BaseFragment
	implements LoaderManager.LoaderCallbacks<Cursor> {
	
	private static final String TAG = HeroListFragment.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;
	
	
	private static final int LOADER_ID_HERO_LIST = 100;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_hero_list, container, false);
		
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		
		startLoader();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
		View rootView = getView();
		if (rootView == null) {
			return null;
		}

		switch (loaderId) {
		case LOADER_ID_HERO_LIST:
			break;
			
		default:
			if (DEBUG) Log.d(TAG, "unknown loaderId=" + loaderId);
			break;
		}
		
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		View rootView = getView();
		if (rootView == null) {
			return;
		}
		
		int loaderId = loader.getId();

		switch (loaderId) {
		case LOADER_ID_HERO_LIST:
			break;
			
		default:
			if (DEBUG) Log.d(TAG, "unknown loaderId=" + loaderId);
			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		View rootView = getView();
		if (rootView == null) {
			return;
		}
		
		int loaderId = loader.getId();

		switch (loaderId) {
		case LOADER_ID_HERO_LIST:
			break;
			
		default:
			if (DEBUG) Log.d(TAG, "unknown loaderId=" + loaderId);
			break;
		}
	}
	
	private void startLoader() {
		super.startLoader(LOADER_ID_HERO_LIST, null, this);
	}

}
