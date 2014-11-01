package org.hellofdevelop.hc_dic.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class HeroProgress extends HeroBase {	

	@SerializedName("level")
	public int mLevel;

	@SerializedName("star")
	public int mStar;
	
	@SerializedName("reinforce")
	public Reinforce mReinforce;
	
	@SerializedName("item_progress_list")
	public final List<ItemProgress> mItemProgressList = new ArrayList<ItemProgress>(kHeroItemListMax);
	
	@SerializedName("skill_progress_list")
	public final List<SkillProgress> mSkillProgressList = new ArrayList<SkillProgress>(kHeroSkillListMax);
	
	
	public HeroProgress(HeroBase heroBase) {
		super(heroBase);
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
