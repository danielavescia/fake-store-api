package assertions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

import org.testng.asserts.SoftAssert;

import io.restassured.response.Response;

public class ApiAssertions {

    public static void assertStatusCode(Response response, int expectedStatusCode){
        assertEquals(response.getStatusCode(), expectedStatusCode, "Erro: Status code retornado foi: " + response.getStatusCode() + " e o esperado era " + expectedStatusCode + ".");
    }

    public static void assertMatchesSchema(Response response, String schemaClassPath){
        
        try{
            assertThat(response.asString(), matchesJsonSchemaInClasspath(schemaClassPath));

        }catch(AssertionError e){
            throw new AssertionError(
                "Erro: a resposta não corresponde ao schema esperado (" + schemaClassPath + "). \n" 
                + "Detalhes: " + e.getMessage() + "\n" 
                + "Corpo da resposta: " + response.asString(), e
            );
            
        } catch(Exception e){
            throw new RuntimeException(
                "Erro ao validar o schema: (" + schemaClassPath + "): \n"
                + " arquivo não encontrado/inválido", e
            );
        }
    }

    public static void softAssertField(SoftAssert softAssert, String field, Object actual, Object expected){
        softAssert.assertEquals(actual, expected, "Campo: " + field + " divergente. Atual é: " + actual + "e o esperado é: " + expected);
    }

     public static void softAssertNotNull(SoftAssert softAssert, String field, Object actual){
        softAssert.assertNotNull(actual, "Campo: " + field + " era esperado não nulo." );
    }
}
