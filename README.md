# Spring-AI kata (Functions)

This is my sandbox for working with Spring AI using more advanced features like functions integrations: it is a follow up
of the other related Spring AI kata repositiories:
- [Spring AI Kata](https://github.com/scalasm/springai-kata) which deals with more common topics!
- [Spring AI Kata Functions](https://github.com/scalasm/springai-kata-functions) which deals with tools integrations!

# Requirements
- JDK 21 or better
  - I use Amazon Corretto 21 but anything should be fine
- Apache Maven 3.9.x 
- OPENAI API Key - to be obtained by the [Open AI Console](https://platform.openai.com/settings/organization/api-keys)
  - ensure there is an environment variable called `OPENAI_API_KEY` set and your will be ready to go!
- NINJAS_API_KEY - to be obtained from [API Ninjas](https://api-ninjas.com/) - the free tier is ok!
  - We want to consume the [Weather API](https://api-ninjas.com/api/weather)

# How to run

## Option 1 - from Visual Studio Code
A launch configuration is provided for Visual Studio Code - just create a `.env` file in the workspace root with you `OPENAPI_API_KEY` set.

## Option 2 - Command line
```
mvn spring-boot:run
```

## How to test

There is a [Swagger UI](http://localhost:8080/swagger-ui/index.html) available.

# References
- [Spring AI - Begineer to Guru](https://www.udemy.com/course/spring-ai-beginner-to-guru) has been my initial reference