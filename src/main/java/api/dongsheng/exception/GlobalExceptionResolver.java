package api.dongsheng.exception;

import api.dongsheng.common.RetCode;
import api.dongsheng.common.RetResult;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Ttan
 * @Date: 2018/4/11
 * @Time: 17:35
 */
@Slf4j
@RestControllerAdvice
// @ControllerAdvice
public class GlobalExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @ExceptionHandler(value = Exception.class)
    public ModelAndView exceptionHandler(Exception ex, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");

            if (ex instanceof BaseException) {
                json.put("code", ((BaseException) ex).getStatus());
                json.put("msg", ex.getMessage());
            } else {
                json.put("code", 1);
                json.put("msg", "系统异常!");
            }

            logger.error(ex.getMessage(), ex);
            response.getWriter().print(json.toString());
        } catch (Exception e) {
            logger.error("ExceptionHandler 异常处理失败", e);
        }
        return new ModelAndView();
    }


    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RetResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return  new RetResult(RetCode.PARAM_ERROR.getCode(),e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
