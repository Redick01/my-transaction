package org.transaction.springtx.exception;

import lombok.Getter;

/**
 * @author liupenghui
 * @date 2021/12/1 4:00 下午
 */
public class TransactionException extends RuntimeException {

    @Getter
    private String code;
    @Getter
    private String msg;

    public TransactionException() {
    }

    public TransactionException(String code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public TransactionException(Throwable cause,String code,String msg){
        super(cause);
        this.code=code;
        this.msg=msg;
    }

    public TransactionException(String message) {
        super(message);
        this.msg=message;
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
        this.msg=cause.getMessage();
    }

    public TransactionException(Throwable cause) {
        super(cause);
        this.msg=cause.getMessage();
    }
}
