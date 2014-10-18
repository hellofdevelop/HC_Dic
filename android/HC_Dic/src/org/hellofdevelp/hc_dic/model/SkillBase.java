package org.hellofdevelp.hc_dic.model;

public class SkillBase {

	public final long mId;
	
	// 이름
	public final String mName;
	
	// 소개
	public final String mIntroduction;
	
	// 썸네일 이미지
	public final String mThumbnailImageUri;
	
	// 성장표

	
	public SkillBase(long id,
			String name, String introduction,
			String thumbnailImageUri) {
		mId = id;
		
		mName = name;
		mIntroduction = introduction;
		
		mThumbnailImageUri = thumbnailImageUri;
	}
	
	public SkillBase(SkillBase skillBase) {
		this(skillBase.mId,
				skillBase.mName, skillBase.mIntroduction,
				skillBase.mThumbnailImageUri);
	}
	
}
