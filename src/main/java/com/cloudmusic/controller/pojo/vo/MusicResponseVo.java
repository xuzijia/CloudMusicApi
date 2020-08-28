package com.cloudmusic.controller.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class MusicResponseVo<T> {
    private Integer code;
    private String message;
    private T data;
    private List<T> dataList;
}
