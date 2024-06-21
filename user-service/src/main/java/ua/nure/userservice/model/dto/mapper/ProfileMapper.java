package ua.nure.userservice.model.dto.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ua.nure.userservice.model.*;
import ua.nure.userservice.model.dto.*;
import ua.nure.userservice.util.InfoMapperUtil;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = { InfoMapperUtil.class } )
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    @Mapping(target = "id", source = "profileId")
    @Mapping(target = "music.id", source = "music.musicId")
    @Mapping(target = "degree.id", source = "degree.degreeId")
    @Mapping(target = "info",
            qualifiedByName = {"InfoMapperUtil", "getInfoDTOListFromInfoDetailsList"},
            source = "infoDetails")
    ProfileDTO profileToProfileDTO(Profile profile);

    @Mapping(target = "id", source = "tag.tagId")
    TagDTO tagToTagDTO(Tag tag);

    @Mapping(target = "id", source = "languageId")
    LanguageDTO languageToLanguageDTO(Language language);

    @Mapping(target = "id", source = "hobbyId")
    HobbyDTO hobbyToHobbyDTO(Hobby hobby);

    @Mapping(target = "id", source = "pictureId")
    PictureDTO pictureToPictureDTO(Picture picture);

    @Mapping(target = "profileId", source = "id")
    @Mapping(target = "degree.degreeId", source = "degree.id")
    @Mapping(target = "music.musicId", source = "music.id")
    @Mapping(target = "infoDetails",
            qualifiedByName = {"InfoMapperUtil", "getInfoDetailsFromInfoDTOList"},
            source = "info"
    )
    Profile profileDTOToProfile(ProfileDTO profileDTO);

    List<ProfileDTO> profileListToProfileDTOList(List<Profile> profiles);

    @Mapping(target = "tagId", source = "id")
    Tag tagDTOToTag(TagDTO tagDTO);

    @Mapping(target = "languageId", source = "id")
    Language languageDTOToLanguage(LanguageDTO language);

    @Mapping(target = "hobbyId", source = "id")
    Hobby hobbyDTOToHobby(HobbyDTO hobby);

    @Mapping(target = "pictureId", source = "id")
    Picture pictureDTOToPicture(PictureDTO picture);

    @AfterMapping
    default void setInfoDetailsId(@MappingTarget Profile.ProfileBuilder profileBuilder, ProfileDTO profileDTO) {
        Profile profile = profileBuilder.build();

        for (InfoDetails id : profile.getInfoDetails()) {
            id.getInfoDetailsId().setUserId(profile.getProfileId());
        }

        profileBuilder.infoDetails(profile.getInfoDetails());
    }

}
