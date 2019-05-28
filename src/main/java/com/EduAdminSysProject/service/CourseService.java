package com.EduAdminSysProject.service;

import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.service.model.CourseModel;

import java.util.List;

public interface CourseService {
    void addCourse(CourseModel courseModel) throws BusinessException;

    List<CourseModel> getAllCourse(String gid);

}
