Feature: Register Functionality

  Scenario: Verify Registering an Account by providing only the Mandatory fields
    Given Open the Application
    When Click on "My Account" Drop menu
    Then Click on "Register" option
    And Enter Account Details
      | First Name | Last Name | E-Mail                | Telephone  |
      | soumya     | ghadei    | soumya.ghadei@xyz.org | 7609996277 |
    And Click on "Continue" button displayed in "Register" page
    Then Verify User should be logged in and taken to Account Success page
    And Verify proper details should be displayed on the "Account Success" page
    And Click on "Continue" button displayed in "Account Success" page
    Then Verify User should be taken to "Account" page

