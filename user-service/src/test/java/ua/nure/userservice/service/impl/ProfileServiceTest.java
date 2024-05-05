package ua.nure.userservice.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ua.nure.userservice.client.MediaServiceClient;
import ua.nure.userservice.exception.ProfileAlreadyExistsException;
import ua.nure.userservice.model.Profile;
import ua.nure.userservice.model.User;
import ua.nure.userservice.repository.ProfileRepository;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private UserService userService;
    @Mock
    private MediaServiceClient mediaServiceClient;

    @InjectMocks
    private ProfileService profileService;

    private Profile profile;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userId(UUID.randomUUID())
                .email("user@example.com")
                .password("password123")
                .build();

        profile = Profile.builder()
                .user(user)
                .firstname("John")
                .lastname("Doe")
                .age(30)
                .build();
    }

    @Test
    void createProfile_Success() {
        when(profileRepository.findByUserUserId(any(UUID.class))).thenReturn(Optional.empty());
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);

        Profile createdProfile = profileService.createProfile(profile);

        assertNotNull(createdProfile);
        assertEquals(profile, createdProfile);
        verify(profileRepository).save(profile);
    }

    @Test
    void createProfile_Failure_ProfileAlreadyExists() {
        when(profileRepository.findByUserUserId(any(UUID.class))).thenReturn(Optional.of(profile));

        Exception exception = assertThrows(ProfileAlreadyExistsException.class, () -> {
            profileService.createProfile(profile);
        });

        assertEquals("A user's " + profile.getUser().getUserId() + " profile already exists", exception.getMessage());
    }
}
