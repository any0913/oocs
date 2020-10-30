package api.dongsheng.common;

import java.io.Serializable;

/**
 * @Author rx
 * @Description 返回对象实体
 * @Date 19:34 2019/7/8
 * @Param
 * @return
 **/
public class RetResult<T> implements Serializable {

    public int code;

    private String msg;

    private T data;

    public RetResult() {
        this(RetCode.SUCCESS.code, RetCode.SUCCESS.getMsg());
    }

    public RetResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RetResult(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public RetResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }


    public RetResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public RetResult<T> setData(T data) {
        this.data = data;
        return this;
    }
}
