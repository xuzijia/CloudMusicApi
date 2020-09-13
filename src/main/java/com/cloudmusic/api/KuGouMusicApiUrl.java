package com.cloudmusic.api;

/**
 * @author simple
 * @description 酷狗音乐API接口地址
 * @date 2019/1/11 11:16
 */
public class KuGouMusicApiUrl {
    //搜索接口
    public static final String searchUrl="http://mobilecdn.kugou.com/api/v3/search/song";
    //获取歌曲播放地址
    public static final String songPlayerUrl="http://trackercdn.kugou.com/i/?cmd=4&hash={hash}&key={key}&pid=1&forceDown=0&vip=1";
    //获取歌曲信息
//    public static final String songInfoUrl="https://m3ws.kugou.com/api/v1/song/get_song_info";
    public static final String songInfoUrl="https://wwwapi.kugou.com/yy/index.php";

    //获取mv信息
    public static final String getMvInfo="https://gateway.kugou.com/v1/get_video_privilege";
    //获取mv播放地址
    public static final String getMvUrl="http://trackermv.kugou.com/interface/index/cmd=100&hash={hash}&key={key}&ext=mp4";
    //获取歌词地址
    public static final String getLyric="https://m3ws.kugou.com/api/v1/krc/get_krc";


}
