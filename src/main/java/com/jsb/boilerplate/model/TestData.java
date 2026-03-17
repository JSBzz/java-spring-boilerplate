package com.jsb.boilerplate.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TestData extends BaseEntity {
    private Long id;
    private String title;
    private String content;
    private String delYn;
}
