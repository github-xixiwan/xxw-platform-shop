package com.xxw.shop.starter.canal.support.parser;

import com.xxw.shop.starter.canal.model.CanalBinLogEvent;
import com.xxw.shop.starter.canal.model.CanalBinLogResult;

import java.util.List;

public interface CanalBinLogEventParser {

    /**
     * 解析binlog事件
     *
     * @param event               事件
     * @param klass               目标类型
     * @param primaryKeyFunction  主键映射方法
     * @param commonEntryFunction 其他属性映射方法
     * @return CanalBinLogResult
     */
    <T> List<CanalBinLogResult<T>> parse(CanalBinLogEvent event,
                                         Class<T> klass,
                                         BasePrimaryKeyTupleFunction primaryKeyFunction,
                                         BaseCommonEntryFunction<T> commonEntryFunction);
}
