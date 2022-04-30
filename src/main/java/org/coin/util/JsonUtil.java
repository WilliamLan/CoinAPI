package org.coin.util;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
	private static Gson sGson = null;

	/**
	 * Present an object is a generic type as a string.
	 *
	 * @return a json string
	 */
	public static <T> String toJson(Object obj, Type type) {
		return getGson().toJson(obj, type);
	}

	/**
	 * Present an object as a string.
	 * 
	 * @param obj The object to parse to the string.
	 * @return a json string
	 */
	public static String toJson(Object obj) {
		return getGson().toJson(obj);
	}

	private static Gson getGson() {
		if (sGson == null) {
			sGson = new GsonBuilder()
					.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.PROTECTED, Modifier.TRANSIENT)
					.disableHtmlEscaping()
					// Uncomment following line to make the json string prettier.
					// .setPrettyPrinting()
					.create();
		}

		return sGson;
	}

	/**
	 * convert T from json
	 * 
	 * @param jsonString
	 * @param klass
	 * 
	 * @return T
	 * 
	 */
	public static <T> T convertTFromJSON(String jsonString, Class<T> klass) {
		T t = null;
		try {
			t = getGson().fromJson(jsonString, klass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * get element of json
	 * 
	 * @param jsonString
	 * @return JsonElement
	 */
	public static JsonElement convertJsonTree(Object jsonObj) {
		return getGson().toJsonTree(jsonObj);
	}
}
