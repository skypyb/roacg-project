package com.roacg.service.system.config.security.oauth;

import com.roacg.core.base.log.RoCommonLoggerEnum;
import com.roacg.core.base.log.RoLoggerFactory;
import com.roacg.service.system.security.model.AuthenticationUser;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class TokenConfig {

    private static Logger preloadLog = RoLoggerFactory
            .getCommonLogger(RoCommonLoggerEnum.AT_STARTUP_PRELOAD, "security-oauth2");

    @Autowired
    private DataSource dataSource;


    /**
     * 持久化令牌是委托一个 TokenStore 接口来实现
     * TokenStore 这个接口有一个默认的实现，就是 InMemoryTokenStore ，所有的令牌是被保存在了内存中。
     *
     * @return JdbcTokenStore
     */
    @Bean
    public TokenStore tokenStore() {

        // Redis管理access_token和refresh_token
//        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);

        // JBDC管理access_token和refresh_token

        // 基于 JDBC 实现，令牌保存到数据库
        JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
        return jdbcTokenStore;
    }

    /**
     * 令牌增强器，可以对默认的令牌进行修改
     * 例如 实现一个 jwt (真要用jwt，还是用 SpringSecurity 自带的吧)
     *
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {

            AuthenticationUser user = (AuthenticationUser) authentication.getUserAuthentication().getPrincipal();

            final Map<String, Object> additionalInfo = Map.of(
                    "rouser", Map.of(
                            "uid", user.getUserId(),
                            "userName", user.getUsername(),
                            "userAuthorities", user.getAuthorities()
                    )
            );
            //加入自定义信息
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

}
