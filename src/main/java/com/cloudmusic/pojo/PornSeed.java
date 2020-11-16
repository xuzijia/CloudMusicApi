package com.cloudmusic.pojo;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "porn_seed")
@Entity
public class PornSeed {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "link_url")
    private String linkUrl;
    @Column(name="status")
    private Integer status;
    @Column(name = "create_date")
    private Date createDate;


}
