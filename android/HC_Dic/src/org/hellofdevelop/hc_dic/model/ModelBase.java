package org.hellofdevelop.hc_dic.model;

import com.google.gson.Gson;
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
	
	public static final String kKEY_MODEL_TYPE = "model_type";
	public static final String kKEY_ID = "id";
	
	
	@SerializedName(kKEY_MODEL_TYPE)
	public final ModelType mModelType;
	
	@SerializedName(kKEY_ID)
	public final String mId;
	
	
	public ModelBase(ModelType modelType, String id) {
		mModelType = modelType;
		mId = id;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
