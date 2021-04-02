package com.chryl.sharding.config;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * 自定义分表算法
 */
@Slf4j
public class Table2x2Algorithm implements PreciseShardingAlgorithm<String> {

    //表的数量
    public static final int TABLE_COUNT = 4;

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        log.info("给到的表为：：：" + availableTargetNames.toString() + "  ::给的主键为：：" + shardingValue);
        int hashVal = Hashing.md5().hashString(shardingValue.getValue(), Charsets.UTF_8).asInt();

        //计算分配的表的值
        int value = BigDecimal.valueOf(hashVal)
                .remainder(BigDecimal.valueOf(TABLE_COUNT))
                .abs()
                .intValue();

        //确定表
        StringBuilder sb = new StringBuilder(shardingValue.getLogicTableName());
        sb.append("_" + value);

        log.info("选定的表 : : : {} ", sb);
        return sb.toString();
    }
}