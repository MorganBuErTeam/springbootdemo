package com.test.demo.controller;

import com.test.demo.domain.City;
import com.test.demo.service.CitySolrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/city")
public class CitySolrController {

    @Resource
    CitySolrService citySolrService;

    @RequestMapping("/query")
    @ResponseBody
    public List<City> query(@RequestParam("query")String query){
        return citySolrService.query(query);
    }

    @RequestMapping("/add")
    @ResponseBody
    public City add(@RequestBody City city){
        citySolrService.add(city);
        return city;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam("query") String query){
        citySolrService.delete(query);
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public List<City> queryAll(){
        return citySolrService.queryAll();
    }

    @RequestMapping("/queryById")
    @ResponseBody
    public City queryById(@RequestParam("id")String id){
        return citySolrService.queryById(id);
    }

    @RequestMapping("/update")
    @ResponseBody
    public City update(@RequestBody City city){
        return citySolrService.update(city);
    }



}
