package com.example.scoring_system.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/** 
* @Description: 与数据库映射的bean
* @Author: 曹鑫
* @Date: 2021/4/28 
*/
@Data
public class DetailsData {
    String id;
    String detailsName;
    String totalScoreRatio;
    String createUserId;
    String createTime;
    String taskId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailsData that = (DetailsData) o;
        return Objects.equals(detailsName, that.detailsName) &&
                Objects.equals(totalScoreRatio, that.totalScoreRatio) &&
                Objects.equals(createUserId, that.createUserId) &&
                Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(detailsName, totalScoreRatio, createUserId, taskId);
    }
}
