package com.example.protected_resource.controllers.responses;

import java.util.ArrayList;

public record ClientFromTokenResponse(
    String clientId,
    ArrayList scope
) {}
