package com.EduAdminSysProject.response;

/**
 * The type Common return type.
 */
public class CommonReturnType {
  //return status success or fail
  private String status;
  //if succes，data is what we need
  //if fail, data is error code
  private Object data;

  /**
   * Create common return type.
   *
   * @param result the result
   *
   * @return the common return type
   */
  public static CommonReturnType create(Object result){
    return CommonReturnType.create(result,"success");
  }

  /**
   * Create common return type.
   *
   * @param result the result
   * @param status the status
   *
   * @return the common return type
   */
  public static CommonReturnType create(Object result, String status){
    CommonReturnType type = new CommonReturnType();
    type.setStatus(status);
    type.setData(result);
    return type;
  }

  /**
   * Gets status.
   *
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets status.
   *
   * @param status the status
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Gets data.
   *
   * @return the data
   */
  public Object getData() {
    return data;
  }

  /**
   * Sets data.
   *
   * @param data the data
   */
  public void setData(Object data) {
    this.data = data;
  }
}
