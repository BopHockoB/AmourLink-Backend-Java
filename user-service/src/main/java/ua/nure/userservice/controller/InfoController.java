package ua.nure.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.userservice.model.dto.mapper.InfoMapper;

import ua.nure.userservice.responce.ResponseBody;
import ua.nure.userservice.service.IInfoService;

import java.util.UUID;

@RestController
@RequestMapping("api/user-service/info")
@RequiredArgsConstructor
public class InfoController {

    private final IInfoService infoService;
    private final InfoMapper infoMapper;

    @GetMapping("/{infoId}")
    public ResponseEntity<ResponseBody> findInfoById(@PathVariable UUID infoId) {
        ResponseBody responseBody = new ResponseBody(
                infoMapper.infoToInfoDTO(infoService.findInfoById(infoId))
        );
        return new ResponseEntity<>(responseBody, HttpStatus.FOUND);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ResponseBody> findAllInfo() {
        ResponseBody responseBody = new ResponseBody(
                infoMapper.infoListToInfoDTOList(infoService.findAllInfo())
        );
        return new ResponseEntity<>(responseBody, HttpStatus.FOUND);
    }

    @GetMapping("/details/{userId}")
    public ResponseEntity<ResponseBody> findInfoDetailsByUserId(@PathVariable UUID userId) {
        ResponseBody responseBody = new ResponseBody(
                infoMapper.infoDetailsListToInfoDetailsDTOList(infoService.findInfoDetailsByUserId(userId))
        );
        return new ResponseEntity<>(responseBody, HttpStatus.FOUND);
    }

    @GetMapping("/details/get-all")
    public ResponseEntity<ResponseBody> findAllInfoDetails() {
        ResponseBody responseBody = new ResponseBody(
                infoMapper.infoDetailsListToInfoDetailsDTOList(infoService.findAllInfoDetails())
        );
        return new ResponseEntity<>(responseBody, HttpStatus.FOUND);
    }

}
