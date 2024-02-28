package pc05401.assignment.controller;

import java.util.ArrayList;
import java.util.Date;
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

import pc05401.assignment.model.StaffModel;

import jakarta.transaction.Transactional;
import pc05401.assignment.entity.Bill;
import pc05401.assignment.entity.BillDetail;
import pc05401.assignment.entity.Product;
import pc05401.assignment.entity.TagProduct;
import pc05401.assignment.entity.Voucher;
import pc05401.assignment.entity.VoucherDetail;
import pc05401.assignment.model.ProductCountBill;
import pc05401.assignment.repository.BillDetailRepository;

import pc05401.assignment.repository.BillRepository;
import pc05401.assignment.repository.ProductRepository;
import pc05401.assignment.repository.TagProductRepository;
import pc05401.assignment.repository.VoucherDetailRepository;
import pc05401.assignment.repository.VoucherRepository;
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
	@Autowired
	private VoucherRepository voucherRepository;
	@Autowired
	private VoucherDetailRepository voucherDetailRepository;

	@GetMapping("bill/view/{billId}")
	public String viewBill(@PathVariable int billId, Model model) {
		StaffModel account = session.get("staff");
		if (account == null) {
			return "redirect:/login";
		}

		model.addAttribute("staff", session.get("staff"));

		// Information about the bill
		List<BillDetail> billInfors = billDetailRepository.findByBill_BillId(billId);
		if (!billInfors.isEmpty()) {
			model.addAttribute("billInfors", billInfors.get(0));
		}

		model.addAttribute("billId", billId);

		// Fill product
		List<Object[]> productCounts = billDetailRepository.findProductCountsByBillId(billId);
		List<ProductCountBill> productCountBills = productCounts.stream()
				.map(arr -> new ProductCountBill((Product) arr[0], (Long) arr[1])).collect(Collectors.toList());

		model.addAttribute("productCounts", productCountBills);

		// Retrieve all vouchers and add them to the model
		List<Voucher> allVouchers = voucherRepository.findAll();
		allVouchers.removeIf(voucher -> !voucher.isActive());

		allVouchers.removeIf(voucher -> voucher.getExpiresAt().before(new Date()));
		model.addAttribute("allVouchers", allVouchers);

		// default fill voucher
		VoucherDetail voucherDetail = voucherDetailRepository.findByBillId(billId);
		model.addAttribute("voucherSelected", voucherDetail);

		return "invoiceDetail";
	}

	@PostMapping("bill/selectVoucher/{billId}")
	@Transactional
	public String selectVoucher(@PathVariable int billId,
			@RequestParam(name = "selectedVoucher", required = false) Integer selectedVoucherId) {
		if (selectedVoucherId != null) {
			// Check if a special value is selected to indicate no voucher
			if (selectedVoucherId == -1) {
				Bill bill = billRepository.findById(billId).orElse(null);

				voucherDetailRepository.deleteByBill(bill);

				// Set totalWithVoucher to the original total
				if (bill != null) {
					bill.setTotalWithVoucher(bill.getTotal());
					billRepository.save(bill);
				}
			} else {
				// Voucher is selected
				Bill bill = billRepository.findById(billId).orElse(null);
				Voucher selectedVoucher = voucherRepository.findById(selectedVoucherId).orElse(null);

				if (bill != null && selectedVoucher != null) {
					double totalWithVoucher;

					if (selectedVoucher.getDiscount() == 0) {
						// Percentage-based discount
						totalWithVoucher = bill.getTotal() * (selectedVoucher.getPercentage() / 100.0);
					} else {
						// Fixed amount discount
						totalWithVoucher = bill.getTotal() - selectedVoucher.getDiscount();
					}

					// Check if a VoucherDetail already exists for the bill
					VoucherDetail existingVoucherDetail = voucherDetailRepository.findByBillId(billId);

					if (existingVoucherDetail != null) {
						// Update the existing VoucherDetail
						existingVoucherDetail.setVoucher(selectedVoucher);
						voucherDetailRepository.save(existingVoucherDetail);
					} else {
						// Create a new VoucherDetail
						VoucherDetail newVoucherDetail = new VoucherDetail();
						newVoucherDetail.setBill(bill);
						newVoucherDetail.setVoucher(selectedVoucher);
						voucherDetailRepository.save(newVoucherDetail);
					}

					bill.setTotalWithVoucher(totalWithVoucher);
					billRepository.save(bill);
				}
			}
		}

		return "redirect:/bill/view/" + billId;
	}

	@GetMapping("bill/menu/{billId}/{tagId}")
	public String viewMenu(@PathVariable int billId, @PathVariable int tagId, Model model) {
		model.addAttribute("staff", session.get("staff"));

		List<TagProduct> tagList = tagRepository.findByActiveTrue();
		model.addAttribute("tagList", tagList);
		model.addAttribute("billId", billId);

		if (tagId == 0) {
			List<Product> productList = productRepository.findActiveProductsWithActiveTag();
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
		Bill bill = billRepository.findById(billId).orElse(null);

		List<BillDetail> billDetails = new ArrayList<>();

		for (int i = 0; i < amount; i++) {
			BillDetail billDetail = new BillDetail();
			billDetail.setBill(bill);
			billDetail.setProduct(productRepository.findById(productId).orElse(null));
			billDetails.add(billDetail);
		}

		// Save all bill details in a single batch
		billDetailRepository.saveAll(billDetails);

		// Calculate the total price for all added products
		double totalPrice = billDetails.stream().mapToDouble(detail -> detail.getProduct().getPrice()).sum();

		// Update the total and totalWithVoucher in a single transaction
		billRepository.updateTotalByAddingPrice(billId, totalPrice);
		billRepository.updateTotalWithVoucherByAddingPrice(billId, totalPrice);

		return "redirect:/bill/menu/" + billId + "/0";
	}

	@Transactional
	@PostMapping("/minusProduct/{billId}/{productId}/{amount}")
	public String minusProduct(@PathVariable int billId, @PathVariable int productId, @PathVariable int amount) {
		Bill bill = billRepository.findById(billId).orElse(null);

		List<BillDetail> detailsToDelete = new ArrayList<>();
		double totalSubtractedPrice = 0;

		for (int i = 0; i < amount; i++) {
			BillDetail detailDel = billDetailRepository.findFirstByBill_BillIdAndProduct_ProductId(billId, productId);
			detailsToDelete.add(detailDel);

			double productPrice = productRepository.findById(productId).orElse(null).getPrice();
			totalSubtractedPrice += productPrice;
			billDetailRepository.delete(detailDel);
		}

		// Update the total before updating the totalWithVoucher
		billRepository.updateTotalBySubtractingPrice(billId, totalSubtractedPrice);

		// Update the totalWithVoucher in a single transaction after updating the total
		billRepository.updateTotalWithVoucherBySubtractingPrice(billId, totalSubtractedPrice);

		return "redirect:/bill/view/" + billId;
	}

	@PostMapping("/plusProduct/{billId}/{productId}")
	public String plusProduct(@PathVariable int billId, @PathVariable int productId) {
		BillDetail billDetail = new BillDetail();
		billDetail.setBill(billRepository.findById(billId).orElse(null));
		System.out.println("Bill id: " + billId);
		billDetail.setProduct(productRepository.findById(productId).orElse(null));
		System.out.println("Product id: " + productId);

		billDetailRepository.save(billDetail);

		double productPrice = billDetail.getProduct().getPrice();
		billRepository.updateTotalByAddingPrice(billId, productPrice);
		billRepository.updateTotalWithVoucherByAddingPrice(billId, productPrice);
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

		billDetailRepository.deleteByBill(billRepository.findById(billId).orElse(null));

		// Delete associated voucherDetails
		voucherDetailRepository.deleteByBill(billRepository.findById(billId).orElse(null));

		// Delete the bill
		billRepository.deleteById(billId);

		return "redirect:/home";
	}

}
