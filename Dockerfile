FROM openjdk:17-jdk-alpine

EXPOSE 5500

ADD build/libs/netology_jclo36_course_work-0.0.1-SNAPSHOT.jar transfermoney.jar

ENTRYPOINT ["java","-jar","/transfermoney.jar"]