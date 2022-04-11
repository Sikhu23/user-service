FROM openjdk:17
ADD target/dockerUserService.jar dockerUserService.jar
EXPOSE 3005
ENTRYPOINT ["java","-jar","dockerUserService.jar"]