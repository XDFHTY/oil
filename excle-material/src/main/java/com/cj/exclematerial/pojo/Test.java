package com.cj.exclematerial.pojo;

import java.util.List;

public class Test {
    private Integer a;
    private List list;

    public Test() {
    }

    public Test(Integer a, List list) {
        this.a = a;
        this.list = list;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
