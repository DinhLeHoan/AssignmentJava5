package pc05401.assignment.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.transaction.Transactional;
import pc05401.assignment.entity.Bill;
import pc05401.assignment.entity.BillDetail;
import pc05401.assignment.entity.Product;
import pc05401.assignment.entity.TagProduct;
import pc05401.assignment.model.ProductCountBill;
import pc05401.assignment.repository.BillDetailRepository;
import pc05401.assignment.repository.BillRepository;
import pc05401.assignment.repository.ProductRepository;
import pc05401.assignment.repository.TagProductRepository;
import pc05401.assignment.service.SessionService;

@Controller
public class BillController {
	@Autowired
	SessionService session;

	@Autowired
	private BillRepository billRepository;
	@Autowired
	private BillDetailRepository billDetailRepository;
	@Autowired
	private TagProductRepository tagRepository;
	@Autowired
	private ProductRepository productRepository;

	@GetMapping("bill/view/{billId}")
	public String viewBill(@PathVariable int billId, Model model) {
		model.addAttribute("staff", session.get("staff"));
		// infor bill
		List<BillDetail> billInfors = billDetailRepository.findByBill_BillId(billId);
		if (!billInfors.isEmpty()) {
			model.addAttribute("billInfors", billInfors.get(0));
		}

		model.addAttribute("billId", billId);

		// fill product
		List<Object[]> productCounts = billDetailRepository.findProductCountsByBillId(billId);
		List<ProductCountBill> productCountBills = productCounts.stream()
				.map(arr -> new ProductCountBill((Product) arr[0], (Long) arr[1])).collect(Collectors.toList());

		model.addAttribute("productCounts", productCountBills);

		return "invoiceDetail";
	}

	@GetMapping("bill/menu/{billId}/{tagId}")
	public String viewMenu(@PathVariable int billId, @PathVariable int tagId, Model model) {
		model.addAttribute("staff", session.get("staff"));

		List<TagProduct> tagList = tagRepository.findAll();
		model.addAttribute("tagList", tagList);
		model.addAttribute("billId", billId);

		if (tagId == 0) {
			List<Product> productList = productRepository.findAll();
			model.addAttribute("productList", productList);

		} else {

			List<Product> productList = productRepository.findByTag(tagRepository.findById(tagId).orElse(null));

			List<Object[]> productCounts = billDetailRepository.findProductCountsByBillId(billId);
			List<ProductCountBill> productCountBills = productCounts.stream()
					.map(arr -> new ProductCountBill((Product) arr[0], (Long) arr[1])).collect(Collectors.toList());

			model.addAttribute("productList", productList);

		}

		return "pickInvoice";
	}

	@PostMapping("addProductToBill/{billId}/{productId}")
	public String addProductToBill(@PathVariable int billId, @PathVariable int productId,
			@RequestParam Integer amount) {
		for (int i = 0; i < amount; i++) {
			BillDetail billDetail = new BillDetail();
			billDetail.setBill(billRepository.findById(billId).orElse(null));
			billDetail.setProduct(productRepository.findById(productId).orElse(null));

			billDetailRepository.save(billDetail);
		}
		return "redirect:/bill/menu/" + billId + "/0";
	}

	@PostMapping("/plusProduct/{billId}/{productId}")
	public String plusProduct(@PathVariable int billId, @PathVariable int productId) {
		BillDetail billDetail = new BillDetail();
		billDetail.setBill(billRepository.findById(billId).orElse(null));
		billDetail.setProduct(productRepository.findById(productId).orElse(null));

		billDetailRepository.save(billDetail);
		return "redirect:/bill/view/" + billId;

	}

	@PostMapping("/minusProduct/{billId}/{productId}/{amount}")
	public String plusProduct(@PathVariable int billId, @PathVariable int productId, @PathVariable int amount) {

		for (int i = 0; i < amount; i++) {
			billDetailRepository.deleteByBill_BillIdAndProduct_ProductId(billId, productId);

		}
		return "redirect:/bill/view/" + billId;

	}

	@GetMapping("/home/{billId}")
	public String paid(@PathVariable int billId) {
		billRepository.markBillAsPaid(billId);
		return "redirect:/home";
	}

	@Transactional
	@GetMapping("/home/delete/{billId}")
	public String delete(@PathVariable int billId) {
		billDetailRepository.deleteByBill_BillId(billId);
		billRepository.deleteBillById(billId);
		return "redirect:/home";
	}
}
