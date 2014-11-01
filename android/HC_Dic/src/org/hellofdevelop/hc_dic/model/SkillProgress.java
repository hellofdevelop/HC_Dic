package org.hellofdevelop.hc_dic.model;

import com.google.gson.Gson;


public class SkillProgress extends SkillBase {

	public int mLevel;
	
	
	public SkillProgress(SkillBase skillBase) {
		super(skillBase);
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
