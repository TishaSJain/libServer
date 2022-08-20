package com.unilib.libserver.repo;

import com.unilib.libserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface UsersRepo extends JpaRepository<User,Integer> {
    User findByEmail(String email);


    @Query(value="select uid,name,email,user_type from users u where u.uid !=:userId", nativeQuery = true)
    List<UserData> findAllUsers(@Param("userId") int userId);

    @Modifying
    @Query(value = "UPDATE users SET user_type=:userType WHERE uid=:userId",nativeQuery = true)
    void deactivateUser(@Param("userId") int userId,@Param("userType") int userType);

}
