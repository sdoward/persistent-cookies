package com.doward.persistantcookietest;

import com.doward.persistantcookies.PersistantCookieStore;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowPreferenceManager;
import junit.framework.Assert;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class PersistantCookieStoreTest {

    private PersistantCookieStore persistantCookieStore;

    private List<Cookie> cookies = new ArrayList<Cookie>(5);

    @Before
    public void setUp() {
        persistantCookieStore = new PersistantCookieStore(ShadowPreferenceManager.getDefaultSharedPreferences(Robolectric.application.getApplicationContext()));
        BasicClientCookie2 cookie = new BasicClientCookie2("cookie1", "value1");
        cookie.setComment("comment");
        cookie.setCommentURL("commentURL");
        cookie.setVersion(3);
        cookie.setPath("path");
        cookie.setDomain("domain");
        cookie.setExpiryDate(new Date(System.currentTimeMillis()));
        cookies.add(cookie);
    }

    @Test
    public void shouldAddCookie() {
        persistantCookieStore.addCookie(cookies.get(0));
        Assert.assertEquals("cookie1", persistantCookieStore.getCookies().get(0).getName());
    }

    @Test
    public void shouldClearAllCookies() {
        persistantCookieStore.clear();
        Assert.assertEquals(0, persistantCookieStore.getCookies().size());
    }

    @Test
    public void shouldClearExpired() {
        persistantCookieStore.addCookie(cookies.get(0));
        long time = System.currentTimeMillis();
        time = time + 1000;
        persistantCookieStore.clearExpired(new Date(time));
        Assert.assertEquals(0, persistantCookieStore.getCookies().size());
    }


}
