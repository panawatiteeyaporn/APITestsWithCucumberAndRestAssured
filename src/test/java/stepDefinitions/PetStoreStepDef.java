package stepDefinitions;

import io.cucumber.java.BeforeAll;
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
        assertThat(actualPet, samePropertyValuesAs(pet, "category", "tags"));

        assertThat(actualPet.getCategory(), samePropertyValuesAs(pet.getCategory()));

        for (int i = 0; i < pet.getTags().size(); i++) {
            assertThat(actualPet.getTags().get(i), samePropertyValuesAs(pet.getTags().get(i)));
        }

    }

    @Then("User updated Pet data as follows")
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

}

