package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 *稿件分类：(ManuscriptClassification)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "ManuscriptClassification")
public class ManuscriptClassification implements Serializable {

    //ManuscriptClassification编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manuscript_classification_id")
    private Integer manuscript_classification_id;
   // 稿件类型
   @Basic
    private String manuscript_type;

    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}
