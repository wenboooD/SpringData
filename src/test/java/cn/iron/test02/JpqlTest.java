package cn.iron.test02;

import cn.iron.dao.CustomerDao;
import cn.iron.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) //声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class JpqlTest {
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testFindJPQL(){
        Customer customer = customerDao.findJpql("zhangsan");
        System.out.println(customer);
    }

    @Test
    public void testFindCustNameAndId(){
        Customer customer = customerDao.findCustNameAndId("zhangsan", 1l);
        System.out.println(customer);
    }

    /**
     * 测试jpql的更新操作
     *  * springDataJpa中使用jpql完成 更新/删除操作
     *         * 需要手动添加事务的支持
     *         * 默认会执行结束之后，回滚事务
     *   @Rollback : 设置是否自动回滚
     *          false | true
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testUpdateCustomer(){
        customerDao.updateCustomer("tom",1);
    }

    @Test
    public void testFindSql(){
        List<Object[]> list = customerDao.findSql("t%");
        for (Object[] objects : list) {
            System.out.println(objects);
        }
    }
}
