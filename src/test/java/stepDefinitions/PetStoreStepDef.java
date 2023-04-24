package stepDefinitions;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import models.Pet;
import models.PetCategory;
import models.PetTag;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class PetStoreStepDef {

    private Pet pet;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/pet";
    }

    @Given("User created a Pet with id {int}")
    public void user_created_a_pet_with_id(int id) {

        PetCategory category = new PetCategory(1, "dog");
        PetTag tags = new PetTag(0, "myTag");

        //Create pet from serialize pet model
        pet = new Pet(id,
                "Yuna",
                "available",
                Arrays.asList("url1", "url2"),
                Arrays.asList(tags),
                category);
    }

    @When("User added a Pet to the store")
    public void user_added_a_pet_to_the_store() {
        given().body(pet).when().post().then()
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

}
