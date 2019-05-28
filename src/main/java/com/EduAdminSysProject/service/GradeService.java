package com.EduAdminSysProject.service;

import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.service.model.GradeModel;

import java.util.List;

public interface GradeService {
    void selectCourse(GradeModel gradeModel) throws BusinessException;

    List<GradeModel> getSelectedCourse(String sid, String gid) throws BusinessException;
}
