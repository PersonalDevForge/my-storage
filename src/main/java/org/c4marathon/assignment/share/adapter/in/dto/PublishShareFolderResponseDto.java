package org.c4marathon.assignment.share.adapter.in.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PublishShareFolderResponseDto {

    private String shareId;

    public static PublishShareFolderResponseDto from(String shareId) {
        return new PublishShareFolderResponseDto(shareId);
    }
}
