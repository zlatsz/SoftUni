package store.service;

import org.junit.Test;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import store.model.entity.Role;
import store.model.service.RoleServiceModel;
import store.repository.RoleRepository;
import store.service.impl.RoleServiceImpl;
import static org.junit.Assert.assertEquals;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RoleServiceTests {

    @InjectMocks
    RoleServiceImpl service;
    @Mock
    RoleRepository roleRepository;
    @Mock
    ModelMapper modelMapper;

    @Test
    public void seedRoles_WhenNoRoles_ShouldSeed(){
        Role role = new Role("test");
        Mockito.when(roleRepository.count())
                .thenReturn(0L);
        service.seedRolesInDb();
        assertEquals(0, roleRepository.findAll().size());
    }

    @Test
    public void findAll_WhenOneROle_ShouldReturn1(){
        Role role = new Role("test");
        List<Role> roles = List.of(role);
       Mockito.when(roleRepository.findAll())
               .thenReturn(roles);
       int result = service.findAllRoles().size();
       assertEquals(1, result);

    }

    @Test
    public void findByAuthority_WhenOneRoleExist_ShouldWork(){
        RoleServiceModel roleService = new RoleServiceModel();
        Role role = new Role("test");
        Mockito.when(roleRepository.findByAuthority("test"))
                .thenReturn(role);
        Mockito.when(modelMapper.map(role, RoleServiceModel.class))
                .thenReturn(roleService);
        RoleServiceModel result = service.findByAuthority("test");
        assertEquals(roleService, result);

    }
}
