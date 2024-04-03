package com.sl.blogapp.users.dtos.commondtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private String message;

}
