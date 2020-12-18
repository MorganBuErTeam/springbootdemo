package com.test.demo.common.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;


public class HttpUtility {


    public static String post(String strparam, String endpoint)
    {



        try {

            byte[] data = strparam.getBytes("UTF-8");
            String encoding = "UTF-8";
            //base认证
            String auth ="pls:123456";
            //对其进行加密
            byte[] rel = Base64.getEncoder().encode(auth.getBytes());
            String res = new String(rel);
            HttpURLConnection conn = (HttpURLConnection) new URL(endpoint).openConnection();
            conn.setRequestProperty("Authorization","Basic " + res);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "text/xml");
            conn.setConnectTimeout(5 * 1000);
            BufferedOutputStream outStream = new BufferedOutputStream(conn.getOutputStream());
            outStream.write(data);
            outStream.flush();
            outStream.close();
            if (conn.getResponseCode() == 200) {
                String readLine = new String();
                StringBuffer sb = new StringBuffer();
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    readLine = new String(readLine.getBytes(), "utf-8");
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
                return sb.toString();

            }else
            {   String readLine = new String();
                StringBuffer sb = new StringBuffer();
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    readLine = new String(readLine.getBytes(), "utf-8");
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
                return sb.toString();

            }
        } catch (Exception e) {
            return getExceptionDetail(e);

        }

    }


    /**
     * 获取exception详情信息
     *
     * @param e
     *            Excetipn type
     * @return String type
     */
    public static String getExceptionDetail(Exception e) {

        StringBuffer msg = new StringBuffer("null");

        if (e != null) {
            msg = new StringBuffer();

            String message = e.toString();

            int length = e.getStackTrace().length;

            if (length > 0) {

                msg.append(message + "\n");

                for (int i = 0; i < length; i++) {

                    msg.append("\t" + e.getStackTrace()[i] + "\n");

                }
            } else {

                msg.append(message);
            }

        }
        return msg.toString();

    }
}
