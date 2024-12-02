package api.service;
import api.dto.UserDTO;
import persistence.dao.UserDAO;
import persistence.entity.User;

public class UserService {
    private final static UserDAO userDAO=new UserDAO();
    public UserDTO create (UserDTO userDTO){
        userDAO.create(toEntity(userDTO));
        User user=userDAO.read(userDTO.getName()).orElse(null);
        return toDto(user);
    }
    public UserDTO readById(String ID){
        return toDto(userDAO.read(ID).orElse(null));
    }
    public UserDTO readByPasswordId(String ID,String password){
        return toDto(userDAO.readByUserPassword(ID,password).orElse(null));
    }
    public UserDTO update(UserDTO userDTO){
        userDAO.update(toEntity(userDTO));
        User user=userDAO.read(userDTO.getName()).orElse(null);
        return toDto(user);
    }
    public void delete(String Id){
        userDAO.delete(Id);
    }
    private static UserDTO toDto(User user) {
        if(user==null) return null;
        return new UserDTO(user.getName(),user.getPassword(),user.getRoles());
    }
    private static User toEntity(UserDTO dto) {
        User entity = new User();
        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setRoles(dto.getRoles());
        return entity;
    }
}
