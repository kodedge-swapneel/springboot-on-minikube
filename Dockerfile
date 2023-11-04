FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
ADD build/libs/springboot-on-minikube.jar springboot-on-minikube.jar
ENTRYPOINT ["java","-jar","/springboot-on-minikube.jar"]