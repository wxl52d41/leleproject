//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.poi.test.util;

import java.util.ArrayList;
import java.util.List;

public class CellEntity<T> {
    private List<T> cellval;
    private boolean flag;

    public CellEntity(List<T> cellval, boolean flag) {
        this.cellval = cellval;
        this.flag = flag;
    }

    public CellEntity(List<T> cellval) {
        this.cellval = cellval;
        this.flag = false;
    }

    public CellEntity(T cellval, boolean flag) {
        this.setCellval(cellval);
        this.flag = flag;
    }

    public CellEntity(T cellval) {
        this.setCellval(cellval);
        this.flag = false;
    }

    public CellEntity() {
    }

    public boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setCellval(List<T> cellval) {
        this.cellval = cellval;
    }

    public List<T> getCellval() {
        return this.cellval;
    }

    public void setCellval(T val) {
        this.cellval = new ArrayList();
        this.cellval.add(val);
    }

    public T getCellval(int i) {
        return this.cellval == null ? null : this.cellval.get(i);
    }

    public String getCellvalString(int i) {
        if (this.cellval != null && this.cellval.size() >= i + 1) {
            return this.cellval.get(i) == null ? "" : String.valueOf(this.cellval.get(i));
        } else {
            return "";
        }
    }

    public int getSize() {
        return this.cellval == null ? 0 : this.cellval.size();
    }
}
