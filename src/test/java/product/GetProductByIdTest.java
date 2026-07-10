package product;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.db.client.product.ProductClient;
import com.db.model.Product;

import base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

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

    @Test(description = "[CO7] - GET /products/{id} deve retornar status code 200 + produto no body")
    @Tag("C07")
    @Severity(SeverityLevel.BLOCKER)
    public void shouldReturnStatusCode200(){
       assertEquals(response.getStatusCode(),200, "Erro: Status code retornado foi: " + response.getStatusCode() + " e o esperado era 200.");
    }

    @Test(description = "[C08] - GET /products/{id} deve retornar produto conforme o schema")
    @Tag("C08")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldReturnProductWithValidSchema(){
        assertThat(response.asString(), matchesJsonSchemaInClasspath("schemas/product-schema.json"));
    }

    @Test(description = "[CO9] - GET /products/{id} id do body é igual ao Id solicitado")
    @Severity(SeverityLevel.BLOCKER)
    public void shouldReturnSameId(){
        int actualId = product.getId();
       assertEquals(actualId, 1, "Erro: id do produto retornado foi: " + actualId + " e o esperado era 1.");
    }
}
