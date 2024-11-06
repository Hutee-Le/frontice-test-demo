package vn.edu.likelion.front_ice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.front_ice.common.api.ResponseUtil;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;
import vn.edu.likelion.front_ice.common.constants.ApiEndpoints;
import vn.edu.likelion.front_ice.dto.request.FollowRequest;
import vn.edu.likelion.front_ice.service.challenger.ChallengerService;

/**
 * ChallengerController -
 *
 * @param
 * @return
 * @throws
 */
@RestController
@RequestMapping(ApiEndpoints.CHALLENGER_API)
@RequiredArgsConstructor
public class   ChallengerController {

    @Autowired
    ChallengerService challengerService;
    @Autowired
    private ResponseUtil responseUtil;

    @PostMapping(ApiEndpoints.FOLLOW)
    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
    public ResponseEntity<RestAPIResponse<Object>> follow(@RequestBody FollowRequest followRequest) {
        return responseUtil.successResponse(challengerService.follow(followRequest));
    }

    @GetMapping(ApiEndpoints.GET_FOLLOW)
    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
    public ResponseEntity<RestAPIResponse<Object>> follow(@RequestParam String id) {
        return responseUtil.successResponse(challengerService.getFollow(id));
    }

}
