package org.hellofdevelp.hc_dic.model;

import java.util.ArrayList;
import java.util.List;

public class HeroProgress extends HeroBase {	

	// 레벨
	public int mLevel;

	// 별 (1 ~ 3)
	public int mStar;
	
	// 강화 단계 (회색, 녹색, 녹색+1, 파랑, 파랑+1, 파랑+2, 보라, 보라+1, 보라+2, 보라+3)
	public Reinforce mReinforce;
	
	// 장비 (6)
	public final List<ItemProgress> mItemProgressList = new ArrayList<ItemProgress>(kHeroItemListMax);
	
	// 스킬레벨 (4)
	public final List<SkillProgress> mSkillProgressList = new ArrayList<SkillProgress>(kHeroSkillListMax);
	
	// 현재 스탯
	
	
	public HeroProgress(HeroBase heroBase) {
		super(heroBase);
	}

}
