package com.doward.persistentcookies;

import android.content.SharedPreferences;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersistentCookieStore implements CookieStore {

    private SharedPreferences sharedPreferences;
    private CookieMapper cookieMapper = new CookieMapper();


    public PersistentCookieStore(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void addCookie(Cookie cookie) {
        List<Cookie> cookies = getCookiesFromStore();
        cookies.add(cookie);
        save(cookies);
    }

    @Override
    public List<Cookie> getCookies() {
        return getCookiesFromStore();
    }

    @Override
    public boolean clearExpired(Date date) {
        List<Cookie> cookies = getCookiesFromStore();
        List<Cookie> clearedCookies = new ArrayList<Cookie>(cookies.size());
        boolean cleared = false;
        for (Cookie cookie: cookies) {
            if (cookie.getExpiryDate().after(date)) {
                clearedCookies.add(cookie);
                cleared = true;
            }
        }
        save(clearedCookies);
        return cleared;
    }

    @Override
    public void clear() {
        sharedPreferences.edit().clear().commit();
    }

    private List<Cookie> getCookiesFromStore() {
        try {
            String jsonString =  sharedPreferences.getString("cookies", "");
            if (jsonString.equals("")) {
                return new ArrayList<Cookie>(0);
            } else {
                JSONArray jsonArray = new JSONArray(jsonString);
                List<Cookie> cookies = new ArrayList<Cookie>(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    cookies.add(cookieMapper.toCookie(jsonObject));
                }
                return cookies;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void save(List<Cookie> cookies) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (Cookie cookie1 : cookies) {
                jsonArray.put(cookieMapper.toJSONObject(cookie1));
            }
            sharedPreferences.edit().putString("cookies", jsonArray.toString()).commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
