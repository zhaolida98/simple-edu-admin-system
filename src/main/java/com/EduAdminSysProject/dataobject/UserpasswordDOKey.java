package com.EduAdminSysProject.dataobject;

public class UserpasswordDOKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_password.sId
     *
     * @mbg.generated Tue May 28 20:46:39 CST 2019
     */
    private String sid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_password.gId
     *
     * @mbg.generated Tue May 28 20:46:39 CST 2019
     */
    private String gid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_password.sId
     *
     * @return the value of user_password.sId
     *
     * @mbg.generated Tue May 28 20:46:39 CST 2019
     */
    public String getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_password.sId
     *
     * @param sid the value for user_password.sId
     *
     * @mbg.generated Tue May 28 20:46:39 CST 2019
     */
    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_password.gId
     *
     * @return the value of user_password.gId
     *
     * @mbg.generated Tue May 28 20:46:39 CST 2019
     */
    public String getGid() {
        return gid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_password.gId
     *
     * @param gid the value for user_password.gId
     *
     * @mbg.generated Tue May 28 20:46:39 CST 2019
     */
    public void setGid(String gid) {
        this.gid = gid == null ? null : gid.trim();
    }
}