package api.controllers;


import api.services.generic.AbstractAccountService;
import api.controllers.generic.ApplicationController;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"https://tp314rates.herokuapp.com", "https://project-motion.herokuapp.com",
        "http://localhost:3000", "*", "http://127.0.0.1:3000"})
@RestController
@RequestMapping(path = "/game")
public class GameController extends ApplicationController {

    public GameController(@NotNull AbstractAccountService accountService,
                          @NotNull ApplicationContext appContext) {
        super(accountService, appContext);
    }


    /*
        ToDo: Add real scores, not the fake ones
     */
    @PostMapping("/scores")
    public ResponseEntity<?> scores(HttpSession session) {

        final String[] names = { "lol", "kek", "cheburek", "Valentin", "VaNilKA", "top", "kokos",
                                 "Pachome", "Sobaka", "Gennadiy", "Alconafter", "EvaPWNZ", "BanePWNZ"};

        final HashMap<String, Integer> scores = new HashMap<>();
        for (String name: names) {
            scores.put(name, ThreadLocalRandom.current().nextInt(0, 2000));
        }

        final Map<String, Integer> sorted = scores.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        final List<Object> list = new ArrayList<>();

        for (String key: sorted.keySet()) {
            list.add(new Object() {
                public final String login = key;
                public final int score = sorted.get(key);
            });
        }

        return ResponseEntity.ok(list);
    }
}
