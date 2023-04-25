package stepDefinitions;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import models.Pet;
import models.PetCategory;
import models.PetTag;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class PetStoreStepDef {

    private Pet pet;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/pet";
    }

    @After
    public void cleanUp() {
        // Since the test is running on public database
        // every Pet should be deleted after the test is complete to lower
        // the change of testing against existing data on the next run
        given().when().delete("/" + pet.getId()).then();
    }

    @Given("User created a Pet with the following data")
    public void user_created_a_pet_with_the_following_data(io.cucumber.datatable.DataTable dataTable) {

        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);

        PetCategory category = new PetCategory(
                Integer.parseInt(data.get(0).get("category.id")),
                data.get(0).get("category.name"));

        PetTag tags = new PetTag(
                Integer.parseInt(data.get(0).get("tag.id")),
                data.get(0).get("tag.name"));

        String[] urls = data.get(0).get("urls").split(",");

        //Create pet from serialize pet model
        pet = new Pet(Integer.parseInt(data.get(0).get("id")),
                data.get(0).get("name"),
                data.get(0).get("status"),
                Arrays.asList(urls),
                Arrays.asList(tags),
                category);
    }

    @When("User added a Pet to the store")
    public void user_added_a_pet_to_the_store() {
        var head = new Header("Content-Type", "application/json");
        given().header(head).body(pet).when().post().then()
                .assertThat().statusCode(200);
    }

    @Then("The store contain Pet with id {int}")
    public void the_store_contain_pet_with_id(int id) {
        given().when().get("/" + id).then()
                .assertThat().statusCode(200);
    }

    @Then("Pet with id {int} contains expected information")
    public void pet_with_id_contains_expected_information(Integer id) {
        var actualPet = given().when().get("/" + id).as(Pet.class);
        assertPetDetails(actualPet);
    }

    @And("User updated Pet data as follows")
    public void user_updated_pet_data_as_follows(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);

        pet.setName(data.get(0).get("name"));
        pet.setStatus(data.get(0).get("status"));

        String[] urls = data.get(0).get("urls").split(",");
        pet.setPhotoUrls(Arrays.asList(urls));

        PetCategory category = new PetCategory(
                Integer.parseInt(data.get(0).get("category.id")),
                data.get(0).get("category.name"));
        pet.setCategory(category);

        PetTag tags = new PetTag(
                Integer.parseInt(data.get(0).get("tag.id")),
                data.get(0).get("tag.name"));
        pet.setTags(Arrays.asList(tags));
    }

    @Then("User updated Pet data on the store")
    public void user_updated_pet_data_on_the_store() {
        var head = new Header("Content-Type", "application/json");
        given().header(head).body(pet).put().then()
                .assertThat().statusCode(200);
    }

    @And("User updated Pet with form data")
    public void user_updated_pet_with_form_data(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);

        var head = new Header("Content-Type", "application/x-www-form-urlencoded");

        String name = data.get(0).get("name");
        String status = data.get(0).get("status");

        // Update expected Pet object
        pet.setName(name);
        pet.setStatus(status);

        given().header(head)
                .formParam("name", name)
                .formParam("status", status)
                .when().post("/" + pet.getId()).then()
                .assertThat().statusCode(200);
    }

    @Then("User can find Pet under status {string}")
    public void user_can_find_pet_under_status(String status) {
        var response = given().queryParam("status", status).when().get("/findByStatus")
                .then().extract().jsonPath().get(String.format("find { it.id == %s }", pet.getId()));

        // Using Jackson object mapper to convert json string to Pet object.
        ObjectMapper objMapper = new ObjectMapper();
        var actualPet = objMapper.convertValue(response, Pet.class);
        assertPetDetails(actualPet);

    }

    @When("User deleted a Pet with id {int}")
    public void user_deleted_a_pet_with_id(Integer id) {
        given().when().delete("/" + id).then().assertThat().statusCode(200);
    }

    @Then("Pet with id {int} is not found")
    public void pet_with_id_is_not_found(Integer id) {
        given().when().delete("/" + id).then().assertThat().statusCode(404);
    }

    private void assertPetDetails(Pet actualPet) {

        assertThat(actualPet, samePropertyValuesAs(pet, "category", "tags"));

        assertThat(actualPet.getCategory(), samePropertyValuesAs(pet.getCategory()));

        for (int i = 0; i < pet.getTags().size(); i++) {
            assertThat(actualPet.getTags().get(i), samePropertyValuesAs(pet.getTags().get(i)));
        }
    }


}

