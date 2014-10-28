package org.hellofdevelp.hc_dic.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class JsonHeroes {

	@SerializedName("format")
	public String mFormat;

	@SerializedName("created")
	public String mCreated;

	@SerializedName("language")
	public String mLanguage;

	@SerializedName("heroes")
	public List<HeroBase> mHeroes;
	
}
