package api.services.generic;

import api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public abstract class AbstractService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final UserRepository userRepository;
    protected final PasswordEncoder encoder;


    public AbstractService (UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

}
