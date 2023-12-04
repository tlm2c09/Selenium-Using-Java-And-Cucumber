Feature:
  @Test1
  Scenario: User logins with valid username and password
    Given the user goes to the login page
    When the user enters username "standard_user" and password "secret_sauce"
    When the user clicks the "button" "login"
    Then the user is at the inventory page

  @Test2
  Scenario: User logins with locked account
    Given the user goes to the login page
    When the user enters username "standard_user" and password "secret_sauce"
    When the user clicks the "button" "login"
    Then the user "sees" the element "error message" has texts "Epic sadface: Sorry, this user has been locked out."