package cn.com.pism.hyacinth.security.spring.filter;

import cn.com.pism.hyacinth.security.base.config.HcSecurityProperties;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author PerccyKing
 * @since 2023/4/10 0:32
 */
@Component
public class HcSecuritySpringTokenFilter extends OncePerRequestFilter {

    @Resource
    private HcSecurityProperties hcSecurityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从header、url参数 中获取token信息

        filterChain.doFilter(request, response);
    }
}
