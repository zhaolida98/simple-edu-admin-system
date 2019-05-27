package com.EduAdminSysProject.service;

import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.service.model.GradeModel;

public interface GradeService {
    void selectCourse(GradeModel gradeModel) throws BusinessException;
}
