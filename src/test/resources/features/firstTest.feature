Feature: Web Login

  Background:
    Given Initialize browser and setup test data

  Scenario: User should be able to login with valid credentials
    Given the user is on login page
    When the user enters valid credentials
    And hits submit button
#    Then the user should be logged in successfully

#  Scenario: User should be able to logout
#    Given the user is on login page
#    When the user enters valid credentials
#    And hits submit button
#    Then the user should be logged in successfully
#
#  Scenario: User should be able to logout new
#    Given the user is on login page
#    When the user enters valid credentials
#    And hits submit button
#    Then the user should be logged in successfully