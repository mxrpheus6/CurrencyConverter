# Currency Converter API Application

This repository contains a simple REST API application that converts currency.

## Introduction

This is a basic REST API application built using [Spring Boot](https://spring.io/projects/spring-boot) framework. The application allows users to convert one currency to another by making HTTP requests to predefined endpoints.

## Features

- Get currency conversion with updated rates.
- Store conversion data in a database.

## Technologies Used

- [Spring Boot](https://spring.io/projects/spring-boot): Web framework for building the REST API.
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa): Data access framework for interacting with the database.
- [ExchangeRate-API](https://www.exchangerate-api.com): External API for weather data.

### Endpoints

- **Convert currency:**

  Example:
  ```http
  GET /convertor/from/USD/amount/1000/to/BYN
  ```

## Contributing

Contributions are welcome! If you find any issues or have improvements to suggest, feel free to open an issue or create a pull request.