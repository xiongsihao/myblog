package com.xsh.web;

import com.xsh.pojo.Tag;
import com.xsh.service.BlogService;
import com.xsh.service.TagService;
import com.xsh.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author : xsh
 * @create : 2020-02-15 - 1:35
 * @describe:
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id, Model model) {
        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1) {
           id = tags.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setPublished(true);
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id,pageable,blogQuery));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
