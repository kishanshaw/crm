package com.onetoone.mapping;

import com.onetoone.mapping.entities.Address;
import com.onetoone.mapping.entities.User;
import com.onetoone.mapping.repositories.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MappingApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class MappingApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenUsers_whenGetUser_thenStatus201()
            throws Exception {

        User user = createTestUser("bob", "street", "pinCode");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/" + user.getId())
                        .header("traceId", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("name", CoreMatchers.is("bob")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.street", CoreMatchers.is("street")));

    }

    private User createTestUser(String name, String street, String pinCode) {
        User user = User.builder().name(name).build();
        Address address = Address.builder().pinCode(pinCode).street(street).build();
        user.setAddress(address);
        address.setUser(user);
        return userRepository.save(user);
    }
}
