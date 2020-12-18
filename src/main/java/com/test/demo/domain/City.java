package com.test.demo.domain;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "test_core")
@Data
public class City {

    @Field
    private Integer cid;

    @Field
    private String city;

    @Field
    private Integer pid;


    public  City (String city){
        this.city=city;
    }
}
