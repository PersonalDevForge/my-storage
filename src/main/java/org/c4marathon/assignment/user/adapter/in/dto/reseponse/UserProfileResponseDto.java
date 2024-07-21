package org.c4marathon.assignment.user.adapter.in.dto.reseponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponseDto {

    private final String email;

    private final String name;

}
