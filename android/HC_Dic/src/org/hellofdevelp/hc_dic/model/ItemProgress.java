package org.hellofdevelp.hc_dic.model;

import com.google.gson.Gson;

public class ItemProgress extends ItemBase {

	public int mReinforce;
	
	
	public ItemProgress(ItemBase itemBase) {
		super(itemBase);
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
