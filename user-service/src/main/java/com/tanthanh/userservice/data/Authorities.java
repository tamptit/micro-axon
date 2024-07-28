package com.tanthanh.userservice.data;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "authorities",
        uniqueConstraints = {
        @UniqueConstraint(name = "ix_auth_username", columnNames = {"username", "authority"})
})
public class Authorities {
    private String username;
    private String authority;
}
