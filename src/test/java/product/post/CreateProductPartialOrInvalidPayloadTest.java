package product.post;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.db.client.product.ProductClient;
import com.db.model.Product;

import assertions.ApiAssertions;
import base.BaseTest;
import dataprovider.ProductDataProvider;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

@Feature("Produtos")
@Story("Criar Produto - Payload Parcial ou Incompleto")
public class CreateProductPartialOrInvalidPayloadTest extends BaseTest{
    
    private final ProductClient productClient = new ProductClient();
    
    private SoftAssert softAssert = new SoftAssert();

    private Response response;

    @Test(description = "[C14][C16] Deve criar produto com payload parcial ou vazio",  dataProvider = "invalidOrPartialPayloads", dataProviderClass = ProductDataProvider.class)
    @Tag("regression")
    public void shouldCreateProductWithPartialPayload(String scenario, Product body){
        
        response = productClient.createProduct(body);

        Product actualProduct = response.as(Product.class);
        
        ApiAssertions.assertStatusCode(response, 201, scenario);
        ApiAssertions.softAssertNotNull(softAssert, "id", actualProduct);
        softAssert.assertAll();
    }
}