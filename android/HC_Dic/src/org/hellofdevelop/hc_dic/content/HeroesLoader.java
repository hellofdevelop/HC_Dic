package org.hellofdevelop.hc_dic.content;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hellofdevelop.hc_dic.Const;
import org.hellofdevelop.hc_dic.R;
import org.hellofdevelop.hc_dic.model.HeroBase;
import org.hellofdevelop.hc_dic.model.HeroesJson;
import org.hellofdevelop.hc_dic.util.StringUtil;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.google.gson.Gson;

public class HeroesLoader extends AsyncTaskLoader<List<HeroBase>> {
	
	private static final String TAG = HeroesLoader.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;

	
	private String mHeroId = null;
	private HeroBase.HeroPosition mHeroPosition = null;
	private HeroBase.HeroType mHeroType = null;
	
	private List<HeroBase> mHeroes = null;
	
	public HeroesLoader(Context context) {
		super(context);
		
		// TODO: need observer
		// http://www.androiddesignpatterns.com/2012/08/implementing-loaders.html
	}
	
	public HeroesLoader(Context context, String heroId) {
		this(context);

		mHeroId = heroId;
	}
	
	public HeroesLoader(Context context, HeroBase.HeroPosition heroPosition, HeroBase.HeroType heroType) {
		this(context);
		
		mHeroPosition = heroPosition;
		mHeroType = heroType;
	}
	
	@Override
	public List<HeroBase> loadInBackground() {
		if (DEBUG) Log.v(TAG, "");

		List<HeroBase> heroes = null;
		
		HeroesJson jsonHeroes = loadJsonHeroes();
		
		if (StringUtil.isNullOrEmpty(mHeroId) == false) {
			if (DEBUG) Log.d(TAG, String.format("mHeroId=%s", mHeroId));
			
			heroes = new ArrayList<HeroBase>();
			
			Iterator<HeroBase> it = jsonHeroes.mHeroes.iterator();
			while (it.hasNext()) {
				HeroBase hero = it.next();
				if (hero.mId.compareTo(mHeroId) == 0) {
					heroes.add(hero);
				}
			}
		} else if ((mHeroPosition != null) || (mHeroType != null)) {
			if (DEBUG) Log.d(TAG, String.format("mHeroPosition=%s mHeroType=%s", mHeroPosition, mHeroType));
			
			heroes = new ArrayList<HeroBase>();
			
			Iterator<HeroBase> it = jsonHeroes.mHeroes.iterator();
			while (it.hasNext()) {
				HeroBase hero = it.next();
				if ((mHeroPosition != null) && (mHeroType != null)) {
					if ((hero.mHeroPosition == mHeroPosition) && (hero.mHeroType == mHeroType)) {
						heroes.add(hero);
					}
				} else if (mHeroPosition != null) {
					if (hero.mHeroPosition == mHeroPosition) {
						heroes.add(hero);
					}
				} else if (mHeroType != null) {
					if (hero.mHeroType == mHeroType) {
						heroes.add(hero);
					}
				}
			}
		} else {
			heroes = jsonHeroes.mHeroes;
		}
		
		return heroes;
	}

	@Override
	public void deliverResult(List<HeroBase> data) {
		if (DEBUG) Log.v(TAG, String.format("data=%s", data));
		
		super.deliverResult(data);
	}

	@Override
	protected void onStartLoading() {
		if (DEBUG) Log.v(TAG, "");

		if (mHeroes != null) {
			deliverResult(mHeroes);
		}
		
		if (takeContentChanged() || (mHeroes == null)) {
			forceLoad();
		}
	}

	@Override
	protected void onStopLoading() {
		if (DEBUG) Log.v(TAG, "");
		
		cancelLoad();
	}

	@Override
	protected void onReset() {
		if (DEBUG) Log.v(TAG, "");
		
		onStopLoading();
		
		if (mHeroes != null) {
			mHeroes = null;
		}
	}

	@Override
	public void onCanceled(List<HeroBase> data) {
		if (DEBUG) Log.v(TAG, String.format("data=%s", data));

		super.onCanceled(data);
		
		if (data != null) {
			data = null;
		}
	}

	private HeroesJson loadJsonHeroes() {
		HeroesJson jsonHeroes = null;
		
		InputStream is = getContext().getResources().openRawResource(R.raw.heroes);
		Gson gson = new Gson();

		try {
		    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		    
		    jsonHeroes = gson.fromJson(reader, HeroesJson.class);
		    if (DEBUG) Log.d(TAG, jsonHeroes.toString());
		} catch (Exception ex) {
			Log.e(TAG, "", ex);
			
			jsonHeroes = null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception ex) {}
				is = null;
			}
		}
		
		if (jsonHeroes != null) {
			if (DEBUG) Log.d(TAG, String.format("jsonHeroes=%s", jsonHeroes));
		}
		
		return jsonHeroes;
	}

}
