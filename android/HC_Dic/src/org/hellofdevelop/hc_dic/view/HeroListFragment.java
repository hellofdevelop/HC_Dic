package org.hellofdevelop.hc_dic.view;

import java.util.ArrayList;
import java.util.List;

import org.hellofdevelop.hc_dic.Const;
import org.hellofdevelop.hc_dic.R;
import org.hellofdevelop.hc_dic.content.HeroesLoader;
import org.hellofdevelop.hc_dic.model.HeroBase;
import org.hellofdevelop.hc_dic.util.StringUtil;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HeroListFragment extends BaseFragment
	implements LoaderManager.LoaderCallbacks<List<HeroBase>> {
	
	private static final String TAG = HeroListFragment.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;
	
	
	private static final int kLOADER_ID_HEROES = 1;
	
	
	private AQuery mAQuery = null;
	
	private LayoutInflater mInflater = null;
	
	private HeroListAdapter mHeroListAdapter = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_hero_list, container, false);

		mAQuery = new AQuery(this.getActivity());
		
		mInflater = inflater;
		
		mHeroListAdapter = new HeroListAdapter(this.getActivity(), R.layout.layout_hero_thumbnail);
		
		initView(rootView);
		
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
		
		View rootView = getView();
		
		switch (loaderId) {
		case kLOADER_ID_HEROES:
			HeroBase.HeroPosition heroPosition = null;
			HeroBase.HeroType heroType = null;
			if (args != null) {
				String strHeroPosition = args.getString(HeroBase.HeroPosition.class.getSimpleName());
				if (StringUtil.isNullOrEmpty(strHeroPosition) == false) {
					heroPosition = HeroBase.HeroPosition.valueOf(strHeroPosition);
				}
				
				String strHeroType = args.getString(HeroBase.HeroType.class.getSimpleName());
				if (StringUtil.isNullOrEmpty(strHeroType) == false) {
					heroType = HeroBase.HeroType.valueOf(strHeroType);
				}
			}
			/*
			HeroBase.HeroPosition selectedPosition = getSelectedPosition(rootView);
			Log.d(TAG, String.format("selectedPosition=%s", selectedPosition));
			
			HeroBase.HeroType selectedType = getSelectedType(rootView);
			Log.d(TAG, String.format("selectedType=%s", selectedType));
			*/
			
			return new HeroesLoader(this.getActivity(), heroPosition, heroType);
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
		
		View rootView = getView();
		
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
		
		View rootView = getView();
		
		switch (loaderId) {
		case kLOADER_ID_HEROES:
			mHeroListAdapter.clear();
			break;
			
		default:
			Log.e(TAG, String.format("unknown loaderId=%d", loaderId));
			break;
		}
	}
	
	private void initView(final View rootView) {
		if (rootView == null) {
			Log.e(TAG, "rootView is null");
			return;
		}
		
		Spinner heroPositionSpinner = (Spinner) rootView.findViewById(R.id.spinner_hero_position);
		{
			List<String> positions = new ArrayList<String>();
			positions.add("All");
			positions.addAll(HeroBase.HeroPosition.getNameList());
			
			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),
					R.layout.layout_hero_position_spinner_item, R.id.view_hero_position,
					positions);
			heroPositionSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					if (DEBUG) Log.d(TAG, String.format("parent=%s view=%s position=%d id=%d", parent, view, position, id));

					HeroBase.HeroPosition selectedPosition = getSelectedPosition(rootView);
					HeroBase.HeroType selectedType = getSelectedType(rootView);
					Log.d(TAG, String.format("selectedPosition=%s selectedType", selectedPosition, selectedType));
					
					Bundle args = new Bundle();
					if (selectedPosition != null) {
						args.putString(HeroBase.HeroPosition.class.getSimpleName(), selectedPosition.name());
					}
					if (selectedType != null) {
						args.putString(HeroBase.HeroType.class.getSimpleName(), selectedType.name());
					}
					
					HeroListFragment.this.startLoader(kLOADER_ID_HEROES, args, HeroListFragment.this);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					if (DEBUG) Log.d(TAG, String.format("parent=%s", parent));
					
					// All
					HeroListFragment.this.startLoader(kLOADER_ID_HEROES, null, HeroListFragment.this);
				}

			});
			heroPositionSpinner.setAdapter(spinnerAdapter);
		}
		
		Spinner heroTypeSpinner = (Spinner) rootView.findViewById(R.id.spinner_hero_type);
		{
			List<String> types = new ArrayList<String>();
			types.add("All");
			types.addAll(HeroBase.HeroType.getNameList());
			
			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),
					R.layout.layout_hero_type_spinner_item, R.id.view_hero_type,
					types);
			heroTypeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					if (DEBUG) Log.d(TAG, String.format("parent=%s view=%s position=%d id=%d", parent, view, position, id));

					HeroBase.HeroPosition selectedPosition = getSelectedPosition(rootView);
					HeroBase.HeroType selectedType = getSelectedType(rootView);
					Log.d(TAG, String.format("selectedPosition=%s selectedType", selectedPosition, selectedType));
					
					Bundle args = new Bundle();
					if (selectedPosition != null) {
						args.putString(HeroBase.HeroPosition.class.getSimpleName(), selectedPosition.name());
					}
					if (selectedType != null) {
						args.putString(HeroBase.HeroType.class.getSimpleName(), selectedType.name());
					}
					
					HeroListFragment.this.startLoader(kLOADER_ID_HEROES, args, HeroListFragment.this);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					if (DEBUG) Log.d(TAG, String.format("parent=%s", parent));
					
					// All
					HeroListFragment.this.startLoader(kLOADER_ID_HEROES, null, HeroListFragment.this);
				}

			});
			heroTypeSpinner.setAdapter(spinnerAdapter);
		}
		
		GridView heroGridView = (GridView) rootView.findViewById(R.id.view_hero_grid);
		{
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
		
		AdView footerBannerAdView = (AdView) rootView.findViewById(R.id.adview_footer_banner);
		{
			AdRequest adRequest = new AdRequest.Builder()
					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
					.build();
			//footerBannerAdView.setAdSize(AdSize.SMART_BANNER);
			//footerBannerAdView.setAdUnitId(getString(R.string.adunit_hero_list_footer_banner));
			footerBannerAdView.setAdListener(new AdListener() {

				@Override
				public void onAdLoaded() {
					super.onAdLoaded();
					
					if (DEBUG) Log.d(TAG, "");
				}

				@Override
				public void onAdFailedToLoad(int errorCode) {
					super.onAdFailedToLoad(errorCode);
					
					Log.e(TAG, String.format("errorCode=%d", errorCode));
				}

				@Override
				public void onAdOpened() {
					super.onAdOpened();
					
					if (DEBUG) Log.d(TAG, "");
				}

				@Override
				public void onAdClosed() {
					super.onAdClosed();
					
					if (DEBUG) Log.d(TAG, "");
				}

				@Override
				public void onAdLeftApplication() {
					super.onAdLeftApplication();
					
					if (DEBUG) Log.d(TAG, "");
				}
				
			});
			footerBannerAdView.loadAd(adRequest);
		}
	}
	
	private HeroBase.HeroPosition getSelectedPosition(View rootView) {
		Spinner heroPositionSpinner = (Spinner) rootView.findViewById(R.id.spinner_hero_position);
		
		View view = heroPositionSpinner.getSelectedView();
		if (view == null) {
			Log.e(TAG, "view is null");
			return null;
		}

		TextView heroPositionView = (TextView) view.findViewById(R.id.view_hero_position);
		if (heroPositionView == null) {
			Log.e(TAG, String.format("invalid view=%s", view));
			
			return null;
		}
		
		HeroBase.HeroPosition selectedPosition;
		try {
			selectedPosition = HeroBase.HeroPosition.valueOf(String.valueOf(heroPositionView.getText()));
			Log.d(TAG, String.format("selectedPosition=%s", selectedPosition));
			
			return selectedPosition;
		} catch (IllegalArgumentException iae) {
			// All
			
			return null;
		}
	}
	
	private HeroBase.HeroType getSelectedType(View rootView) {
		Spinner heroTypeSpinner = (Spinner) rootView.findViewById(R.id.spinner_hero_type);
		
		View view = heroTypeSpinner.getSelectedView();
		if (view == null) {
			Log.e(TAG, "view is null");
			return null;
		}

		TextView heroTypeView = (TextView) view.findViewById(R.id.view_hero_type);
		if (heroTypeView == null) {
			Log.e(TAG, String.format("invalid view=%s", view));
			
			return null;
		}
		
		HeroBase.HeroType selectedType;
		try {
			selectedType = HeroBase.HeroType.valueOf(String.valueOf(heroTypeView.getText()));
			Log.d(TAG, String.format("selectedType=%s", selectedType));
			
			return selectedType;
		} catch (IllegalArgumentException iae) {
			// All
			
			return null;
		}
	}
	
	private class HeroListAdapter extends ArrayAdapter<HeroBase> {

		private int mResource = -1;
		
		public HeroListAdapter(Context context, int resource) {
			super(context, resource);
			
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
	        
	        {
		        ImageView heroThumbnailView = (ImageView) view.findViewById(R.id.view_hero_thumbnail);
		        {
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
		        }
	        }

			return view;
		}
		
	}

}
