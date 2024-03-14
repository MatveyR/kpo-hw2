FROM openjdk:17
ADD /build/libs/hw2.jar restaraunt-api.jar
CMD ["java", "-jar", "restaraunt-api.jar"]