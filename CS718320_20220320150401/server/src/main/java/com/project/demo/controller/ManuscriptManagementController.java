package com.project.demo.controller;

import com.project.demo.entity.ManuscriptManagement;
import com.project.demo.service.ManuscriptManagementService;
import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *稿件管理：(ManuscriptManagement)表控制层
 *
 */
@RestController
@RequestMapping("/manuscript_management")
public class ManuscriptManagementController extends BaseController<ManuscriptManagement,ManuscriptManagementService> {

    /**
     *稿件管理对象
     */
    @Autowired
    public ManuscriptManagementController(ManuscriptManagementService service) {
        setService(service);
    }

    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        this.addMap(paramMap);
        return success(1);
    }

}
