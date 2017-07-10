package com.agm.dao.function;

/**
 * 用于执行简单查询，基于MapperInterface的方法，
 * @author david
 *
 * @param <E>
 * @param <T>
 */
@FunctionalInterface
public interface MapperSQLExecutor<T,E> {
	T executor(E e);
}
