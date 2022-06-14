package com.github.enid3.questionnaire.database;

import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.data.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@AutoConfigureTestDatabase
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"
})
public class FieldRepositoryTest {
    @Autowired private FieldsRepository fieldsRepository;
    @Autowired private ResponseRepository responseRepository;

    private static final long EXISTING_FIELD_ID_TO_DELETE = 18L;

    // fixme fix test
    /*
    @Test
    void findAllByUserEmailShouldReturnNotNull() {
        List<Field> fields = fieldsRepository.findAllByOwnerEmailAndIsActive("john@doe.com", true);
        assertNotNull(fields);
        assertNotEquals(0, fields.size());
    }

     Response findResponseThatIncludeField(long id) {
       List<Response> responses = responseRepository.findAll();
       Optional<Response> responseWithThatField = responses.stream()
               .filter((response) -> response.getResponses().keySet().stream()
                       .anyMatch((field) -> field.getId() == id))
               .findAny();

       return responseWithThatField.orElse(null);
    }

     */

    // fixme fix test
    /*
    @Test
    void deleteFieldByIdShouldBeCascade() {
        int deleted = fieldsRepository.deleteFieldById(EXISTING_FIELD_ID_TO_DELETE);

        fieldsRepository.flush();
        assertEquals(1, deleted);
        assertTrue(fieldsRepository.findById(EXISTING_FIELD_ID_TO_DELETE).isEmpty());
        assertNull(findResponseThatIncludeField(EXISTING_FIELD_ID_TO_DELETE));

    }
     */
}
