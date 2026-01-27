FROM amazoncorretto:17-alpine
COPY target/financial-accounting-system-backend-0.1.war financial-accounting-system-backend-0.1.war
ENTRYPOINT ["java","-jar","/financial-accounting-system-backend-0.1.war"]
