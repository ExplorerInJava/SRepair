package top.xiaohang456.srepair.dao;

import org.springframework.orm.hibernate5.HibernateTemplate;
import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseDao<T> {

    private HibernateTemplate hibernateTemplate;
    private Class clazz;

    //构造函数的目的就是：获得继承它的第一个子类的class，
    public BaseDao() {
        hibernateTemplate = this.getHibernateTemplate();
        Class clazz = this.getClass();
        Type type = clazz.getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        this.clazz = (Class) types[0];
    }

    public void save(T t) {
        hibernateTemplate.save(t);
    }

    public void delete(T t) {
        hibernateTemplate.delete(t);
    }

    //更新一个实体是什么意思呢？ 要求就是所更新的对象t的ID(主键)设为数据表中的一条对应记录的Id，其他属性随便设置，当执行update的时候，它会找到
    //与t主键相同的那条记录，并把那条记录的处主键外的属性全部更新为t的。
    public void update(T t) {
        hibernateTemplate.update(t);
    }

    public T loadById(int id) {
        return (T) (hibernateTemplate.find("from " + clazz.getSimpleName() + " c where c.id = " + id).get(0));
    }

    public void deleteById(int id) {
        delete((T)(hibernateTemplate.find("from " + clazz.getSimpleName() + " c where c.id = " + id).get(0)));
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    @Resource
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }
}
