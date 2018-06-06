package spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.dtos.UserFromRegisterDto;
import spring.entities.User;
import spring.repositories.UserRepository;
import spring.utility.MapperConverter;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final MapperConverter mapperConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MapperConverter mapperConverter) {
        this.userRepository = userRepository;
        this.mapperConverter = mapperConverter;
    }

    @Override
    public List<UserFromRegisterDto> findAll() {
        List<User> users = this.userRepository.findAll();
        List<UserFromRegisterDto> userFromRegisterDtos = new ArrayList<>();
        for (User user : users) {
            UserFromRegisterDto userFromRegisterDto = this.mapperConverter.convert(user, UserFromRegisterDto.class);
            userFromRegisterDtos.add(userFromRegisterDto);
        }
        return userFromRegisterDtos;
    }

    @Override
    public User findById(String id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public User createOne(UserFromRegisterDto userFromRegisterDto) {
        User user = this.mapperConverter.convert(userFromRegisterDto, User.class);
        return this.userRepository.save(user);
    }

    @Override
    public List<User> createMany(Iterable<User> users) {
        return this.userRepository.saveAll(users);
    }

    @Override
    public User updateOne(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public List<User> updateMany(Iterable<User> users) {
        return this.userRepository.saveAll(users);
    }

    @Override
    @Modifying
    public void deleteById(String id) {
        this.userRepository.deleteById(id);
    }

    @Override
    @Modifying
    public void deleteByUser(User user) {
        this.userRepository.delete(user);
    }

}
