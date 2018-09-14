package com.Vo;

import java.util.Map;

/**
 * Created by huangyt on 2017/7/10.
 */
public class XlsRowVO {

    private static final long serialVersionUID = 5097785563547534592L;

    /** 行数 */
    private int   rowNo;

    /** 该行数据 */
    private Map<String,Object> mapData;


    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    public Map<String, Object> getMapData() {
        return mapData;
    }

    public void setMapData(Map<String, Object> mapData) {
        this.mapData = mapData;
    }
}
