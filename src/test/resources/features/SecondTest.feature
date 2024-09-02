Feature: Web Logout

  Background: Open Browser with given configuration
    When Open browser

  Scenario: User should be able to login One
    Given the user is on login page
    When the user enters valid credentials

  Scenario: User should be able to login Two
    Given the user is on login page
    When the user enters valid credentials


  Scenario: User should be able to login Three
    Given the user is on login page
    When the user enters valid credentials