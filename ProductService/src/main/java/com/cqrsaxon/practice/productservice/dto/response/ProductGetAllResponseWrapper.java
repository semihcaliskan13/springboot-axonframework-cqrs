package com.cqrsaxon.practice.productservice.dto.response;

import java.util.List;

public record ProductGetAllResponseWrapper(List<ProductGetAllResponse> products) {
}
