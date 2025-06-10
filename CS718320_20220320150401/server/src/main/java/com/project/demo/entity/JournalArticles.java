package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 *期刊文章：(JournalArticles)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "JournalArticles")
public class JournalArticles implements Serializable {

    //JournalArticles编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "journal_articles_id")
    private Integer journal_articles_id;
   // 标题
   @Basic
    private String title;
   // 作者
   @Basic
    private String author;
   // 发布日期
   @Basic
    private Timestamp release_date;
   // 文章附件
   @Basic
    private String article_attachment;
   // 文章封面
   @Basic
    private String article_cover;
   // 文章内容
   @Basic
    private String article_content;

    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}
