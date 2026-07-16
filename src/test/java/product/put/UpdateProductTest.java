package product.put;

import static org.testng.Assert.assertTrue;

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
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

@Feature("Produtos")
@Story("Atualizar Produto Válido")
public class UpdateProductTest extends BaseTest{

    private static final ProductClient productClient = new ProductClient();

    private Response response;

    private Product bodyRequest = Product.builder()
                    .title("Mens Casual Premium Slim Fit Shirt")
                    .price(50.99f)
                    .description("Jeans 100% polyester")
                    .category("men's clothing")
                    .image("https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_t.png")
                    .build();

    private Product actualProduct;

    @BeforeClass
    public void createProduct(){
        response = productClient.updateProduct(bodyRequest, "1");
        actualProduct = response.as(Product.class);
    }

    @Test(description = "[C19] - PUT /products Deve retornar produto atualizado com sucesso")
    @Tag("smoke")
    @Severity(SeverityLevel.BLOCKER)
    void shouldCreateProductSuccessfully(){
        ApiAssertions.assertStatusCode(response, 200);
    }

    @Test(description = "[C19] -  PUT /products deve retornar Content-Type application/json")
    @Tag("regression")
    @Severity(SeverityLevel.MINOR)
    void shouldReturnJsonContentType(){
        assertTrue(response.getContentType().contains("application/json; charset=utf-8"),
         "Erro: Content-Type retornado: " + response.getContentType() + "e o esperado era application/json; charset=utf-8");
    }

    @Test(description = "[C19] - PUT /products deve retornar produto atualizado")
    @Tag("smoke")
    @Severity(SeverityLevel.CRITICAL)
    public void shoudlReturnExpectedProduct(){
        SoftAssert softAssert = new SoftAssert();

        ApiAssertions.softAssertNotNull(softAssert,"id", actualProduct);
        ApiAssertions.softAssertField(softAssert, "price", actualProduct.getPrice(), bodyRequest.getPrice());
        ApiAssertions.softAssertField(softAssert, "category", actualProduct.getCategory(), bodyRequest.getCategory());
        ApiAssertions.softAssertField(softAssert, "description", actualProduct.getDescription(), bodyRequest.getDescription());
        ApiAssertions.softAssertField(softAssert, "title", actualProduct.getTitle(), bodyRequest.getTitle());
        ApiAssertions.softAssertField(softAssert, "image", actualProduct.getImage(), bodyRequest.getImage());

        softAssert.assertAll();
    }

    @Test(description = "[C20] - PUT /products deve retornar response conforme schema")
    @Tag("regression")
    public void shouldReturnProductWithValidSchema(){
        ApiAssertions.assertMatchesSchema(response, "schemas/product-without-rating-schema.json");
    }
}
