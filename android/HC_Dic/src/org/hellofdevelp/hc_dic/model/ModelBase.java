package org.hellofdevelp.hc_dic.model;

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
	
	
	public final ModelType mModelType;
	
	public final long mId;
	
	
	public ModelBase(ModelType modelType, long id) {
		mModelType = modelType;
		mId = id;
	}
	
}
