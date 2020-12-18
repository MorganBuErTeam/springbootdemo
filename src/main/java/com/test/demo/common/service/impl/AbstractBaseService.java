package com.test.demo.common.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.test.demo.common.data.PageBean;
import com.test.demo.common.service.BaseService;
import com.test.demo.common.util.EntityUtils;
import com.test.demo.common.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * @author cj
 **/
public abstract class AbstractBaseService<M extends Mapper<T>,T> implements BaseService<M,T> {

    @Autowired
    protected M mapper;

    
    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

   
    public T selectById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }

    
    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }

    
    public List<T> selectListAll() {
        return mapper.selectAll();
    }

    
    public Long selectCount(T entity) {
        return new Long(mapper.selectCount(entity));
    }

    
    public void insert(T entity) {
        EntityUtils.setCreatAndUpdatInfo(entity);
         mapper.insert(entity);
    }

    
    public void insertSelective(T entity) {
        EntityUtils.setCreatAndUpdatInfo(entity); //对于所以新增的创建时间、创建人、创建host已经修改的统一处理
        mapper.insertSelective(entity);
    }

    
    public void delete(T entity) {
        mapper.delete(entity);
    }

   
    public void deleteById(Object id) {
        mapper.deleteByPrimaryKey(id);
    }

    
    public void updateById(T entity) {
        mapper.updateByPrimaryKey(entity);
    }

    
    public void updateSelectiveById(T entity) {
        mapper.updateByPrimaryKeySelective(entity);
    }

    
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }
    
    /**
     * 根据某个条件查询
     * @param name 属性名称
     * @param value 属性值
     * @return List<T>
     */
    public List<T> selectByExample(String name,Object value) {
    	Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		Example example = new Example(clazz);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo(name, value);
        return mapper.selectByExample(example);
    }

    
    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }
    
    public int selectCountBy(String name,Object value){
		Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		Example example = new Example(clazz);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo(name, value);
		return mapper.selectCountByExample(example);
	}
    
    public int selectCountBy(String [] name,Object [] value){
		Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		Example example = new Example(clazz);
		Example.Criteria criteria = example.createCriteria();
		for (int i = 0; i < name.length; i++) {
			criteria.andEqualTo(name[i], value[i]);
		}
		return mapper.selectCountByExample(example);
	}

    
    public PageBean<T> selectByQuery(Query query) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        if(query.entrySet().size()>0) {
            
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                if (query.isLike()) {
                    criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
                    continue;
                }
                if(entry.getValue().toString().indexOf(",")!=-1){
//                    String [] split=entry.getValue().toString().split(",");
                    criteria.andCondition("state in("+entry.getValue()+")");
//                    for(String str:split){
//                        criteria.andEqualTo(entry.getKey(), str);
//                    }
                    continue;
                }
                criteria.andEqualTo(entry.getKey(), entry.getValue());
            }
        }
        if (query.isQueryByDate()) {
        	criteria.andBetween(query.getName(), query.getStartDate(), query.getEndDate());//按日期区间查询
		}
        if (query.isOrderBy()) {
            example.setOrderByClause(query.getOrderBy());//排序条件
        }
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<T> list = mapper.selectByExample(example);
        return new PageBean<T>(list,result.getTotal());
    }
    
}

