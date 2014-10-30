package org.hellofdevelp.hc_dic.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class SkillBase extends ModelBase {

	@SerializedName("name")
	public final String mName;
	
	@SerializedName("description")
	public final String mDescription;
	
	@SerializedName("thumbnail_image_uri")
	public final String mThumbnailImageUri;
	
	// 성장표

	
	public SkillBase(String id,
			String name, String description,
			String thumbnailImageUri) {
		super(ModelBase.ModelType.kSkill, id);
		
		mName = name;
		mDescription = description;
		
		mThumbnailImageUri = thumbnailImageUri;
	}
	
	public SkillBase(SkillBase skillBase) {
		this(skillBase.mId,
				skillBase.mName, skillBase.mDescription,
				skillBase.mThumbnailImageUri);
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
