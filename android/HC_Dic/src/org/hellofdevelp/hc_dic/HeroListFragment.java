package org.hellofdevelp.hc_dic;

import java.util.List;

import org.hellofdevelp.hc_dic.content.HeroesLoader;
import org.hellofdevelp.hc_dic.model.HeroBase;
import org.hellofdevelp.hc_dic.util.StringUtil;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
		
		mInflater = inflater;
		
		mHeroListAdapter = new HeroListAdapter(this.getActivity(), R.layout.view_hero_thumbnail);
		
		// init view
		{
			GridView heroGridView = (GridView) rootView.findViewById(R.id.view_hero_grid);
			heroGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (DEBUG) Log.d(TAG, String.format("parent=%s view=%s position=%d id=%d", parent, view, position, id));
					
			        String heroId = (String) view.getTag(R.id.hero_id);
			        HeroBase heroBase = (HeroBase) view.getTag(R.id.hero_base);
			        if (StringUtil.isNullOrEmpty(heroId) || (heroBase == null)) {
			        	Log.e(TAG, String.format("invalid tag: heroId=%s heroBase=%s", heroId, heroBase));
			        	
			        	return;
			        }

			        if (DEBUG) Log.d(TAG, String.format("heroId=%s heroBase=%s", heroId, heroBase));
			        
			        // open hero detail
			        {
				        Fragment fragment = new HeroDetailFragment();
				        Bundle args = new Bundle();
				        args.putString(HeroBase.kKEY_ID, heroId);
				        fragment.setArguments(args);
			            
			            FragmentManager fm = getFragmentManager();
			            FragmentTransaction transaction = fm.beginTransaction();
			            transaction.replace(R.id.fragment_container, fragment);
			            transaction.addToBackStack(null);
			            transaction.commit();
			        }
				}
				
			});
			heroGridView.setAdapter(mHeroListAdapter);
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
	        
	        ImageView heroThumbnailView = (ImageView) view.findViewById(R.id.view_hero_thumbnail);
	        if (heroBase == null) {
	        	Log.e(TAG, "heroBase is null");
	        	
		        view.setTag(R.id.hero_id, null);
		        view.setTag(R.id.hero_base, null);
		        
		        // TODO: need place holder
		        mAQuery.id(heroThumbnailView).image("http://programmerguru.com/android-tutorial/wp-content/uploads/2014/01/aquery_introduction.png", false, true);
		        heroThumbnailView.setContentDescription(getString(R.string.empty));
	        } else {
		        if (DEBUG) Log.d(TAG, String.format("heroBase={ mName=%s }", heroBase.mName));
		        
		        view.setTag(R.id.hero_id, heroBase.mId);
		        view.setTag(R.id.hero_base, heroBase);
		        
		        if (StringUtil.isNullOrEmpty(heroBase.mThumbnailImageUri)) {
		        	// TODO: need place holder
			        mAQuery.id(heroThumbnailView).image("http://programmerguru.com/android-tutorial/wp-content/uploads/2014/01/aquery_introduction.png", false, true);
		        } else {
		        	// TODO: need place holder
			        //mAQuery.id(heroThumbnailView).image(heroBase.mThumbnailImageUri, false, true, 0, fallbackId);
			        mAQuery.id(heroThumbnailView).image(heroBase.mThumbnailImageUri, false, true);
		        }
		        heroThumbnailView.setContentDescription(heroBase.mName);
	        }

			return view;
		}
		
	}

}
