package org.hellofdevelp.hc_dic;

import java.util.List;

import org.hellofdevelp.hc_dic.content.HeroesLoader;
import org.hellofdevelp.hc_dic.model.HeroBase;
import org.hellofdevelp.hc_dic.util.StringUtil;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.androidquery.AQuery;

public class HeroListFragment extends BaseFragment
	implements LoaderManager.LoaderCallbacks<List<HeroBase>> {
	
	private static final String TAG = HeroListFragment.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;
	
	
	private static final int kLOADER_ID_HEROES = 1;
	
	private LayoutInflater mInflater = null;
	
	private HeroListAdapter mHeroListAdapter = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_hero_list, container, false);
		
		rootView.setBackgroundColor(0x00ffff);
		
		mInflater = inflater;
		
		mHeroListAdapter = new HeroListAdapter(this.getActivity(), R.layout.view_hero_thumbnail);
		
		// init view
		{
			GridView heroGridView = (GridView) rootView.findViewById(R.id.view_hero_grid);
			heroGridView.setAdapter(mHeroListAdapter);
			heroGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (DEBUG) Log.d(TAG, String.format("parent=%s view=%s position=%d id=%d", parent, view, position, id));
					
				}
				
			});
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
			if (mHeroListAdapter == null) {
				Log.e(TAG, "mHeroListAdapter is null");
				break;
			}
			
			mHeroListAdapter.clear();

			mHeroListAdapter.addAll(data);
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
			mHeroListAdapter.clear();
			break;
			
		default:
			Log.e(TAG, String.format("unknown loaderId=%d", loaderId));
			break;
		}
	}
	
	private class HeroListAdapter extends ArrayAdapter<HeroBase> {
		
		private final AQuery mAQuery;

		private int mResource = -1;
		
		public HeroListAdapter(Context context, int resource) {
			super(context, resource);

			mAQuery = new AQuery(context);
			
			mResource = resource;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			HeroBase heroBase = null;
			
	        if (convertView == null) {
	            view = mInflater.inflate(mResource, parent, false);
	        } else {
	            view = convertView;
	        }
	        
	        heroBase = getItem(position);
	        if (heroBase == null) {
	        	Log.e(TAG, "heroBase is null");
	        	
		        // init view
		        ImageView heroThumbnailView = (ImageView) view.findViewById(R.id.view_hero_thumbnail);
		        // TODO: need place holder
		        //heroThumbnailView.setImageURI(Uri.parse("http://programmerguru.com/android-tutorial/wp-content/uploads/2014/01/aquery_introduction.png"));
		        mAQuery.id(heroThumbnailView).image("http://programmerguru.com/android-tutorial/wp-content/uploads/2014/01/aquery_introduction.png", false, true);
		        heroThumbnailView.setContentDescription(getString(R.string.empty));
	        } else {
		        if (DEBUG) Log.d(TAG, String.format("heroBase={ mName=%s }", heroBase.mName));
		        
		        // init view
		        ImageView heroThumbnailView = (ImageView) view.findViewById(R.id.view_hero_thumbnail);
		        
		        if (StringUtil.isNullOrEmpty(heroBase.mThumbnailImageUri)) {
		        	// TODO: need place holder
		        	//heroThumbnailView.setImageURI(Uri.parse("http://programmerguru.com/android-tutorial/wp-content/uploads/2014/01/aquery_introduction.png"));
			        mAQuery.id(heroThumbnailView).image("http://programmerguru.com/android-tutorial/wp-content/uploads/2014/01/aquery_introduction.png", false, true);
		        } else {
			        //heroThumbnailView.setImageURI(Uri.parse(heroBase.mThumbnailImageUri));
			        mAQuery.id(heroThumbnailView).image(heroBase.mThumbnailImageUri, false, true);
		        }
		        heroThumbnailView.setContentDescription(heroBase.mName);
	        }

			return view;
		}
		
	}

}
