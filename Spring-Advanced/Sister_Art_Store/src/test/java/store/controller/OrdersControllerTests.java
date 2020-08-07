package store.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;
import store.model.entity.Order;
import store.model.view.OrderViewModel;
import store.repository.OrderRepository;
import store.web.controllers.OrdersController;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters=false)
public class OrdersControllerTests {
    @Autowired
    OrdersController controller;

    @Mock
    Principal principal;

    @MockBean
    OrderRepository mockOrderRepository;
    private ArrayList<Order> orders;
    private ArrayList<Order> allOrders;

    @Before
    public void setupTest(){
        orders = new ArrayList<>();
        allOrders = new ArrayList<>();

        when(mockOrderRepository.findAllOrdersByCustomer_UsernameOrderByFinishedOn(any()))
                .thenReturn(orders);

        when(mockOrderRepository.findAll((Example<Order>) any()))
                .thenReturn(allOrders);
    }

    @Test
    @WithMockUser
    public void getCustomerOrders_whenCustomerHasNoOrders() {
        orders.clear();
        ModelAndView modelAndView = new ModelAndView();
        when(principal.getName())
                .thenReturn("");

        ModelAndView result = controller.getMyOrders(modelAndView, principal);

        List<OrderViewModel> viewModels = (List<OrderViewModel>) result.getModel().get("orders");
        assertTrue(viewModels.isEmpty());
    }

    @Test
    @WithMockUser
    public void getCustomerOrders_whenAllOrdersAreForCustomer() {
        orders.addAll(List.of(
                new Order()
        ));

        ModelAndView modelAndView = new ModelAndView();
        when(principal.getName())
                .thenReturn("");

        ModelAndView result = controller.getMyOrders(modelAndView, principal);

        List<OrderViewModel> viewModels = (List<OrderViewModel>) result.getModel().get("orders");
        assertEquals(orders.size(), viewModels.size());

    }
}
