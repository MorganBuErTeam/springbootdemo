package com.test.demo.controller;

import com.test.demo.annotation.KthLog;
import com.test.demo.common.vo.sse.*;
import com.test.demo.domain.UpKeepInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sse")
@Slf4j
@Api(value = "全文",description = "sse测试")
public class SseTestController {

    @PostMapping(value = "/querey")
    @ApiOperation(value="查询")
    public ResponseVo querey(@RequestBody RequestQueryVo requestQueryVo) {
        List<QueryDataVo> queryDataVoList=new ArrayList<>();
        List<QueryAttachVo> attachVoList=new ArrayList<>();
        attachVoList.add(new QueryAttachVo().setAttachTitile("附件标题1(上网)"));
        attachVoList.add(new QueryAttachVo().setAttachTitile("附件标题2(上网)"));
        List<BulletinInfoVo> bulletinInfoList=new ArrayList<>();
        BulletinInfoVo bulletinInfoVo=new BulletinInfoVo()
                .setBulletinId("123")
                .setBulletinTitle("工商银行年报公告标题")
                .setAttachList(attachVoList)
                .setAuditorName("初审人")
                .setPublishMedia("中证,上证");
        BulletinInfoVo bulletinInfoVoTwo=new BulletinInfoVo()
                .setBulletinId("124")
                .setBulletinTitle("工商银行半年报公告标题")
                .setAttachList(attachVoList)
                .setAuditorName("初审人")
                .setPublishMedia("中证,上证");
        bulletinInfoList.add(bulletinInfoVo);
        bulletinInfoList.add(bulletinInfoVoTwo);
        QueryDataVo queryDataVo=new QueryDataVo()
                .setStockCode("601398")
                .setStockAbbr("工商银行")
                .setDiscloseDate("2020-12-08")
                .setSaveTime("2020-12-07T14:14:30")
                .setBulletinInfoList(bulletinInfoList);
        queryDataVoList.add(queryDataVo);
        ResponseVo responseVo=new ResponseVo()
                .setStatus("1")
                .setMessage("")
                .setData(queryDataVoList);
        return responseVo;
    }

    @PostMapping(value = "/comparison")
    @ApiOperation(value="比对")
    public ResponseVo comparison(@RequestBody RequestComparisonVo requestComparisonVo) {

        List<ComparisonAttachVo> attachList=new ArrayList<>();
        attachList.add(new ComparisonAttachVo().setAttachTitile("附件标题1(上网)"));
        attachList.add(new ComparisonAttachVo().setAttachTitile("附件标题2(上网)"));
        List<ComparisonBulletinInfoVo> receiptBulletinInfoList=new ArrayList<>();
        ComparisonBulletinInfoVo comparisonBulletinInfoVo=new ComparisonBulletinInfoVo()
                .setBulletinId("123")
                .setBulletinTitle("工商银行年报公告标题")
                .setAttachList(attachList);
        receiptBulletinInfoList.add(comparisonBulletinInfoVo);
        List<ComparisonBulletinInfoVo> issueBulletinInfoList=new ArrayList<>();
        ComparisonBulletinInfoVo comparisonBulletinInfoVoTwo=new ComparisonBulletinInfoVo()
                .setBulletinId("123")
                .setBulletinTitle("工商银行年报公告标题")
                .setAttachList(attachList);
        issueBulletinInfoList.add(comparisonBulletinInfoVoTwo);

        List<ComparisonVo> comparisonVoList=new ArrayList<>();
        ComparisonVo comparisonVo=new ComparisonVo()
                .setStockCode("601398")
                .setStockAbbr("工商银行")
                .setDiscloseDate("2020-12-08")
                .setReceiptNumber("2")
                .setIssueNumber("2")
                .setAccordance(true)
                .setReceiptBulletinInfoList(receiptBulletinInfoList)
                .setIssueBulletinInfoList(issueBulletinInfoList);
        comparisonVoList.add(comparisonVo);


        ResponseVo responseVo=new ResponseVo()
                .setStatus("1")
                .setMessage("")
                .setData(comparisonVoList);
        return responseVo;
    }

    @PostMapping(value = "/export")
    @ApiOperation(value="导出")
    public ResponseVo export(@RequestBody RequestExportVo requestExportVo) {
        List<QueryDataVo> queryDataVoList=new ArrayList<>();


        ResponseVo responseVo=new ResponseVo()
                .setStatus("1")
                .setMessage("")
                .setData(queryDataVoList);
        return responseVo;
    }


}
