package com.cloudmusic.controller.pojo.vo;

import lombok.Data;
import java.util.Map;
/**
 * 歌曲信息
 */
@Data
public class SongVo {
    private String id;
    private String type;
    private String songName;
    private String singerName;
    private String songUrl;
    private Map<String,String> mvUrl;
    private String lyric;
    private String imgUrl;
}
