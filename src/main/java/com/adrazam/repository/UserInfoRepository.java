package com.adrazam.repository;

import com.adrazam.model.User;
import com.adrazam.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo findByUser(User user);
}
