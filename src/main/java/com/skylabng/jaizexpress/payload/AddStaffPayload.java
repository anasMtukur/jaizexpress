package com.skylabng.jaizexpress.payload;

public record AddStaffPayload(
        String firstName,
        String lastName,
        String username,
        String password,
        String role
) {
}
