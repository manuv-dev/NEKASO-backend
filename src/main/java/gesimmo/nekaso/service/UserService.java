package gesimmo.nekaso.service;

import gesimmo.nekaso.dto.AuthRequestDTO;

public interface UserService {
    String register(AuthRequestDTO authRequest);
}
