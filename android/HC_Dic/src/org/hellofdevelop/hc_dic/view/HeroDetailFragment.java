package org.hellofdevelop.hc_dic.view;

import java.util.Iterator;
import java.util.List;

import org.hellofdevelop.hc_dic.Const;
import org.hellofdevelop.hc_dic.R;
import org.hellofdevelop.hc_dic.content.HeroesLoader;
import org.hellofdevelop.hc_dic.model.HeroBase;
import org.hellofdevelop.hc_dic.model.SkillBase;
import org.hellofdevelop.hc_dic.util.StringUtil;

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
import android.widget.TextView;

import com.androidquery.AQuery;

public class HeroDetailFragment extends BaseFragment
implements LoaderManager.LoaderCallbacks<List<HeroBase>> {
	
	private static final String TAG = HeroDetailFragment.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;
	
	
	private static final int kLOADER_ID_HEROES = 1;
	
	
	private AQuery mAQuery = null;
	
	private LayoutInflater mInflater = null;
	
	private String mHeroId = null;
	private HeroBase mHeroBase = null;
	
	private HeroSkillListAdapter mHeroSkillListAdapter = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_hero_detail, container, false);
		
		mAQuery = new AQuery(this.getActivity());
		
		mInflater = inflater;
		
		mHeroSkillListAdapter = new HeroSkillListAdapter(this.getActivity(), R.layout.layout_hero_skill);
		
		// set argument
		Bundle args = this.getArguments();
		if (args == null) {
			Log.e(TAG, "args is null");
			
			getFragmentManager().popBackStack();
			return null;
		}
		
		mHeroId = args.getString(HeroBase.kKEY_ID);
		if (DEBUG) Log.d(TAG, String.format("mHeroId=%s", mHeroId));
		
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
		
		View rootView = getView();
		
		switch (loaderId) {
		case kLOADER_ID_HEROES:
			initView(rootView);
			
			// find hero
			{
				Iterator<HeroBase> it = data.iterator();
				HeroBase currentHeroBase;
				
				while (it.hasNext()) {
					currentHeroBase = it.next();
					
					if (currentHeroBase.mId.equals(mHeroId)) {
						mHeroBase = currentHeroBase;
						break;
					}
				}
			}
			
			if (mHeroBase == null) {
				Log.e(TAG, "mHeroBase is null");
				break;
			}
			
			setView(rootView);
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
			initView(rootView);
			break;
			
		default:
			Log.e(TAG, String.format("unknown loaderId=%d", loaderId));
			break;
		}
	}
	
	private void initView(View rootView) {
		if (rootView == null) {
			Log.e(TAG, "rootView is null");
			return;
		}
		
		View heroLayout = rootView.findViewById(R.id.layout_hero);
		{
			View heroThumbnailLayout = heroLayout.findViewById(R.id.layout_hero_thumbnail);
			if (DEBUG) Log.d(TAG, String.format("heroThumbnailLayout=%s", heroThumbnailLayout));
			{
	
			}
		}
		
		GridView heroSkillGridView = (GridView) rootView.findViewById(R.id.view_hero_skill_grid);
		{
			heroSkillGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (DEBUG) Log.d(TAG, String.format("parent=%s view=%s position=%d id=%d", parent, view, position, id));
					
					/*
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
			        */
				}
				
			});
			heroSkillGridView.setAdapter(mHeroSkillListAdapter);
		}
	}
	
	private void setView(View rootView) {
		if (rootView == null) {
			Log.e(TAG, "rootView is null");
			return;
		}
		
		View heroLayout = rootView.findViewById(R.id.layout_hero);
		{
			View heroThumbnailLayout = heroLayout.findViewById(R.id.layout_hero_thumbnail);
			{
				ImageView heroThumbnailView = (ImageView) heroThumbnailLayout.findViewById(R.id.view_hero_thumbnail);
		        if (mHeroBase == null) {
		        	Log.e(TAG, "mHeroBase is null");
		        	
		        	rootView.setTag(R.id.hero_id, null);
		        	rootView.setTag(R.id.hero_base, null);
			        
			        // TODO: need place holder
			        mAQuery.id(heroThumbnailView).image("http://programmerguru.com/android-tutorial/wp-content/uploads/2014/01/aquery_introduction.png", false, true);
			        heroThumbnailView.setContentDescription(getString(R.string.empty));
		        } else {
			        if (DEBUG) Log.d(TAG, String.format("heroBase={ mName=%s }", mHeroBase.mName));
			        
			        rootView.setTag(R.id.hero_id, mHeroBase.mId);
			        rootView.setTag(R.id.hero_base, mHeroBase);
			        
			        if (StringUtil.isNullOrEmpty(mHeroBase.mThumbnailImageUri)) {
			        	// TODO: need place holder
				        mAQuery.id(heroThumbnailView).image("http://programmerguru.com/android-tutorial/wp-content/uploads/2014/01/aquery_introduction.png", false, true);
			        } else {
			        	// TODO: need place holder
				        //mAQuery.id(heroThumbnailView).image(mHeroBase.mThumbnailImageUri, false, true, 0, fallbackId);
				        mAQuery.id(heroThumbnailView).image(mHeroBase.mThumbnailImageUri, false, true);
			        }
			        heroThumbnailView.setContentDescription(mHeroBase.mName);
		        }
			}
			
			TextView heroNameValueView = (TextView) heroLayout.findViewById(R.id.view_hero_name_value);
			{
				heroNameValueView.setText(mHeroBase.mName);
			}
			TextView heroDescriptionValueView = (TextView) heroLayout.findViewById(R.id.view_hero_description_value);
			{
				heroDescriptionValueView.setText(mHeroBase.mDescription);
			}
		}
		
		GridView heroSkillGridView = (GridView) rootView.findViewById(R.id.view_hero_skill_grid);
		{
			mHeroSkillListAdapter.clear();
			
			mHeroSkillListAdapter.addAll(mHeroBase.mSkillList);
		}
	}
	
	private class HeroSkillListAdapter extends ArrayAdapter<SkillBase> {

		private int mResource = -1;
		
		public HeroSkillListAdapter(Context context, int resource) {
			super(context, resource);
			
			mResource = resource;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			SkillBase skillBase = null;
			
	        if (convertView == null) {
	            view = mInflater.inflate(mResource, parent, false);
	        } else {
	            view = convertView;
	        }
	        
	        skillBase = getItem(position);
	        
	        View heroSkillLayout = view;
	        {
		        ImageView skillThumbnailView = (ImageView) heroSkillLayout.findViewById(R.id.view_skill_thumbnail);
		        {
			        if (skillBase == null) {
			        	Log.e(TAG, "skillBase is null");
			        	
				        view.setTag(R.id.hero_id, null);
				        view.setTag(R.id.hero_base, null);
				        
				        // TODO: need place holder
				        mAQuery.id(skillThumbnailView).image("http://programmerguru.com/android-tutorial/wp-content/uploads/2014/01/aquery_introduction.png", false, true);
				        skillThumbnailView.setContentDescription(getString(R.string.empty));
			        } else {
				        if (DEBUG) Log.d(TAG, String.format("skillBase={ mName=%s }", skillBase.mName));
				        
				        view.setTag(R.id.skill_id, skillBase.mId);
				        view.setTag(R.id.skill_base, skillBase);
				        
				        if (StringUtil.isNullOrEmpty(skillBase.mThumbnailImageUri)) {
				        	// TODO: need place holder
					        mAQuery.id(skillThumbnailView).image("http://programmerguru.com/android-tutorial/wp-content/uploads/2014/01/aquery_introduction.png", false, true);
				        } else {
				        	// TODO: need place holder
					        //mAQuery.id(heroThumbnailView).image(skillBase.mThumbnailImageUri, false, true, 0, fallbackId);
					        mAQuery.id(skillThumbnailView).image(skillBase.mThumbnailImageUri, false, true);
				        }
				        skillThumbnailView.setContentDescription(skillBase.mName);
			        }
		        }
				
				TextView heroSkillNameValueView = (TextView) heroSkillLayout.findViewById(R.id.view_hero_skill_name_value);
				{
					heroSkillNameValueView.setText(skillBase.mName);
				}
				TextView heroSkillDescriptionValueView = (TextView) heroSkillLayout.findViewById(R.id.view_hero_skill_description_value);
				{
					heroSkillDescriptionValueView.setText(skillBase.mDescription);
				}
	        }

			return view;
		}
		
	}
	
}
