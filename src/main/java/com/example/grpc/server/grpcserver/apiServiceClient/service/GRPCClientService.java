package com.example.grpc.server.grpcserver.apiServiceClient.service;

import com.example.grpc.server.grpcserver.*;
import com.example.grpc.server.grpcserver.hotelService.entity.User;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class GRPCClientService {

    public User saveUserDetails(User user) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        UserServiceGrpc.UserServiceBlockingStub stub
                = UserServiceGrpc.newBlockingStub(channel);
        stub.saveUserDetails( UserResponse.newBuilder()
                .setUserId(user.getUserId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setAge(user.getAge())
                .setIdentity(user.getIdentity())
                .setEmailId(user.getEmailId())
                .build());
        channel.shutdown();
        return user;
    }

    public User getUserDetails(int userId){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        UserServiceGrpc.UserServiceBlockingStub stub
                = UserServiceGrpc.newBlockingStub(channel);
        UserResponse userResponse = stub.getUserDetails(UserRequest.newBuilder().setUserId(userId).build());
        User user = new User();
        user.setUserId(userResponse.getUserId());
        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());
        user.setEmailId(userResponse.getEmailId());
        user.setAge(userResponse.getAge());
        user.setIdentity(userResponse.getIdentity());
        channel.shutdown();
        return user;
    }
    public void deleteUserDetails(int userId){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        UserServiceGrpc.UserServiceBlockingStub stub
                = UserServiceGrpc.newBlockingStub(channel);
        stub.deleteUserDetails(UserRequest.newBuilder().setUserId(userId).build());
        channel.shutdown();
    }


    public void saveUserDetailsInBulk(List<User>userList) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        UserServiceGrpc.UserServiceStub stub
                = UserServiceGrpc.newStub(channel);
        StreamObserver<Empty> empty  = new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty empty) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        };
        StreamObserver<UserResponse> inputStreamObserver = stub.saveUserDetailsInBulk(empty);
        for (int i = 0; i < userList.size(); i++) {
            // build the request object
            User user= userList.get(i);
            UserResponse input = UserResponse.newBuilder()
                    .setUserId(user.getUserId())
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setAge(user.getAge())
                    .setIdentity(user.getIdentity())
                    .setEmailId(user.getEmailId())
                    .build();
            // pass the request object via input stream observer
            inputStreamObserver.onNext(input);
        }

        inputStreamObserver.onCompleted();
    }
    public List<User>getUserStartingWith(String pattern){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        UserServiceGrpc.UserServiceBlockingStub stub
                = UserServiceGrpc.newBlockingStub(channel);
        UserName userName= UserName.newBuilder().setUserName(pattern).build();
        Iterator<UserResponse> userResponseIterator;
        List<User> userList= new ArrayList<>();

        userResponseIterator= stub.getUserStartingWith(userName);
        for(int i=0; userResponseIterator.hasNext();i++){
            UserResponse userResponse= userResponseIterator.next();
            User user  = new User();
            user.setUserId(userResponse.getUserId());
            user.setFirstName(userResponse.getFirstName());
            user.setLastName(userResponse.getLastName());
            user.setEmailId(userResponse.getEmailId());
            user.setAge(userResponse.getAge());
            user.setIdentity(userResponse.getIdentity());
            userList.add(user);

        }
        channel.shutdown();
        return userList;

    }
}