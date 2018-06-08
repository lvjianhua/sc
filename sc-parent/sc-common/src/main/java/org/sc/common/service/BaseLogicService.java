package org.sc.common.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface BaseLogicService<T> {
	/**
	 * 
	 * 根据id获取对应的对象
	 * 
	 * @param: @param
	 *             orderId
	 * @param: @return
	 * @return: Order
	 * @throws:
	 * @author: lvjh
	 * @param <N>
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getById")
	public T getById(@RequestParam(value = "id") String id);

	/**
	 * 
	 * 更新对应的对象
	 * 
	 * @param: @param
	 *             order
	 * @param: @return
	 * @return: Order
	 * @throws:
	 * @author: lvjh
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public T update(T object);

	/**
	 * 
	 * 保存对象
	 * 
	 * @param: @param
	 *             order
	 * @param: @return
	 * @param: @throws
	 *             ServiceRunTimeException
	 * @return: Order
	 * @throws: order
	 *              id is not null!
	 * @author: lvjh
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public T save(T object);
}
