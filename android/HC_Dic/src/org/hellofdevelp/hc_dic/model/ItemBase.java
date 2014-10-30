package org.hellofdevelp.hc_dic.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

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
	
	
	@SerializedName("name")
	public final String mName;
	
	@SerializedName("thumbnail_image_uri")
	public final String mThumbnailImageUri;
	
	@SerializedName("grade")
	public final Grade mGrade;

	// 성장표 필요
	
	
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

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
