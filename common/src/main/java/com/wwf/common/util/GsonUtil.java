package com.wwf.common.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.wwf.common.bean.BaseBean;
import com.wwf.common.net.gson.DoubleDefault0Adapter;
import com.wwf.common.net.gson.IntegerDefault0Adapter;
import com.wwf.common.net.gson.LongDefault0Adapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GsonUtil {
    private static Gson mGson;
    static {
        mGson = new GsonBuilder()
                .serializeNulls()//序列化null
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(long.class, new LongDefault0Adapter())
                .create();
    }

    public static <T> T getWeakReference(T t) {
        WeakReference<T> tWeakReference = new WeakReference<T>(t);
        return tWeakReference.get();
    }


    /**
     * {
     * "state": true,
     * "data": {
     * "is_can_receive": false,
     * "activity_img_url": "http://res.webi.com.cn/webi_app/newcomer_package.png"
     * },
     * "error": "",
     * "error_key": ""
     * }解析类似以上json的数据, data为list或者object都支持,
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> BaseBean<T> parseJson2Bean(String json, Class<T> clazz) {
        BaseBean<T> tBaseBean = new BaseBean<>();
        List<T> list = null;
        try {
            JSONObject jsonObject = new JSONObject(json);

            String data = jsonObject.optString("data");
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            tBaseBean.setCode(code);
            tBaseBean.setMsg(msg);

            if (!TextUtils.isEmpty(data)) {
                Object jsonTokener = new JSONTokener(data).nextValue();
                if (jsonTokener instanceof JSONObject) {//对象型
                    T t = parseJsonToBean(data, clazz);
                    tBaseBean.setData(t);
                } else if (jsonTokener instanceof JSONArray) {
                    list = jsonToArrayList(data, clazz);
                    tBaseBean.setDataList(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tBaseBean;
    }

    public static <T> T parseJsonToBean(String json, Class<T> clazz) {
        T info = mGson.fromJson(json, clazz);
        return info;
    }

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = mGson.fromJson(json, type);
        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(mGson.fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

}
