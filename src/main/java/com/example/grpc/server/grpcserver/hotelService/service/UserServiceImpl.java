package com.example.grpc.server.grpcserver.hotelService.service;

import com.example.grpc.server.grpcserver.*;
import com.example.grpc.server.grpcserver.hotelService.dao.UserDao;
import com.example.grpc.server.grpcserver.hotelService.entity.User;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserDao userDao;

    @Override
    public void getUserDetails(
            UserRequest request, StreamObserver<UserResponse> responseObserver) {
        int userId= request.getUserId();
        User user = userDao.findByUserId(userId);

        UserResponse resultResponse = UserResponse.newBuilder()
                .setUserId(userId)
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setAge(user.getAge())
                .setIdentity(user.getIdentity())
                .setEmailId(user.getEmailId())
                .build();

        responseObserver.onNext(resultResponse);
        responseObserver.onCompleted();
    }
    @Override
    public void saveUserDetails(UserResponse userResponse, StreamObserver<UserResponse> responseObserver){
        User user = new User();
        user.setUserId(userResponse.getUserId());
        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());
        user.setEmailId(userResponse.getEmailId());
        user.setAge(userResponse.getAge());
        user.setIdentity(userResponse.getIdentity());
        userDao.save(user);
        responseObserver.onNext(userResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteUserDetails(UserRequest deleteUserRequest, StreamObserver<Empty> userResponseStreamObserver){
        userDao.deleteByUserId(deleteUserRequest.getUserId());
        userResponseStreamObserver.onNext(Empty.newBuilder().build());
        userResponseStreamObserver.onCompleted();
    }

    @Override
    public StreamObserver<UserResponse> saveUserDetailsInBulk(StreamObserver<Empty> emptyStreamObserver){


            return new StreamObserver<UserResponse>() {

                private List<User> userList = new ArrayList<>();

                @Override
                public void onNext(UserResponse userResponse) {
                    User user = new User();
                    user.setUserId(userResponse.getUserId());
                    user.setFirstName(userResponse.getFirstName());
                    user.setLastName(userResponse.getLastName());
                    user.setEmailId(userResponse.getEmailId());
                    user.setAge(userResponse.getAge());
                    user.setIdentity(userResponse.getIdentity());
                    userDao.save(user);
                }

                @Override
                public void onError(Throwable t) {
                    t.printStackTrace();
                }

                @Override
                public void onCompleted() {
                     Empty value = Empty.newBuilder()
                            .build();
                    emptyStreamObserver.onNext(value);
                    emptyStreamObserver.onCompleted();
                }
            };
    }
    @Override
    public void getUserStartingWith(UserName userName, StreamObserver<UserResponse> userResponseStreamObserver){
        List<User> userList= userDao.findByFirstNameStartingWith(userName.getUserName());

        for(int i =0;i<userList.size();i++){
            User user= userList.get(i);
            UserResponse userResponse= UserResponse.newBuilder()
                    .setUserId(user.getUserId())
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setAge(user.getAge())
                    .setIdentity(user.getIdentity())
                    .setEmailId(user.getEmailId())
                    .build();
            userResponseStreamObserver.onNext(userResponse);
        }
        userResponseStreamObserver.onCompleted();
    }
}