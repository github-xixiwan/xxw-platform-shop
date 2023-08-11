package com.xxw.shop.api.search.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class EsPageVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int INIT_VALUE = -1;

    /**
     * 当前页数据。
     */
    private List<T> records = Collections.emptyList();

    /**
     * 当前页码。
     */
    private long pageNumber = 1;

    /**
     * 每页数据数量。
     */
    private long pageSize = 10;

    /**
     * 总页数。
     */
    private long totalPage = INIT_VALUE;

    /**
     * 总数据数量。
     */
    private long totalRow = INIT_VALUE;

    /**
     * 是否优化分页查询 COUNT 语句。
     */
    private boolean optimizeCountQuery = true;

    /**
     * 创建分页对象。
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页数据数量
     * @param <T>        数据类型
     * @return 分页对象
     */
    public static <T> EsPageVO<T> of(Number pageNumber, Number pageSize) {
        return new EsPageVO<>(pageNumber, pageSize);
    }

    /**
     * 创建分页对象。
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页数据数量
     * @param totalRow   总数据数量
     * @param <T>        数据类型
     * @return 分页对象
     */
    public static <T> EsPageVO<T> of(Number pageNumber, Number pageSize, Number totalRow) {
        return new EsPageVO<>(pageNumber, pageSize, totalRow);
    }

    /**
     * 创建分页对象。
     */
    public EsPageVO() {
    }

    /**
     * 创建分页对象。
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页数据数量
     */
    public EsPageVO(Number pageNumber, Number pageSize) {
        this.setPageNumber(pageNumber);
        this.setPageSize(pageSize);
    }

    /**
     * 创建分页对象。
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页数据数量
     * @param totalRow   总数居数量
     */
    public EsPageVO(Number pageNumber, Number pageSize, Number totalRow) {
        this.setPageNumber(pageNumber);
        this.setPageSize(pageSize);
        this.setTotalRow(totalRow);
    }

    /**
     * 创建分页对象。
     *
     * @param records    当前页数据
     * @param pageNumber 当前页码
     * @param pageSize   每页数据数量
     * @param totalRow   总数居数量
     */
    public EsPageVO(List<T> records, Number pageNumber, Number pageSize, Number totalRow) {
        this.setRecords(records);
        this.setPageNumber(pageNumber);
        this.setPageSize(pageSize);
        this.setTotalRow(totalRow);
    }

    /**
     * 获取当前页的数据。
     *
     * @return 当前页的数据
     */
    public List<T> getRecords() {
        return records;
    }

    /**
     * 设置当前页的数据。
     *
     * @param records 当前页的数据
     */
    public void setRecords(List<T> records) {
        if (records == null) {
            records = Collections.emptyList();
        }
        this.records = records;
    }

    /**
     * 获取当前页码。
     *
     * @return 页码
     */
    public long getPageNumber() {
        return pageNumber;
    }

    /**
     * 设置当前页码。
     *
     * @param pageNumber 页码
     */
    public void setPageNumber(Number pageNumber) {
        if (pageNumber.longValue() < 1) {
            throw new IllegalArgumentException("pageNumber must greater than or equal 1，current value is: " + pageNumber);
        }
        this.pageNumber = pageNumber.longValue();
    }

    /**
     * 获取当前每页数据数量。
     *
     * @return 每页数据数量
     */
    public long getPageSize() {
        return pageSize;
    }

    /**
     * 设置当前每页数据数量。
     *
     * @param pageSize 每页数据数量
     */
    public void setPageSize(Number pageSize) {
        if (pageSize == null || pageSize.longValue() < 0) {
            throw new IllegalArgumentException("pageSize must greater than or equal 0，current value is: " + pageSize);
        }
        this.pageSize = pageSize.longValue();
        this.calcTotalPage();
    }

    /**
     * 获取数据总数。
     *
     * @return 数据总数
     */
    public long getTotalPage() {
        return totalPage;
    }

    /**
     * 设置总页数。
     *
     * @param totalPage 总页数
     */
    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 获取数据总数。
     *
     * @return 数据总数
     */
    public long getTotalRow() {
        return totalRow;
    }

    /**
     * 设置数据总数。
     *
     * @param totalRow 数据总数
     */
    public void setTotalRow(Number totalRow) {
        this.totalRow = totalRow == null ? INIT_VALUE : totalRow.longValue();
        this.calcTotalPage();
    }

    /**
     * 计算总页码。
     */
    private void calcTotalPage() {
        if (pageSize < 0 || totalRow < 0) {
            totalPage = INIT_VALUE;
        } else {
            totalPage = totalRow % pageSize == 0 ? (totalRow / pageSize) : (totalRow / pageSize + 1);
        }
    }

    /**
     * 当前页是否为空。
     *
     * @return {@code true} 空页，{@code false} 非空页
     */
    public boolean isEmpty() {
        return getTotalRow() == 0 || getPageNumber() > getTotalPage();
    }

    /**
     * 是否存在下一页。
     *
     * @return {@code true} 存在下一页，{@code false} 不存在下一页
     */
    public boolean hasNext() {
        return getTotalPage() != 0 && getPageNumber() < getTotalPage();
    }

    /**
     * 是否存在上一页。
     *
     * @return {@code true} 存在上一页，{@code false} 不存在上一页
     */
    public boolean hasPrevious() {
        return getPageNumber() > 1;
    }

    /**
     * 获取当前分页偏移量。
     *
     * @return 偏移量
     */
    public long offset() {
        return getPageSize() * (getPageNumber() - 1);
    }

    /**
     * 设置是否自动优化 COUNT 查询语句。
     *
     * @param optimizeCountQuery 是否优化
     */
    public void setOptimizeCountQuery(boolean optimizeCountQuery) {
        this.optimizeCountQuery = optimizeCountQuery;
    }

    /**
     * 是否自动优化 COUNT 查询语句（默认优化）。
     *
     * @return {@code true} 优化，{@code false} 不优化
     */
    public boolean needOptimizeCountQuery() {
        return optimizeCountQuery;
    }

    public <R> EsPageVO<R> map(Function<? super T, ? extends R> mapper) {
        EsPageVO<R> newPage = new EsPageVO<>();
        newPage.pageNumber = pageNumber;
        newPage.pageSize = pageSize;
        newPage.totalPage = totalPage;
        newPage.totalRow = totalRow;

        if (records != null && !records.isEmpty()) {
            List<R> newRecords = new ArrayList<>(records.size());
            for (T t : records) {
                newRecords.add(mapper.apply(t));
            }
            newPage.records = newRecords;
        }
        return newPage;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                ", totalRow=" + totalRow +
                ", records=" + records +
                '}';
    }
}
