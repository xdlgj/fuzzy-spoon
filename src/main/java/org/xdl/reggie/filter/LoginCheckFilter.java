package org.xdl.reggie.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.xdl.reggie.common.BaseContext;
import org.xdl.reggie.common.R;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    // 路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        log.info("拦截到请求：{}", requestURI);
        // 定义直接放行的uri(白名单)
        String[] white_uris = new String[]{
                "/employee/login",
                "/employee/login",
                "/backend/**",
                "/front/**",
                "/swagger-resources/**",
                "/webjars/**",
                "/v2/**",
                "/swagger-ui.html/**",
                "/employee/**"

        };
        // 1、在白名单中的uri执行放行
        if (check(white_uris, requestURI)) {
            log.info("本次请求{}不需要处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        //2、判断是否已经登录
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已经登录，用户ID为：{}", request.getSession().getAttribute("employee"));
            // 将用户ID存放到 ThreadLocal 中
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));
            filterChain.doFilter(request, response);
            return;
        }
        log.info("用户未登录");
        //3、拦截请求直接返回，通过输出流方式
        response.getWriter().write(JSON.toJSONString(R.error("NOT_LOGIN")));
        return;
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param uris
     * @param requestURI
     * @return
     */
    public boolean check(String[] uris, String requestURI) {
        for (String uri : uris) {
            if (PATH_MATCHER.match(uri, requestURI)) {
                return true;
            }
        }
        return false;
    }
}
