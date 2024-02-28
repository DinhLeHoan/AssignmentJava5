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

	String error = "";

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
		model.addAttribute("listItem", listItem);
		model.addAttribute("tagList", tagProductRepository.findAll());
		return checkAdmin("productManager");
	}

	@GetMapping("productUpdate")
	public String showProductUpdate(@RequestParam("productId") Integer productId, Model model) {
		model.addAttribute("tagList", tagProductRepository.findAll());

		Product productEdit = productRepository.findById(productId).orElseThrow();
		
		model.addAttribute("productEdit", productEdit);
		model.addAttribute("error", error);
		error = "";
		return checkAdmin("productUpdate");
	}

	@GetMapping("productAdd")
	public String showProductAdd(Model model) {
		model.addAttribute("tagList", tagProductRepository.findByActiveTrue());
		model.addAttribute("error", error);
		error = "";
		return checkAdmin("productAdd");
	}

	@PostMapping("productUpdate")
	public String updateProduct(@RequestParam("productId") Integer productId, @RequestParam("file") MultipartFile file,
			@RequestParam("name") String productName,

			@RequestParam("note") String note, @RequestParam("price") Double price, @RequestParam("tag") TagProduct tag,
			@RequestParam("active") boolean active, Model model) {
		switch (validateProduct(file, note, price)) {
		case 1:
			error = "Vui lòng điền đầy đủ thông tin !";
			return checkAdmin("redirect:/productUpdate?productId=" + productId);
		case 2:
			error = "Giá phải tử 0 - 1000000000 !";
			return checkAdmin("redirect:/productUpdate?productId=" + productId);
		case 3:
			error = "Ghi chú phải ít hơn 1000 ký tự !";
			return checkAdmin("redirect:/productUpdate?productId=" + productId);
		default:
			try {
				Product productEdit = productRepository.findById(productId).orElseThrow();
				String fileName = UUID.randomUUID().toString().split("-")[0] + file.getOriginalFilename();

				if (file.getSize() != 0) {

					client.blobName(productEdit.getImage()).buildClient().delete();

					System.out.println("filename: " + fileName);

					client.blobName(fileName).buildClient().upload(file.getInputStream(), file.getSize());

					productEdit.setImage(fileName);
				}

				productEdit.setName(productName);
				productEdit.setNote(note);
				productEdit.setPrice(price);
				productEdit.setTag(tag);
				productEdit.setActive(active);
				productRepository.save(productEdit);
				model.addAttribute("message", "File uploaded successfully!");
			} catch (IOException e) {
				model.addAttribute("message", "Failed to upload file: " + e.getMessage());
			}
			return checkAdmin("redirect:/productManager");
		}
	}

	@PostMapping("productAdd")
	public String addProductAdd(@RequestParam("file") MultipartFile file, @RequestParam("name") String productName,

			@RequestParam("note") String note, @RequestParam("price") Double price, @RequestParam("tag") TagProduct tag,
			Model model) {

		// Handle file upload
		switch (validateProduct(file, note, price)) {
		case 0:
			error = "Vui lòng chọn ảnh !";
			return checkAdmin("redirect:/productAdd");
		case 1:
			error = "Vui lòng điền đầy đủ thông tin !";
			return checkAdmin("redirect:/productAdd");
		case 2:
			error = "Giá phải tử 0 - 1000000000 !";
			return checkAdmin("redirect:/productAdd");
		case 3:
			error = "Ghi chú phải ít hơn 1000 ký tự !";
			return checkAdmin("redirect:/productAdd");
		default:
			try {
				String fileName = UUID.randomUUID().toString().split("-")[0] + file.getOriginalFilename();
				client.blobName(fileName).buildClient().upload(file.getInputStream(), file.getSize());
				Product product = new Product();
				product.setImage(fileName);
				product.setName(productName);
				product.setNote(note);
				product.setActive(true);
				product.setPrice(price);
				product.setTag(tag);
				productRepository.save(product);
				model.addAttribute("message", "File uploaded successfully!");

			} catch (IOException e) {
				model.addAttribute("message", "Failed to upload file: " + e.getMessage());
			}
			return checkAdmin("redirect:/productAdd");
		}
	}

	@GetMapping("tagProductUpdate")
	public String showTagProductUpdate(@RequestParam("tagId") Integer tagId, Model model) {
		TagProduct tagProduct = tagProductRepository.findById(tagId).orElseThrow();
		model.addAttribute("tagProduct", tagProduct);
		model.addAttribute("error", error);
		error = "";
		return checkAdmin("tagProductUpdate");
	}

	@PostMapping("tagProductUpdate")
	public String updateTagProduct(@RequestParam("tagId") Integer tagId, @RequestParam("name") String name,
			@RequestParam("active") boolean active, Model model) {
		TagProduct tagProduct = tagProductRepository.findById(tagId).orElseThrow();
		tagProduct.setName(name);
		tagProduct.setActive(active);
		if (name.length() == 0 || name.length() > 250) {
			error = "Tên loại sản phẩm phải từ 1 - 250 ký tự";
			return "redirect:/tagProductUpdate?tagId=" + tagId;
		}
		tagProductRepository.save(tagProduct);
		return "redirect:/productManager";
	}

	@GetMapping("tagProductAdd")
	public String showTagProductAdd(Model model) {

		return checkAdmin("tagProductAdd");
	}

	@PostMapping("tagProductAdd")
	public String tagProductAdd(@RequestParam("name") String name, Model model) {
		if (name.length() == 0 || name.length() > 250) {
			model.addAttribute("error", "Tên loại sản phẩm phải từ 1 - 250 ký tự");
			return checkAdmin("tagProductAdd");
		}
		TagProduct tagProduct = new TagProduct();
		tagProduct.setName(name);
		tagProduct.setActive(true);
		tagProductRepository.save(tagProduct);
		return checkAdmin("tagProductAdd");
	}

	@GetMapping("deleteProduct")
	public String deleteProduct(@RequestParam("productId") Integer productId) {

		Product productDel = productRepository.findById(productId).orElseThrow();

		productDel.setActive(false);
		productRepository.save(productDel);

		return "redirect:/productManager";
	}

	@GetMapping("tagProductDelete")
	public String deleteTagProduct(@RequestParam("tagId") Integer tagId) {

		TagProduct tagDel = tagProductRepository.findById(tagId).orElseThrow();

		tagDel.setActive(false);
		tagProductRepository.save(tagDel);

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

	private int validateProduct(MultipartFile file, String note, Double price) {
		if (file.getSize() == 0) {
			return 0;
		}
		if (note.length() == 0) {
			return 1;
		}
		if (price < 0 || price > 1000000000) {
			return 2;
		}
		if (note.length() > 1000) {
			return 3;
		}
		return -1;
	}

}
