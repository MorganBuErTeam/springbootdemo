package com.test.demo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class TCPClient implements Runnable {



    @Override
    public void run() {
        while (true) {
            try {

            } catch (Exception e) {

            } finally {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }
    }



//    private boolean isRackCTaskCode(AGVInfo agvInfo, AGVPLCparamInfo agvReal) throws Exception {
//        List<byte[]> rackPath=new ArrayList<>();
//        if (agvInfo.getClient().SockSend(rackPath.get(0))) {   //todo tcp写  下发路径指令
//            logger.info(agvReal.getAgv_no()+"号车在" + agvInfo.getSiteCode() + "号站点,"
//                    +"下发回程路径指令："
//                    + byteArrToString(rackPath.get(0)));
//            Thread.sleep(1000);
//            return true;
//        }
//        return false;
//    }

    private String byteArrToString(byte[] values) {
        String result = "";
        for (byte v : values) {
            result += v + " ";
        }
        return result;
    }


}
