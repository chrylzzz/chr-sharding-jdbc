package com.chryl.sharding.controller;

import com.chryl.sharding.service.ShadingJdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Chr.yl on 2021/4/1.
 *
 * @author Chr.yl
 */
@RestController
public class ShardingJdbcController {

    @Autowired
    private ShadingJdbcService shadingJdbcService;

    /**
     * 读写分离
     */
    @RequestMapping("/sharding/get/goods")
    public String getGoods(Long id) {
        return shadingJdbcService.getGoods(id);
    }


    @RequestMapping("/sharding/get/order")
    public String getOrder(Long id) {
        return shadingJdbcService.getOrder(id);
    }

}
