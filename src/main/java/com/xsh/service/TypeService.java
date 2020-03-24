package com.xsh.service;

import com.xsh.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author : xsh
 * @create : 2020-02-08 - 1:33
 * @describe:
 */
public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);

    Type updateType(Long id,Type type);

    void deleteType(Long id);
}
