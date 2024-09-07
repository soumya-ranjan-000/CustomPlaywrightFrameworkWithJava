Feature: Web Logout

  Background: Open Browser with given configuration
    When Open browser

  Scenario: User should be able to log out One
    Given the user is on login page
    When the user enters valid credentials

#  Scenario: User should be able to log out Two
#    Given the user is on login page
#    When the user enters valid credentials
#
#  Scenario: User should be able to log out Three
#    Given the user is on login page
#    When the user enters valid credentials
