package com.EduAdminSysProject.error;

/**
 * The interface Common error.
 */
public interface CommonError {
    /**
     * Gets error code.
     *
     * @return the error code
     */
    public int getErrorCode();

    /**
     * Gets err msg.
     *
     * @return the err msg
     */
    public String getErrMsg();

    /**
     * Sets err msg.
     *
     * @param errMsg the err msg
     *
     * @return the err msg
     */
    public CommonError setErrMsg(String errMsg);
}
