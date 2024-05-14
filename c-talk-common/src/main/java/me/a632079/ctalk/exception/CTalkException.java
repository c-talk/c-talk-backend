package me.a632079.ctalk.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className: CTalkException
 * @description: CTalkException - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CTalkException extends RuntimeException {

    private IErrorCode errorCode;

    public CTalkException(IErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
