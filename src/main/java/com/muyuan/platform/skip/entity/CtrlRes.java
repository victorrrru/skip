package com.muyuan.platform.skip.entity;

import com.muyuan.platform.skip.entity.db.MyOperationLog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * @author 范文武
 * @date 2018/11/28 11:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CtrlRes extends MyOperationLog implements Serializable {

    //ip
    private String ip;
}
