package ua.nure.userservice.service;

import ua.nure.userservice.model.Info;
import ua.nure.userservice.model.InfoDetails;

import java.util.List;
import java.util.UUID;

public interface IInfoService {

    Info findInfoById(UUID infoId);
    List<Info> findAllInfo();

    List<InfoDetails> findInfoDetailsByUserId(UUID userId);
    List<InfoDetails> findAllInfoDetails();
}
