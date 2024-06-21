package com.duskio.features.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.duskio.common.constant.Constant.ADMIN_PATH;

@Controller
@RequiredArgsConstructor
@RequestMapping(ADMIN_PATH + "categories")
@Tag(name = "category-admin", description = "Category admin API")
public class CategoryAdminController {

    private final CategoryDao categoryDao;

    @GetMapping("/{id}")
    @Operation(summary = "Find category by id")
    public String findById(@PathVariable int id, Model model) {
        model.addAttribute("category", categoryDao.findById(id));
        return "category/id";
    }
}
