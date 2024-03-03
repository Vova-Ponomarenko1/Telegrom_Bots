# Telegram Bot Using Java + Spring

This is a simple Telegram bot developed using Java and Spring framework.

## Features

- **Interactive Commands:** Responds to various commands from users.
- **Message Handling:** Handles incoming messages effectively.
- **Spring Framework:** Utilizes the Spring framework for dependency injection and MVC architecture.

## Setup

1. **Clone the Repository:**
    ```
    git clone 
    ```

2. **Configure Bot Token:**
    - Obtain a bot token from the [BotFather](https://core.telegram.org/bots#botfather) on Telegram.
    - Replace `BOT_TOKEN_HERE` in `application.properties` with your bot token.

3. **Build and Run:**
    ```
    mvn clean install
    mvn spring-boot:run
    ```

4. **Start Interacting:**
    - Start a conversation with your bot on Telegram.
    - Use commands like `/start`, `/help`, etc., to interact with the bot.

## Project Structure

- `src/main/java`: Contains Java source files.
- `src/main/resources`: Contains configuration files, such as `application.properties`.

## Dependencies

- [Spring Boot](https://spring.io/projects/spring-boot): For building stand-alone, production-grade Spring-based applications.
- [TelegramBots Java Library](https://github.com/rubenlagus/TelegramBots): A Java library for creating Telegram bots.


## It was Sunday, I was boring:D