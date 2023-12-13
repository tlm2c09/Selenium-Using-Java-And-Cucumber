package apiTests;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pojos.Photo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.filter.log.LogDetail.ALL;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@WireMockTest(httpPort = 9876)
public class RestAssured1Test {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpecification;

    @BeforeEach
    public void beforeEachTest() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                log(ALL).
                build();
        responseSpecification = new ResponseSpecBuilder()
                .log(ALL)
                .build();
    }

    /*******************************************************
     * Send a GET request to /customer/12212
     * and check that the response has HTTP status code 200
     * and check that the response is in JSON format
     * and the first name of the person associated with this customer ID is 'John'.
     * and the city of the person associated with this customer ID is 'Beverly Hills'
     ******************************************************/
    @Test
    public void requestDataForCustomer12212_checkResponseCode_ContentType_FirstName_AddressCity() {
        given()
                .spec(requestSpec)
        .when()
                .get("/customer/12212")
        .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200)
        .and()
                .contentType(equalTo("application/json"))
        .and()
                .body("firstName", equalTo("John"))
        .and()
                .body("address.city", equalTo("Beverly Hills"));
    }

    /*******************************************************
     * Send a GET request to /customer/99999
     * and check that the answer has HTTP status code 404
     ******************************************************/
    @Test
    public void requestDataForCustomer99999_checkResponseCode_expect404() {
        given()
                .spec(requestSpec)
        .when()
                .get("/customer/99999")
        .then()
                .assertThat()
                .statusCode(404);
    }

    /***********************************************
     * Send a GET request to /customer/12212/accounts
     * and check that the list of accounts returned
     * includes an account with ID 12345
     * does not include an account with ID 99999
     **********************************************/
    @Test
    public void requestAccountsForCustomer12212_checkListOfAccountsIDs_expectContains12345() {
        given()
                .spec(requestSpec)
        .when()
                .get("/customer/12212/accounts")
        .then()
                .spec(responseSpecification)
                .assertThat()
                .body("accounts.id", hasItem(12345))
        .and()
                .body("accounts.id", not(hasItem(99999)))
        .and()
                .body("accounts", hasSize(3));
    }

    /*******************************************************
     * Transform these tests into a single ParameterizedTest,
     * using a CsvSource data source with three test data rows:
     * ------------------------------------
     * customer ID | first name | last name
     * ------------------------------------
     * 12212       | John       | Smith
     * 12323       | Susan      | Holmes
     * 14545       | Anna       | Grant
     *
     * Request user data for the given user IDs by sending
     * an HTTP GET to /customer/<customerID>.
     *
     * Use the test data collection created
     * above. Check that both the first name and the last name
     * for each of the customer IDs matches the expected values
     * listed in the table above
     *
     ******************************************************/
    @ParameterizedTest
    @CsvSource({
            "12212, John, Smith",
            "12323, Susan, Holmes",
            "14545, Anna, Grant"
    })
    public void requestDataForCustomer12212_checkNames_expectJohnSmith(int customerID, String customerFirstName, String customerLastName) {
        given()
                .spec(requestSpec)
        .when()
                .get("/customer/" + customerID)
        .then()
                .spec(responseSpecification)
                .assertThat()
                .body("firstName", equalTo(customerFirstName))
        .and()
                .body("lastName", equalTo(customerLastName));
    }

    /*******************************************************
     * Perform a GET request to /token and pass in basic
     * authentication details with username 'john' and
     * password 'demo'.
     * Extract the value of the 'token' element in the
     * response into a String variable.
     * Verify that the status code of this response is equal to HTTP 200
     ******************************************************/

    @Test
    public void getTokenUsingBasicAuth_extractFromResponse_thenReuseAsOAuthToken() {
        String token =
                given()
                    .spec(requestSpec)
                    .auth()
                    .preemptive()
                    .basic("john", "demo")
                .when()
                    .get("/token")
                .then()
                    .spec(responseSpecification)
                    .assertThat()
                    .statusCode(200)
                    .extract()
                    .path("token");

        given()
                .spec(requestSpec)
                .auth()
                .oauth2(token)
        .when()
                .get("/secure/customer/12212")
        .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200);
    }

    /*******************************************************
     * Perform a GET request to /xml/customer/12212/accounts
     * to get the list of accounts associated with customer 12212 in XML format
     * Assert that the ID of the first account equals 12345
     * Assert that the balance for the third account in the list is equal to 43.21
     * Assert that the list contains 3 accounts of type 'checking'
     * Assert that the list contains 2 accounts that have an id starting with a '5'
     * ******************************************************/
    @Test
    public void getAccountsForCustomer12212AsXml_checkIdOfFirstAccount_shouldBe12345() {
        given()
                .spec(requestSpec)
        .when()
                .get("/xml/customer/12212/accounts")
        .then()
                .spec(responseSpecification)
                .assertThat()
                .body("accounts.account[0].id", equalTo("12345"))
        .and()
                .body("accounts.account[2].balance", equalTo("43.21"))
        .and()
                .body("accounts.account.findAll{account -> account.type == 'checking'}", hasSize(3))
        .and()
                .body("accounts.account.id.grep(~/5.*/)", hasSize(2));
    }

    /*******************************************************
     * Create a new payload for a GraphQL query using a
     * HashMap and the specified query (with hardcoded ID)
     *
     * POST this object to /graphql
     *
     * Assert that the name of the fruit is equal to "Apple"
     * Use "data.fruit.fruit_name" as the GPath
     * expression to extract the required value from the response
     *
     * Also, assert that the tree name is equal to "Malus"
     * Use "data.fruit.tree_name" as the GPath
     * expression to extract the required value from the response
     ******************************************************/

    @Test
    public void getFruitData_checkFruitAndTreeName_shouldBeAppleAndMalus() {
        String queryString = """
                {
                    fruit(id: 1) {
                        id
                        fruit_name
                        tree_name
                    }
                }
                """;
        HashMap<String, Object> graphQlQuery = new HashMap<>();
        graphQlQuery.put("query", queryString);
        given()
                .spec(requestSpec)
                .body(graphQlQuery)
        .when()
                .post("/graphql")
        .then()
                .spec(responseSpecification)
                .assertThat()
                .body("data.fruit.fruit_name", Matchers.equalTo("Apple"))
        .and()
                .body("data.fruit.tree_name", Matchers.equalTo("Malus"));
    }

    /*******************************************************
     * Transform this Test into a ParameterizedTest, using
     * a CsvSource data source with three test data rows:
     * ---------------------------------
     * fruit id | fruit name | tree name
     * ---------------------------------
     *        1 |      Apple |     Malus
     *        2 |       Pear |     Pyrus
     *        3 |     Banana |      Musa
     *
     * Parameterize the test
     *
     * Create a new GraphQL query from the given query string
     * Pass in the fruit id as a variable value
     *
     * POST this object to /graphql
     *
     * Assert that the HTTP response status code is 200
     *
     * Assert that the name of the fruit is equal to the value in the data source
     * Use "data.fruit.fruit_name" as the GPath
     * expression to extract the required value from the response
     *
     * Also, assert that the tree name is equal to the value in the data source
     * Use "data.fruit.tree_name" as the GPath
     * expression to extract the required value from the response
     ******************************************************/

    @ParameterizedTest
    @CsvSource({
            "1, Apple, Malus",
            "2, Pear, Pyrus",
            "3, Banana, Musa"
    })
    public void getFruitDataById_checkFruitNameAndTreeName(int id, String fruitName, String treeName) {

        String queryString = """
                query GetFruit($id: ID!)
                {
                    fruit(id: $id) {
                        id
                        fruit_name
                        tree_name
                    }
                }
                """;
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("fruit_name", fruitName);
        variables.put("tree_name", treeName);

        HashMap<String, Object> graphQLQuery = new HashMap<>();
        graphQLQuery.put("query", queryString);
        graphQLQuery.put("variables", variables);

        given()
                .spec(requestSpec)
        .and()
                .body(graphQLQuery)
        .when()
                .post("/graphql")
        .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200)
        .and()
                .body("data.fruit.fruit_name", Matchers.equalTo(fruitName))
        .and()
                .body("data.fruit.tree_name", Matchers.equalTo(treeName));
    }

    @Test
    public void fromUserId_findPhotoTitle_expectPariaturSuntEveniet() {

        /*******************************************************
         * Perform a GET to /users and extract the user id
         * that corresponds to the user with username 'Karianne'
         *
         * Hint: use extract().path() and a 'find' filter to do this.
         *
         * Store the user id in a variable of type int
         ******************************************************/

        int userId =
                given()
                        .spec(requestSpec)
                .when()
                        .get("/users")
                .then()
                        .spec(responseSpecification)
                        .extract()
                        .path("find {user -> user.username == 'Karianne'}.id");

        /*******************************************************
         * Use a JUnit assertEquals to verify that the userId
         * is equal to 4
         ******************************************************/

        Assertions.assertEquals(4, userId);

        /*******************************************************
         * Perform a GET to /albums and extract all albums that
         * are associated with the previously retrieved user id.
         *
         * Hint: use extract().path() and a 'findAll' to do this.
         *
         * Store these in a variable of type List<Integer>.
         ******************************************************/

        List<Integer> allAlbumIds =
                given()
                        .spec(requestSpec)
                .when()
                        .get("/albums")
                .then()
                        .spec(responseSpecification)
                        .extract()
                        .path(String.format("findAll {album -> album.userId == %d}.id", userId));
        /*******************************************************
         * Use a JUnit assertEquals to verify that the list has
         * exactly 10 items (hint: use the size() method)
         ******************************************************/

        Assertions.assertEquals(10, allAlbumIds.size());

        /*******************************************************
         * Perform a GET to /albums/XYZ/photos, where XYZ is the
         * id of the fifth album in the previously extracted list
         * of album IDs (hint: use get(index) on the list).
         *
         * Deserialize the list of photos returned into a variable
         * of type List<Photo>.
         *
         * Hint: see
         * https://stackoverflow.com/questions/21725093/rest-assured-deserialize-response-json-as-listpojo
         * (the accepted answer should help you solve this one).
         ******************************************************/

        List<Photo> photos = Arrays.asList(
                given()
                        .spec(requestSpec)
                        .pathParams("albumId", allAlbumIds.get(4))
                .when()
                        .get("/albums/{albumId}/photos")
                        .as(Photo[].class));
        /*******************************************************
         * Use a JUnit assertEquals to verify that the title of
         * the 32nd photo in the list equals 'pariatur sunt eveniet'
         *
         * Hint: use the get() method to retrieve an object with a
         * specific index from a List
         ******************************************************/
        Assertions.assertEquals(photos.get(31).getTitle(), "pariatur sunt eveniet");
    }
}
