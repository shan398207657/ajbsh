package com.work.ssj.common.utils;

import com.baomidou.mybatisplus.plugins.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 * 
 * @author zh
 * @email 45838976@qq.com
 * @date 2016年11月4日 下午12:59:00
 */
public class PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	//总记录数
	private int totalCount;
	//每页记录数
	private int pageSize;
	//总页数
	private int totalPage;
	//当前页数
	private int currPage;
	//列表数据
	private List<?> list;
	
	/**
	 * 分页
	 * @param list        列表数据
	 * @param totalCount  总记录数
	 * @param pageSize    每页记录数
	 * @param currPage    当前页数
	 */
	public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
	}

	/**
	 * 分页
	 */
	public PageUtils(Page<?> page) {
		this.list = page.getRecords();
		this.totalCount = (int)page.getTotal();
		this.pageSize = page.getSize();
		this.currPage = page.getCurrent();
		this.totalPage = (int)page.getPages();
	}

	/**
	 * 总记录条数（根据查询条件查出的结果count条数）
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}
	/**
	 * 总记录条数（根据查询条件查出的结果count条数）
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * 页长（每页显示的记录条数）
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 页长（每页显示的记录条数）
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * 总页数（由总条数/页长计算得到）
	 */
	public int getTotalPage() {
		if (totalPage==0){
			int i = getTotalCount()%getPageSize();
			if(i==0){
				return getTotalCount()/getPageSize();
			}
			return getTotalCount()/getPageSize()+1;
		}
		return totalPage;
	}
	/**
	 * 总页数（由总条数/）
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	/**
	 * 当前查询的页码(显示的第几页)
	 */
	public int getCurrPage() {
		return currPage;
	}
	/**页长计算得到
	 * 当前查询的页码(显示的第几页)
	 */
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	/**
	 * 计算分页查询逻辑中起始的行号
	 * @return
	 */
	public int getFirstIndex() {
		return (currPage - 1) * pageSize;
	}
}
