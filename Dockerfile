FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S sping -G spring
EXPOSE 8080

ENV JAVA_PROFILE prod
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/MATA-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java", "-Dspring.profiles.active=${JAVA_PROFILE}",\
            "-cp","app:app/lib/*","se331.rest.Lab08Application"]