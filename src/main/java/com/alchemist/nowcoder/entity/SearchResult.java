package com.alchemist.nowcoder.entity;

import java.util.List;

/**
 * 自定义实体
 * 用于保存ES查询中使用到的列表和总行数
 */
public class SearchResult {
    private List<DiscussPost> list;
    private long total;

    public SearchResult(List<DiscussPost> list, long total){
        this.list = list;
        this.total = total;
    }

    public List<DiscussPost> getList() {
        return list;
    }

    public void setList(List<DiscussPost> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
