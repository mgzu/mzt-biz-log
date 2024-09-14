package com.mzt.logserver.pojo;

import com.mzt.logapi.starter.annotation.DIffLogIgnore;
import com.mzt.logapi.starter.annotation.DiffLogAllFields;
import com.mzt.logapi.starter.annotation.DiffLogField;
import com.mzt.logserver.annotations.LogRecordFieldEnum;
import com.mzt.logserver.enums.UserSexEnum;
import com.mzt.logserver.enums.UserStatusEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wulang
 **/
@Data
@DiffLogAllFields
public class User {

    private Long id;
    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    @DIffLogIgnore
    private Integer age;

    /**
     * 性别
     */
    @DiffLogField(name = "性别", function = "ENUM")
    @LogRecordFieldEnum(UserSexEnum.class)
    private String sex;

    /**
     * 状态
     */
    @DiffLogField(name = "状态", function = "ENUM")
    @LogRecordFieldEnum(UserStatusEnum.class)
    private Integer status;

    /**
     * 用户地址
     */
    private Address address;

    /**
     * 爱好
     */
    @DIffLogIgnore
    private List<String> likeList;

    /**
     * 不喜欢
     */
    private List<String> noLikeList;

    @DIffLogIgnore
    private List<Address> testList;

    @DIffLogIgnore
    private String[] likeStrings;

    private String[] noLikeStrings;

    private LocalDateTime localDateTime;

    private LocalDate localDate;

    @Data
    public static class Address {
        /**
         * 省名称
         */
        private String provinceName;

        /**
         * 市名称
         */
        private String cityName;

        /**
         * 区/县名称
         */
        private String areaName;
    }
}
