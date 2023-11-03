package com.skylabng.jaizexpress.klikpay.models;

public record PayloadItem(
        String title,
        int quantity,
        double total
) {
}
