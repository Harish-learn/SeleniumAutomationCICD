Feature: Purchase a product from Ecommerce website
  Background:
    Given User landed on Ecommerce page

  @Regression
    Scenario Outline:
      Given User logged in with username <userName> and password <password>
      When User added product <productName> to cart
      And Checkout <productName> and submit the order
      Then "THANKYOU FOR THE ORDER." message is displayed in confiirmation page
      Examples:
        | userName              | password     | productName |
        | postman_api@gmail.com | 123456789@hH | ZARA COAT 3 |