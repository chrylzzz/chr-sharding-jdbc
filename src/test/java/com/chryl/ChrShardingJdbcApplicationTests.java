package com.chryl;

import com.chryl.sharding.service.ShadingJdbcService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ChrShardingJdbcApplicationTests {

    @Autowired
    private ShadingJdbcService shadingJdbcService;

    /**
     * /////////////商品测试//////////////////////////为分片
     */
    //商品未分片,只会insert到默认库(defaultDataSourceName)
    @Test
    public void contextLoads() {
//        log.debug("debug=====" + shadingJdbcService.getGoods());
        log.info("info=====" + shadingJdbcService.getGoods(530607201456128L));
    }

    //未分片,根据默认数据库查询
    @Test
    public void contextLoad2s() {
        shadingJdbcService.insertGoods();
    }


    /**
     * /////////////订单测试//////////////////////////分库分表
     */
    //分片插入
    @Test
    public void co2ntextLoad2s() {
        shadingJdbcService.insertOrder();
    }

    //根据id查询
    @Test
    public void show() {
        log.info(shadingJdbcService.getOrder(533619181490176L));
    }

    //分片查询
    @Test
    public void getOrderByOrderNo() {
        log.info(shadingJdbcService.getOrderByOrderNo(""));
    }

    @Test
    public void getOrderDetail() {
        log.info(shadingJdbcService.getOrderDetail("44ec96ad7d284d23a95e0f7062da2f48"));
    }
}
