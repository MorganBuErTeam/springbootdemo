package com.test.demo.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CookieUtils {
    /** 
     * Cookie的名字 
     */  
    public static final String COOKIE_HISTORYID = "cookies_history";  
    /** 
     * Cookie的存活时间 
     */  
    public static final int COOKIE_LIFE_TIME = 14 * 24 * 60 * 60;  
    /** 
     * 最大的浏览历史记录条数 
     */  
    public static final int HISTORY_COUNT = 5;  
  
    /** 
     * 获得指定cookie中的值 
     *  
     * @param request 
     * @param cookieName 
     *            要查找的cookie的名字 
     * @return 返回指定Cookie中的字符串值 
     */  
    public static String getCookie(HttpServletRequest request, String cookieName) {
  
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {  
            for (Cookie cookie : cookies) {  
                // 找到指定的cookie  
                if (cookie != null && COOKIE_HISTORYID.equals(cookie.getName())) {  
                    return cookie.getValue();  
                }  
            }  
        }  
        return null;  
    }  
  
    /** 
     * 设置指定cookie中的值（1.得到原先cookie中的值；2.重新设置cookie的值；3.保存重置后的cookie） 
     *  
     * @param request 
     * @param response 
     * @param cookieName 
     *            要设置的cookie的名字 
     * @param goodsid 
     *            要添加到浏览历史中的 goodsID号 
     * @param count 
     *            总共可以显示的历史记录条数 
     */  
    public static void setCookie(HttpServletRequest request,
                                 HttpServletResponse response, String cookieName, String goodsid,
                                 int count) {
        // 得到指定的cookie  
        String ids = getCookie(request, cookieName);  
        // 设置cookie中格的浏览记录  
        ids = setValue(ids, goodsid, count);  
        // 保存cookie  
        saveCookie(request, response, cookieName, ids);  
    }  
  
    // 测试方法  
    public static void main(String[] args) {  
        System.out.println(setValue(null, "1", 3));  
        System.out.println(setValue("1,2,3", "1", 3));  
        System.out.println(setValue("2,1,3", "1", 3));  
        System.out.println(setValue("2,1", "1", 3));  
        System.out.println(setValue("2,3,4", "1", 3));  
        System.out.println(setValue("2,4", "1", 3));  
        System.out.println(setValue("4,3,1", "1", 3));  
    }  
  
    /** 
     * 设置浏览历史字符串 
     *  
     * @param ids 
     * @param sbookid 
     *            最新浏览的id号 
     * @return 修改后的字符串 
     */  
    private static String setValue(String ids, String bookid, int count) {  
        // 1、 没有任何记录--》直接添加 id  
        // 2、 1,2,3 4--》 4,1,2  
        // 3、 1,2,3 2 --》 2,1,3  
        // 4、1,2 3--》 3,1,2  
        // 如果不存在Cookie或者Cookie中没有值  
        StringBuffer sb = new StringBuffer();  
        if (ids == null) {  
            sb.append(bookid);  
        } else {  
            List<String> list = Arrays.asList(ids.split("\\,"));
            LinkedList<String> idsList = new LinkedList<String>(list);
            // 未浏览过  
            if (!idsList.contains(bookid)) {  
                if (idsList.size() < count) {  
                    idsList.addFirst(bookid);  
                } else {  
                    idsList.removeLast();  
                    idsList.addFirst(bookid);  
                }  
            } else {  
                // 如果包含已浏览的  
                idsList.remove(bookid);  
                idsList.addFirst(bookid);  
            }  
            for (String id : idsList) {  
                sb.append(id).append(",");  
            }  
            if (sb != null && sb.length() > 0) {  
                sb.deleteCharAt(sb.length() - 1);  
            }  
        }  
        return sb.toString();  
    }  
  
    /** 
     * 保存cookie的值 
     *  
     * @param request 
     * @param response 
     * @param cookieName 
     *            要保存的cookie的名字 
     * @param value 
     *            保存到cookie中的值 
     */  
    public static void saveCookie(HttpServletRequest request,  
            HttpServletResponse response, String cookieName, String value) {  
        saveCookie(request, response, cookieName, value, COOKIE_LIFE_TIME);  
    }  
  
    /** 
     * 删除指定的cookie 
     *  
     * @param request 
     * @param response 
     * @param cookieName 
     *            要删除的cookie 
     */  
    public static void deleteCookie(HttpServletRequest request,  
            HttpServletResponse response, String cookieName) {  
        saveCookie(request, response, cookieName, "", 0);  
    }  
  
    /** 
     * 保存cookie 并设置cookie存活的时间 
     *  
     * @param request 
     * @param response 
     * @param cookieName 
     *            要保存的cookie的名字 
     * @param value 
     *            cookie中要存放的值 
     * @param time 
     *            cookie存活时间 
     */  
    public static void saveCookie(HttpServletRequest request,  
            HttpServletResponse response, String cookieName, String value,  
            int time) {  
        Cookie cookie = new Cookie(cookieName, value);  
        //cookie.setMaxAge(time);// 设置Cookie的存活时间
        //cookie.setDomain("localhost");
        cookie.setPath("/");
        response.addCookie(cookie);// 保存cookie  
    }  
}  