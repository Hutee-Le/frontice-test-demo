package vn.edu.likelion.front_ice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChallengerResponse {
    String accountId;
    String levelId;
    int isPremium ;
    String urlGithub;
}
