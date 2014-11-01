package org.hellofdevelp.hc_dic.model;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class HeroesJson {

	@SerializedName("format")
	public String mFormat;

	@SerializedName("created")
	public String mCreated;

	@SerializedName("language")
	public String mLanguage;

	@SerializedName("hero_list")
	public List<HeroBase> mHeroes;

	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
