package ua.nure.userservice.model.dto.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;
import ua.nure.userservice.model.*;

import ua.nure.userservice.model.dto.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    Profile profileDtoToProfile(ProfileDTO profileDTO);
    ProfileDTO profileToProfileDto(Profile profile);

    List<Profile> profileDtoListToProfileList(List<ProfileDTO> profileDTOList);
    List<ProfileDTO> profileListToProfileDtoList(List<Profile> profileList);

    Picture pictureDtoToPicture(PictureDTO pictureDTO);
    PictureDTO pictureToPictureDto(Picture picture);

    List<Picture> pictureDTOListToPictureList(List<PictureDTO> pictureDTOList);
    List<PictureDTO> pictureListToPictureList(List<Picture> pictureList);

    Answer answerDTOToAnswer(AnswerDTO answerDTO);
    AnswerDTO answerToAnswerDTO(Answer answer);

    List<Answer> answerListDTOToAnswerList(List<Answer> answerList);
    List<AnswerDTO> answerListToAnswerDTOList(List<Answer> answerList);

    Degree degreeDTOToDegree(DegreeDTO degreeDTO);
    DegreeDTO degreeToDegreeDTO(Degree degree);

    List<Degree> degreeDTOListToDegreeList(List<Degree> degreeDTOList);
    List<DegreeDTO> degreeListToDegreeDTOList(List<Degree> degreeList);

    Info InfoDTOToInfo(InfoDTO dto);
    InfoDTO InfoToInfoDTO(Info Info);

    List<Info> InfoDTOListToInfoList(List<Info> infoList);
    List<InfoDTO> InfoListToInfoDTOList(List<Info> infoList);

    Tag tagDTOToTag(TagDTO tagDTO);
    TagDTO tagToTagDTO(Tag tag);


    List<Tag> tagDTOListToTagList(List<TagDTO> tagDTOList);
    List<TagDTO> tagListToTagDTOList(List<Tag> tagList);



}
