package org.hellofdevelop.hc_dic.view;

import org.hellofdevelop.hc_dic.Const;
import org.hellofdevelop.hc_dic.R;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

public class YouTubePlayListFragment extends BaseFragment {
	
	private static final String TAG = YouTubePlayListFragment.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;
	
	
	private AQuery mAQuery = null;
	
	private LayoutInflater mInflater = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_youtube_playlist, container, false);

		mAQuery = new AQuery(this.getActivity());
		
		mInflater = inflater;
		
		//mHeroListAdapter = new HeroListAdapter(this.getActivity(), R.layout.layout_hero_thumbnail);
		
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

		//super.startLoader(kLOADER_ID_HEROES, null, this);
		{
			String baseUrl = "https://www.googleapis.com/youtube/v3";
			String apiKey = "AIzaSyB0ie-L57C9wCyE8fuVbJSiXU_G2w44BrI";
			String playlistId = "AIzaSyB0ie-L57C9wCyE8fuVbJSiXU_G2w44BrI";
			
			String uriString = String.format("%s/playlistItems?part=contentDetails&playlistId=%s&key=%s", baseUrl, playlistId, apiKey);

			mAQuery.ajax(uriString, JSONObject.class, new AjaxCallback<JSONObject>() {

				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status) {
					if (status.getCode() != 200) {
						Log.e(TAG, String.format("code=%d", status.getCode()));
						
						return;
					}
					
					if (DEBUG) Log.d(TAG, object.toString());
					
				}
				
			});
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		
	}
	
	private void initView(View rootView) {
		if (rootView == null) {
			Log.e(TAG, "rootView is null");
			return;
		}
		
	}

}
