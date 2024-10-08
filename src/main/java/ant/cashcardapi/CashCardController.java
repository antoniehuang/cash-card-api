package ant.cashcardapi;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController // Tells Spring that this class is a Component of type RestController
@RequestMapping("/cashcards")
public class CashCardController {

    // Inject CashCardRepository into CashCardController
    private  final CashCardRepository cashCardRepository;

    private CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        Optional<CashCard> cashCardOptional = cashCardRepository.findById(requestedId);
        if (cashCardOptional.isPresent()) {
            return ResponseEntity.ok(cashCardOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // In Post request Spring Web will deserialise the data into a CashCard object for us.
    // We can add UriComponentsBuilder ucb as a method argument to this POST handler method and it was
    // automatically passed through dependency injections.
    @PostMapping
    private ResponseEntity<Void> createCashCash(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
        CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);
        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}")
                .buildAndExpand(savedCashCard.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping
    private ResponseEntity<Iterable<CashCard>> getAllCashCards() {
        Iterable<CashCard> listOfAllCashCards = cashCardRepository.findAll();
        return ResponseEntity.ok(listOfAllCashCards);
    }
}
