package com.xsh.dao;

import com.xsh.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

/**
 * @author : xsh
 * @create : 2020-02-08 - 1:37
 * @describe:
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    Type findByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
