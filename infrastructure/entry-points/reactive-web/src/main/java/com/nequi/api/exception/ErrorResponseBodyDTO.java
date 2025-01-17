package com.nequi.api.exception;

import lombok.Builder;

@Builder
public record ErrorResponseBodyDTO(String message){
}
