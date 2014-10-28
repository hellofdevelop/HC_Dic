package org.hellofdevelp.hc_dic.model;

import com.google.gson.annotations.SerializedName;

public abstract class ModelBase {

	public enum ModelType {
		
		kHero("Hero"),
		kSkill("Skill"),
		kItem("Item");
		
		
		public final String mCode;
		
		private ModelType(String code) {
			mCode = code;
		}
		
	}
	
	
	@SerializedName("model_type")
	public final ModelType mModelType;
	
	@SerializedName("id")
	public final String mId;
	
	
	public ModelBase(ModelType modelType, String id) {
		mModelType = modelType;
		mId = id;
	}
	
}
