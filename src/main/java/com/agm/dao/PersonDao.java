package com.agm.dao;
import java.util.List;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.agm.dao.function.SQLExecutor;
import com.agm.dao.mapper.PersonMapper;
import com.agm.model.Person;

/**
 * 代码会涉及到函数式编程和接口回调和Lambda表达式，使用时根据自身情况取舍，各种实现都有。
 * <p>
 * 接口回调和函数式接口回调本质上是相同的，只是一个使用new关键字实现匿名内部类，并实现其方法，另一个利用函数式编程方式实现 仅仅是写法不一样而已
 *
 * @author Administrator
 */
public class PersonDao extends DBConfiguration {
    public static PersonDao INSTANCE = null;

    // 懒汉式
    static {
        if (null == INSTANCE)
            INSTANCE = new PersonDao();
    }

    private PersonDao() {
        super();
    }

    /**
     * 查询所有记录 使用匿名内部类方式
     * 其中{agm_example_Person}是映射文件命名空间，{list_person}是指定执行映射文件中id为list_person的语句
     * 无分页
     *
     * @return
     */
    public List<Person> getAllPersons() {
        return myBatisExecute(new SQLExecutor<List<Person>>() {
            @Override
            public List<Person> executor(SqlSession session) {
                return session.selectList("agm_example_Person.list_person");
            }
        });
    }

	/*
     * 查询所有记录 使用函数式编程，利用Lambda表达式,本段代码作用和上一段代码作用一模一样，只是使用函数式编程方法实现,依然是利用函数式接口进行回调
	 * Lambda 表达式语法： ( arguments )->(body)即( 参数 )->(body)
	 * session对应上面方法的SqlSession session，只是Lambda自动推导参数类型， 所以不用谢类型直接使用
	 * 带分页的查询，分页使用RowBounds类，使用其带参数的构造方法RowBounds(int offset, int limit)
	 * 对于dao层方法，在编写时应重载分页，以及不分页的方法，以供别的模块调用，减少冗余和判断等。
	 * 这个new RowBounds(page,pageSize)可作为一个参数，由上一层向本层传递，这样使dao尽量少的与数据库相关的逻辑解耦，而仅仅只关心数据库的相关操作。
	 *
	 *
	 */


    public List<Person> getAllPersons(int page,int pageSize) {
        //session对应上面方法的SqlSession session，只是Lambda自动推导参数类型， 所以不用谢类型直接使用,写出来也可以
        List<Person> list = myBatisExecute((session) ->
                session.selectList("agm_example_Person.list_person", null,new RowBounds(page,pageSize)));
        return list;
    }

    /**
     * 删除指定Person，根据person属性值（在mapper设定）
     *
     * @param person（查询参数对象，会根据对象里面的值查询相应结果（在mapper配置文件中使用请查看mapper配置文件））
     * @return
     */
    public Integer deletePerson(Person person) {
        // 接口回调
        return myBatisExecute(new SQLExecutor<Integer>() {

            @Override
            public Integer executor(SqlSession session) {
                return session.delete("agm_example_Person.delete_specified_person", person);
            }

        });
    }

    /**
     * 按照指定类属性为条件查询条目，需在mapper中指定查询属性。
     *
     * @param person
     * @return
     */
    public List<Person> getPersons(Person person) {
        // 函数式接口回调
        List<Person> list = myBatisExecute((SqlSession session) -> session.selectList("agm_example_Person.list_person_by_condition", person));
        return list;
    }

    /**
     * 批量插入记录
     *
     * @param person_list
     * @return 受影响的行数
     */
    public Integer insertPersons(List<Person> person_list) {
        // 函数式接口回调
        Integer affected_rows = myBatisExecute((SqlSession session) -> session.insert("agm_example_Person.insert_batch", person_list));
        return affected_rows;
    }

    /**
     * 执行更新操作
     *
     * @param person
     * @return
     */
    public Integer updatePerson(Person person) {
        // 函数式接口回调
        return myBatisExecute((session) -> {
            return session.update("agm_example_Person.update_person", person);
        });
    }

    /**
     * 批量删除用户
     *
     * @param persons
     * @return
     */
    public Integer deletePersons(List<Person> persons) {
        // 函数式接口回调
        return myBatisExecute((session) -> {
            return session.delete("agm_example_Person.delete_specified_person", persons);
        });
    }

    /**
     * 以接口方式配置简单查询，以ｘｍｌ方式配置复杂查询, 此为简单查询，利用接口进行映射
     *
     * @param id
     * @return
     */
    public Person getPerson(Integer id) {
        //传入相应的映射接口，调用接口映射的方法。
        return (Person) myBatisMapperExecute((PersonMapper mapper) -> {
            return mapper.selectPerson(id);
        }, PersonMapper.class);
    }

    /**
     * 以接口方式配置简单查询，以ｘｍｌ方式配置复杂查询, 此为简单查询，利用接口进行映射
     * <p>
     * 查询所有数据，分页,在使用MapperInterface时，我们使用PageHelper.offsetPage(1,5);来进行分页，其中
     * 第一个参数代表页数，第二个参数代表分页大小。
     *
     * @return
     */
    public List<Person> getPersonMapper() {
        //传入相应的映射接口，调用接口映射的方法。
        return (List<Person>) myBatisMapperExecute((PersonMapper mapper) -> {
            PageHelper.offsetPage(1,5);
            return mapper.selectAllPersons();
        }, PersonMapper.class);
    }

}
