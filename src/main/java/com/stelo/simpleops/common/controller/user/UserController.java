package com.stelo.simpleops.common.controller.user;

import com.github.pagehelper.PageInfo;
import com.stelo.simpleops.common.domain.User;
import com.stelo.simpleops.common.dto.UpdatePasswordDto;
import com.stelo.simpleops.common.security.service.AuthorizationManager;
import com.stelo.simpleops.common.security.utils.SessionHelper;
import com.stelo.simpleops.common.service.UserService;
import com.stelo.simpleops.constants.ApiPrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = ApiPrefix.APIV1)
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthorizationManager authorizationManager;

    @PostMapping("/private/users/register")
    public ResponseEntity register(@RequestBody @Valid User user) {
        return userService.registerUser(user) != null ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/private/users/infos/{userId}")
    public ResponseEntity<User> getCurrentUserInfo(@PathVariable("userId") long userId) {
        return Optional.ofNullable(userService.queryUserById(SessionHelper.getCurrentLoginUserId()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/private/users")
    public ResponseEntity<PageInfo<User>> getPageUsers(@RequestParam("page") int page, @RequestParam("rows") int rows) {
        return Optional.ofNullable(userService.pageQueryUser(page, rows))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping("/private/users/{userId}")
    public ResponseEntity deleteUserById(@PathVariable("userId") long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/private/users/{userId}")
    public ResponseEntity updateUserNickName(@PathVariable("userId") long userId,
                                                @RequestBody User user) {
        if (!SessionHelper.getCurrentLoginUserId().equals(userId)) {
            throw new AuthorizationServiceException("user.update.no-permission");
        }
        userService.updateUserNickNameById(userId, user.getNickname());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/private/users/{userId}/password")
    public ResponseEntity updateUserPassword(@PathVariable("userId") long userId,
                                                @RequestBody @Valid UpdatePasswordDto updatePasswordDto) {
        User user = userService.queryUserByIdNotNull(userId);
        if (!passwordEncoder.matches(updatePasswordDto.getRawPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().build();
        }
        userService.updatePasswordById(userId,updatePasswordDto.getNewPassword());
        authorizationManager.logout(userId, null);
        return ResponseEntity.noContent().build();
    }
}
