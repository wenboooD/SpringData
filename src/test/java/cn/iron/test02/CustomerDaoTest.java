package cn.iron.test02;

import cn.iron.dao.CustomerDao;
import cn.iron.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) //声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class CustomerDaoTest {
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testFindOne(){
        Customer cus = customerDao.findOne(1l);
        System.out.println(cus);
    }

    @Test
    public void testSave() {
        Customer customer  = new Customer();
        customer.setCustName("asd");
        customer.setCustLevel("vip");
        customer.setCustIndustry("asd");
        customerDao.save(customer);
    }

    @Test
    public void testUpdate() {
        Customer one = customerDao.getOne(4l);
        Customer customer  = new Customer();
        customer.setCustId(4l);
        one.setCustName("asd");
        customerDao.save(one); //update xx set 所有的列！！
    }

    @Test
    public void testDelete () {
        customerDao.delete(3l);
    }

    @Test
    public void testFindAll() {
        List<Customer> list = customerDao.findAll();
        for(Customer customer : list) {
            System.out.println(customer);
        }
    }

    @Test
    public void testCount() {
        long count = customerDao.count();//查询全部的客户数量
        System.out.println(count);
    }

    @Test
    public void  testExists() {
        boolean exists = customerDao.exists(4l);
        System.out.println("id为4的客户 是否存在："+exists);
    }

    @Test
    @Transactional  //如果没有加事务控制   no Session  没有跟数据库的会话连接！！
    // 发送在延迟加载情况的一个典型的异常！！
    public void  testGetOne() {
        Customer customer = customerDao.getOne(1l);
        //延迟加载  customer_$$_V432432 代理对象 属性没有值。 没有发送sql语句
        System.out.println(customer);//调用customer对象中的属性。发现没有属性值。发送sql语句，执行sql。填充属性值

    }
}
