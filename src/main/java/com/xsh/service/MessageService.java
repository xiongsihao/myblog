package com.xsh.service;

import com.xsh.pojo.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author : xsh
 * @create : 2020-03-18 - 15:31
 * @describe:
 */
public interface MessageService {

    Message saveMessage(Message message);

    Page<Message> listMessage(Pageable pageable);

    void findParentMessage(Message message);
}
