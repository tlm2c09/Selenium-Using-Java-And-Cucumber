package apiTests;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testng.Assert;
import pojos.Account;
import pojos.Customer;

import java.util.List;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestAssured2Test {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeEach
    public void setUp() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(8765)
                .log(LogDetail.ALL)
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance()
            .options(wireMockConfig()
                    .port(8765)
                    .globalTemplating(true))
            .build();

    /*******************************************************
     * Create a new Account object with 'savings' as the account
     * type
     *
     * POST this object to /customer/12212/accounts
     *
     * Verify that the response HTTP status code is equal to 201
     ******************************************************/

    @Test
    public void postAccountObject_checkResponseHttpStatusCode_expect201() {
        Account account = new Account(87654, "savings", "0");

        given()
                .spec(requestSpecification)
        .and()
                .body(account)
        .when()
                .post("/customer/12212/accounts")
        .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(201);
    }

    /*******************************************************
     * Perform an HTTP GET to /customer/12212/accounts and
     * deserialize the response into an object of type
     * AccountResponse after checking that the response
     * status code is equal to 200.
     *
     * Using a JUnit assertEquals() method, verify that the
     * number of account in the response (in other words,
     * the size() of the accounts property) is equal to 3
     ******************************************************/

    @Test
    public void getAccountsForCustomer12212_deserializeIntoList_checkListSize_shouldEqual3() {
          List<Account> accounts =
                  given()
                    .spec(requestSpecification)
                .when()
                    .get("/customer/12212/accounts")
                .then()
                    .spec(responseSpecification)
                    .assertThat()
                    .statusCode(200)
                          .extract()
                          .response()
                          .jsonPath()
                          .get("accounts");
                          // .body()
                            // .path("accounts")
        Assert.assertEquals(accounts.size(), 3);
    }

    /*******************************************************
     * Create a new Customer object by using the constructor
     * that takes a first name and last name as its parameters
     *
     * Use a first name and a last name of your own choosing
     *
     * POST this object to /customer
     *
     * Deserialize the response into another object of type
     * Customer and use JUnit assertEquals() assertions to
     * check that the first name and last name returned by
     * the API are the same as those you passed into the
     * constructor of the Customer method you POSTed
     ******************************************************/

    @Test
    public void postCustomerObject_checkReturnedFirstAndLastName_expectSuppliedValues() {

        Customer customer = new Customer("Tuong", "Le");
        Customer createdCustomer =
                given()
                    .spec(requestSpecification)
                .and()
                    .body(customer)
                .when()
                    .post("/customer")
                    .as(Customer.class);
        assertEquals("Tuong", createdCustomer.getFirstName());
        assertEquals("Le", createdCustomer.getLastName());
    }
}
