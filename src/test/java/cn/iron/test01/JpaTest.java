package cn.iron.test01;

import cn.iron.domain.Customer;
import cn.iron.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class JpaTest {
    /**
     * 测试jpa的保存
     *      案例：保存一个客户到数据库中
     *  Jpa的操作步骤
     *     1.加载配置文件创建工厂（实体管理器工厂）对象
     *     2.通过实体管理器工厂获取实体管理器
     *     3.获取事务对象，开启事务
     *     4.完成增删改查操作
     *     5.提交事务（回滚事务）
     *     6.释放资源
     */
    @Test
    public void testSave(){
        //调用JpaUtils中的getEntityManager方法获取实体管理器
        EntityManager em = JpaUtils.getEntityManager();
        //获取事务对象,开启事务
        EntityTransaction et = em.getTransaction();
        //开启绿色心情
        et.begin();
        //保存操作
        Customer customer = new Customer();
        customer.setCustName("zhangsan");
        customer.setCustIndustry("it");
        em.persist(customer);
        //提交事务
        et.commit();
        //释放资源
        em.close();
        JpaUtils.closed();
    }

    /**
     * 根据id查询客户
     *  使用find方法查询：
     *      1.查询的对象就是当前客户对象本身
     *      2.在调用find方法的时候，就会发送sql语句查询数据库
     *
     *  立即加载
     */
    @Test
    public void testFind(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        /**
         * find : 根据id查询数据
         *      class：查询数据的结果需要包装的实体类类型的字节码
         *      id：查询的主键的取值
         */
        Customer customer = em.find(Customer.class,1l);
        et.commit();
        em.close();
    }

    /**
     * 根据id查询客户
     *      getReference方法
     *          1.获取的对象是一个动态代理对象
     *          2.调用getReference方法不会立即发送sql语句查询数据库
     *              * 当调用查询结果对象的时候，才会发送查询的sql语句：什么时候用，什么时候发送sql语句查询数据库
     *
     * 延迟加载（懒加载） -- 提高程序的性能！！
     *      * 得到的是一个动态代理对象 对Customer对象（原生的Customer对象属性是没有任何值）的代理
     *      * 什么时候用（获取属性值），什么使用才会查询。
     */
    @Test
    public  void testReference() {
        //1.通过工具类获取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.增删改查 -- 根据id查询客户
        /**
         * getReference : 根据id查询数据
         *      class：查询数据的结果需要包装的实体类类型的字节码
         *      id：查询的主键的取值
         */
        Customer customer = entityManager.getReference(Customer.class, 1l);
        System.out.print(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 删除客户的案例
     */
    @Test
    public void testRemove(){
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = entityManager.find(Customer.class,2l);
        entityManager.remove(customer);
        transaction.commit();
        entityManager.close();
    }

    /**
     * 更新客户的操作
     *      merge(Object)
     */
    @Test
    public void testUpdate(){
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = entityManager.find(Customer.class, 1l);
        customer.setCustName("zhangsan");
        entityManager.merge(customer);
        transaction.commit();
        entityManager.close();
    }
}
