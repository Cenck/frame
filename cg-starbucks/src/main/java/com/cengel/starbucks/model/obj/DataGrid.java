package com.cengel.starbucks.model.obj;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据列表返回格式封装实体。
 * 
 * @author galaxy
 * @param <T>
 *            数据列表类型
 */
public class DataGrid<T> implements java.io.Serializable {

	private static final long	serialVersionUID	= -4353059775338989141L;

	// 总数量
	private Integer				numRows;

	// 当前分页记录列表
	private Collection<T>		items;

	// 标识字段
	private String				identified			= "id";

	// 统计信息
	private Map<String, Object>	stats				= new HashMap<String, Object>();

	private Integer				limit;

	private Integer				offsetLimit;

	//面角显示
	private String footer;
	
	public DataGrid() {
	}

	public DataGrid(Collection<T> items) {
		this(items, items.size());
	}

	public DataGrid(Collection<T> items, Integer numRows) {
		this.items = items;
		this.numRows = numRows;
	}

	public DataGrid(Collection<T> items, Integer numRows, Integer offsetLimit, Integer limit) {
		this.items = items;
		this.numRows = numRows;
		this.limit = limit;
		this.offsetLimit = offsetLimit;
	}

	public DataGrid(Collection<T> items, Integer numRows, String identified) {
		this(items, items.size());
		this.identified = identified;
	}

	public Integer getNumRows() {
		return numRows;
	}

	public void setNumRows(Integer numRows) {
		this.numRows = numRows;
	}

	public Collection<T> getItems() {
		return items;
	}

	public void setItems(Collection<T> items) {
		this.items = items;
	}

	public String getIdentified() {
		return identified;
	}

	public void setIdentified(String identified) {
		this.identified = identified;
	}

	public Map<String, Object> getStats() {
		return stats;
	}

	public void setStats(Map<String, Object> stats) {
		this.stats = stats;
	}

	/**
	 * 增加统计信息
	 * 
	 * @param name
	 * @param value
	 */
	public void addStat(String name, Object value) {
		this.stats.put(name, value);
	}

	/**
	 * 返回一个空的数据列表。
	 * 
	 * @return
	 */
	public static <T> DataGrid<T> empty() {
		Collection<T> items = Collections.emptyList();
		return new DataGrid<T>(items);
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffsetLimit() {
		return offsetLimit;
	}

	public void setOffsetLimit(Integer offsetLimit) {
		this.offsetLimit = offsetLimit;
	}

	public Integer getCurrentPage() {
		return this.getOffsetLimit() / this.getLimit();
	}

	public Integer getTotalPage() {
		return numRows % this.getLimit() == 0 ? numRows / this.getLimit() : numRows / this.getLimit() + 1;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}


	/**
	 * 将分页数据对象转换为JSON并设置到data属性。
	 *
	 * @param datagrid
	 */
	public static Map<String, Object> renderDataGrid(DataGrid<?> datagrid) {
		if (datagrid == null) return null;
		Map<String, Object> items = new HashMap<String, Object>();
		items.put("code",0);
		items.put("count", datagrid.getNumRows());
		items.put("data", datagrid.getItems());
		return items;
	}
}
