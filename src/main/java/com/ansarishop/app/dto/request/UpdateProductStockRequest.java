package com.ansarishop.app.dto.request;

import lombok.Data;

@Data
public class UpdateProductStockRequest {

    private long id;
    private long quantity;
}
