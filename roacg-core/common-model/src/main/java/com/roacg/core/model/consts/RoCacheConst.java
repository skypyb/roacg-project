package com.roacg.core.model.consts;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public final class RoCacheConst {

    /**
     * 默认的缓存key前缀
     */
    public static final String DEFAULT_KEY_PREFIX = "RO@DEF-KEY:";


    /**
     * 默认的缓存TTL时间, 三十分钟
     */
    public static final Duration DEFAULT_TTL = Duration.of(30, ChronoUnit.MINUTES);


    /**
     * 系统的 Token 缓存信息
     * String
     */
    public static final String TOKEN_CACHE_KEY = "RO@ACCESS_TOKEN_CACHE:";

    /**
     * 用户对应的令牌
     * SET
     */
    public static final String USER_TO_TOKEN = "RO@USER_TO_TOKEN:";


    public static String getTokenCacheKey(String accessToken) {
        return TOKEN_CACHE_KEY.concat(accessToken);
    }

    public static String getUserToTokenKey(Long uid, String clientId) {
        return new StringBuilder(USER_TO_TOKEN).append(clientId).append(':').append(uid).toString();
    }

    /**
     * 默认生成key的策略
     *
     * @param target 类信息
     * @param method 方法信息
     * @param params 参数信息
     * @return
     */
    public static final String defaultKeyGenerate(Object target, Method method, Object... params) {
        StringBuilder key = new StringBuilder();
        key.append(target.getClass().getName())
                .append(':')
                .append(method.getName());

        String paramsStringValue = Arrays.stream(params).map(param -> {
            //null参数设置
            if (Objects.isNull(param)) {
                param = "_NULL";
            }
            return param.toString();
        }).map((param) -> {
            if (param.length() > 10) {
                //do ...
            }
            return param;
        }).collect(Collectors.joining(",", "[", "]"));

        return key.append(paramsStringValue).toString();
    }
}
