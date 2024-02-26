package pc05401.assignment.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClientBuilder;

import pc05401.assignment.entity.Ingredient;
import pc05401.assignment.entity.Product;
import pc05401.assignment.entity.TagProduct;
import pc05401.assignment.model.StaffModel;
import pc05401.assignment.repository.IngredientRepository;
import pc05401.assignment.repository.ProductRepository;
import pc05401.assignment.repository.TagProductRepository;
import pc05401.assignment.service.SessionService;

@Controller
public class ProductController {

	@Autowired
	SessionService session;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private TagProductRepository tagProductRepository;

	@Autowired
	BlobClientBuilder client;

	@GetMapping("productManager")
	public String showProductManager(Model model) {
		List<Product> listItem = productRepository.findAll();
		System.out.println(listItem);
		model.addAttribute("listItem", listItem);
		return checkAdmin("productManager");
	}

	@GetMapping("productUpdate")
	public String showProductUpdate() {
		return checkAdmin("productUpdate");
	}

	@GetMapping("productAdd")
	public String showProductAdd(Model model) {
		model.addAttribute("tagList", tagProductRepository.findAll()) ;
		return checkAdmin("productAdd");
	}
	
	@PostMapping("productAdd")
	public String addProductAdd(@RequestParam("file") MultipartFile file,
	                             @RequestParam("name") String productName,
	       
	                             @RequestParam("note") String note,
	                             @RequestParam("price") Double price,
	                             @RequestParam("tag") TagProduct tag,
	                             Model model) {

	    // Handle file upload
	    try {
	        String fileName = UUID.randomUUID().toString().split("-")[0] + file.getOriginalFilename();
	        client.blobName(fileName).buildClient().upload(file.getInputStream(), file.getSize());
	        Product product = new Product();
			product.setImage(fileName);
			product.setName(productName);
			product.setNote(note);
			product.setPrice(price);
			product.setTag(tag);
			productRepository.save(product);
	        model.addAttribute("message", "File uploaded successfully!");
	    } catch (IOException e) {
	        model.addAttribute("message", "Failed to upload file: " + e.getMessage());
	    }  

	    return checkAdmin("productAdd");
	}

	@GetMapping("tagProductUpdate")
	public String showTagProductUpdate() {
		return checkAdmin("tagProductUpdate");
	}

	@GetMapping("tagProductAdd")
	public String showTagProductAdd() {
		return checkAdmin("tagProductAdd");
	}
	
	@GetMapping("deleteProduct/{productId}")
	public String deleteProduct(@PathVariable int productId) {
		return "redirect:/productManager";
	}

	public String checkAdmin(String path) {
		StaffModel account = session.get("staff");
		if (account == null) {
			return "redirect:/login";
		}
		if (account.getRole().equals("USER") || account.getRole().equals("CASHIER")) {
			return "redirect:/home";
		}
		return path;
	}

	
}
