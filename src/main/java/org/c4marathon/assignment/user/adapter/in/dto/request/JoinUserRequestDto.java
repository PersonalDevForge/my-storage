package org.c4marathon.assignment.user.adapter.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinUserRequestDto {

    private final String name;

    private final String email;

    private final String password;

}
