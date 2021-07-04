//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.poi.test.util;

import java.util.ArrayList;
import java.util.List;

public class RowEntity {
    private List<CellEntity> cells;
    private int rowcross = 1;

    public RowEntity() {
    }

    public void setCells(List<CellEntity> cells) {
        this.cells = cells;
    }

    public void addCell(CellEntity cell) {
        if (this.cells == null) {
            this.cells = new ArrayList();
        }

        this.cells.add(cell);
    }

    public List<CellEntity> getCells() {
        return this.cells;
    }

    public void setRowcross(int rowcross) {
        this.rowcross = rowcross;
    }

    public int getRowcross() {
        return this.rowcross;
    }
}
