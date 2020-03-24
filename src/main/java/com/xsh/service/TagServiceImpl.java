package com.xsh.service;

import com.xsh.dao.TagRepository;
import com.xsh.handler.NotFoundException;
import com.xsh.pojo.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : xsh
 * @create : 2020-02-12 - 0:06
 * @describe:
 */
@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) { //前端页面传递字符串参数1.2.3...，需转成数组类型作为查询条件
        List<Long> tagIds = convertToList(ids);
        return tagRepository.findAll(tagIds);
    }

    private List<Long> convertToList(String ids) { //将字符串转换为一个数组list
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t=tagRepository.findOne(id);
        if(t==null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,t); //将tag里面的值赋值给t
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(id);
    }
}
