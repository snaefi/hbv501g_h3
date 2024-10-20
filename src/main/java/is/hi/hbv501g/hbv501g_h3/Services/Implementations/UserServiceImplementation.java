package is.hi.hbv501g.hbv501g_h3.Services.Implementations;

import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import jakarta.transaction.Transactional;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	private UserRepository userRepository;


    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

	@Override
	@Transactional
	public User patchUser(Long id, Map<String, Object> updates) {
		Optional<User> existingUser = userRepository.findById(id);
	
		if (existingUser.isEmpty()) {
			throw new NoSuchElementException("User with id " + id + " not found");
		}
	
		User user = existingUser.get();
	
		// Check if the existing user has invalid data
		if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
			throw new IllegalStateException("User data is invalid: required fields are null");
		}
	
		// Apply only the updates provided; leave other fields unchanged
		updates.forEach((key, value) -> {
			switch (key) {
				case "username":
					if (value instanceof String && !((String) value).isBlank()) {
						user.setUsername((String) value);
					} else {
						throw new IllegalArgumentException("Username cannot be blank");
					}
					break;
				case "password":
					if (value instanceof String && !((String) value).isBlank()) {
						user.setPassword((String) value);
					} else {
						throw new IllegalArgumentException("Password cannot be blank");
					}
					break;
				case "email":
					if (value instanceof String && !((String) value).isBlank()) {
						user.setEmail((String) value);
					} else {
						throw new IllegalArgumentException("Email cannot be blank");
					}
					break;
				case "favoritePatternsIds":
					if (value instanceof List) {
						user.setFavoritePatternsIds((List<Long>) value);
					}
					break;
				case "sharedWithQueue":
					if (value instanceof List) {
						user.setSharedWithQueue((List<String>) value);
					}
					break;
				// Add more fields as necessary, following the same pattern
				default:
					throw new IllegalArgumentException("Invalid field or value type: " + key);
			}
		});
	
		// Save the updated user
		return userRepository.save(user);
	}

    public void delete(Long id) {
		Optional<User> existingUser = userRepository.findById(id);
		if (existingUser.isEmpty()) {
			throw new NoSuchElementException("User with id " + id + " not found");
		}
		User user = existingUser.get();
        userRepository.delete(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 5);
		}
        return userRepository.findAll(pageable);
    }
	
	@Override
	public Page<User> searchUsers(String searchTerm, Pageable pageable) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 5);
		}
		if (searchTerm == null) {
			searchTerm = "";
		}
		return userRepository.findByUsernameContainingIgnoreCase(searchTerm, pageable);
	}


    @Override
    public User findById(Long ID){
        //User user = userRepository.findById(ID).get();
        return userRepository.findById(ID).orElseThrow(() -> new NoSuchElementException("User with id=" + ID + " not found"));
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public User login(User user){
        User doesExist = findByUsername(user.getUsername());
        if(doesExist != null){
            if(doesExist.getPassword().equals(user.getPassword())){
                return doesExist;
            }
            return null;
        }
        return null;
    }

}
