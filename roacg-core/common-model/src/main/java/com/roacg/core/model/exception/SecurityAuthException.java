package com.roacg.core.model.exception;


import com.roacg.core.model.enums.RoApiStatusEnum;

/**
 * 401认证异常
 */
public class SecurityAuthException extends RoApiException {

    public SecurityAuthException() {
        super(RoApiStatusEnum.UNAUTHORIZED, "Unauthorized");
    }

    public SecurityAuthException(String message) {
        super(RoApiStatusEnum.UNAUTHORIZED, message);
    }
}
