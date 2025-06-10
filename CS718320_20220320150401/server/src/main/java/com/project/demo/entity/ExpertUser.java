package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 *专家用户：(ExpertUser)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "ExpertUser")
public class ExpertUser implements Serializable {

    //ExpertUser编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expert_user_id")
    private Integer expert_user_id;
   // 专家姓名
   @Basic
    private String name_of_expert;
   // 性别
   @Basic
    private String gender;
    // 用户编号
    @Id
    @Column(name = "user_id")
    private Integer userId;

    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}
