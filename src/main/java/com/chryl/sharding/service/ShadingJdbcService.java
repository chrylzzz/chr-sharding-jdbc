package com.chryl.sharding.service;

import com.alibaba.fastjson.JSON;
import com.chryl.sharding.entity.Goods;
import com.chryl.sharding.entity.MyOrder;
import com.chryl.sharding.entity.MyOrderCriteria;
import com.chryl.sharding.entity.OrderDetailCriteria;
import com.chryl.sharding.mapper.GoodsMapper;
import com.chryl.sharding.mapper.MyOrderMapper;
import com.chryl.sharding.mapper.OrderDetailMapper;
import com.chryl.util.NS_Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by Chr.yl on 2021/4/1.
 *
 * @author Chr.yl
 */
@Service
public class ShadingJdbcService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private MyOrderMapper myOrderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    private static final NS_Snowflake snowFlake = new NS_Snowflake(1, 1);


    //goods
    public String getGoods(Long id) {
        return JSON.toJSONString(goodsMapper.selectByPrimaryKey(id));
    }


    public void insertGoods() {
        Goods goods = new Goods();
        goods.setGoodId("24" + String.valueOf(Math.random() * 100000).split("\\.")[0]);
        goods.setGoodName("小米" + String.valueOf(Math.random() * 100000).split("\\.")[0]);
        goods.setId(snowFlake.nextId());
        goodsMapper.insert(goods);
    }


    //order
    public void insertOrder() {
        MyOrder order = new MyOrder();
        order.setOrderNo(UUID.randomUUID().toString().replaceAll("-", ""));
        order.setOrderPrice(new BigDecimal(String.valueOf(Math.random() * 10000).split("\\.")[0]));
        order.setUserId("60" + String.valueOf(Math.random() * 100000).split("\\.")[0]);
        order.setId(snowFlake.nextId());
        myOrderMapper.insertSelective(order);


    }

    //查询id为1的,如果分片有多个id为1,则会查询出错 : Expected one result (or null) to be returned by selectOne(), but found: 2
    public String getOrder(Long id) {
        return JSON.toJSONString(myOrderMapper.selectByPrimaryKey(id));

    }


    public String getOrderByOrderNo(String orderNo) {
        MyOrderCriteria myOrderCriteria = new MyOrderCriteria();
        MyOrderCriteria.Criteria criteria = myOrderCriteria.createCriteria();
        criteria.andOrderNoEqualTo(orderNo);
        return JSON.toJSONString(myOrderMapper.selectByExample(myOrderCriteria));
    }


    //order detail
    public String getOrderDetail(String orderNo) {
        OrderDetailCriteria detailCriteria = new OrderDetailCriteria();
        OrderDetailCriteria.Criteria criteria = detailCriteria.createCriteria();
        criteria.andOrderNoEqualTo(orderNo);
        return JSON.toJSONString(orderDetailMapper.selectByExample(detailCriteria));
    }

}
