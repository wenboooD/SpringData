package cn.iron.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtils {
    /**
     * 解决实体管理器工厂的浪费资源和耗时问题
     *      通过静态代码块的形式，当程序第一次访问此工具类时，创建一个公共的实体管理器工厂对象
     *
     * static:static关键字还有一个比较关键的作用就是 用来形成静态代码块以优化程序性能。static块
     *      可以置于类中的任何地方，类中可以有多个static块。在类初次被加载的时候，会按照static块
     *      的顺序来执行每个static块，并且只会执行一次。
     *
     * 第一次访问getEntityManager方法：经过静态代码块创建一个factory对象，再调用方法创建一个EntityManager对象
     * 第二次方法getEntityManager方法：直接通过一个已经创建好的factory对象，创建EntityManager对象
     */
    // JPA的实体管理器工厂：相当于Hibernate的SessionFactory
    private static EntityManagerFactory factory;
    //使用静态代码块赋值
    static {
        factory = Persistence.createEntityManagerFactory("myJpa");
    }
    /**
     * 使用管理器工厂生产一个管理器对象
     *
     * @return
     */
    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    //关闭方法
    public static void closed(){
        factory.close();
    }
}
