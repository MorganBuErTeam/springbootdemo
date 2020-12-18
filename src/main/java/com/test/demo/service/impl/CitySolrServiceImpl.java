package com.test.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.test.demo.domain.City;
import com.test.demo.service.CitySolrService;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.noggit.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CitySolrServiceImpl implements CitySolrService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public List<City> query(String query) {  //query="city:这个暑假我打算去北京市,从上海市出发,经过天津市";   city: 表示分词的字段
        List<City> cityList = new ArrayList<City>();
        SolrQuery solrQuery = new SolrQuery();
        //设置默认搜索的域
//        solrQuery.set("df", "description");
        solrQuery.setQuery(query);
        //高亮显示
        solrQuery.setHighlight(true);
        //设置高亮显示的域
//        solrQuery.addHighlightField("description");
        //高亮显示前缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        //后缀
        solrQuery.setHighlightSimplePost("</font>");
        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);
            if (queryResponse == null){
                return null;
            }
            SolrDocumentList solrDocumentList = queryResponse.getResults();
            if (solrDocumentList.isEmpty()){
                return null;
            }
            //获取高亮
            Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
            for (SolrDocument solrDocument : solrDocumentList){
                City city;
                List<String> list = map.get(solrDocument.get("id")).get("description");
                if (!CollectionUtils.isEmpty(list)){
                    solrDocument.setField("description",list.get(0));
                }
                String bookStr = JSONUtil.toJSON(solrDocument);
                city = JSON.parseObject(bookStr,City.class);
                cityList.add(city);
            }
        } catch (Exception e) {
            log.error("异常：",e);
        }
        return cityList;
    }



    @Override
    public void add(City city) {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("cid",city.getCid());
        document.setField("city",city.getCity());
        document.setField("pid",city.getPid());
        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String query) {
        try {
            solrClient.deleteByQuery(query);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public City update(City city) {
        try {
            solrClient.addBean(city);
            solrClient.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return city;
    }



    @Override
    public List<City> queryAll() {
        List<City> cityList = new ArrayList<City>();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);
            if (queryResponse != null){
                cityList = queryResponse.getBeans(City.class);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityList;
    }

    @Override
    public City queryById(String id) {
        City city = null;
        try {
            SolrDocument solrDocument = solrClient.getById(id);
            String str = JSONUtil.toJSON(solrDocument);
            city = JSON.parseObject(str,City.class);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
    }
}
