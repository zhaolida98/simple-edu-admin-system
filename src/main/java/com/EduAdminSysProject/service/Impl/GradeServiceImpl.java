package com.EduAdminSysProject.service.Impl;

import com.EduAdminSysProject.dao.GradeDOMapper;
import com.EduAdminSysProject.dataobject.GradeDO;
import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.error.EmBusinessError;
import com.EduAdminSysProject.service.GradeService;
import com.EduAdminSysProject.service.model.GradeModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {

    @Resource
    GradeDOMapper gradeDOMapper;

    @Override
    public void selectCourse(GradeModel gradeModel) throws BusinessException {
        if (gradeModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        GradeDO gradeDO = converFromModel(gradeModel);
        gradeDOMapper.insertSelective(gradeDO);
        return;
    }

    @Override
    public List<GradeModel> getSelectedCourse(String sid) throws BusinessException {
        if (sid == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        List<GradeDO> gradeDOList = gradeDOMapper.selectAllChosenCourse(sid);
        List<GradeModel> gradeModelList = gradeDOList.stream().map(gradeDO -> {
            GradeModel gradeModel = converToModel(gradeDO);
            return gradeModel;
        }).collect(Collectors.toList());
        return gradeModelList;
    }

    private GradeDO converFromModel(GradeModel gradeModel) throws BusinessException {
        if (gradeModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        GradeDO gradeDO = new GradeDO();
        BeanUtils.copyProperties(gradeModel, gradeDO);
        return gradeDO;
    }

    private GradeModel converToModel(GradeDO gradeDO) {
        if (gradeDO == null) {
            return null;
        }
        GradeModel gradeModel = new GradeModel();
        BeanUtils.copyProperties(gradeDO, gradeModel);
        return gradeModel;
    }
}
