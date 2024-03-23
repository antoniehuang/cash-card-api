package ant.cashcardapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class CashCard {

    @Id // Specify id as the id for the CashCardRepository.
    private Long id;
    private Double amount;
    private String owner;

}
