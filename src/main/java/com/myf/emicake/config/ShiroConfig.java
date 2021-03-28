package com.myf.emicake.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.myf.emicake.common.Constants;
import com.myf.emicake.config.shiro.authenticator.MyModularRealmAuthenticator;
import com.myf.emicake.config.shiro.realm.UserPasswordRealm;
import com.myf.emicake.config.shiro.realm.UserPhoneRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName com.myf.emicake.config ShiroConfig
 * @Description Shiro配置类
 * @Author Afengis
 * @Date 2021/2/7 10:38
 * @Version V1.0
 **/
@Configuration
public class ShiroConfig {




    /**
     * url过滤器
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/druid/**", "anon");
        chainDefinition.addPathDefinition("/api/**", "anon");
        chainDefinition.addPathDefinition("/","anon");
        // all other paths require a logged in user
        chainDefinition.addPathDefinition("/static/**","anon");
        //chainDefinition.addPathDefinition("/upload/prod/img/banner/**","anon");
        chainDefinition.addPathDefinition("/sms","anon");
        chainDefinition.addPathDefinition("//member/**.html","anon");
        chainDefinition.addPathDefinition("/member/**","anon");
        chainDefinition.addPathDefinition("/product/**.html", "anon");
        chainDefinition.addPathDefinition("/prod/**","anon");
        chainDefinition.addPathDefinition("/shop/**.html","anon");
        chainDefinition.addPathDefinition("/cart/**.html","anon");
        chainDefinition.addPathDefinition("/cart/**","anon");
        chainDefinition.addPathDefinition("/order/**.html","anon");
        chainDefinition.addPathDefinition("/order/**","anon");
        chainDefinition.addPathDefinition("/shop/**","anon");
        chainDefinition.addPathDefinition("/memberAddress/**","anon");
        chainDefinition.addPathDefinition("/upload/**","anon");

        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }
    /**
     * 密码匹配器
     * @return
     */
    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);  //散列算法，这里使用更安全的sha256算法
        credentialsMatcher.setStoredCredentialsHexEncoded(false);  // 数据库存储的密码字段使用HEX还是BASE64方式加密
        credentialsMatcher.setHashIterations(Constants.HASH_ITERATIONS);  // 散列迭代次数
        return credentialsMatcher;
    }


    /**
     * 手机验证码登录验证
     * @return
     */
    @Bean
    public UserPhoneRealm userPhoneRealm() {
        UserPhoneRealm userPhoneRealm = new UserPhoneRealm();
        return userPhoneRealm;
    }

    /**
     * 用户名密码登录验证
     * @param credentialsMatcher
     * @return
     */
    @Bean
    public UserPasswordRealm userPasswordRealm(CredentialsMatcher credentialsMatcher) {
        UserPasswordRealm userPasswordRealm = new UserPasswordRealm();
        userPasswordRealm.setCredentialsMatcher(credentialsMatcher); //配置使用哈希密码匹配
        return userPasswordRealm;
    }


    /**
     * 系统自带的Realm管理，主要针对多realm
     */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(UserPasswordRealm userPasswordRealm,
                                                               UserPhoneRealm userPhoneRealm){
        ModularRealmAuthenticator myModularRealmAuthenticator = new MyModularRealmAuthenticator();
        //设置认证策略
        myModularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        List<Realm> realms = new ArrayList<>();
        realms.add(userPasswordRealm);
        realms.add(userPhoneRealm);
        myModularRealmAuthenticator.setRealms(realms);
        return myModularRealmAuthenticator;
    }


    /**
     * 安全管理器
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(UserPhoneRealm userPhoneRealm,
                                                     UserPasswordRealm userPasswordRealm,
                                                     ModularRealmAuthenticator modularRealmAuthenticator) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realms
        List<Realm> realms = new ArrayList<>();
        realms.add(userPhoneRealm);
        realms.add(userPasswordRealm);
        securityManager.setRealms(realms);
        // 认证器
        securityManager.setAuthenticator(modularRealmAuthenticator);
        return securityManager;
    }

    /**
     * 方言处理,用于thymeleaf
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
