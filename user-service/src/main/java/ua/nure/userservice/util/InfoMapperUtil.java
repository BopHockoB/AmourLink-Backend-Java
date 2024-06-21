package ua.nure.userservice.util;

import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ua.nure.userservice.model.Answer;
import ua.nure.userservice.model.Info;
import ua.nure.userservice.model.InfoDetails;
import ua.nure.userservice.model.dto.AnswerDTO;
import ua.nure.userservice.model.dto.InfoDTO;

import java.util.ArrayList;
import java.util.List;

@Named("InfoMapperUtil")
@Component
@AllArgsConstructor
public class InfoMapperUtil {

    @Named("getInfoDTOFromInfoDetails")
    public InfoDTO getInfoDTOFromInfoDetails(InfoDetails infoDetails) {
        if (infoDetails == null)
            return null;

        return InfoDTO.builder()
                .id(infoDetails.getInfo().getInfoId())
                .title(infoDetails.getInfo().getTitle())
                .answers(List.of(getAnswerDTOFromInfoDetails(infoDetails)))
                .build();
    }

    @Named("getAnswerDTOFromInfoDetails")
    private AnswerDTO getAnswerDTOFromInfoDetails(InfoDetails infoDetails) {
        if (infoDetails == null)
            return null;

        return AnswerDTO.builder()
                .id(infoDetails.getAnswer().getAnswerId())
                .answer(infoDetails.getAnswer().getAnswer())
                .build();

    }

    @Named("getInfoDTOListFromInfoDetailsList")
    public List<InfoDTO> getInfoDTOListFromInfoDetailsList(List<InfoDetails> infoDetailsList) {
        if (infoDetailsList == null)
            return null;

        List<InfoDTO> infoList = new ArrayList<>();

        infoDetailsList.forEach(infoDetails -> {
            infoList.add(getInfoDTOFromInfoDetails(infoDetails));
        });

        return infoList;
    }

    @Named("getInfoDetailsFromInfoDTOList")
    public List<InfoDetails> getInfoDetailsFromInfoDTOList(List<InfoDTO> infoDTOList) {
        if (infoDTOList == null)
            return null;

        List<InfoDetails> infoDetailsList = new ArrayList<>();

        infoDTOList.forEach(infoDTO -> {
            infoDetailsList.add(getInfoDetailsFromInfoDTO(infoDTO));
        });

        return infoDetailsList;
    }

    @Named("getInfoDetailsFromInfoDTO")
    private InfoDetails getInfoDetailsFromInfoDTO(InfoDTO infoDTO) {

        if (infoDTO == null)
            return null;

        return InfoDetails.builder()
                .info(Info.builder()
                        .infoId(infoDTO.getId())
                        .title(infoDTO.getTitle())
                        .answers(List.of(getAnswerFromInfoDTO(infoDTO)))
                        .build())
                .answer(getAnswerFromInfoDTO(infoDTO))
                .build();

    }

    @Named("getAnswerFromInfoDTO")
    private Answer getAnswerFromInfoDTO(InfoDTO infoDTO) {
        if (infoDTO == null)
            return null;

        return Answer.builder()
                .answerId(infoDTO.getAnswers().get(0).getId())
                .answer(infoDTO.getAnswers().get(0).getAnswer())
                .build();
    }
}
