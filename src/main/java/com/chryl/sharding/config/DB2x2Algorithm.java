package com.chryl.sharding.config;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * 自定义分库算法
 */
@Slf4j
public class DB2x2Algorithm implements PreciseShardingAlgorithm<String> {

    //库的数量
    public static final int TABLE_SIZE_PER_DB = 2;

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {

        log.info("给到的库为：：：" + availableTargetNames.toString() + "  ::给的主键为：：" + shardingValue);

        int hashVal = Hashing.md5().hashString(shardingValue.getValue(), Charsets.UTF_8).asInt();

        //计算分配的库值
        int value = BigDecimal.valueOf(hashVal)
                .remainder(BigDecimal.valueOf(Table2x2Algorithm.TABLE_COUNT))
                .divide(BigDecimal.valueOf(TABLE_SIZE_PER_DB))
                .abs()
                .intValue();

        //计算的和所有的对比,确定库
        for (String targetName : availableTargetNames) {
            if (targetName.endsWith(String.valueOf(value))) {
                log.info("选定的库targetName : : : {} , :: intValue : : {}", targetName, value);
                return targetName;
            }
        }
        return null;

    }

}