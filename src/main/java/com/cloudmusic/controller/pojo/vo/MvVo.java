package com.cloudmusic.controller.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class MvVo {
    private String id;
    private String coverPic;
    private String name;
    private String singId;
    private List<MvDetilsVo> mvDetails;
}
