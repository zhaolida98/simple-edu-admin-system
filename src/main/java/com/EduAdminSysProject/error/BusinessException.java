package com.EduAdminSysProject.error;

/**
 * The type Business exception.
 */
//wrapper exception
public class BusinessException extends Exception implements CommonError {

  private CommonError commonError;

  /**
   * Instantiates a new Business exception.
   *
   * @param commonError the common error
   */
  public BusinessException(CommonError commonError){
    super();
    this.commonError = commonError;
  }

  /**
   * Instantiates a new Business exception.
   *
   * @param commonError the common error
   * @param errMsg      the err msg
   */
  public BusinessException(CommonError commonError, String errMsg){
    super();
    this.commonError = commonError;
    this.commonError.setErrMsg(errMsg);
  }

  @Override
  public int getErrorCode() {
    return this.commonError.getErrorCode();
  }

  @Override
  public String getErrMsg() {
    return this.commonError.getErrMsg();
  }

  @Override
  public CommonError setErrMsg(String errMsg) {
    this.commonError.setErrMsg(errMsg);
    return this;
  }
}
