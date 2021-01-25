package org.uniplore.tools;

import cn.hutool.poi.excel.sax.handler.RowHandler;

import java.util.ArrayList;
import java.util.List;

public class Excel07ReaderUtil {

    // 预览行数
    private int previewNumber = 100;
    private List<List<String>> list = new ArrayList<>();
    private List<List<Object>> dataList = new ArrayList<>();
    private List<String> tmpList;
    private List<Object> tmpList2;

    public int getPreviewNumber() {
        return previewNumber;
    }

    public void setPreviewNumber(int previewNumber) {
        this.previewNumber = previewNumber;
    }

    public List<List<Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<List<Object>> dataList) {
        this.dataList = dataList;
    }

    public List<List<String>> getList() {
        return list;
    }

    public void setList(List<List<String>> list) {
        this.list = list;
    }

    public RowHandler previewDataRowHandler() {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, int rowIndex, List<Object> rowlist) {
                // 预览前100条数据
                if (rowIndex <= previewNumber) {
                    tmpList = new ArrayList<String>();
                    for (Object obj : rowlist) {
                        if (obj != null) {
                            tmpList.add(obj.toString().trim());
                        }else{
                            tmpList.add("");
                        }
                    }
                    list.add(tmpList);
                }
            }
        };
    }

    public RowHandler getDataRowHandler() {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, int rowIndex, List<Object> rowlist) {
                tmpList2 = new ArrayList<>();
                for (Object obj : rowlist) {
                    tmpList2.add(obj);
                }
                dataList.add(tmpList2);
            }
        };
    }
}
