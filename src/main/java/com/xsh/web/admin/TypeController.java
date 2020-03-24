package com.xsh.web.admin;

import com.xsh.pojo.Type;
import com.xsh.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author : xsh
 * @create : 2020-02-08 - 1:48
 * @describe:
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")  //PageableDefault指定分页的默认参数
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type()); //将一个空Type放入model，方便前端渲染Type
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    /*@Valid注明需要校验的内容，BindingResult接收对实体类的校验结果,也可自定义错误信息;两者必须紧靠才可生效*/
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1!=null){
            /*rejectValue自定义验证错误结果,第一个参数s为校验的数据，第二个参数s1为自定义错误字符串，第三个参数s2为自定义错误信息*/
            result.rejectValue("name","nameError","不能添加重复分类");
        }
        if(result.hasErrors()){ //如果校验结果存在错误
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if(t == null){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,
                           @PathVariable Long id,RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1!=null){
            /*rejectValue自定义验证错误结果,第一个参数s为校验的数据，第二个参数s1为自定义错误字符串，第三个参数s2为自定义错误信息*/
            result.rejectValue("name","nameError","不能添加重复分类");
        }
        if(result.hasErrors()){ //如果校验结果存在错误
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if(t == null){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
