package product.delete;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.db.client.product.ProductClient;

import assertions.ApiAssertions;
import base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

@Feature("Produtos")
@Story("Deletar produto Existente")
public class DeleteProductTest extends BaseTest {

    private static final ProductClient productClient = new ProductClient();

    private Response response;

    @BeforeClass
    public void createProduct(){
        response = productClient.deleteProduct("1");
    }

    @Test(description = "[C28] - Deve retornar produto criado com sucesso")
    @Tag("smoke")
    @Severity(SeverityLevel.BLOCKER)
    void shouldDeleteProductSuccessfully(){
        ApiAssertions.assertStatusCode(response, 200);
    }

    @Test(description = "[C28] - DELETE /products deve retornar Content-Type application/json")
    @Tag("regression")
    @Severity(SeverityLevel.MINOR)
    void shouldReturnJsonContentType(){
        assertTrue(response.getContentType().contains("application/json; charset=utf-8"),
         "Erro: Content-Type retornado: " + response.getContentType() + "e o esperado era application/json; charset=utf-8");
    }

    @Test(description = "[C30] - DELETE /products deve retornar response conforme schema")
    @Tag("regression")
    public void shouldReturnProductWithValidSchema(){
        ApiAssertions.assertMatchesSchema(response, "schemas/product-schema.json");
    }
}