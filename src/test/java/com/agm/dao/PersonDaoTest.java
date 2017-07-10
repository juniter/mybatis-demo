package com.agm.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;

import com.agm.model.Person;

public class PersonDaoTest {

	/**
	 * 测试批量获取
	 */
	@Test
	public void testGetAllPersons() {
		System.out.println("测试getAllPersons()方法\n");
		List<Person> persons=PersonDao.INSTANCE.getAllPersons();
		persons.forEach((person)->{
			System.out.println(person.toString());
		});
	}
	@Test
	public void testGetAllPersonsMapper() {
		System.out.println("测试getAllPersons()方法\n");
		List<Person> persons=PersonDao.INSTANCE.getPersonMapper();
		if(null!=persons)
			persons.forEach((person)->{
				System.out.println(person.toString());
			});
	}

	/**
	 * 测试批量删除
	 */
	@Test
	public void testDeletePersonPersons() {
		System.out.println("测试deletePersons()方法\n");
		List<Person> persons=new ArrayList<>();
		for(int i=0;i<2;i++){
			Person person=new Person();
			person.setId(i+1);
			persons.add(person);
		}
		Integer affected_rows=PersonDao.INSTANCE.deletePersons(persons);
		System.out.println("Affected　Rows:"+affected_rows);
		this.testGetAllPersons();
	}

	/**
	 * 测试单个删除
	 */
	@Test
	public void testGetPersons() {
		System.out.println("测试deletePersons()方法\n");
		Person person=new Person();
		person.setId(3);
		List<Person> list=PersonDao.INSTANCE.getPersons(person);
		System.out.println("Affected　Rows:"+list.size()+"\n Selected Records:\n");
		list.forEach(new Consumer<Person>() {

			@Override
			public void accept(Person t) {
				System.out.println(t.toString()+"\n");
			}
			
		});
		this.testGetAllPersons();
	}

	/**
	 * 测试批量插入
	 */
	@Test
	public void testInsertPersons() {
		System.out.println("测试InsertPersons()方法\n");
		List<Person> persons=new ArrayList<>();
		for(int i=0;i<2;i++){
			Person person=new Person();
			person.setName("Test"+i);
			person.setPassword("PWD"+i);
			person.setAddress("Address"+i);
			person.setPhone("1778j"+i);
			person.setCompany("Alpha Go "+i);
			persons.add(person);
		}
		
		Integer affected_rows=PersonDao.INSTANCE.insertPersons(persons);
		System.out.println("Affected　Rows:"+affected_rows+"\n Selected Records:\n");
		this.testGetAllPersons();
	}

	@Test
	public void testUpdatePerson() {
		System.out.println("测试UpdatePerson()方法\n");
		Person person=new Person();
		person.setName("te");
		person.setPassword("tes");
		person.setPhone("1778");
		person.setId(4);
		System.out.println("The Record Will Be Set To :"+person.toString());
		PersonDao.INSTANCE.updatePerson(person);
		this.testGetAllPersons();
	}

	@Test
	public void testGetPerson() {
		System.out.println("测试使用接口InterfaceMapper方式获取用户\n简单查询模式\n");
		Person person=PersonDao.INSTANCE.getPerson(3);
		System.out.println(person.toString()); 
	}
}
