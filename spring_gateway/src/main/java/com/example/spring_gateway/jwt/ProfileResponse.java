package com.example.spring_gateway.jwt;

import java.util.Set;

record ProfileResponse(String username, Set<String> roles) {
}
