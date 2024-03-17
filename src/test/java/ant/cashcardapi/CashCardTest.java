package ant.cashcardapi;

import com.jayway.jsonpath.DocumentContext;
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
        assertThat(cashCardObjectContent).isEqualTo(new CashCard(99L, 123.45));

        CashCard cashCardObject = json.parseObject(expected);
        assertThat(cashCardObject.getId()).isEqualTo(99);
        assertThat(cashCardObject.getAmount()).isEqualTo(123.45);
    }
}
