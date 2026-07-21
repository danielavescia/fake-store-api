package product.get.getById;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.db.client.product.ProductClient;
import com.db.model.Product;

import assertions.ApiAssertions;
import base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

/* Id é hardcoded nos testes porque API não persiste alterações */

@Feature("Produtos")
@Story("Retornar produto específico do catálago")
public class GetProductByIdTest extends BaseTest {

    private static final ProductClient productClient = new ProductClient();

    private Response response;

    private Product product;
    
    @BeforeClass(alwaysRun = true)
    @Step("Busca todos os produtos")
    public void fetchAllProducts(){
        response = productClient.getProductById("1");
        product = response.as(Product.class);
    }

    @Test(description = "[C07] - GET /products/{id} deve retornar status code 200 + produto no body")
    @Tag("smoke")
    @Severity(SeverityLevel.BLOCKER)
    public void shouldReturnStatusCode200(){
        ApiAssertions.assertStatusCode(response, 200);
    }

    @Test(description = "[C08] - GET /products/{id} deve retornar produto conforme o schema")
    @Tag("smoke")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldReturnProductWithValidSchema(){
        ApiAssertions.assertMatchesSchema(response, "schemas/product-schema.json");
    }

    @Test(description = "[C09] - GET /products/{id} id do body é igual ao Id solicitado")
    @Tag("smoke")
    @Severity(SeverityLevel.BLOCKER)
    public void shouldReturnSameId(){
        int actualId = product.getId();
        SoftAssert softAssert = new SoftAssert();

        ApiAssertions.softAssertNotNull(softAssert, "id", actualId);
        ApiAssertions.softAssertField(softAssert, "id", actualId, 1 );
    }
}