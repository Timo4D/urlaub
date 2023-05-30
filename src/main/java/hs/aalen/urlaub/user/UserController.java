package hs.aalen.urlaub.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping("/user")
  public List<User> getUserList() {
    return userService.getUserList();
  }

  @GetMapping("/user/{id}")
  public User getUser(@PathVariable long id) {
    return userService.getUser(id);
  }

  @PostMapping("/user")
  public void addUser(@RequestBody User user) {
    userService.addUser(user);
  }

  @PutMapping("/user/{id}")
  public void updateUser(@PathVariable long id, @RequestBody User user) {
    userService.updateUser(id, user);
  }

  @DeleteMapping("/user/{id}")
  public void deleteUser(@PathVariable long id) {
    userService.deleteUser(id);
  }
}
