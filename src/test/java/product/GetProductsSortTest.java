package product;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import com.db.client.product.ProductClient;
import com.db.model.Product;

import base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;

@Feature("Produtos")
@Story("Ordenar Lista de Produtos Conforme Especificação na Requisição")
public class GetProductsSortTest extends BaseTest{

    private final ProductClient productClient = new ProductClient();

    @Test(description = "Deve retornar quantidade produtos ordenados decrescente(id)")
    @Severity(SeverityLevel.MINOR)
    void shouldReturnProductsSortedDesc(){

        Response response = productClient.getProductsSorted("desc");
        assertEquals(response.getStatusCode(), 200);

        List<Product> products = List.of(response.as(Product[].class));

        assertFalse(products.isEmpty(), "A lista de produtos está vazia");

        List<Integer> ids = products.stream()
                            .map(Product::getId)
                            .collect(Collectors.toList());
                            
        List<Integer> idsSortedDesc = ids.stream()
                                        .sorted(Comparator.reverseOrder())
                                        .collect(Collectors.toList());

        assertEquals(ids, idsSortedDesc, "A lista de produtos não está ordenada por id de forma descrente");
    }
}

