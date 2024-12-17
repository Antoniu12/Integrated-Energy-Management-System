package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.repositories.PersonRepository;

import javax.naming.NameNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private PersonRepository personRepository;

    @Autowired
    public CustomUserDetailsService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Person person = personRepository.findByName(name);
        if (person == null) {
            throw new UsernameNotFoundException("User not found with name: " + name);
        }

        return new User(
                person.getName(),
                person.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(person.getRole()))
        );
    }

}
