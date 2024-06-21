package ua.nure.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.nure.userservice.exception.InfoNotFoundException;
import ua.nure.userservice.model.Info;
import ua.nure.userservice.model.InfoDetails;
import ua.nure.userservice.model.dto.InfoDetailsDTO;
import ua.nure.userservice.service.impl.repository.InfoDetailsRepository;
import ua.nure.userservice.service.IInfoService;
import ua.nure.userservice.service.impl.repository.InfoRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InfoService implements IInfoService {

    private final InfoDetailsRepository infoDetailsRepository;
    private final InfoRepository infoRepository;

    @Override
    public Info findInfoById(UUID infoId) {
        return infoRepository.findById(infoId).orElseThrow(
                ()->{
                    log.info("Info not found with id {}", infoId);
                    return new InfoNotFoundException("Info not found with id " + infoId);
                }
        );

    }

    @Override
    public List<Info> findAllInfo() {
        return infoRepository.findAll();
    }

    @Override
    public List<InfoDetails> findInfoDetailsByUserId(UUID userId) {
        return infoDetailsRepository.findByIdUserId(userId);
    }

    @Override
    public List<InfoDetails> findAllInfoDetails() {
        return infoDetailsRepository.findAll();
    }
}
