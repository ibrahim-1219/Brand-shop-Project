package com.global.shop.configuration;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.global.shop.entity.Cart;
import com.global.shop.entity.CartItem;
import com.global.shop.entity.Customer;
import com.global.shop.entity.Inventory;
import com.global.shop.entity.Product;
import com.global.shop.entity.Role;
import com.global.shop.service.CartItemService;
import com.global.shop.service.CartService;
import com.global.shop.service.CustomerService;
import com.global.shop.service.InventoryService;
import com.global.shop.service.ProductService;
import com.global.shop.service.RoleService;

import lombok.val;

@Component
public class AppStartup implements CommandLineRunner{
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		
		if(productService.findAll().isEmpty())
		{
		
		//add some products
		Product product1=new Product();
		product1.setName("rice");
		product1.setPrice(28.5);
		product1.setDescription("white rice");	
		
		Product product2=new Product();
		product2.setName("meet");
		product2.setPrice(250);
		product2.setDescription("red meet");
		
		Product product3=new Product();
		product3.setName("oil");
		product3.setPrice(22.25);
		product3.setDescription("pure oil");
		
		Product product4 = new Product();
		product4.setName("sugar");
		product4.setPrice(29.5);
		product4.setDescription("diet sugar");

		Product product5 = new Product();
		product5.setName("tea");
		product5.setPrice(25);
		product5.setDescription("Elarosa tea");

		Product product6 = new Product();
		product6.setName("water");
		product6.setPrice(10);
		product6.setDescription("Mineral water");
		
		Product product7 = new Product();
		product7.setName("biscuits");
		product7.setPrice(40);
		product7.setDescription("digestive ");

		Product product8 = new Product();
		product8.setName("cheese ");
		product8.setPrice(30);
		product8.setDescription("Mozzarella");

		Product product9 = new Product();
		product9.setName("Luncheon");
		product9.setPrice(32);
		product9.setDescription("Corned Beef");
		
		Product product10 = new Product();
		product10.setName("pasta");
		product10.setPrice(12);
		product10.setDescription("Spaghetti");
		
		
		
		productService.insertAll(Arrays.asList(product1,product2,product3,product4,product5,product6,product7,product8,product9,product10));
		
	}
		if(inventoryService.findAll().isEmpty())
		{
			Inventory inventory1 = new Inventory();
			inventory1.setQuantity(10);
			inventory1.setProduct(productService.findById(1));
			inventoryService.insert(inventory1);
			
			Inventory inventory2 = new Inventory();
			inventory2.setQuantity(20);
			inventory2.setProduct(productService.findById(2));
			inventoryService.insert(inventory2);
			
			Inventory inventory3 = new Inventory();
			inventory3.setQuantity(30);
			inventory3.setProduct(productService.findById(3));
			inventoryService.insert(inventory3);
			
			Inventory inventory4 = new Inventory();
			inventory4.setQuantity(40);
			inventory4.setProduct(productService.findById(4));
			inventoryService.insert(inventory4);
			
			Inventory inventory5 = new Inventory();
			inventory5.setQuantity(50);
			inventory5.setProduct(productService.findById(5));
			inventoryService.insert(inventory5);
			
			Inventory inventory6 = new Inventory();
			inventory6.setQuantity(60);
			inventory6.setProduct(productService.findById(6));
			inventoryService.insert(inventory6);
			
			Inventory inventory7 = new Inventory();
			inventory7.setQuantity(70);
			inventory7.setProduct(productService.findById(7));
			inventoryService.insert(inventory7);
			
			Inventory inventory8 = new Inventory();
			inventory8.setQuantity(80);
			inventory8.setProduct(productService.findById(8));
			inventoryService.insert(inventory8);
			
			Inventory inventory9 = new Inventory();
			inventory9.setQuantity(90);
			inventory9.setProduct(productService.findById(9));
			inventoryService.insert(inventory9);
			
			Inventory inventory10 = new Inventory();
			inventory10.setQuantity(100);
			inventory10.setProduct(productService.findById(10));
			inventoryService.insert(inventory10);
	    }
		
		if(roleService.findAll().isEmpty())
		{
			Role role=new Role();
			role.setName("ADMIN_ROLE");
			
			Role role1=new Role();
			role1.setName("USER_ROLE");
			
			roleService.insertAll(Arrays.asList(role,role1));
			
			
		}
         
		if(customerService.findAll().isEmpty())
		{
			Role roleAdmin = roleService.getAdminRole();
			Role role = roleService.getUserRole();
			
		//add some users
		Customer user1=new Customer();
		user1.setName("hema");
		user1.setEmail("hema@yahoo.com");;
		user1.setPassword(passwordEncoder.encode("hema"));	
		user1.setActive(true);
		user1.addRole(roleAdmin);
		user1.addRole(role);

		
		Customer user2=new Customer();
		user2.setName("amr");
		user2.setEmail("amr@yahoo.com");;
		user2.setPassword(passwordEncoder.encode("mora"));
		user2.setActive(true);
		user2.addRole(role);
		
		Customer user3=new Customer();
		user3.setName("mohamed");
		user3.setEmail("mohamed@yahoo.com");
		user3.setPassword(passwordEncoder.encode("medo"));
		user3.setActive(true);
		user3.addRole(role);
		
		Customer user4=new Customer();
		user4.setName("ahmed");
		user4.setEmail("ahmed@yahoo.com");
		user4.setPassword(passwordEncoder.encode("ahmed"));
		user4.setActive(true);
		user4.addRole(role);
		
		Customer user5=new Customer();
		user5.setName("mostafa");
		user5.setEmail("mostafa@yahoo.com");
		user5.setPassword(passwordEncoder.encode("mostafa"));
		user5.setActive(true);
		user5.addRole(role);
		
		Customer user6=new Customer();
		user6.setName("ali");
		user6.setEmail("ali@yahoo.com");
		user6.setPassword(passwordEncoder.encode("ali"));
		user6.setActive(true);
		user6.addRole(role);
		
		Customer user7=new Customer();
		user7.setName("amir");
		user7.setEmail("amir@yahoo.com");
		user7.setPassword(passwordEncoder.encode("amir"));
		user7.setActive(true);
		user7.addRole(role);
		
		Customer user8=new Customer();
		user8.setName("omar");
		user8.setEmail("omar@yahoo.com");
		user8.setPassword(passwordEncoder.encode("omar"));
		user8.setActive(true);
		user8.addRole(role);
		
		Customer user9=new Customer();
		user9.setName("saad");
		user9.setEmail("saad@yahoo.com");
		user9.setPassword(passwordEncoder.encode("saad"));
		user9.setActive(true);
		user9.addRole(role);
		
		Customer user10=new Customer();
		user10.setName("khaled");
		user10.setEmail("khaled@yahoo.com");
		user10.setPassword(passwordEncoder.encode("khaled"));
		user10.setActive(true);
		user10.addRole(role);
		
		customerService.insertAll(Arrays.asList(user1,user2,user3,user4,user5,user6,user7,user8,user9,user10));
		
	}
		
		
		if(cartService.findAll().isEmpty())
		{
			Cart cart1 = new Cart();
			Cart cart2 = new Cart();
			Cart cart3 = new Cart();
			Cart cart4 = new Cart();
			Cart cart5 = new Cart();
			Cart cart6 = new Cart();
			Cart cart7 = new Cart();
			Cart cart8 = new Cart();
			Cart cart9 = new Cart();
			Cart cart10 = new Cart();
			
			cartService.insertAll(Arrays.asList(cart1,cart2,cart3,cart4,cart5,cart6,cart7,cart8,cart9,cart10));
		  
		}
		
		if(cartItemService.findAll().isEmpty())
		{
			CartItem cartItem = new CartItem();
			cartItem.setProduct(productService.findById(1));
			cartItem.setQuantity(1);
			cartItem.setCart(cartService.findById(1));
			
			CartItem cartItem2 = new CartItem();
			cartItem2.setProduct(productService.findById(2));
			cartItem2.setQuantity(2);
			cartItem2.setCart(cartService.findById(2));
			
			CartItem cartItem3 = new CartItem();
			cartItem3.setProduct(productService.findById(3));
			cartItem3.setQuantity(3);
			cartItem3.setCart(cartService.findById(3));
			
			CartItem cartItem4 = new CartItem();
			cartItem4.setProduct(productService.findById(4));
			cartItem4.setQuantity(4);
			cartItem4.setCart(cartService.findById(4));
			
			CartItem cartItem5 = new CartItem();
			cartItem5.setProduct(productService.findById(5));
			cartItem5.setQuantity(5);
			cartItem5.setCart(cartService.findById(5));
			
			CartItem cartItem6 = new CartItem();
			cartItem6.setProduct(productService.findById(6));
			cartItem6.setQuantity(6);
			cartItem6.setCart(cartService.findById(6));
			
			CartItem cartItem7 = new CartItem();
			cartItem7.setProduct(productService.findById(7));
			cartItem7.setQuantity(7);
			cartItem7.setCart(cartService.findById(7));
			
			
			CartItem cartItem8 = new CartItem();
			cartItem8.setProduct(productService.findById(8));
			cartItem8.setQuantity(8);
			cartItem8.setCart(cartService.findById(8));
			
			
			CartItem cartItem9 = new CartItem();
			cartItem9.setProduct(productService.findById(9));
			cartItem9.setQuantity(9);
			cartItem9.setCart(cartService.findById(9));
			
			CartItem cartItem10 = new CartItem();
			cartItem10.setProduct(productService.findById(10));
			cartItem10.setQuantity(10);
			cartItem10.setCart(cartService.findById(10));
			
			cartItemService.insertAll(Arrays.asList(cartItem,cartItem2,cartItem3,cartItem4,cartItem5,cartItem6,cartItem7,cartItem8,cartItem9,cartItem10));
			
			
			
			
		}
		

}
}
