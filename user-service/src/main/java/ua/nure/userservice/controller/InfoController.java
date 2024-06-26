package ua.nure.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.userservice.model.dto.mapper.InfoMapper;

import ua.nure.userservice.resolver.UserId;
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
    public ResponseEntity<ResponseBody> findInfoById(@PathVariable("infoId") UUID infoId) {
        ResponseBody responseBody = new ResponseBody(
                infoMapper.infoToInfoDTO(infoService.findInfoById(infoId))
        );
       return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<ResponseBody> findAllInfo() {
        ResponseBody responseBody = new ResponseBody(
                infoMapper.infoListToInfoDTOList(infoService.findAllInfo())
        );
       return ResponseEntity.ok(responseBody);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or " +
            "{#userId == #userIdToCheck and hasAnyRole('ROLE_USER', 'ROLE_PREMIUM_USER')}")
    @GetMapping("/details/{userId}")
    public ResponseEntity<ResponseBody> findInfoDetailsByUserId(@PathVariable("userId") UUID userId, @UserId UUID userIdToCheck) {
        ResponseBody responseBody = new ResponseBody(
                infoMapper.infoDetailsListToInfoDetailsDTOList(infoService.findInfoDetailsByUserId(userId))
        );
       return ResponseEntity.ok(responseBody);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/details/get-all")
    public ResponseEntity<ResponseBody> findAllInfoDetails() {
        ResponseBody responseBody = new ResponseBody(
                infoMapper.infoDetailsListToInfoDetailsDTOList(infoService.findAllInfoDetails())
        );
       return ResponseEntity.ok(responseBody);
    }

}
