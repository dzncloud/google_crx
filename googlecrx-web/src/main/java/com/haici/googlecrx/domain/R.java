/**
 * 
 */
package com.haici.googlecrx.domain;

/**
 * @author AnyAi
 *
 */
public class R<T> {
	
    private int code = 0;
    private String msg;
    private T data;

    public R() {
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
    public static R<Void> sucess() {
    	return new R<Void>(200,"请求成功！");
    }
}
