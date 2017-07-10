package com.agm.dao.function;

import org.apache.ibatis.session.SqlSession;

/**
 * The Method To Execute SQL,用于执行复杂查询（基于xml）模式的方法
 * @author david
 * 
 * @param <T>
 */
@FunctionalInterface
public interface SQLExecutor<T> {
	T executor(SqlSession session);
}
