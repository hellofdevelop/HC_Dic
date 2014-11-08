package org.hellofdevelop.hc_dic.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class HeroBase extends ModelBase {
	
	public enum Position {
		
		Front,
		Central,
		Back;
		
		public static List<String> getNameList() {
			Position[] values = Position.values();
			
			List<String> names = new ArrayList<String>();
			for (int i = 0; i < values.length; i++) {
				names.add(values[i].name());
			}
			
			return names;
		}
		
	}

	public enum Type {
		
		Strength,
		Intelligence,
		Agility;
		
		public static List<String> getNameList() {
			Type[] values = Type.values();
			
			List<String> names = new ArrayList<String>();
			for (int i = 0; i < values.length; i++) {
				names.add(values[i].name());
			}
			
			return names;
		}
		
	}
	
	public static final int kHeroItemListMax = 6;
	
	public static final int kHeroSkillListMax = 4;
	
	public static final int kHeroStarMax = 3;
	
	public enum Reinforce {
		
		kGrey0,
		kGreen0,
		kGreen1,
		kBlue0,
		kBlue1,
		kBlue2,
		kViolet0,
		kViolet1,
		kViolet2,
		kViolet3;
		
	}
	
	
	@SerializedName("name")
	public final String mName;
	
	@SerializedName("description")
	public final String mDescription;
	
	@SerializedName("position")
	public final Position mPosition;
	
	@SerializedName("type")
	public final Type mType;
	
	@SerializedName("thumbnail_image_uri")
	public final String mThumbnailImageUri;
	
	@SerializedName("portrait_image_uri")
	public final String mPortraitImageUri;
	
	@SerializedName("skill_list")
	public final List<SkillBase> mSkillList = new ArrayList<SkillBase>(kHeroSkillListMax);
	
	// TODO: 성장표 필요
	
	
	public HeroBase(String id,
			String name, String description,
			Position position, Type type,
			String thumbnailImageUri, String portraitImageUri) {
		super(ModelBase.ModelType.kHero, id);
		
		mName = name;
		mDescription = description;
		
		mPosition = position;
		mType = type;
		
		mThumbnailImageUri = thumbnailImageUri;
		mPortraitImageUri = portraitImageUri;
	}
	
	public HeroBase(HeroBase heroBase) {
		this(heroBase.mId,
				heroBase.mName, heroBase.mDescription,
				heroBase.mPosition, heroBase.mType,
				heroBase.mThumbnailImageUri, heroBase.mPortraitImageUri);
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
