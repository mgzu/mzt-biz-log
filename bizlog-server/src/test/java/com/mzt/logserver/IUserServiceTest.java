package com.mzt.logserver;

import cn.hutool.extra.spring.SpringUtil;
import com.mzt.logapi.beans.LogRecord;
import com.mzt.logapi.starter.support.aop.LogRecordInterceptor;
import com.mzt.logserver.infrastructure.constants.LogRecordType;
import com.mzt.logserver.infrastructure.logrecord.service.DbLogRecordService;
import com.mzt.logserver.pojo.Order;
import com.mzt.logserver.pojo.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.jdbc.Sql;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wulang
 **/
@EnableAspectJAutoProxy(exposeProxy = true)
class IUserServiceTest extends BaseTest {
    @Resource
    private IUserService userService;
    @Resource
    private DbLogRecordService logRecordService;

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void diffUser() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setSex("MALE");
        user.setAge(18);
        User.Address address = new User.Address();
        address.setProvinceName("湖北省");
        address.setCityName("武汉市");
        user.setAddress(address);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("李四");
        newUser.setSex("FEMALE");
        newUser.setAge(20);
        User.Address newAddress = new User.Address();
        newAddress.setProvinceName("湖南省");
        newAddress.setCityName("长沙市");
        newUser.setAddress(newAddress);
        userService.diffUser(user, newUser);

        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.USER);
        Assertions.assertEquals(1, logRecordList.size());
        LogRecord logRecord = logRecordList.get(0);
        Assertions.assertEquals("更新了用户信息【address的cityName】从【武汉市】修改为【长沙市】；【address的provinceName】从【湖北省】修改为【湖南省】；【name】从【张三】修改为【李四】；【性别】从【男】修改为【女】", logRecord.getAction());
        Assertions.assertNotNull(logRecord.getExtra());
        Assertions.assertEquals("111", logRecord.getOperator());
        logRecordService.clean();
    }

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGlobalVariable() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setSex("MALE");
        user.setAge(18);
        User.Address address = new User.Address();
        address.setProvinceName("湖北省");
        address.setCityName("武汉市");
        user.setAddress(address);

        Order order = new Order();
        order.setOrderId(99L);
        order.setOrderNo("MT0000011");
        order.setProductName("超值优惠红烧肉套餐");
        order.setPurchaseName("张三");

        userService.testGlobalVariable(user, order);
        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.USER);
        Assertions.assertEquals(2, logRecordList.size());
        LogRecord userLogRecord = logRecordList.get(0);
        Assertions.assertEquals("更新张三用户积分信息", userLogRecord.getAction());
        LogRecord orderLogRecord = logRecordList.get(1);
        Assertions.assertEquals("更新用户张三的订单xxxx(99)信息,更新内容为...", orderLogRecord.getAction());
        logRecordService.clean();
    }

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGlobalVariableCover() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setSex("MALE");
        user.setAge(18);
        User.Address address = new User.Address();
        address.setProvinceName("湖北省");
        address.setCityName("武汉市");
        user.setAddress(address);

        Order order = new Order();
        order.setOrderId(99L);
        order.setOrderNo("MT0000011");
        order.setProductName("超值优惠红烧肉套餐");
        order.setPurchaseName("张三");

        userService.testGlobalVariableCover(user, order);
        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.USER);
        Assertions.assertEquals(2, logRecordList.size());
        LogRecord userLogRecord = logRecordList.get(0);
        Assertions.assertEquals("更新张三用户积分信息", userLogRecord.getAction());
        LogRecord orderLogRecord = logRecordList.get(1);
        Assertions.assertEquals("更新用户李四的订单xxxx(99)信息,更新内容为...", orderLogRecord.getAction());
        logRecordService.clean();
    }

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testAbstract() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setSex("MALE");
        user.setAge(18);
        user.setStatus(0);
        User.Address address = new User.Address();
        address.setProvinceName("湖北省");
        address.setCityName("武汉市");
        user.setAddress(address);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("李四");
        newUser.setSex("FEMALE");
        newUser.setAge(20);
        newUser.setStatus(1);
        User.Address newAddress = new User.Address();
        newAddress.setProvinceName("湖南省");
        newAddress.setCityName("长沙市");
        newUser.setAddress(newAddress);
        userService.testAbstracts(user, newUser);

        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.USER);
        Assertions.assertEquals(1, logRecordList.size());
        LogRecord logRecord = logRecordList.get(0);
        Assertions.assertEquals("更新了用户信息【address的cityName】从【武汉市】修改为【长沙市】；【address的provinceName】从【湖北省】修改为【湖南省】；【name】从【张三】修改为【李四】；【性别】从【男】修改为【女】；【状态】从【正常】修改为【锁定】", logRecord.getAction());
        Assertions.assertNotNull(logRecord.getExtra());
        Assertions.assertEquals("111", logRecord.getOperator());
        logRecordService.clean();
    }

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testAbstracts() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setSex("MALE");
        user.setAge(18);
        User.Address address = new User.Address();
        address.setProvinceName("湖北省");
        address.setCityName("武汉市");
        user.setAddress(address);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("李四");
        newUser.setSex("FEMALE");
        newUser.setAge(20);
        User.Address newAddress = new User.Address();
        newAddress.setProvinceName("湖南省");
        newAddress.setCityName("长沙市");
        newUser.setAddress(newAddress);
        userService.testAbstracts(user, newUser);

        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.USER);
        Assertions.assertEquals(1, logRecordList.size());
        LogRecord logRecord = logRecordList.get(0);
        Assertions.assertEquals("更新了用户信息【address的cityName】从【武汉市】修改为【长沙市】；【address的provinceName】从【湖北省】修改为【湖南省】；【name】从【张三】修改为【李四】；【性别】从【男】修改为【女】", logRecord.getAction());
        Assertions.assertNotNull(logRecord.getExtra());
        Assertions.assertEquals("111", logRecord.getOperator());

        List<LogRecord> orderLogRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.ORDER);
        Assertions.assertEquals(1, orderLogRecordList.size());
        LogRecord orderLogRecord = orderLogRecordList.get(0);
        Assertions.assertEquals("更新了用户信息【address的cityName】从【武汉市】修改为【长沙市】；【address的provinceName】从【湖北省】修改为【湖南省】；【name】从【张三】修改为【李四】；【性别】从【男】修改为【女】", orderLogRecord.getAction());
        Assertions.assertNotNull(orderLogRecord.getExtra());
        Assertions.assertEquals("111", orderLogRecord.getOperator());
        logRecordService.clean();
    }

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testInterface() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setSex("MALE");
        user.setAge(18);
        User.Address address = new User.Address();
        address.setProvinceName("湖北省");
        address.setCityName("武汉市");
        user.setAddress(address);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("李四");
        newUser.setSex("FEMALE");
        newUser.setAge(20);
        User.Address newAddress = new User.Address();
        newAddress.setProvinceName("湖南省");
        newAddress.setCityName("长沙市");
        newUser.setAddress(newAddress);
        userService.testInterface(user, newUser);

        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.ORDER);
        Assertions.assertEquals(1, logRecordList.size());
        LogRecord logRecord = logRecordList.get(0);
        Assertions.assertEquals("更新了用户信息【address的cityName】从【武汉市】修改为【长沙市】；【address的provinceName】从【湖北省】修改为【湖南省】；【name】从【张三】修改为【李四】；【性别】从【男】修改为【女】", logRecord.getAction());
        Assertions.assertNotNull(logRecord.getExtra());
        Assertions.assertEquals("111", logRecord.getOperator());

        List<LogRecord> orderLogRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.ORDER);
        Assertions.assertEquals(1, orderLogRecordList.size());
        LogRecord orderLogRecord = orderLogRecordList.get(0);
        Assertions.assertEquals("更新了用户信息【address的cityName】从【武汉市】修改为【长沙市】；【address的provinceName】从【湖北省】修改为【湖南省】；【name】从【张三】修改为【李四】；【性别】从【男】修改为【女】", orderLogRecord.getAction());
        Assertions.assertNotNull(orderLogRecord.getExtra());
        Assertions.assertEquals("111", orderLogRecord.getOperator());
        logRecordService.clean();
    }

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testInterfaceAndAbstract() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setSex("MALE");
        user.setAge(18);
        User.Address address = new User.Address();
        address.setProvinceName("湖北省");
        address.setCityName("武汉市");
        user.setAddress(address);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("李四");
        newUser.setSex("FEMALE");
        newUser.setAge(20);
        User.Address newAddress = new User.Address();
        newAddress.setProvinceName("湖南省");
        newAddress.setCityName("长沙市");
        newUser.setAddress(newAddress);
        userService.testInterfaceAndAbstract(user, newUser);

        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.USER);
        Assertions.assertEquals(1, logRecordList.size());
        LogRecord logRecord = logRecordList.get(0);
        Assertions.assertEquals("更新了用户信息【address的cityName】从【武汉市】修改为【长沙市】；【address的provinceName】从【湖北省】修改为【湖南省】；【name】从【张三】修改为【李四】；【性别】从【男】修改为【女】", logRecord.getAction());
        Assertions.assertNotNull(logRecord.getExtra());
        Assertions.assertEquals("111", logRecord.getOperator());

        List<LogRecord> orderLogRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.ORDER);
        Assertions.assertEquals(1, orderLogRecordList.size());
        LogRecord orderLogRecord = orderLogRecordList.get(0);
        Assertions.assertEquals("更新了用户信息【address的cityName】从【武汉市】修改为【长沙市】；【address的provinceName】从【湖北省】修改为【湖南省】；【name】从【张三】修改为【李四】；【性别】从【男】修改为【女】", orderLogRecord.getAction());
        Assertions.assertNotNull(orderLogRecord.getExtra());
        Assertions.assertEquals("111", orderLogRecord.getOperator());
        logRecordService.clean();
    }

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testInterfaceAndAbstract2() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setSex("MALE");
        user.setAge(18);
        User.Address address = new User.Address();
        address.setProvinceName("湖北省");
        address.setCityName("武汉市");
        user.setAddress(address);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("李四");
        newUser.setSex("FEMALE");
        newUser.setAge(20);
        User.Address newAddress = new User.Address();
        newAddress.setProvinceName("湖南省");
        newAddress.setCityName("长沙市");
        newUser.setAddress(newAddress);
        userService.testInterfaceAndAbstract2(user, newUser);

        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.ORDER);
        Assertions.assertEquals(1, logRecordList.size());
        LogRecord logRecord = logRecordList.get(0);
        Assertions.assertEquals("更新了用户信息【address的cityName】从【武汉市】修改为【长沙市】；【address的provinceName】从【湖北省】修改为【湖南省】；【name】从【张三】修改为【李四】；【性别】从【男】修改为【女】", logRecord.getAction());
        Assertions.assertNotNull(logRecord.getExtra());
        Assertions.assertEquals("111", logRecord.getOperator());
        //接口+抽象类，只支持抽象类
//        List<LogRecord> orderLogRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.ORDER);
//        Assertions.assertEquals(1, orderLogRecordList.size());
//        LogRecord orderLogRecord = orderLogRecordList.get(0);
//        Assertions.assertEquals(orderLogRecord.getAction(), "更新了用户信息【address的cityName】从【武汉市】修改为【长沙市】；【address的provinceName】从【湖北省】修改为【湖南省】；【name】从【张三】修改为【李四】；【性别】从【男】修改为【女】");
//        Assertions.assertNotNull(orderLogRecord.getExtra());
//        Assertions.assertEquals(orderLogRecord.getOperator(), "111");
        logRecordService.clean();
    }

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_DIffLogIgnore_容器类失效() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setSex("MALE");
        user.setAge(18);
        User.Address address = new User.Address();
        address.setProvinceName("湖北省");
        address.setCityName("武汉市");
        user.setAddress(address);
        List<String> likeList = new ArrayList<>();
        likeList.add("锅盔");
        likeList.add("热干面");
        likeList.add("豆皮");
        user.setLikeList(likeList);
        user.setTestList(Collections.singletonList(address));
        List<String> noLikeList = new ArrayList<>();
        noLikeList.add("蛙");
        noLikeList.add("鱼");
        user.setNoLikeList(noLikeList);
        user.setLikeStrings(new String[]{"a", "b", "c"});
        user.setNoLikeStrings(new String[]{"k", "p", "m"});

        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("李四");
        newUser.setSex("FEMALE");
        newUser.setAge(20);
        User.Address newAddress = new User.Address();
        newAddress.setProvinceName("湖南省");
        newAddress.setCityName("长沙市");
        newUser.setAddress(newAddress);
        List<String> newLikeList = new ArrayList<>();
        newLikeList.add("臭豆腐");
        newLikeList.add("茶颜悦色");
        newUser.setLikeList(newLikeList);
        newUser.setTestList(Collections.singletonList(newAddress));
        List<String> newNoLikeList = new ArrayList<>();
        newNoLikeList.add("虾");
        newNoLikeList.add("龟");
        newUser.setNoLikeList(newNoLikeList);
        newUser.setLikeStrings(new String[]{"a", "p", "c"});
        newUser.setNoLikeStrings(new String[]{"k", "j", "u"});

        userService.diffUser(user, newUser);

        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.USER);
        Assertions.assertEquals(1, logRecordList.size());
        LogRecord logRecord = logRecordList.get(0);
        Assertions.assertEquals("更新了用户信息【address的cityName】从【武汉市】修改为【长沙市】；【address的provinceName】从【湖北省】修改为【湖南省】；【name】从【张三】修改为【李四】；【noLikeList】添加了【虾，龟】删除了【蛙，鱼】；【noLikeStrings】添加了【j，u】删除了【p，m】；【性别】从【男】修改为【女】", logRecord.getAction());
        Assertions.assertNotNull(logRecord.getExtra());
        Assertions.assertEquals("111", logRecord.getOperator());
        logRecordService.clean();
    }

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_LocalDateTime() {
        User user = new User();
        user.setId(1L);
        LocalDateTime now = LocalDateTime.now();
        user.setLocalDateTime(now);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setLocalDateTime(LocalDateTime.MIN);

        userService.diffUser(user, newUser);

        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.USER);
        Assertions.assertEquals(1, logRecordList.size());
        LogRecord logRecord = logRecordList.get(0);
        Assertions.assertEquals("更新了用户信息【localDateTime】从【" + now + "】修改为【-999999999-01-01T00:00】", logRecord.getAction());
        Assertions.assertNotNull(logRecord.getExtra());
        Assertions.assertEquals("111", logRecord.getOperator());
        logRecordService.clean();
    }

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_sameDiffNotRecord() {
        User user = new User();
        user.setId(1L);
        User newUser = new User();
        newUser.setId(1L);
        userService.diffUser(user, newUser);

        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.USER);
        Assertions.assertEquals(0, logRecordList.size());
        logRecordService.clean();
    }

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_sameDiffNotRecordWithMoreExpression() {
        User user = new User();
        user.setId(1L);
        User newUser = new User();
        newUser.setId(1L);
        userService.diffUserByTwoExpression(user, newUser);

        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.USER);
        Assertions.assertEquals(0, logRecordList.size());
        logRecordService.clean();
    }

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_LocalDate() {
        User user = new User();
        user.setId(1L);
        LocalDate now = LocalDate.now();
        user.setLocalDate(now);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setLocalDate(LocalDate.MIN);

        userService.diffUser(user, newUser);

        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.USER);
        Assertions.assertEquals(1, logRecordList.size());
        LogRecord logRecord = logRecordList.get(0);
        Assertions.assertEquals("更新了用户信息【localDate】从【" + now + "】修改为【-999999999-01-01】", logRecord.getAction());
        Assertions.assertNotNull(logRecord.getExtra());
        Assertions.assertEquals("111", logRecord.getOperator());
        logRecordService.clean();
    }

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_diffLog_true() {
        LogRecordInterceptor bean = SpringUtil.getBean(LogRecordInterceptor.class);
        bean.setDiffSameWhetherSaveLog(true);
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setSex("MALE");
        user.setAge(18);
        User.Address address = new User.Address();
        address.setProvinceName("湖北省");
        address.setCityName("武汉市");
        user.setAddress(address);
        userService.diffUser(user, user);

        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.USER);
        Assertions.assertEquals(1, logRecordList.size());
        LogRecord logRecord = logRecordList.get(0);
        Assertions.assertEquals("更新了用户信息", logRecord.getAction());
    }

    void test_testGlobalVariable_diff() {
        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("李四");
        newUser.setSex("FEMALE");
        newUser.setAge(20);
        User.Address newAddress = new User.Address();
        newAddress.setProvinceName("湖南省");
        newAddress.setCityName("长沙市");
        newUser.setAddress(newAddress);
        userService.testGlobalVariableDiff(newUser);

        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(newUser.getId()), LogRecordType.USER);
        Assertions.assertEquals(1, logRecordList.size());
        LogRecord logRecord = logRecordList.get(0);
        Assertions.assertEquals("更新了用户信息【address的cityName】从【武汉市】修改为【长沙市】；【address的provinceName】从【湖北省】修改为【湖南省】；【name】从【张三】修改为【李四】；【性别】从【男】修改为【女】", logRecord.getAction());
        Assertions.assertNotNull(logRecord.getExtra());
        Assertions.assertEquals("111", logRecord.getOperator());
        logRecordService.clean();
    }

    @Test
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_diffLog_false() {
        LogRecordInterceptor bean = SpringUtil.getBean(LogRecordInterceptor.class);
        bean.setDiffSameWhetherSaveLog(false);
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setSex("MALE");
        user.setAge(18);
        User.Address address = new User.Address();
        address.setProvinceName("湖北省");
        address.setCityName("武汉市");
        user.setAddress(address);
        userService.diffUser(user, user);

        List<LogRecord> logRecordList = logRecordService.queryLog(String.valueOf(user.getId()), LogRecordType.USER);
        Assertions.assertEquals(0, logRecordList.size());
        logRecordService.clean();
    }

}
