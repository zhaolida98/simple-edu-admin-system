package com.EduAdminSysProject.service;

import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.service.model.CourseModel;

public interface CourseService {
    void addCourse(CourseModel courseModel) throws BusinessException;
}
