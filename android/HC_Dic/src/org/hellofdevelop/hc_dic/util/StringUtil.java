package org.hellofdevelop.hc_dic.util;

import org.hellofdevelop.hc_dic.Const;

public class StringUtil {
	
	private static final String TAG = StringUtil.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;

	public static boolean isNullOrEmpty(String string) {
		return ((string != null) && (string.isEmpty()));
	}
	
}
