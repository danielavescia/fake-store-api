package dataprovider;

import java.util.Comparator;

import org.testng.annotations.DataProvider;

import com.db.model.Product;

public class ProductDataProvider {

    @DataProvider(name = "validParamLimits")
    public static Object[][] validParamLimits(){
        return new Object[][]{
            {"1"},
            {"10"},
            {"19"}
        };
    }

    @DataProvider(name = "sortOrders")
    public static Object[][] sortOrders(){
        return new Object[][]{
            {"asc", Comparator.<Integer>naturalOrder(), "crescente"},
            {"desc", Comparator.<Integer>reverseOrder(), "decrescente"}
        };
    }

    @DataProvider(name = "invalidParamsLimit")
    public static Object[][] invalidParamsLimit(){
        return new Object[][]{
            {"0", 20},
            {"-1", 19},
            {"abc", 20}
        };
    }

    @DataProvider(name = "invalidOrOutOfRangeId")
    public static Object [][] invalidOrOutOfRangeId(){
        return new Object[][]{
            {"abc"},
            {"-1"},
            {"%2"},
            {"2147483648"},
            {"99999999999"},
            {"-2147483649"}
        };
    }
    
    @DataProvider(name = "invalidOrPartialPayloads")
    public static Object [][] invalidOrPartialPayloads(){
        return new Object [][]{
            {
                "payload vazio",
                Product.builder().build()
            },

            {
                "payload somente title",
                Product.builder().title("Camisa teste").build()
            },

            {
                "payload somente price",
                Product.builder().price(12.9f).build()
            },

            {
                "payload somente description",
                Product.builder().description("Descrição teste").build()
            },

            {
                "payload somente image",
                Product.builder().image("http://exemplo.com/imagem.jpg").build()
            },

            {
                "payload somente description",
                Product.builder().category("Vestuário masculino").build()
            },

            {
                "payload sem price",
                Product.builder()
                .title("Calça teste")
                .description("Calça")
                .category("Vestuário masculino")
                .image("http://exemplo.com/imagem.jpg")
                .build()
            },
            {
                "payload sem title",
                Product.builder()
                .price(59.99f)
                .description("Calça")
                .category("Vestuário masculino")
                .image("http://exemplo.com/imagem.jpg")
                .build()
            },
            {
                "payload sem description",
                Product.builder()
                .price(59.99f)
                .title("Calça teste")
                .category("Vestuário masculino")
                .image("http://exemplo.com/imagem.jpg")
                .build()
            },
            {
                "payload sem category",
                Product.builder()
                .price(59.99f)
                .title("Calça teste")
                .description("Calça")
                .image("http://exemplo.com/imagem.jpg")
                .build()
            },

            {
                "payload sem image",
                Product.builder()
                .price(59.99f)
                .title("Calça teste")
                .description("Calça")
                .category("Vestuário masculino")
                .build()
            },
            {
                "payload com price negativo",
                Product.builder()
                .price(-59.99f)
                .title("Calça teste")
                .description("Calça")
                .category("Vestuário masculino")
                .image("http://exemplo.com/imagem.jpg")
                .build()
            },

            {
                "payload com id",
                Product.builder()
                .id(50)
                .price(59.99f)
                .title("Calça teste")
                .description("Calça")
                .category("Vestuário masculino")
                .image("http://exemplo.com/imagem.jpg")
                .build()
            },
        };
    }

    @DataProvider(name = "oversizedPayloads")
    public static Object[][] oversizedPayloads(){
        return new Object[][]{
            {
                "payload extremamente grande (DoS)",
                Product.builder()
                    .title("A".repeat(5_000_000))
                    .price(59.90f)
                    .description("desc")
                    .category("roupas")
                    .image("http://x.com/a.jpg")
                    .build()
            }
        };
    }

    @DataProvider(name = "invalidJsonPayloads")
    public static Object[][] invalidJsonPayloads(){
        return new Object[][]{
                {
                    "json com chave sem aspas",
                    "{title: \"Camiseta\", price: 59.90}"
                },

                {
                    "json com vírgula sobrando",
                    "{\"title\": \"Camiseta\", \"price\": 59.90,}"
                },

                {
                    "json com chave não fechada",
                    "{\"title\": \"Camiseta\", \"price\": 59.90"
                },

                {
                    "json vazio como string (não é objeto)",
                    "\"apenas uma string\""
                },

                {
                    "json com aspas simples ao invés de duplas",
                    "{'title': 'Camiseta', 'price': 59.90}"
                }
        };
    }
}