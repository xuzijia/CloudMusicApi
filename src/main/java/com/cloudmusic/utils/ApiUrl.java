package com.cloudmusic.utils;

/**
 * 网易云音乐API接口地址
 * Created by xuzijia
 * 2018/5/18 9:59
 */
public class ApiUrl {
    //手机号登陆API
    public static final String cellPhoneLoginUrl="http://music.163.com/weapi/login/cellphone";
    //刷新登陆状态
    public static final String RefreshLoginUrl="http://music.163.com/weapi/login/token/refresh";


    //音乐评论API
    public static final String MusicCommentUrl="http://music.163.com/weapi/v1/resource/comments/R_SO_4_{id}";
    //歌单评论API
    public static final String PlayListCommentUrl="http://music.163.com/weapi/v1/resource/comments/A_PL_0_{id}";
    //专辑评论API
    public static final String AlbumCommentUrl="http://music.163.com/weapi/v1/resource/comments/R_AL_3_{id}";
    //Mv评论API
    public static final String MvCommentUrl="http://music.163.com/weapi/v1/resource/comments/R_MV_5_{id}";
    //电台节目评论API
    public static final String DjCommentUrl="http://music.163.com/weapi/v1/resource/comments/A_DJ_1_{id}";


    //获取歌曲详情API
    public static final String SongDetailUrl="http://music.163.com/api/song/detail/?id={id}&ids=[{id}]";
    //歌曲歌词API
    public static final String SongLyricUrl="http://music.163.com/weapi/song/lyric?os=osx&id={id}&lv=-1&kv=-1&tv=-1";
    //歌曲播放地址API(该地址可以直接get请求)
    public static final String FinalSongUrl="http://music.163.com/song/media/outer/url?id={id}.mp3";
    //歌曲播放地址API(可以有多个id)
    public static final String songPlayerUrl="http://music.163.com/weapi/song/enhance/player/url";
    //推荐的新音乐
    public static final String songPersonalizedUrl="http://music.163.com/weapi/personalized/newsong";


    //歌手列表API
    public static final String artistListUrl="http://music.163.com/weapi/artist/list";
    //热门歌手列表API
    public static final String artistListHotUrl="http://music.163.com/weapi/artist/top";
    //歌手热门单曲API
    public static final String artistSongUrl="http://music.163.com/weapi/v1/artist/{id}";
    //歌手mv信息API
    public static final String artistMvUrl="http://music.163.com/weapi/artist/mvs";
    //歌手专辑API
    public static final String artistAlbumUrl="http://music.163.com/weapi/artist/albums/{id}";
    //歌手描述API
    public static final String artistDescUrl="http://music.163.com/weapi/artist/introduction";


    //相似歌手API
    public static final String simiArtistUrl="http://music.163.com/weapi/discovery/simiArtist";
    //相似MvAPI
    public static final String simiMvUrl="http://music.163.com/weapi/discovery/simiMV";
    //相似歌单API
    public static final String simiPlaylistUrl="http://music.163.com/weapi/discovery/simiPlaylist";
    //相似歌曲API
    public static final String simiSongUrl="http://music.163.com/weapi/v1/discovery/simiSong";
    //最近5个听了这首歌的用户API
    public static final String simiUserUrl="http://music.163.com/weapi/discovery/simiUser";


    //歌单分类API
    public static final String playlistCatListUrl="http://music.163.com/weapi/playlist/catalogue";
    //歌单详细内容API
    public static final String playlistDetailUrl="http://music.163.com/api/playlist/detail?id={id}";
    //热门歌单分类API
    public static final String playlistHotCatListUrl="http://music.163.com/weapi/playlist/hottags";
    //精品歌单API
    public static final String playlistHighqualityUrl="http://music.163.com/weapi/playlist/highquality/list";
    //网友精品碟歌单API
    public static final String playlistListUrl="http://music.163.com/weapi/playlist/list";
    //推荐的歌单
    public static final String playlistPersonalizedUrl="http://music.163.com/weapi/personalized/playlist";


    //歌曲排行榜API
    public static final String topListUrl="http://music.163.com/weapi/v3/playlist/detail";
    //歌手排行榜API
    public static final String topArtistUrl="http://music.163.com/eapi/toplist/artist?params=B5CAE4715306477C2EFA74D383640F01BF227BF8E889F80E2E2A442958463A7E589CC99878CFCE88D165B64712332AF39EC61B7E68903B2F9F079E8D1AB99FC61049A6D5B97AF8E6FFE8DA16ED540D2CFA80205B889ACA39F8B05AE593FDF5A094F118FF4600C2025094ECF6EB58F6D424B7A97B21A8C1D7CF0609AF2FBE9FDD88826E1667C889757BA920684C5C425FF01B5514AF1EB08AB7D298DB4D65187829E315F9FFBBEB43C2AE3DC21222B31CEC6FF337957AC122FBCB3E793FC1960151B0BDEBB1565BFD835E7A7D6A2D034A5591070D42C32DA4B69E0061C46D61239221A1C64EF676D891B44D7B855E27C82A7EB376F0B0C27952F2006E302B47DA1DE86C3488D53FD98ED9FDC6AA341DF0ECF92BA2E8F77E41811BF9447973C5C34FFED13E28AC544347F9E6ADF4B0008C371FC41C4490D3C9E1A225791D2170326231C40662633AA93D5CEF9AABC777AF268A4B13C560157339478DFAD5D910C966B43E1F017410DBF06D189E2BD6D0CD2682F343A83994E66CA73B5E2A67A122842BF945F2B434CBDE4C5A589A3A90F70DF1A8B63E7BAFBEB624956C62CFB1114AB841379541E5BB4625F2C28CAEA6A67E77A7EEAA1149D9D0F7E190D3A3408DF88B62FBF27996ABC925A93E5A67B4B0D1D931214BB07064F2BA4DCBA2E548E5A110E9B992C21E3930EB488172929C02C06D76BB193EF923D1906E0A0C4D75F5EB909AE77B0A2E55539A182D0B2533C654F2C90A038406B8850BFC022639F2B3FB7EDF40FD74AEA0B9119E9987D2909C01C587794F53459DB8EE83AA8D15FBEAC71EB3A00D8E40E78FE9A9A4068495D9257B39D8F825086F391FD5E7A48AACA96BC261E334A1929C81633234A0B22C573AEAD05BC8B4216283ACFD9E022950AEC812F554B913B4457FDF68AA2CC5E476922C2670D49154BC1DEB6D464F60DBFAD2BB4144762CD3721F52D42FDAE56DB9C529EDB6FB946CD725B3E2EA2AFDCF3F759D384B4F7F75AAA6F01F8093C8A140B3B388FF57272A6A7E10274290A79CDCA69E37BC066CE8CCD5B4BB4E12DA841B";


    //热门Mv API
    public static final String topMvUrl="http://music.163.com/weapi/mv/toplist";
    //mv详细信息API
    public static final String mvDetailUrl="http://music.163.com/weapi/mv/detail";
    //最新mv API
    public static final String MvNewUrl="http://music.163.com/weapi/mv/first";
    //推荐mv API
    public static final String recommendMvUrl="http://music.163.com/personalized/mv";

    //专辑内容API
    public static final String albumDetailUrl="http://music.163.com/weapi/v1/album/{id}";
    //获取新碟上架列表
    public static final String albumNewUrl="http://music.163.com/weapi/album/new";


    //搜索
    public static final String searchUrl="http://music.163.com/weapi/search/get";
    //搜索建议
    public static final String searchSuggestUrl="http://music.163.com/weapi/search/suggest/web";
    //搜索多重匹配
    public static final String searchMultimatchUrl="http://music.163.com/weapi/search/suggest/multimatch";
    //热搜
    public static final String searchHotUrl="http://music.163.com/weapi/search/hot";


    //获取推荐电台
    public static final String djRecommendUrl="http://music.163.com/weapi/djradio/recommend/v1";
    //获取电台分类
    public static final String djCatelistUrl="http://music.163.com/weapi/djradio/category/get";
    //获取指定分类电台推荐
    public static final String djRecommendTypeUrl="http://music.163.com/weapi/djradio/recommend";
    //获取电台订阅列表
    public static final String djSublistUrl="http://music.163.com/weapi/djradio/get/subed";
    //获取电台详情
    public static final String djDetailUrl="http://music.163.com/weapi/djradio/get";
    //获取电台节目
    public static final String djProgramUrl="http://music.163.com/weapi/dj/program/byradio";
    //订阅或者取消订阅电台
    public static final String djSubUrl="http://music.163.com/weapi/djradio/{action}";
    //推荐的电台
    public static final String djPersonalizedUrl="http://music.163.com/weapi/personalized/djprogram";
    //推荐的节目
    public static final String programPersonalizedUrl="http://music.163.com/weapi/program/recommend/v1";


    //获取banner
    public static final String bannerUrl="http://music.163.com/weapi/djradio/{action}";
    //独家放送
    public static final String privatecontentPersonalizedUrl="http://music.163.com/weapi/personalized/privatecontent";


    //--------以下接口需要登陆后才能操作----------

    //每日推荐歌曲API
    public static final String userRecommendUrl="http://music.163.com/weapi/v1/discovery/recommend/songs";
    //每日推荐歌单API
    public static final String playlistRecommendUrl="http://music.163.com/weapi/v1/discovery/recommend/resource";
    //用户私人FM API
    public static final String userPersonalFmUrl="http://music.163.com/weapi/v1/radio/get";
    //每日签到API
    public static final String userDailySigninUrl="http://music.163.com/weapi/point/dailyTask";
    //用户云盘API
    public static final String userCloudUrl="http://music.163.com/weapi/v1/cloud/get";
    //用户详情API
    public static final String userDetailUrl="http://music.163.com/weapi/v1/user/detail/{id}";
    //用户信息 , 歌单，收藏，mv, dj 数量API
    public static final String userSubcountUrl="http://music.163.com/weapi/subcount";
    //用户歌单API
    public static final String userPlaylistUrl="http://music.163.com/weapi/user/playlist";
    //更新歌单API
    public static final String userUpdatePlaylistUrl="http://music.163.com/weapi/batch";
    //更新用户信息API
    public static final String userUpdateUrl="http://music.163.com/weapi/user/profile/update";
    //获取用户电台API
    public static final String userDjUrl="http://music.163.com/weapi/dj/program/{id}";
    //获取用户关注列表API
    public static final String userFollowsUrl="http://music.163.com/weapi/user/getfollows/{id}";
    //获取用户粉丝列表API
    public static final String userFollowedsUrl="http://music.163.com/weapi/user/getfolloweds/";
    //获取用户动态API
    public static final String userEventUrl="http://music.163.com/weapi/event/get/{id}";
    //获取用户播放记录API
    public static final String userPlayRecordUrl="http://music.163.com/weapi/v1/play/record";
    //获取朋友动态消息API
    public static final String evnetUrl="http://music.163.com/weapi/v1/event/get";
    //收藏歌手API
    public static final String ArtistSubUrl="http://music.163.com/weapi/artist/sub";
    //取消收藏歌手API
    public static final String ArtistUnsubUrl="http://music.163.com/weapi/artist/unsub";
    //收藏歌手列表API
    public static final String ArtistSublistUrl="http://music.163.com/weapi/artist/sublist";
    //发送私信
    public static final String sendMsgUrl="http://music.163.com/weapi/msg/private/send";
    //新建歌单
    public static final String createPlaylistUrl="http://music.163.com/weapi/playlist/create";
    //收藏/取消收藏歌单
    public static final String PlaylistSubUrl="http://music.163.com/weapi/playlist/{action}";
    //向歌单添加或者删除操作
    public static final String PlaylistTracksUrl="http://music.163.com/weapi/playlist/manipulate/tracks";
    //给评论点赞或者取消点赞
    public static final String CommentLikeUrl="http://music.163.com/weapi/v1/comment/{action}";
    //添加或者移除音乐到喜欢的音乐歌单中
    public static final String likeSongUrl="http://music.163.com/weapi/radio/like?alg={alg}&trackId={trackId}&like={like}&time={time}";
    //将音乐从私人 FM 中移除至垃圾桶
    public static final String fmTrashUrl="http://music.163.com/weapi/radio/trash/add?alg={alg}&songId={songId}&time={time}";


}
