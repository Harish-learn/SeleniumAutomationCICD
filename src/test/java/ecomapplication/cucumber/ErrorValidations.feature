Feature: Error validations

  @ErrorValidation
  Scenario Outline:
    Given User landed on Ecommerce page
    Given User logged in with username <userName> and password <password>
    Then "Incorrect email or password." error message is displayed
    Examples:
      | userName              | password      |
      | postman_api@gmail.com | 1234567899@hH |