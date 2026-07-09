package product;

/** 
 * Testes para avaliar o comportamento da API para valores inválidos do query param 'limit'
 * A API não realiza validação de tipo nesses casos
 * limit=0 - retorna status code 200 e array com 20 produtos 
 * limit=-1 - retorna status code 200 e array com 19 produtos
 * limit=abc - retorna status code 200 e array com 20 produtos
 * logo, os testes só capturam o comportamento observado já que não existe nada documentado
**/

public class GetProductsLimitEdgeCasesTest {

}
