package com.mzt.logserver.pojo;

import com.mzt.logapi.starter.annotation.DIffLogIgnore;
import com.mzt.logapi.starter.annotation.DiffLogAllFields;
import com.mzt.logapi.starter.annotation.DiffLogField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wulang
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@DiffLogAllFields
public class ExtendUser extends User {

    private Long extend1;

    private String extend2;

    @DIffLogIgnore
    private Integer extend3;

    @DiffLogField(name = "扩展字段4")
    private String extend4;

}
