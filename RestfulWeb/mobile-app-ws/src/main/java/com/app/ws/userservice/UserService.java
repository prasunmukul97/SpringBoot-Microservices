package com.app.ws.userservice;

import com.app.ws.ui.model.request.UserDetailsRequestModel;
import com.app.ws.ui.model.response.UserRest;

public interface UserService {
	UserRest createUser(UserDetailsRequestModel userDetails);

}
