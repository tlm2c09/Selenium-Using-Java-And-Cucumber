Feature:
  @Login
  Scenario: User logins successfully
    Given the user goes to the login page
    When the user logins with username "standard_user" and password "secret_sauce"
    When the user clicks the "button" "login"
    Then the user is at the inventory page