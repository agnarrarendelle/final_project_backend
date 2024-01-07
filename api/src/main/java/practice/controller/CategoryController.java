package practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import practice.dto.CategoryDto;
import practice.result.Result;
import practice.service.CategoryService;
import practice.user.CustomUserDetails;
import practice.vo.CategoryVo;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;



}
