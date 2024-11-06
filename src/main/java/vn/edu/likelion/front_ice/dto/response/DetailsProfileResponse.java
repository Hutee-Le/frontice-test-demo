package vn.edu.likelion.front_ice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetailsProfileResponse {

    RegisterResponse registerResponse;

    ChallengerResponse challengerResponse;

    RecruiterResponse recruiterResponse;

    StaffResponse staffResponse;

}
