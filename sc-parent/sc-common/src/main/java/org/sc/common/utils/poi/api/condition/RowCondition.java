package org.sc.common.utils.poi.api.condition;

/**
 * Defines conditional row creation routines.
 * 
 * @author i.voshkulat
 *
 * @param <T> implementing builder type
 */
public interface RowCondition<T> {

	/**
	 * Create row in current position within the sheet and process all the configurations on it only if condition evaluates to true.
	 * 
	 * @param condition execution condition
     * @return this
	 */
	public T nextConditionalRow(boolean condition);
	
}
