package com.sxkl.fastrigger.commoner.base.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 系统公用模块 控制层响应实体
 * @author wy
 * @date 2019-06-10 11:14:52
 */
@Data
@Builder
@AllArgsConstructor
public class OperateResult {

    private boolean status;
    private String msg;
    private Object data;
    private Throwable e;

    private static final String DEFAULT_SUCCESS_MSG = "操作成功";
    private static final String DEFAULT_FAIL_MSG = "操作失败";

    public static OperateResult buildSuccess() {
        return new OperateResult(Boolean.TRUE, DEFAULT_SUCCESS_MSG, null, null);
    }

    public static OperateResult buildFail() {
        return new OperateResult(Boolean.FALSE, DEFAULT_FAIL_MSG, null, null);
    }

    public static OperateResult buildFail(Throwable e) {
        return new OperateResult(Boolean.FALSE, DEFAULT_FAIL_MSG, null, e);
    }
}
