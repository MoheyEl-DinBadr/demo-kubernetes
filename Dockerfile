FROM openjdk:17
LABEL authors="moheyeldinbadr"

EXPOSE 8080

ADD build/libs/demo-kubernetes-0.0.1-SNAPSHOT.jar demo-kubernetes-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/demo-kubernetes-0.0.1-SNAPSHOT.jar"]
