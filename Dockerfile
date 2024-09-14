FROM mcr.microsoft.com/playwright/java:v1.47.0-jammy
LABEL authors="Soumya"
WORKDIR /app
COPY . /app
RUN mvn clean install -DskipTests
EXPOSE 3000

ENTRYPOINT ["mvn", "test"]