package org.hellofdevelp.hc_dic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.hellofdevelp.hc_dic.model.JsonHeroes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

public class HeroListFragment extends BaseFragment {
	
	private static final String TAG = HeroListFragment.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_hero_list, container, false);
		
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		loadHeroes();
	}

	@Override
	public void onResume() {
		super.onResume();
		
	}

	@Override
	public void onPause() {
		super.onPause();
		
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		
	}
	
	private void loadHeroes() {
		InputStream is = getResources().openRawResource(R.raw.heroes);
		Gson gson = new Gson();

		try {
		    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		    
		    JsonHeroes jsonHeroes = gson.fromJson(reader, JsonHeroes.class);
		    if (DEBUG) Log.d(TAG, jsonHeroes.toString());
		} catch (Exception ex) {
			Log.e(TAG, "", ex);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception ex) {}
				is = null;
			}
		}
	}

}
