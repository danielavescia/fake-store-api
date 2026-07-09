package product;

import java.util.List;

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
import io.restassured.response.Response;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Feature("Produtos")
@Story("Listar todos os Produtos do catálago")
public class GetAllProductsTest extends BaseTest{
    
    private final ProductClient productClient = new ProductClient();

    private Response response;

    private List<Product> products;

    @BeforeClass(alwaysRun = true)
    @Step("Busca todos os produtos")
    public void fetchAllProducts(){
        response = productClient.getAllProducts();
        products = List.of(response.as(Product[].class));
    }

    @Test(description = "GET products deve retornar status code 200")
    @Severity(SeverityLevel.BLOCKER)
    public void shouldReturnStatusCode200(){
       assertEquals(response.getStatusCode(),200, "Erro: Status code retornado foi: " + response.getStatusCode() + " e o esperado era 200.");
    }

    @Test(description = "GET products deve retornar Content-Type application/json")
    @Severity(SeverityLevel.MINOR)
    public void shouldReturnJsonContentType(){
        assertTrue(response.getContentType().contains("application/json"),
         "Erro: Content-Type retornado: " + response.getContentType() + "e o esperado era application/json");
    }

    @Test(description = "GET /products deve retornar um array com 20 produtos")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldReturnExactlyTwentyProducts(){
        assertNotNull(products, "A lista de produtos é nula");
        assertEquals(products.size(), 20, "Quantidade de produtos retornada é de: " + products.size() + " e o esperado é 20");
    }

    @Test(description = "GET /products deve retornar um array de produtos com schema válido")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldReturnEachProductWithValidSchema(){
        assertThat(response.asString(), matchesJsonSchemaInClasspath("schemas/products-schema.json"));
    }
}