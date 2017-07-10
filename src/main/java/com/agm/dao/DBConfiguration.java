package com.agm.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.agm.dao.function.SQLExecutor;
import com.agm.dao.function.MapperSQLExecutor;

/**
 * @author David-Yang
 */
public abstract class DBConfiguration {
	private static SqlSessionFactory factory = null;

	static {
		try {
			factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatisconfig/mybatis-configuration.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用于执行XML类型Mapper配置文件
	 * 
	 * @param executeSQL database operation
	 * @return
	 * @throws Exception
	 */
	protected <T> T myBatisExecute(SQLExecutor<T> executeSQL){
		T t = null;
		SqlSession session = null;
		try {
			session = factory.openSession(true);
			t = executeSQL.executor(session);
			session.commit();
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (null != session)
				session.close();
		}

		return t;
	}
	
	/**
	 * 用于执行接口类型Mapper的配置（mapperInterface）
	 * Interface Mapper(基于注解和接口)
	 * @param sqlExecuter
	 * @param e
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T,E> T myBatisMapperExecute(MapperSQLExecutor<T,E> sqlExecuter, Class<?> e) {
		T t=null;
		SqlSession session=null;
		try {
			session=factory.openSession(true);
			E mapper=(E) session.getMapper(e);
			t=sqlExecuter.executor(mapper);
			session.commit();
		} catch (Exception es) {
			es.printStackTrace();
		}finally {
			if(session!=null)
				session.close();
		}
		return t;
	}
}
