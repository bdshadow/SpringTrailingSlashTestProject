package com.dbocharov;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.UrlHandlerFilter;

@Configuration
public class Config {

    /**
     * It solves the issue after deprecation of trailing slash in the endpoints paths
     * https://github.com/spring-projects/spring-framework/issues/28552
     * You can find many angry comments there.
     *
     * As written in the javadoc of the UrlHandlerFilter class: "this filter should be ordered after
     * ForwardedHeaderFilter and before the Spring Security filter chain".
     * I (dmitrii.bocharov) haven't found ForwardedHeaderFilter in the chain when calling any endpoint via rest.
     * But what i found is that the last Filter before the Security filter chain is OrderedRequestContextFilter.
     * You can find inside the OrderedRequestContextFilter, that its order = REQUEST_WRAPPER_FILTER_MAX_ORDER - 105.
     * That's why (REQUEST_WRAPPER_FILTER_MAX_ORDER - 104) here puts it right after the OrderedRequestContextFilter
     * and before the Security filter chain.
     */
    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> userInsertingMdcFilterRegistrationBean() {
        FilterRegistrationBean<OncePerRequestFilter> registrationBean = new FilterRegistrationBean<>();
        UrlHandlerFilter filter = UrlHandlerFilter
                .trailingSlashHandler("/**").wrapRequest()
                .build();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER - 104);
        return registrationBean;
    }
}