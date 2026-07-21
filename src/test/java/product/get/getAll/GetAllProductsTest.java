package product.get.getAll;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
/* API expoõe o header 'x-powered-by: Express, 
*  violando uma recomendação da OWASP para evitar ataques de segurança
*  nesse caso optou-se por deixar o teste ativo para documentar essa falha.
 */

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

    @Test(description = "[CO1] - GET /products deve retornar status code 200")
    @Tag("C01")
    @Severity(SeverityLevel.BLOCKER)
    public void shouldReturnStatusCode200(){
       assertEquals(response.getStatusCode(),200, "Erro: Status code retornado foi: " + response.getStatusCode() + " e o esperado era 200.");
    }

    @Test(description = "[C04] - GET /products deve retornar Content-Type application/json")
    @Tag("C04")
    @Severity(SeverityLevel.MINOR)
    public void shouldReturnJsonContentType(){
        assertTrue(response.getContentType().contains("application/json; charset=utf-8"),
         "Erro: Content-Type retornado: " + response.getContentType() + "e o esperado era application/json; charset=utf-8");
    }

    @Test(description = "[C04] - GET /products não deve expor tecnologia do backend no header")
    @Tag("C04")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldNotExposeSensitiveHeaders(){
        boolean hasPoweredBy = response.getHeaders().hasHeaderWithName("x-powered-by");

        assertFalse(hasPoweredBy, "Header sensível é exposto: " + hasPoweredBy);
    }

    @Test(description = "[C01] - GET /products deve retornar um array com 20 produtos")
    @Tag("C01")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldReturnExactlyTwentyProducts(){
        assertNotNull(products, "A lista de produtos é nula");
        assertEquals(products.size(), 20, "Quantidade de produtos retornada é de: " + products.size() + " e o esperado é 20");
    }

    @Test(description = "[C02] - GET /products deve retornar um array de produtos com schema válido")
    @Tag("C02")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldReturnEachProductWithValidSchema(){
        assertThat(response.asString(), matchesJsonSchemaInClasspath("schemas/products-schema.json"));
    }

    @Test(description = "[C03] - GET /products deve retornar um array de produtos com ids únicos")
    @Tag("C03")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldReturnUniqueIds(){
       List<Integer> ids = response.jsonPath().getList("id");
       Set<Integer> uniqueIds = new HashSet<>(ids);

       assertEquals(ids.size(), uniqueIds.size(), "Existem "+ (ids.size()-uniqueIds.size()) + "elemento(s) com id(s) que não são unicos na lista");
    }  
}