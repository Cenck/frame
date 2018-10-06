package com.cengel.starbucks.model.obj;

import com.cengel.starbucks.annotation.Description;
import org.apache.commons.lang3.StringUtils;

/**
 * 分页数据封装类.
 *
 * @author andy
 */
public class Page implements java.io.Serializable {

	private static final long serialVersionUID = -5823482162101235168L;

	public static final String ORDER_ASC  = "ASC";
	public static final String ORDER_DESC = "DESC";

	// 指针起始位置
	private int offset;
	/** 获取行数 默认单次10行 */
	private int limit;

	@Description("需要排序的字段名")
	private String sort;
	private String order;
	/** 当前页数 */
	private int    page = 1;

	public Page() {
		this(0, Integer.MAX_VALUE);
	}

	public Page(int offset, int limit) {
		this.offset = offset;
		this.limit = limit;
	}

	public Page(int offset, int limit, String sort, String order) {
		this.offset = offset;
		this.limit = limit;
		this.sort = sort;
		this.order = order;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		if (limit < 1) limit = 10;
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	@Description("是否逆序排列")
	public void setDescOrder(boolean desc) {
		if (desc) {
			this.order = ORDER_DESC;
		} else {
			this.order = ORDER_ASC;
		}
	}

	public int getOffsetLimit() {
		return this.offset + this.limit;
	}

	public String toString() {
		return "[Page: " + offset + ", " + limit + ", " + sort + ", " + order + "]";
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public static Page renderPage(Page pg) {
		if (pg.getPage() <= 0) {
			return null;
		}
		int start = (pg.getPage() - 1) * pg.getLimit();
		if (StringUtils.isEmpty(pg.getSort())) {
			return new Page(start, pg.getLimit());
		} else {
			return new Page(start, pg.getLimit(), pg.getSort(), pg.getOrder());
		}
	}
}
