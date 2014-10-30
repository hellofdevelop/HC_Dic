package org.hellofdevelp.hc_dic;

import java.util.List;

import org.hellofdevelp.hc_dic.content.HeroesLoader;
import org.hellofdevelp.hc_dic.model.HeroBase;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HeroDetailFragment extends BaseFragment
implements LoaderManager.LoaderCallbacks<List<HeroBase>> {
	
	private static final String TAG = HeroListFragment.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;
	
	
	private static final int kLOADER_ID_HEROES = 1;
	
	private LayoutInflater mInflater = null;
	
	private String mHeroId = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_hero_detail, container, false);
		
		mInflater = inflater;
		
		// set argument
		Bundle args = this.getArguments();
		if (args == null) {
			Log.e(TAG, "args is null");
			
			getFragmentManager().popBackStack();
			return null;
		}
		
		mHeroId = args.getString(HeroBase.kKEY_ID);
		
		// init view
		{

		}
		
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

	@Override
	public void onResume() {
		super.onResume();

		super.startLoader(kLOADER_ID_HEROES, null, this);
	}

	@Override
	public void onPause() {
		super.onPause();
		
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		
	}
	
	@Override
	public Loader<List<HeroBase>> onCreateLoader(int loaderId, Bundle args) {
		if (DEBUG) Log.v(TAG, String.format("loaderId=%d args=%s", loaderId, args));
		
		switch (loaderId) {
		case kLOADER_ID_HEROES:
			return new HeroesLoader(this.getActivity());
			//break;
			
		default:
			Log.e(TAG, String.format("unknown loaderId=%d", loaderId));
			break;
		}
		
		return null;
	}

	@Override
	public void onLoadFinished(Loader<List<HeroBase>> loader, List<HeroBase> data) {
		if (DEBUG) Log.v(TAG, String.format("loader=%s data=%s", loader, data));
		
		int loaderId = loader.getId();
		
		switch (loaderId) {
		case kLOADER_ID_HEROES:

			break;
			
		default:
			Log.e(TAG, String.format("unknown loaderId=%d", loaderId));
			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<List<HeroBase>> loader) {
		if (DEBUG) Log.v(TAG, String.format("loader=%s", loader));
		
		int loaderId = loader.getId();
		
		switch (loaderId) {
		case kLOADER_ID_HEROES:

			break;
			
		default:
			Log.e(TAG, String.format("unknown loaderId=%d", loaderId));
			break;
		}
	}

}
