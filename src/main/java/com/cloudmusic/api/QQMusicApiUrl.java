package com.cloudmusic.api;

/**
 * @author simple
 * @description qq音乐Api接口
 * @date 2019/1/10 11:14
 */
public class QQMusicApiUrl {
    //获取排行榜列表
    public static final String rankUrl="https://c.y.qq.com/v8/fcg-bin/fcg_myqq_toplist.fcg";
    //获取排行榜中的歌曲数据
    public static final String rankDetailUrl="https://c.y.qq.com/v8/fcg-bin/fcg_v8_toplist_cp.fcg";
    //获取首页轮播图列表
    public static final String bannerUrl="https://c.y.qq.com/musichall/fcgi-bin/fcg_yqqhomepagerecommend.fcg";

    //获取歌单详细信息
    public static final String playlistDetailUrl="https://c.y.qq.com/qzone/fcg-bin/fcg_ucc_getcdinfo_byids_cp.fcg";

    //获取歌手详情
    public static final String singerDetailUrl="https://c.y.qq.com/v8/fcg-bin/fcg_v8_singer_track_cp.fcg";
    //获取热门搜索
    public static final String hotSearchKeyUrl="https://c.y.qq.com/splcloud/fcgi-bin/gethotkey.fcg";
    //获取mv列表
    public static final String mvListUrl="https://c.y.qq.com/mv/fcgi-bin/getmv_by_tag";

}
