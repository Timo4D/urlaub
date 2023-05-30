package hs.aalen.urlaub.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> getUserList() {
    ArrayList<User> userList = new ArrayList<>();
    Iterator<User> iterator = userRepository.findAll().iterator();
    while (iterator.hasNext()) userList.add(iterator.next());
    return userList;
  }

  public User getUser(long id) {
    return userRepository.findById(id).orElse(null);
  }

  public void addUser(User user) {
    userRepository.save(user);
  }

  public void updateUser(long id, User user) {
    userRepository.save(user);
  }

  public void deleteUser(long id) {
    userRepository.deleteById(id);
  }
}
