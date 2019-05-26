package com.EduAdminSysProject.error;

/**
 * The enum Em business error.
 */
public enum EmBusinessError implements CommonError{
  /**
   * Parameter validation error em business error.
   */
//general error code 10001
  PARAMETER_VALIDATION_ERROR(10001,"invalid parameter"),
  /**
   * Unknow error em business error.
   */
  UNKNOW_ERROR(10002,"unknown error"),

  /**
   * User not exist em business error.
   */
//20000开头为用户信息相关的错误定义
  USER_NOT_EXIST(20001,"user do not exist"),
  /**
   * User login fail em business error.
   */
  USER_LOGIN_FAIL(20002,"sid or password wrong"),

  USER_OLDPASS_WRONG(20003, "old password is wrong"),

  USER_INPUT_EMPTY(20004, "your input is empty"),

  USER_PRIVILEGE_ERROR(20005, "user do not have enough privilege"),

;
  private EmBusinessError(int errCode, String errMsg){
    this.errCode = errCode;
    this.errMsg = errMsg;
  }

  private int errCode;
  private String errMsg;

  @Override
  public int getErrorCode() {
    return this.errCode;
  }

  @Override
  public String getErrMsg() {
    return this.errMsg;
  }

  @Override
  public CommonError setErrMsg(String errMsg) {
    StringBuffer stringBuilder = new StringBuffer(errMsg);
    this.errMsg = stringBuilder.toString();
    return this;
  }
}
