package com.bzh.data.basic;

import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bzh.common.utils.SystemUtils;
import com.bzh.data.exception.TaskException;
import com.bzh.data.film.DetailEntity;
import com.bzh.data.ticket.TicketValidateEntity;
import com.bzh.log.MyLog;
import com.google.gson.Gson;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-27<br>
 * <b>描述</b>：　　　数据相关的处理方法<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class DataStoreController {

    ///////////////////////////////////////////////////////////////////////////
    // Single Instance
    public static DataStoreController dataStoreController;


    public static DataStoreController getInstance() {
        DataStoreController tmp = dataStoreController;
        if (tmp == null) {
            synchronized (DataStoreController.class) {
                tmp = dataStoreController;
                if (tmp == null) {
                    tmp = new DataStoreController();
                    dataStoreController = tmp;
                }
            }
        }
        return tmp;
    }
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////

    // 公共
    private static final String TRANSLATIONNAME = "译名";
    private static final String NAME = "片名";
    private static final String YEARS = "年代";
    private static final String COUNTRY = "国家";
    private static final String CATEGORY = "类别";
    private static final String LANGUAGE = "语言";
    private static final String SHOWTIME = "片长";
    private static final String DIRECTOR = "导演";
    private static final String LEADINGPLAYERS = "主演";
    private static final String DESCRIPTION = "简介";
    private static final String AREA = "地区";

    // 电影
    private static final String SUBTITLE = "字幕";
    private static final String FILEFORMAT = "文件格式";
    private static final String VIDEOSIZE = "视频尺寸";
    private static final String FILESIZE = "文件大小";
    private static final String IMDB = "IMDb评分";

    // 电视剧
    private static final String EPISODENUMBER = "集数";
    private static final String PLAYTIME = "上映日期";

    // 日韩电视剧
    private static final String PLAYNAME = "剧名";
    private static final String SOURCE = "播送";
    private static final String TYPE = "类型";
    private static final String PREMIERE = "首播";
    private static final String PREMIERE_TIME = "首播日期";
    private static final String TIME = "时间";
    private static final String JIE_DANG = "接档";
    private static final String SCREENWRITER = "编剧";


    // 欧美电视剧
    private static final String TVSTATION = "电视台";
    private static final String TVSTATION_1 = "播放平台";
    private static final String PERFORMER = "演员";
    private static final String SOURCENAME = "原名";

    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // Charset
    private final String TO_CHARSET_NAME = "UTF-8";
    ///////////////////////////////////////////////////////////////////////////

    private Gson gson = new Gson();

    ///////////////////////////////////////////////////////////////////////////
    // variable
    private Func1<String, Map<String, String>> getConfigFun;
    private Func1<ResponseBody, String> transformCharset;
    private Func1<String, ArrayList<BaseInfoEntity>> listFun;
    private Func1<String, DetailEntity> filmDetailFun;
    private Func1<String, TicketValidateEntity> validateTicketFun;
    ///////////////////////////////////////////////////////////////////////////

    @NonNull
    public Observable<Map<String, String>> getConfigObservable(final Observable<ResponseBody> observable) {
        return getObservable(observable, getTransformCharset(), getConfigFun());
    }

    @NonNull
    public Observable<ArrayList<BaseInfoEntity>> getNewWorkObservable(final Observable<ResponseBody> observable) {
        return getObservable(observable, getTransformCharset(), getListFun());
    }

    @NonNull
    public Observable<DetailEntity> getNewWorkDetailObservable(final Observable<ResponseBody> observable) {
        return getObservable(observable, getTransformCharset(), getDetailFun());
    }

    @NonNull
    public Observable<TicketValidateEntity> validateTicketObservable(final Observable<ResponseBody> observable) {
        return getObservable(observable, getTransformCharset(), validateTicketFun());
    }

    @NonNull
    private Func1<String, TicketValidateEntity> validateTicketFun() {
        if (validateTicketFun == null) {
            validateTicketFun = new Func1<String, TicketValidateEntity>() {
                @Override
                public TicketValidateEntity call(String s) {
                    // TODO 在这里解析观影券是否有效
                    Boolean isTicketOk = false;
                    Date expireTime = null;
                    Object data = getData(s);
                    if (null != data) {
                        JSONObject obj = JSON.parseObject(JSON.toJSONString(data));
                        Boolean ok = obj.getBoolean("is_ticket_ok");
                        expireTime = obj.getDate("expire_time");
                        isTicketOk = (null != ok) && ok;
                    }
                    TicketValidateEntity entity = new TicketValidateEntity();
                    entity.setExpireTime(expireTime);
                    entity.setTicketOk(isTicketOk);
                    return entity;
                }
            };
        }
        return validateTicketFun;
    }

    @NonNull
    private Func1<String, Map<String, String>> getConfigFun() {
        if (getConfigFun == null) {
            getConfigFun = new Func1<String, Map<String, String>>() {
                @Override
                public Map<String, String> call(String s) {
                    // TODO 在这里解析配置数据
                    Map<String, String> configs = new HashMap<>();
                    Object data = getData(s);
                    if (null != data) {
                        JSONArray arr = JSON.parseArray(JSON.toJSONString(data));
                        if (null != arr && arr.size() > 0) {
                            for (int i = 0; i < arr.size(); i++) {
                                JSONObject obj = arr.getJSONObject(i);
                                configs.put(obj.getString("k"), obj.getString("v"));
                            }
                        }
                    }
                    return configs;
                }
            };
        }
        return getConfigFun;
    }

    @NonNull
    private Func1<String, ArrayList<BaseInfoEntity>> getListFun() {
        if (listFun == null) {
            listFun = new Func1<String, ArrayList<BaseInfoEntity>>() {
                @Override
                public ArrayList<BaseInfoEntity> call(String s) {
                    // TODO 在这里解析列表数据
                    ArrayList<BaseInfoEntity> filmEntities = new ArrayList<>();
                    Object data = getData(s);
                    if (null != data) {
                        JSONArray arr = JSON.parseArray(JSON.toJSONString(data));
                        if (null != arr && arr.size() > 0) {
                            for (int i = 0; i < arr.size(); i++) {
                                BaseInfoEntity entity = new BaseInfoEntity();
                                JSONObject obj = arr.getJSONObject(i);
                                entity.setId(obj.getString("id"));
                                entity.setTitle(obj.getString("title"));
                                entity.setImage(obj.getString("image"));
                                entity.setPublishDate(obj.getString("publish_date"));
                                entity.setPrice(obj.getString("price"));
                                filmEntities.add(entity);
                            }
                        }
                    }
                    return filmEntities;
                }
            };
        }
        return listFun;
    }

    private Object getData(String json) {
        JSONObject obj = JSON.parseObject(json);
        Boolean isOk = obj.getBoolean("is_ok");
        if (null == isOk) {
            Log.e("DYTT", "服务端返回数据格式错误");
        } else if (!isOk) {
            String error = obj.getString("error");
            Log.e("DYTT", "ERROR " + error);
        } else {
            return obj.get("data");
        }
        return null;
    }

    private boolean isFilmType(String fullName) {
        return fullName.contains("》");
    }

    @NonNull
    private Func1<String, DetailEntity> getDetailFun() {
        if (filmDetailFun == null) {
            filmDetailFun = new Func1<String, DetailEntity>() {
                @Override
                public DetailEntity call(String s) {
                    // TODO 在这里解析详情数据
                    DetailEntity entity = new DetailEntity();
                    Object data = getData(s);
                    JSONObject obj = JSON.parseObject(JSON.toJSONString(data));
                    entity.setId(obj.getString("id"));
                    entity.setTitle(obj.getString("title"));
                    entity.setImage(obj.getString("image"));
                    entity.setPublishDate(obj.getString("publish_date"));
                    entity.setPrice(obj.getString("price"));
                    entity.setContent(obj.getString("content"));
                    entity.setVideo(obj.getString("video"));
                    entity.setCover(obj.getString("cover"));
                    return entity;
                }
            };
        }
        return filmDetailFun;
    }

    private ArrayList<String> getDownloadNames(Document document) {
        ArrayList<String> strings = new ArrayList<>();
        Elements elements = document.select("div.co_content8").select("ul").select("a");

        for (Element e : elements) {
            String href = e.attr("href");
            MyLog.d("href = [" + href + "]");
            if (href.startsWith("ftp") || (href.startsWith("http") && !href.contains("www.ygdy8.net") && !href.contains("www.dytt8.net") && !href.contains("www.dygod.cn"))) {
                href = href.substring(href.indexOf("]") + 1, href.length()).replaceAll(".rmvb", "").replaceAll(".mkv", "").replaceAll(".mp4", "");
                strings.add(href);
            }
        }
        return strings;
    }


    private ArrayList<String> getDownloadUrls(Document document) {
        ArrayList<String> strings = new ArrayList<>();
        Elements elements = document.select("div.co_content8").select("ul").select("a");

        for (Element e : elements) {
            String href = e.attr("href");
            MyLog.d("href = [" + href + "]");
            if (href.startsWith("ftp") || (href.startsWith("http") && !href.contains("www.ygdy8.net"))) {
                strings.add(href);
            }
        }
        return strings;
    }

    @NonNull
    private Func1<ResponseBody, String> getTransformCharset() {
        if (transformCharset == null) {
            transformCharset = new Func1<ResponseBody, String>() {
                @Override
                public String call(ResponseBody responseBody) {
                    try {
                        return new String(responseBody.bytes(), TO_CHARSET_NAME);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new TaskException(TaskException.ERROR_HTML_PARSE);
                    }
                }
            };
        }
        return transformCharset;
    }

    @NonNull
    private String getPublishTime(String html) {
        String publishTime;
        Pattern pattern = Pattern.compile("发布时间：.*&");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            publishTime = matcher.group();
            publishTime = publishTime.substring(publishTime.indexOf("：") + 1, publishTime.length() - 1);
        } else {
            publishTime = "";
        }
        return rejectHtmlSpaceCharacters(publishTime);
    }

    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "(\\s|　|&nbsp;)*|\t|\r|\n";//定义空格回车换行符
    private static final String regEx_regular = "(\\]|:|】|：)*";

    private String rejectHtmlSpaceCharacters(String str) {
        Pattern p_html = Pattern.compile(regEx_html);
        Matcher m_html = p_html.matcher(str);
        str = m_html.replaceAll(""); // 过滤html标签
        Pattern p_space = Pattern.compile(regEx_space);
        Matcher m_space = p_space.matcher(str);
        str = m_space.replaceAll(""); // 过滤空格回车标签
        return str.trim(); // 返回文本字符串
    }

    private String rejectSpecialCharacter(String str) {
        Pattern compile = Pattern.compile(regEx_regular);
        Matcher matcher = compile.matcher(str);
        if (matcher.find()) {
            return matcher.replaceAll("");
        }
        return str;
    }

    private <Entity> Observable<Entity> getObservable(final Observable<ResponseBody> observable, final Func1<ResponseBody, String> transformCharset, final Func1<String, Entity> transformToEntity) {
        return Observable.create(new Observable.OnSubscribe<Entity>() {
            @Override
            public void call(final Subscriber<? super Entity> subscriber) {
                observable
                        .map(transformCharset)
                        .map(transformToEntity)
                        .subscribe(new Action1<Entity>() {
                            @Override
                            public void call(Entity entity) {
                                MyLog.json(gson.toJson(entity));
                                Observable.just(entity)
                                        .subscribe(subscriber);
                            }
                        }, getOnErrorProcess(subscriber));
            }
        });
    }

    @NonNull
    private Action1<Throwable> getOnErrorProcess(final Subscriber subscriber) {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (SystemUtils.getNetworkType() == SystemUtils.NETWORK_TYPE_NONE) {
                    subscriber.onError(new TaskException(TaskException.ERROR_NONE_NETWORK));
                } else {
                    subscriber.onError(new TaskException(TaskException.ERROR_UNKNOWN));
                }
            }
        };
    }

}
