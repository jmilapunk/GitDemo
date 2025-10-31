@tag
Feature: Purchase the order from Ecommerce Website

  Background:
    Given I landed on Ecommerce Page

  @Regression
  Scenario Outline: Positive Test of Submitting the order
    Given Logged in with username <name> and password <password>
    When I add product <productName> to Cart
    And Checkout product <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on Confirmation Page

    Examples:
      | name                | password   | productName |
      | jmila.ice@gmail.com | $Miej18496 | ZARA COAT 3 |
