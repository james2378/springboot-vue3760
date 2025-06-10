package com.project.demo.controller;

import com.project.demo.entity.ExpertUser;
import com.project.demo.service.ExpertUserService;
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
 *专家用户：(ExpertUser)表控制层
 *
 */
@RestController
@RequestMapping("/expert_user")
public class ExpertUserController extends BaseController<ExpertUser,ExpertUserService> {

    /**
     *专家用户对象
     */
    @Autowired
    public ExpertUserController(ExpertUserService service) {
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
