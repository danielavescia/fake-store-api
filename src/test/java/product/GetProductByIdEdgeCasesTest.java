package product;

import org.testng.annotations.Test;

import com.db.client.product.ProductClient;

import base.BaseTest;
import dataprovider.ProductDataProvider;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Feature("Produtos")
@Story("Testar edge cases de getBy Id")
public class GetProductByIdEdgeCasesTest extends BaseTest {

    private static final ProductClient productClient = new ProductClient();

    @Test(description = "[CO10] - GET /products/{id} Comportamento atual: API não valida id inválido, retorna 200 e body vazio",
        dataProvider = "invalidId", dataProviderClass = ProductDataProvider.class)
    @Tag("C08")
    @Severity(SeverityLevel.NORMAL)
    public void shouldReturnStatusCode200(String invalidId) {
        Response response = productClient.getProductById(invalidId);
        String body = response.getBody().asString().trim();

        assertEquals(response.getStatusCode(), 200,
                "Erro: Status code retornado foi: " + response.getStatusCode() + " e o esperado era 200.");
        assertTrue(
                body.isEmpty() || body.equals("[]") ,
                "Esperado resposta vazia, mas veio: " + body);
    }

    @Test(description = "[CO11] - GET /products/{id} Comportamento atual: API não trata id fora do range",
        dataProvider = "InvalidRangeId", dataProviderClass = ProductDataProvider.class)
    @Tag("C011")
    @Severity(SeverityLevel.NORMAL)
    public void shouldReturnSameId(String invalidId) {
        Response response = productClient.getProductById(invalidId);
        assertEquals(response.getStatusCode(), 200,
                "Erro: Status code retornado foi: " + response.getStatusCode() + " e o esperado era 200.");
        String body = response.getBody().asString().trim();
        assertTrue(
                body.isEmpty() || body.equals("[]") ,
                "Esperado resposta vazia, mas veio: " + body);
    }
}