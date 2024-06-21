package ua.nure.userservice.model.dto.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.nure.userservice.model.*;
import ua.nure.userservice.model.dto.ProfileDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.offset;

@Slf4j
@SpringBootTest
public class ProfileMapperTest {

    @Autowired
    private ProfileMapper mapper;

    @Test
    void profileToProfileDTO() throws JsonProcessingException {
        // Given
        UUID profileId = UUID.randomUUID();
        String bio = "Sample bio";
        Gender gender = Gender.MALE;
        String firstName = "Sample first name";
        String lastName = "Sample last name";
        Music music = Music.builder()
                .musicId(UUID.randomUUID())
                .title("Sample title")
                .artistName("Sample artist")
                .spotifyId(null)
                .build();
        GeometryFactory geometryFactory = new GeometryFactory();
        Point lastLocation = geometryFactory.createPoint(new Coordinate(42.0, 32.0));
        int age = 20;
        int height = 180;
        String occupation = "Sample occupation";
        String nationality = "Sample nationality";
        Language language = Language.builder()
                .languageId(UUID.randomUUID())
                .languageName("Sample language name")
                .build();
        Hobby hobby = Hobby.builder()
                .hobbyId(UUID.randomUUID())
                .hobbyName("Sample hobby")
                .build();
        Degree degree = Degree.builder()
                .degreeId(UUID.randomUUID())
                .degreeName("Sample degree name")
                .schoolName("Sample school name")
                .degreeType("Bachelor")
                .build();

        Picture picture = Picture.builder()
                .pictureId(UUID.randomUUID())
                .timeAdded(new Date(System.currentTimeMillis()))
                .position(1)
                .pictureUrl("https://sample-picture-qqwert-y213")
                .build();

        Tag tag = Tag.builder()
                .tagId(UUID.randomUUID())
                .tagName("Sample tag")
                .build();


        List<Answer> answers = new ArrayList<>();
        Answer answer1 = Answer.builder()
                .answerId(UUID.randomUUID())
                .answer("Sample Answer 1")
                .build();

        answers.add(answer1);

        Info info = Info.builder()
                .infoId(UUID.randomUUID())
                .title("Sample title")
                .answers(answers)
                .build();

        InfoDetails infoDetails = InfoDetails.builder()
                .infoDetailsId(profileId)
                .info(
                        info
                )
                .answer(answer1)
                .build();

        Profile profile = Profile.builder()
                .profileId(profileId)
                .firstname(firstName)
                .lastname(lastName)
                .bio(bio)
                .gender(gender)
//                .lastLocation(lastLocation)
                .age(age)
                .occupation(occupation)
                .nationality(nationality)
                .languages(List.of(language))
                .music(music)
                .hobbies(List.of(hobby))
                .degree(degree)
                .tags(List.of(tag))
                .pictures(List.of(picture))
                .height(height)
                .infoDetails(List.of(infoDetails))
                .build();

        // When
        ProfileDTO dto = mapper.profileToProfileDTO(profile);
        Profile profile1 = mapper.profileDTOToProfile(dto);
        // Then

        List<Picture> pictures= profile1.getPictures();
        for (int i = 0; i < pictures.size(); i++) {
            pictures.get(i).setTimeAdded(profile.getPictures().get(i).getTimeAdded());
        }




        ObjectMapper objectMapper = new ObjectMapper();


            // Convert the DTO object to a JSON string
            String jsonDTO = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
            String jsonProfile = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(profile);
            String jsonProfile1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(profile1);

//            log.info(jsonDTO);

            assertThat(jsonProfile1).isEqualTo(jsonProfile);


    }
}