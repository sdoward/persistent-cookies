package com.doward.persistantcookies;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;

 class CookieMapper {

    public Cookie toCookie(JSONObject jsonObject) throws JSONException {
        BasicClientCookie2 cookie = new BasicClientCookie2(jsonObject.getString("name"), jsonObject.getString("value"));
        cookie.setComment(jsonObject.optString("comment"));
        cookie.setCommentURL(jsonObject.optString("commentURL"));
        cookie.setDomain(jsonObject.optString("domain"));
        cookie.setPath(jsonObject.optString("path"));
        cookie.setSecure(jsonObject.optBoolean("secure"));
        cookie.setVersion(jsonObject.optInt("version"));
        cookie.setExpiryDate(new Date(jsonObject.optLong("expiryDate")));
        if (jsonObject.has("ports")) {
            cookie.setPorts(JSONArrayToArray(jsonObject.optJSONArray("ports")));
        }
        return cookie;
    }

    public JSONObject toJSONObject(Cookie cookie) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", cookie.getName());
        jsonObject.put("value", cookie.getValue());
        jsonObject.put("comment", cookie.getComment());
        jsonObject.put("commentURL", cookie.getCommentURL());
        jsonObject.put("domain", cookie.getDomain());
        jsonObject.put("path", cookie.getPath());
        jsonObject.put("secure", cookie.isSecure());
        jsonObject.put("version", cookie.getVersion());
        if (cookie.getExpiryDate() != null) {
            jsonObject.put("expiryDate", cookie.getExpiryDate().getTime());
        }
        if (cookie.getPorts() != null) {
            jsonObject.put("ports", arrayToJSONArray(cookie));
        }
        return jsonObject;
    }

    private JSONArray arrayToJSONArray(Cookie cookie) {
        List<Integer> ports = new ArrayList(Arrays.asList(cookie.getPorts()));
        return new JSONArray(ports);
    }

    private int[] JSONArrayToArray(JSONArray jsonArray) throws JSONException {
        int[] ports = new int[jsonArray.length()];
        for(int i = 0 ; i < jsonArray.length(); i++){
            ports[i] = jsonArray.getInt(i);
        }
        return ports;
    }

}
