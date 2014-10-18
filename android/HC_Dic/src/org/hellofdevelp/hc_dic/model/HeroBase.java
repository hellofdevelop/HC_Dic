package org.hellofdevelp.hc_dic.model;

import java.util.ArrayList;
import java.util.List;

public class HeroBase {
	
	public enum Position {
		
		kFront,
		kCentral,
		kBack;
		
	}

	public enum Type {
		
		kStrength,
		kIntelligence,
		kAgility;
		
	}
	
	public static final int kHeroItemListMax = 6;
	
	public static final int kHeroSkillListMax = 4;
	
	public static final int kHeroStarMax = 3;
	
	public enum Reinforce {
		
		kGrey0,
		kGreen0,
		kGreen1,
		kBlue0,
		kBlue1,
		kBlue2,
		kViolet0,
		kViolet1,
		kViolet2,
		kViolet3;
		
	}
	
	
	public final long mId;
	
	// 이름
	public final String mName;
	
	// 소개
	public final String mIntroduction;
	
	// 위치 (프론트, 센터, 백)
	public final Position mPosition;
	
	// 타입 (스트렝스, 인텔리전트, 어질러티)
	public final Type mType;
	
	// 썸네일 이미지
	public final String mThumbnailImageUri;
	
	// 포트레이트 이미지
	public final String mPortraitImageUri;
	
	// 스킬종류 (4)
	public final List<SkillBase> mSkillList = new ArrayList<SkillBase>(kHeroSkillListMax);
	
	// 성장표 (레벨과 별에 따라 스트렝스, 인텔리전트, 어질러티 성장비율 변경)
	
	
	public HeroBase(long id,
			String name, String introduction,
			Position position, Type type,
			String thumbnailImageUri, String portraitImageUri) {
		mId = id;
		
		mName = name;
		mIntroduction = introduction;
		
		mPosition = position;
		mType = type;
		
		mThumbnailImageUri = thumbnailImageUri;
		mPortraitImageUri = portraitImageUri;
	}
	
	public HeroBase(HeroBase heroBase) {
		this(heroBase.mId,
				heroBase.mName, heroBase.mIntroduction,
				heroBase.mPosition, heroBase.mType,
				heroBase.mThumbnailImageUri, heroBase.mPortraitImageUri);
	}
	
}
