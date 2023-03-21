package com.example.grpc.server.grpcserver.hotelService.dao;

import com.example.grpc.server.grpcserver.hotelService.entity.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Id> {
    public User findByUserId(int userId);
    public User save(User user);
    public List<User> findByFirstNameStartingWith(String firstName);

    public void deleteByUserId(int userId);
}
