package product.get.getById;

import org.testng.annotations.Test;

import com.db.client.product.ProductClient;

import assertions.ApiAssertions;
import base.BaseTest;
import dataprovider.ProductDataProvider;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

import static org.testng.Assert.assertTrue;

/* Classe de teste que valida o limite superior+1 e limite inferior-1 do parâmetro Integer 
* Tipo inválido - string não numérica e também com caractere de enconding
* Id dentro do limite de Integer, mas negativo
*/
@Feature("Produtos")
@Story("Testar edge cases de getBy Id")
public class GetProductByIdEdgeCasesTest extends BaseTest {

    private static final ProductClient productClient = new ProductClient();

    @Test(description = "[C10/C11] - GET /products/{id} Comportamento atual: API não valida id inválido, retorna 200 e body vazio",
        dataProvider = "invalidOrOutOfRangeId", dataProviderClass = ProductDataProvider.class)
    @Tag("regression")
    @Severity(SeverityLevel.NORMAL)
    public void shouldReturnStatusCode200(String invalidId) {
        Response response = productClient.getProductById(invalidId);
        String body = response.getBody().asString().trim();

        ApiAssertions.assertStatusCode(response, 200);

        assertTrue(
                body.isEmpty() || body.equals("[]") ,
                "Esperado resposta vazia, mas veio: " + body);
    }
}