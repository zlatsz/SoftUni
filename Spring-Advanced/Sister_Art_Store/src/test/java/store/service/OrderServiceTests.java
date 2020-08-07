package store.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import store.model.entity.Order;
import store.model.entity.OrderProduct;
import store.model.entity.User;
import store.model.service.OrderServiceModel;
import store.model.service.UserServiceModel;
import store.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTests {
  @Mock
  ModelMapper modelMapper;

  @Autowired
  OrderService orderService;

  @MockBean
  OrderRepository mockOrderRepository;

  private OrderServiceModel orderServiceModel;

  private Order order;

  private List<Order> orderList;

  @Before
  public void setup(){
    this.orderServiceModel = new OrderServiceModel(){{
      setId("1");
      setTotalPrice(BigDecimal.valueOf(25));
      setFinishedOn(LocalDateTime.of(2019, 2, 2, 22, 22, 22));
      setCustomer(new UserServiceModel());
      setProducts(new HashSet<>());
    }};

    this.order = new Order(){{
      setId(orderServiceModel.getId());
      setTotalPrice(orderServiceModel.getTotalPrice());
      setFinishedOn(orderServiceModel.getFinishedOn());
      setCustomer(modelMapper.map(orderServiceModel.getCustomer(),User.class));
      List<OrderProduct> collect = orderServiceModel.getProducts().stream().map(o -> modelMapper.map(o, OrderProduct.class)).collect(Collectors.toList());
      setProducts(collect);
    }};

    this.orderList = new ArrayList<>(){{
      add(order); add(order);
    }};

    when(mockOrderRepository.findAll())
            .thenReturn(this.orderList);
  }

  @Test(expected = NullPointerException.class)
  public void saveOrder_whenOrderNull_ShouldReturnFalse(){
    boolean isSaved = this.orderService.createOrder(null);
    assertFalse(isSaved);
  }

  @Test
  public void saveOrder_whenOrderValid_ShouldReturnTrue(){
    boolean isSaved = this.orderService.createOrder(orderServiceModel);
    assertTrue(isSaved);
  }

  @Test
  public void findAllOrders_whenListEmpty_ShouldReturnEmptyList(){
    this.orderList.clear();
    List<OrderServiceModel> orders = this.orderService.findAllOrders();
    assertTrue(orders.isEmpty());
  }

  @Test
  public void findAllOrders_whenListHasOrders_ShouldReturnCorrectSize(){
    List<OrderServiceModel> orders = this.orderService.findAllOrders();
    assertEquals(2, orders.size());
  }

}

// @Autowired
//    CartService cartService;
//
//    @MockBean
//    CartRepository mockCartRepository;
//
//    @MockBean
//    UserRepository mockUserRepository;
//
//    private CartServiceModel cartServiceModel;
//
//    private Cart cart;
//
//    private UserServiceModel userServiceModel;
//
//    private User user;
//
//    @Before
//    public void setup(){
//        this.cartServiceModel = new CartServiceModel(){{
//            setId("test id");
//            setProducts(new LinkedHashMap<>());
//            setUser(new User());
//            setTotalPrice(BigDecimal.TEN);
//        }};
//
//        this.cart = new Cart(){{
//            setId(cartServiceModel.getId());
//            setProducts(cartServiceModel.getProducts());
//            setUser(cartServiceModel.getUser());
//            setTotalPrice(cartServiceModel.getTotalPrice());
//        }};
//
//        this.userServiceModel = new UserServiceModel(){{
//            setId("1");
//            setAddress("Test address");
//            setEmail("test@email.com");
//            setCart(new Cart(){{
//                setId("cartId");
//                setProducts(new LinkedHashMap<>());
//            }});
//            setFirstName("test");
//            setLastName("testName");
//            setGender("male");
//            setPassword("123");
//            setPhoneNumber("+359888123123");
//            setRole("Guest");
//            setTown("Test town");
//        }};
//
//        this.user =  new User(){{
//            setId(userServiceModel.getId());
//            setAddress(userServiceModel.getAddress());
//            setEmail(userServiceModel.getEmail());
//            setCart(userServiceModel.getCart());
//            setFirstName(userServiceModel.getFirstName());
//            setLastName(userServiceModel.getLastName());
//            setGender(userServiceModel.getGender());
//            setPassword(DigestUtils.sha256Hex(userServiceModel.getPassword()));
//            setPhoneNumber(userServiceModel.getPhoneNumber());
//            setRole(Role.Guest);
//            setTown(userServiceModel.getTown());
//        }};
//
//        when(mockCartRepository.getCountOfProductsInCart(cart.getId()))
//                .thenReturn(cart.getProducts().size());
//
//        when(mockUserRepository.findById(userServiceModel.getId()))
//                .thenReturn(java.util.Optional.ofNullable(user));
//
//        when(mockCartRepository.findById(cartServiceModel.getId()))
//                .thenReturn(java.util.Optional.ofNullable(cart));
//    }
//
//    @Test
//    public void saveCart_whenCartNull_ShouldReturnFalse(){
//        boolean isSaved = this.cartService.saveCart(null);
//        assertFalse(isSaved);
//    }
//
//    @Test
//    public void saveCart_whenCartValid_ShouldReturnTrue(){
//        boolean isSaved = this.cartService.saveCart(cartServiceModel);
//        assertTrue(isSaved);
//    }
//
//    @Test
//    public void deleteCartProduct_whenProductsInCartNotZero_ShouldRemoveUserCart(){
//        this.cartService.deleteCartProduct("test product id", cart.getId(), this.userServiceModel);
//        assertNull(user.getCart());
//    }
//
//    @Test
//    public void deleteCartProduct_whenProductsInCartZero_ShouldDeleteOnlyCartProduct(){
//        cart.setProducts(new LinkedHashMap<>(){{
//            put("test product id", new Product());
//        }});
//        user.getCart().setProducts(new LinkedHashMap<>(){{
//            put("test user product id", new Product());
//        }});
//
//        when(mockCartRepository.getCountOfProductsInCart(cart.getId()))
//                .thenReturn(cart.getProducts().size());
//
//        this.cartService.deleteCartProduct("test product id", cart.getId(), this.userServiceModel);
//        assertNotNull(user.getCart());
//        assertEquals(1, user.getCart().getProducts().size());
//    }
//
//    @Test(expected = Exception.class)
//    public void findCartByID_whenIDIsDifferent_ShouldThrowException(){
//        cartServiceModel.setId("false id");
//        CartServiceModel model = this.cartService.findCartById(cartServiceModel.getId());
//    }
//
//    @Test
//    public void findCartByID_whenIDExists_ShouldReturnCorrectModel(){
//        CartServiceModel model = this.cartService.findCartById(cartServiceModel.getId());
//        assertEquals(model.getId(), cartServiceModel.getId());
//        assertEquals(model.getTotalPrice(), cartServiceModel.getTotalPrice());
//        assertEquals(model.getProducts().size(), cartServiceModel.getProducts().size());
//        assertEquals(model.getUser(), cartServiceModel.getUser());
//    }