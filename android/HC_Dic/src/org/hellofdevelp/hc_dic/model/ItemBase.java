package org.hellofdevelp.hc_dic.model;

public class ItemBase extends ModelBase {

	public enum Grade {
		
		kGrey(0),
		kGreen(1),
		kBlue(3),
		kViolet(3); // TODO: don't know max reinforce of violet item

		
		public final int mReinforceMax;
		
		private Grade(int reinforceMax) {
			mReinforceMax = reinforceMax;
		}
		
	}
	
	
	// 이름
	public final String mName;
	
	// 썸네일 이미지
	public final String mThumbnailImageUri;
	
	// 등급 (회색, 녹색, 파랑, 보라)
	public final Grade mGrade;
	
	// 필요 재료

	
	public ItemBase(String id,
			String name,
			String thumbnailImageUri,
			Grade grade) {
		super(ModelBase.ModelType.kItem, id);
		
		mName = name;
		
		mThumbnailImageUri = thumbnailImageUri;
		
		mGrade = grade;
	}
	
	public ItemBase(ItemBase itemBase) {
		this(itemBase.mId,
				itemBase.mName,
				itemBase.mThumbnailImageUri,
				itemBase.mGrade);
	}
	
}
