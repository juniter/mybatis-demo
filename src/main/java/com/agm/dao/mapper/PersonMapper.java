package com.agm.dao.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.agm.model.Person;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author david
 * 简单查询，利用interface mapper进行映射，复杂的则使用xml
 * 调用方法时会自动利用接口和语句映射，这是一个简单的接口映射
 * 这个例子提供了三个重载方法，按照不同的方法查询，分页，不分页，
 * @important
 *  需要注意的是，使用接口映射时，方法名相当于xml配置里面的id，不可重复，要实现不同的功能，请取不同的名称，不能重载方法。
 */
@Mapper
public interface PersonMapper{
	@Select("SELECT * FROM person WHERE id=#{id}")
	Person selectPerson(int id);
	@Select("SELECT * FROM person")
	List<Person> selectAllPersons();
}
