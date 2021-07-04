package com.poi.test.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;


/**
 * 值集
 * @author conyfrommars
 *
 */
@Setter
@Getter
@ToString
//@ApiModel(description = "值集")
@Data
public class DataSetTmpVo implements Serializable {

	private static final long serialVersionUID = 1L;

//	@ApiModelProperty(value = "值编码", required = false)
    private String dataSetCode;

//	@ApiModelProperty(value = "值含义中文", required = false)
    private String dataSetMeaning;

//	@ApiModelProperty(value = "值含义英文", required = false)
	private String dataSetEnMeaning;

//	@ApiModelProperty(value = "编码体系soid", required = false)
    private String derailCodingSystemSoid;


//	@ApiModelProperty(value = "描述", required = false)
    private String dataSetDescription;

//	@ApiModelProperty(value = "上级值编码", required = false)
    private String superDataSetCode;

//	@ApiModelProperty(value = "状态", required = false)
	private String status;

	/**
	 * 上级值编码的取得方式 0:soid在数据库中 0以外：soid重新生成
	 */
	private String superDataSetFlg;


    public DataSetTmpVo () {

	}

	public DataSetTmpVo (Map<String, Object> map) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if ("编码体系soid".equals(entry.getKey())) {
				this.derailCodingSystemSoid = (String)entry.getValue();
			}

			if ("值编码".equals(entry.getKey())) {
				this.dataSetCode = (String)entry.getValue();
			}

			if ("值含义中文".equals(entry.getKey())) {
				this.dataSetMeaning = (String)entry.getValue();
			}

			if ("值含义英文".equals(entry.getKey())) {
				this.dataSetEnMeaning = (String)entry.getValue();
			}

			if ("值说明".equals(entry.getKey())) {
				this.dataSetDescription = (String)entry.getValue();
			}

			if ("状态".equals(entry.getKey())) {
				this.status = (String)entry.getValue();
			}

			if ("上级值编码".equals(entry.getKey())) {
				this.superDataSetCode = (String)entry.getValue();
			}
		}
	}
}