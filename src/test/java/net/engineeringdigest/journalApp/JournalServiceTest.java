package net.engineeringdigest.journalApp;

import net.engineeringdigest.journalApp.Repository.UserRepository;
import net.engineeringdigest.journalApp.Services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JournalServiceTest {

    @Autowired
    UserService userService;
    @MockBean
    UserRepository userRepository;

    @Test
    public void viewAllTest(){



    }


}
