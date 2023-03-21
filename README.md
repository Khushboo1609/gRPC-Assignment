# gRPC-Assignment

Create GRPC Microservice with Spring Boot
I have recived this a test from Bluebik. Happy to learn about gRPC explored a lot of thing while completing the Assignment

What is gRPC?
The framework is based on a client-server model of remote procedure calls. A client application can directly call methods on a server application as if it was a local object.

This article will use following steps to create a typical client-server Spring boot application using gRPC:

Define a services in a .proto file
Generate server and client code using the protocol buffer compiler
Use Spring initializer to create the Spring boot Application. Add photo buffer specific dependencies.
Create the client application, making RPC calls using generated stubs\
Add APISand try to access them using Postman.
Majorly, I have defined 5 APIs. Intutive with there name we can use them to save, find, delete or update our Users Data.

Added gRPC specific dependencies in pom.xml

<!-- For both -->
  <dependency>
   <groupId>net.devh</groupId>
   <artifactId>grpc-spring-boot-starter</artifactId>
   <version>2.5.1.RELEASE</version>
   <exclusions>
    <exclusion>
     <groupId>io.grpc</groupId>
     <artifactId>grpc-netty-shaded</artifactId>
    </exclusion>
   </exclusions>
  </dependency>
Also add some plugins

<build>
  <extensions>
   <extension>
    <groupId>kr.motd.maven</groupId>
    <artifactId>os-maven-plugin</artifactId>
    <version>1.6.1</version>
   </extension>
  </extensions>
  <plugins>
   <plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
   </plugin>
   <plugin>
    <groupId>org.xolstice.maven.plugins</groupId>
    <artifactId>protobuf-maven-plugin</artifactId>
    <version>0.6.1</version>
    <configuration>
     <protocArtifact>
      com.google.protobuf:protoc:3.3.0:exe:${os.detected.classifier}
     </protocArtifact>
     <pluginId>grpc-java</pluginId>
     <pluginArtifact>
      io.grpc:protoc-gen-grpc-java:1.4.0:exe:${os.detected.classifier}
     </pluginArtifact>
    </configuration>
    <executions>
     <execution>
      <goals>
       <goal>compile</goal>
       <goal>compile-custom</goal>
      </goals>
     </execution>
    </executions>
   </plugin>
  </plugins>
 </build>
Using SQL Server as Database to store user specific data. Specify the port on which you need to run your application. Add the data source, username and password for accessing the server.

server.port=8081
grpc.server.port=9090
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url= jdbc:mysql://localhost:3306/users
spring.datasource.username=root
spring.datasource.password=root
Defined a UserService.proto file, adding few basic configuration details:

syntax = "proto3";
option java_multiple_files = true;
package com.example.grpc.server.grpcserver;
Also Define message structure and Service Contracts. For all the required operation like : save, saveAll, findByPattern, delete and delete in Bulk.

We we complie the above code, it will generate corresponding java files.

In the backend, currently three type of communcations we are using :

Unary -> where the client sends a request to the server using the stub and waits for a response to come back, just like a normal function call. In case of save and find single object

A server-side streaming RPC where the client sends a request to the server and gets a stream to read a sequence of messages back. The client reads from the returned stream until there are no more messages. getUserStartingWith

A client-side streaming RPC where the client writes a sequence of messages and sends them to the server, again using a provided stream. Once the client has finished writing the messages, it waits for the server to read them all and return its response. saveUserDetailsInBulk.

The code design goes like :

As our gRPC service is talking to DB i.e. in hotelService folder.

We have defined Table Entities inside entity folder.
We have Data Access Object defined in dao folders. There are the functions we are using for communication with out db.
Then gRPC services in service folder where we have extended class created by protobuffers and overridden functions.
For our client in apiService folder.

Inside the Controller class defined all the APIs User is using to communicate with our complete service.
Calling the function from gRPC service using stubs.
To test the Service:

Updated Credentials for SQL Server
Download postman to test the APIs. Can be accessed from here.
https://api.postman.com/collections/24196708-d0af6518-8199-4074-99a7-f8d86a00ed20?access_key=PMAT-01GW1T9KGE6G204ADRF826WXGS
