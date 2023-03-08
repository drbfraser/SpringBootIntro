package ca.pledgetovote.controllers;

import ca.pledgetovote.model.Pledge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class PledgeController {
    private List<Pledge> pledges = new ArrayList<>();
    private AtomicLong nextId = new AtomicLong();

    @GetMapping("/hello")
    public String getHelloMessage() {
        return "Hello Spring Boot world!";
    }

    @PostMapping("/pledges")
    @ResponseStatus(HttpStatus.CREATED)
    public Pledge createNewPledge(@RequestBody Pledge pledge) {
        // Set pledge to have next ID:
        pledge.setId(nextId.incrementAndGet());

        pledges.add(pledge);
        return pledge;
    }

    @GetMapping("/pledges")
    public List<Pledge> getAllPledges() {
        pledges.add(new Pledge(10, "bert", "yhoyoyoy"));
        pledges.add(new Pledge(11, "bert", "yhoyoyoy"));
        return pledges;
    }


    @GetMapping("/pledges/{id}")
    public Pledge getOnePledge(@PathVariable("id") long pledgeId) {
        for (Pledge pledge : pledges) {
            if (pledge.getId() == pledgeId) {
                return pledge;
            }
        }

        throw new IllegalArgumentException();
    }

    @PostMapping("pledges/{id}")
    public Pledge editOnePledge(
            @PathVariable("id") long pledgeId,
            @RequestBody Pledge newPledge
    ) {
        for (Pledge pledge : pledges) {
            if (pledge.getId() == pledgeId) {
                pledges.remove(pledge);
                newPledge.setId(pledgeId);
                pledges.add(newPledge);
                return pledge;
            }
        }

        throw new IllegalArgumentException();
    }


    // Create Exception Handle
    @ResponseStatus(value = HttpStatus.BAD_REQUEST,
                reason = "Request ID not found.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badIdExceptionHandler() {
        // Nothing to do
    }
}
