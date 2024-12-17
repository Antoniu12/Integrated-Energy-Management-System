package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ro.tuc.ds2020.dtos.PersonDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;
import ro.tuc.ds2020.repositories.PersonRepository;
import ro.tuc.ds2020.security.JwtGenerator;
import ro.tuc.ds2020.services.PersonService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/person")
public class PersonController {

    private final PersonService personService;
    private final RestTemplate restTemplate;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    private JwtGenerator jwtGenerator;

    @Autowired
    public PersonController(PersonService personService,
                            AuthenticationManager authenticationManager,
                            PasswordEncoder passwordEncoder,
                            JwtGenerator jwtGenerator) {
        this.personService = personService;
        this.restTemplate = new RestTemplate();
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @GetMapping(value = "/Persons")
    public ResponseEntity<List<PersonDTO>> getPersons() {
        List<PersonDTO> dtos = personService.findPersons();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<UUID> insertProsumer(@Valid @RequestBody PersonDetailsDTO personDTO) {
        UUID personID = personService.insert(personDTO);
        return new ResponseEntity<>(personID, HttpStatus.CREATED);
    }

//    @PostMapping(value = "/login")
//    public ResponseEntity<Map<String,String>> login(@Valid @RequestBody Map<String,String> loginRequest){
//        String name = loginRequest.get("name");
//        String password = loginRequest.get("password");
//        if (name == null || password == null) {
//            return new ResponseEntity<>(Map.of("error", "Name and password must be provided"), HttpStatus.BAD_REQUEST);
//        }
//        PersonDTO authentificationResponse = personService.login(name, password);
//
//        if (authentificationResponse != null) {
//            System.out.println(authentificationResponse.getRole());
//            return new ResponseEntity<>(Map.of("role", authentificationResponse.getRole(),
//                    "userId", authentificationResponse.getId().toString()),
//                    HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(Map.of("role", "denied",
//                    "userId","denied")
//                    , HttpStatus.OK);
//        }
//    }
@PostMapping(value = "/login")
public ResponseEntity<Object> login(@Valid @RequestBody Map<String, String> loginRequest) {
    String name = loginRequest.get("name");
    String password = loginRequest.get("password");

    if (name == null || password == null) {
        return new ResponseEntity<>(Map.of("error", "Name and password must be provided"), HttpStatus.BAD_REQUEST);
    }

    System.out.println("Login attempt: Name = " + name + ", Password = " + password);

    try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(name, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);
        System.out.println("Authentication successful. Token generated: " + token);

        PersonDTO authentificationResponse = personService.login(name, password);
        System.out.println("PersonService response: " + authentificationResponse);

        if (authentificationResponse != null) {
            return new ResponseEntity<>(
                    Map.of(
                            "role", authentificationResponse.getRole(),
                            "userId", authentificationResponse.getId().toString(),
                            "token", token
                    ),
                    HttpStatus.OK
            );
        } else {
            System.err.println("PersonService returned null for user: " + name);
            return new ResponseEntity<>(
                    Map.of(
                            "role", "denied",
                            "userId", "denied"
                    ),
                    HttpStatus.FORBIDDEN
            );
        }
    } catch (Exception ex) {
        System.err.println("Authentication failed: " + ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(Map.of("error", "Invalid credentials"), HttpStatus.UNAUTHORIZED);
    }
}


    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable("id") UUID personId) {
        PersonDTO dto = personService.findPersonById(personId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<UUID> updatePerson(@PathVariable("id") UUID personID, @Valid @RequestBody PersonDetailsDTO personDTO){
        UUID person = personService.update(personID, personDTO);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable("id") UUID personID){
        String deviceServiceUrl = "http://reverse-proxy/device-api/device/deletedUser/" + personID;
        //String deviceServiceUrl = "http://device.localhost/device/deletedUser/" + personID;

        System.out.println("Device management URL: " + deviceServiceUrl);
        personService.delete(personID);
        try {
            restTemplate.postForLocation(deviceServiceUrl, null);
        } catch (Exception e) {
            System.out.println("Failed to update devices for user: " + personID);
            return new ResponseEntity<>("Person deleted, but failed to update devices", HttpStatus.OK);
        }
        return new ResponseEntity<>("Person successfully deleted", HttpStatus.OK);
    }

}
