package com.sxkl.fastrigger.commoner.base.entity;

import com.sxkl.fastrigger.commoner.base.annotation.Id;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统公用模块基础实体
 * @author wy
 * @date 2019-06-06 16:10:45
 */
@Data
public class BaseEntity implements Serializable {

    @Id(column = BaseConstants.PK_ID_NAME)
    protected String id;
    protected String otherPK;
    protected LocalDateTime createDateTime;
    protected LocalDateTime updateDateTime;
    protected LocalDateTime startDateTime;
    protected LocalDateTime endDateTime;
    protected String userId;
    protected int index;
    protected BaseEntity source;
}
