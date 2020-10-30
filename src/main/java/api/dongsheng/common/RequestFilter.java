package api.dongsheng.common;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器
 * post请求验证签名，因为post流输入，所以在拦截器验证签名获取body参数之后，controller获取不到，
 * 增加过滤器
 */
@WebFilter(urlPatterns = "/*",filterName = "RequestFilter")
public class RequestFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        RequestWrapper requestWrapper = null;
        if(servletRequest instanceof HttpServletRequest) {
            requestWrapper =new RequestWrapper((HttpServletRequest) servletRequest);
        }
        if(requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }

    }
}
