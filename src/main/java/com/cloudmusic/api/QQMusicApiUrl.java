package com.cloudmusic.api;

/**
 * @author simple
 * @description qq音乐Api接口
 * @date 2019/1/10 11:14
 */
public class QQMusicApiUrl {
    //获取首页轮播图列表
    public static final String bannerUrl="https://c.y.qq.com/musichall/fcgi-bin/fcg_yqqhomepagerecommend.fcg";
    //获取排行榜列表
    public static final String rankUrl="https://c.y.qq.com/v8/fcg-bin/fcg_myqq_toplist.fcg";
    //获取排行榜中的歌曲数据
    public static final String rankDetailUrl="https://c.y.qq.com/v8/fcg-bin/fcg_v8_toplist_cp.fcg";
    //获取歌单列表
    public static final String playListUrl="https://c.y.qq.com/splcloud/fcgi-bin/fcg_get_diss_by_tag.fcg";
    //获取歌单分类列表
    public static final String playListCategoryUrl="https://c.y.qq.com/splcloud/fcgi-bin/fcg_get_diss_tag_conf.fcg";
    //获取歌单详细信息
    public static final String playlistDetailUrl="https://c.y.qq.com/qzone/fcg-bin/fcg_ucc_getcdinfo_byids_cp.fcg";
    //获取歌手列表
    public static final String singerListUrl="https://u.y.qq.com/cgi-bin/musicu.fcg";
    //获取歌手单曲列表
    public static final String singerSongListUrl="https://c.y.qq.com/v8/fcg-bin/fcg_v8_singer_track_cp.fcg";
    //获取歌手专辑列表
    public static final String singerAlbumListUrl="https://u.y.qq.com/cgi-bin/musicu.fcg";
    //获取歌手的mv列表
    public static final String singerMvListUrl="https://c.y.qq.com/mv/fcgi-bin/fcg_singer_mv.fcg";
    //获取专辑详情
    public static final String albumDetailUrl="https://c.y.qq.com/v8/fcg-bin/fcg_v8_album_info_cp.fcg";
    //获取热门搜索
    public static final String hotSearchKeyUrl="https://c.y.qq.com/splcloud/fcgi-bin/gethotkey.fcg";
    //搜索接口
    public static final String SearchUrl="https://c.y.qq.com/soso/fcgi-bin/client_search_cp";
    //获取mv列表
    public static final String mvListUrl="https://c.y.qq.com/mv/fcgi-bin/getmv_by_tag";
    //首页推荐数据
    public static final String recommendUrl="https://u.y.qq.com/cgi-bin/musicu.fcg";
    //获取核心加密参数vkey
    //public static final String vkeyUrl="https://c.y.qq.com/base/fcgi-bin/fcg_music_express_mobile3.fcg";
    public static final String vkeyUrl="https://c.y.qq.com/base/fcgi-bin/fcg_music_express_mobile3.fcg";
    //guid
    public static final String guid="2295443695";
    //音乐源地址
    public static final String MusicSourceUrl="http://dl.stream.qqmusic.qq.com/{1}?vkey={2}&guid="+guid+"&fromtag=1";


    //获取音乐源地址请求参数模板
    public static final String MusicRequestParamData="{\"req\":{\"module\":\"CDN.SrfCdnDispatchServer\",\"method\":\"GetCdnDispatch\",\"param\":{\"guid\":\"7403626864\",\"calltype\":0,\"userip\":\"\"}},\"req_0\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\",\"param\":{\"guid\":\"7403626864\",\"songmid\":[\"%s\"],\"songtype\":[0],\"uin\":\"2295443695\",\"loginflag\":1,\"platform\":\"20\"}},\"comm\":{\"uin\":2295443695,\"format\":\"json\",\"ct\":24,\"cv\":0}}";
    //获取音乐详细信息 请求参数模板
    public static final String musicInfoRequestParamData="{\"comm\":{\"ct\":24,\"cv\":0},\"songinfo\":{\"method\":\"get_song_detail_yqq\",\"param\":{\"song_type\":0,\"song_mid\":\"%s\",\"song_id\":\"%s\"},\"module\":\"music.pf_song_detail_svr\"}}";
    //获取mv详细信息 请求参数模板
    public static final String mvInfoRequestParamData="{\"comm\":{\"ct\":24,\"cv\":0},\"mv\":{\"module\":\"MvService.MvInfoProServer\",\"method\":\"GetMvBySongid\",\"param\":{\"mids\":[\"%s\"]}}}";
    //获取mv播放地址 请求参数模板
//    public static final String mvUrlRequestParamData="%7B\"getMVInfo\"%3A%7B\"module\"%3A\"video.VideoDataServer\"%2C\"method\"%3A\"get_video_info_batch\"%2C\"param\"%3A%7B\"vidlist\"%3A%5B\"{vid}\"%5D%2C\"required\"%3A%5B\"vid\"%2C\"sid\"%2C\"gmid\"%2C\"type\"%2C\"name\"%2C\"cover_pic\"%2C\"video_switch\"%2C\"msg\"%5D%2C\"from\"%3A\"h5.mvplay\"%7D%7D%2C\"getMVUrl\"%3A%7B\"module\"%3A\"gosrf.Stream.MvUrlProxy\"%2C\"method\"%3A\"GetMvUrls\"%2C\"param\"%3A%7B\"vids\"%3A%5B\"{vid}\"%5D%2C\"from\"%3A\"h5.mvplay\"%7D%2C\"request_typet\"%3A10001%7D%7D";
    public static final String mvUrlRequestParamData="{\"getMvUrl\":{\"module\":\"gosrf.Stream.MvUrlProxy\",\"method\":\"GetMvUrls\",\"param\":{\"vids\":[\"%s\"],\"request_typet\":10001,\"addrtype\":3,\"format\":264}},\"comm\":{\"ct\":6,\"cv\":4747474,\"g_tk\":5381,\"uin\":0,\"format\":\"json\",\"platform\":\"yqq\"}}";
    //获取音乐信息
    public static final String MusicUrlApi="https://u.y.qq.com/cgi-bin/musicu.fcg";
    //音乐播放地址前缀
    public static final String urlPrefixStr="http://ws.stream.qqmusic.qq.com/";
    //搜索接口
    public static final String searchUrl="https://c.y.qq.com/soso/fcgi-bin/client_search_cp";
    //获取歌词
    public static final String getLyricUrl="https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?g_tk=753738303";
}
