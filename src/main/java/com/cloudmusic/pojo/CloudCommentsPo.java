package com.cloudmusic.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "cloud_comments")
@Data
public class CloudCommentsPo {

    @Id
    private String id;
    private String songName;

    private String singer;

    private String albumName;

    private String songId;

    private String commentUser;

    private String commentTime;


    private Long likeCount;


    private String cover;

    private String comment;


}
