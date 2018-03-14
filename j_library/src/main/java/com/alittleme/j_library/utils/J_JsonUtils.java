package com.alittleme.j_library.utils;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guojie on 2018/1/30.
 * Json相关工具类
 */
public class J_JsonUtils {
    public static List<JSONObject> findAsList(JSONObject jsonObject, String name) {
        List<JSONObject> currList = new ArrayList<>();
        JSONArray jsonArray = jsonObject.optJSONArray(name);
        for (int i = 0; i < jsonArray.length(); i++) {
            currList.add(jsonArray.optJSONObject(i));
        }
        return currList;
    }
}
