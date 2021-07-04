//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.poi.test.util;

import java.util.ArrayList;
import java.util.List;

public class ExcelEntity {
    private List<String> head;
    private List<RowEntity> Body;
    private String sheetname;

    public ExcelEntity() {
    }

    public List<String> getHead() {
        return this.head;
    }

    public void setHead(List<String> head) {
        this.head = head;
    }

    public String getSheetname() {
        return this.sheetname;
    }

    public void setSheetname(String sheetname) {
        this.sheetname = sheetname;
    }

    public void setBody(List<RowEntity> body) {
        this.Body = body;
    }

    public List<RowEntity> getBody() {
        return this.Body;
    }

    public void addRow(RowEntity rowEntity) {
        if (this.Body == null) {
            this.Body = new ArrayList();
        }

        this.Body.add(rowEntity);
    }
}
