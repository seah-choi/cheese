package org.fullstack4.cheese.config;

import org.fullstack4.cheese.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFilterConfig {
    @Bean
    public FilterRegistrationBean<LoginFilter> LoginFilter() {
        FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginFilter());
        registrationBean.addUrlPatterns("/member/login"); // 필터를 적용할 URL 패턴 지정
        registrationBean.addUrlPatterns("/member/join");
        registrationBean.addUrlPatterns("/member/findpwd");
        registrationBean.addUrlPatterns("/member/changepwd");
        registrationBean.setOrder(1); // 필터의 순서 지정
        return registrationBean;
    }

}