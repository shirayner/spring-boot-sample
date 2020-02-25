package com.ray.study.smaple.security.config;

import com.ray.study.smaple.security.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * WebSecurityConfiguration
 *
 * @author ray
 * @date 2020/2/25
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 基于数据库的认证
        auth.userDetailsService(detailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()                            // 定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers( "/login","/registry","/loginProcessing","/user/add").permitAll()   // 设置所有人都可以访问登录页面
                .anyRequest().authenticated()               // 任何请求,登录后可以访问
            .and().formLogin()                              // 定义当需要用户登录时候，转到的登录页面。
                .loginPage("/login")                        // 设置登录页面
                .loginProcessingUrl("/loginProcessing")          // 自定义的登录接口
                .defaultSuccessUrl("/home")                 // 登录成功之后，默认跳转的页面
                .failureUrl("/login?error").permitAll()     // 登录失败后，跳转到登录页面
            .and().logout().logoutSuccessUrl("/login").permitAll()  // 登出之后，跳转到登录页面
            .and().rememberMe().tokenValiditySeconds(1209600)   // 记住我
            .and().csrf().disable();                        // 关闭csrf防护

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }



    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService detailsService() {
        return new UserDetailsServiceImpl();
    }
}
