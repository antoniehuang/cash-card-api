package ant.cashcardapi;

import com.jayway.jsonpath.DocumentContext;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.ObjectContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CashCardTest {

    @Autowired
    private JacksonTester<CashCard> json;

    @Autowired
    private JacksonTester<CashCard[]> jsonList;

    private CashCard[] cashCards;

    @BeforeEach
    void setUp() {
        cashCards = Arrays.array(
                new CashCard(99L, 123.45),
                new CashCard(100L, 1.0),
                new CashCard(101L, 150.0)
        );
    }

    @Test
    void shouldSerialiseCashCard() throws IOException {
        CashCard cashCard = new CashCard(99L, 123.45);

        JsonContent<CashCard> jsonResult = json.write(cashCard);

        assertThat(jsonResult).isStrictlyEqualToJson("single_cash_card.json");

        assertThat(jsonResult).hasJsonPathNumberValue("@.id");
        assertThat(jsonResult).extractingJsonPathNumberValue("@.id").isEqualTo(99);

        assertThat(jsonResult).hasJsonPathNumberValue("@.amount");
        assertThat(jsonResult).extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);
    }

    @Test
    void shouldDeserialiseCashCard() throws IOException {
        String expected = """
                {
                  "id": 99,
                  "amount": 123.45
                }
                """;

        ObjectContent<CashCard> cashCardObjectContent = json.parse(expected);
        assertThat(cashCardObjectContent.getObject()).isEqualTo(new CashCard(99L, 123.45));

        CashCard cashCardObject = json.parseObject(expected);
        assertThat(cashCardObject.getId()).isEqualTo(99);
        assertThat(cashCardObject.getAmount()).isEqualTo(123.45);
    }

    @Test
    void cashCardListSerialisationTest() throws IOException {
        assertThat(jsonList.write(cashCards)).isStrictlyEqualToJson("list_of_cash_cards.json");
    }

    @Test
    void cashCardListDeserialisationTest() throws IOException {
        String expected = """
                [
                  { "id": 99, "amount": 123.45 },
                  { "id": 100, "amount": 1.0 },
                  { "id": 101, "amount": 150.0 }
                ]
                """;

        assertThat(jsonList.parse(expected).getObject()).isEqualTo(cashCards);
    }
}
