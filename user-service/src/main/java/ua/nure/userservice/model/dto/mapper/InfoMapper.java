package ua.nure.userservice.model.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ua.nure.userservice.model.Info;
import ua.nure.userservice.model.InfoDetails;
import ua.nure.userservice.model.dto.InfoDTO;
import ua.nure.userservice.model.dto.InfoDetailsDTO;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InfoMapper {

    @Mapping(target = "id", source = "infoId")
    InfoDTO infoToInfoDTO(Info infoById);
    List<InfoDTO> infoListToInfoDTOList(List<Info> infoList);

    @Mapping(target = "id", source = "infoDetailsId.userId")
    InfoDetailsDTO infoDetailsToInfoDetailsDTO(InfoDetails infoDetails);
    List<InfoDetailsDTO> infoDetailsListToInfoDetailsDTOList(List<InfoDetails> infoDetailsList);

}
