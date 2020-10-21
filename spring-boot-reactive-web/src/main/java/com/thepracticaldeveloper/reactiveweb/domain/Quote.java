package com.thepracticaldeveloper.reactiveweb.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public final class Quote {

    private String id;
    private String book;
    private String content;

    // Empty constructor is required by the data layer and JSON de/ser
}
