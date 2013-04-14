package com.doward.persistantcookies;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class CookieMapper {


    public Cookie toCookie(JSONObject jsonObject) throws JSONException {
        BasicClientCookie2 cookie = new BasicClientCookie2(jsonObject.getString("name"), jsonObject.getString("value"));
        cookie.setComment(jsonObject.getString("comment"));
        cookie.setCommentURL(jsonObject.getString("commentURL"));
        cookie.setDomain(jsonObject.getString("domain"));
        cookie.setPath(jsonObject.getString("path"));
        cookie.setSecure(jsonObject.getBoolean("secure"));
        cookie.setVersion(jsonObject.getInt("version"));
        cookie.setExpiryDate(new Date(jsonObject.getLong("expiryDate")));
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
        jsonObject.put("expiryDate", cookie.getExpiryDate().getTime());
        return jsonObject;
    }

}
