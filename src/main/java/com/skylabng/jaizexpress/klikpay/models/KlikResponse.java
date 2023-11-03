package com.skylabng.jaizexpress.klikpay.models;

public record KlikResponse(
        String status,
        String message,
        String trace_id,
        String reference_id,
        String url
) {
    @Override
    public String toString() {
        return "KlikResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", trace_id='" + trace_id + '\'' +
                ", reference_id='" + reference_id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
