package ant.cashcardapi;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CashCard {
    private Long id;
    private Double amount;
}
