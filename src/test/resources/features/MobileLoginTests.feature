Feature: Mobile Login Tests 1

  @Android
  Scenario: Login scenarios 1
    Given the user generates random value for "RandomEmail"

    And the user goes to the url "@TD:login_page"
    When the user enters username "@TD:RandomEmail" and password "@TD:standard_user_password"
    When the user clicks the "button" "login"
    Then the user "sees" the element "error message" has texts "Epic sadface: Username and password do not match any user in this service"

    When the user enters username "@TD:locked_out_user_username" and password "@TD:locked_out_user_password"
    When the user clicks the "button" "login"
    Then the user "sees" the element "error message" has texts "Epic sadface: Sorry, this user has been locked out."

    When the user enters username "@TD:standard_user_username" and password "@TD:standard_user_password"
    When the user clicks the "button" "login"
    Then the user is at the inventory page