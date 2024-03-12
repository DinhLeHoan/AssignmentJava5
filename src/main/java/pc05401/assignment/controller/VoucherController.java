package pc05401.assignment.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pc05401.assignment.entity.Ingredient;
import pc05401.assignment.entity.Staff;
import pc05401.assignment.entity.Voucher;
import pc05401.assignment.model.StaffModel;
import pc05401.assignment.repository.StaffRepository;
import pc05401.assignment.repository.VoucherRepository;
import pc05401.assignment.service.SessionService;

@Controller
public class VoucherController {

	String error = "";

	@Autowired
	SessionService session;

	@Autowired
	StaffRepository staffRepository;

	@Autowired
	private VoucherRepository voucherRepository;

	@GetMapping("voucherManager")
	public String showVoucherManager(Model model) {
		StaffModel account = session.get("staff");
		if (account == null) {
			return "redirect:/login";
		}
		if (account.getRole().equals("USER") || account.getRole().equals("CASHIER")) {
			return "redirect:/home";
		}

		List<Voucher> listItem = voucherRepository.findByActiveTrue();
		System.out.println(listItem);

		model.addAttribute("listItem", listItem);

		return "voucherManager";
	}

	@GetMapping("voucherAdd")
	public String showVoucherAdd() {
		return "voucherAdd";
	}

	@PostMapping("voucherAdd")
	public String addVoucherAdd(@RequestParam("name") String name, @RequestParam("discount") double discount,
			@RequestParam("percentage") double percentage,
			@RequestParam("createDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date createDate,
			@RequestParam("expiresAt") @DateTimeFormat(pattern = "yyyy-MM-dd") Date expiresAt,
			@RequestParam("description") String description, Model model) {

		Voucher voucher = new Voucher();
		voucher.setName(name);
		voucher.setDiscount(discount);
		voucher.setPercentage(percentage);
		;
		voucher.setCreateDate(createDate);
		voucher.setExpiresAt(expiresAt);
		voucher.setDescription(description);
		voucher.setActive(true);
		switch (validateVoucher(voucher)) {
		case 0:
			model.addAttribute("error", "Vui lòng nhập đầy đủ thông tin !");
			return "voucherAdd";
		case 1:
			model.addAttribute("error", "Tên phải ít hơn 250 ký tự !");
			return "voucherAdd";
		case 2:
			model.addAttribute("error", "Mô tả phải ít hơn 1000 ký tự !");
			return "voucherAdd";
		case 3:
			model.addAttribute("error", "Giảm giá phải lớn hơn 0 !");
			return "voucherAdd";
		case 4:
			model.addAttribute("error", "Phần trăm giảm giá phải từ 0 - 100!");
			return "voucherAdd";
		case 5:
			model.addAttribute("error", "Ngày tạo phải nhỏ hơn ngày hết hạn!");
			return "voucherAdd";
		default:		
			voucherRepository.save(voucher);
			return "redirect:/voucherManager";
		}
	}

	@GetMapping("voucherUpdate")
	public String showVoucherUpdate(@RequestParam("voucherId") Integer voucherId, Model model) {
		Optional<Voucher> voucherOptional = voucherRepository.findById(voucherId);
		if (voucherOptional.isPresent()) {
			Voucher voucherEdit = voucherOptional.get();
			model.addAttribute("voucherEdit", voucherEdit);
			model.addAttribute("error", error) ;
			error = "" ;
		}
		return "voucherUpdate";
	}

	@PostMapping("voucherUpdate")
	public String submitVoucherUpdate(@RequestParam("voucherId") int voucherId, @RequestParam("name") String name,
			@RequestParam("discount") double discount, @RequestParam("percentage") double percentage,
			@RequestParam("createDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date createDate,
			@RequestParam("expiresAt") @DateTimeFormat(pattern = "yyyy-MM-dd") Date expiresAt,
			@RequestParam("description") String description, Model model) {

		Voucher voucherEdit = voucherRepository.findById(voucherId).orElseThrow();

		voucherEdit.setName(name);
		voucherEdit.setDiscount(discount);
		voucherEdit.setPercentage(percentage);
		;
		voucherEdit.setCreateDate(createDate);
		voucherEdit.setExpiresAt(expiresAt);
		voucherEdit.setDescription(description);
		voucherEdit.setActive(true);
		switch (validateVoucher(voucherEdit)) {
		case 0:
			error = "Vui lòng nhập đầy đủ thông tin !";
			return "redirect:/voucherUpdate?voucherId=" + voucherEdit.getVoucherId();
		case 1:
			error = "Tên phải ít hơn 250 ký tự !";
			return "redirect:/voucherUpdate?voucherId=" + voucherEdit.getVoucherId();
		case 2:
			error = "Mô tả phải ít hơn 1000 ký tự !";
			return "redirect:/voucherUpdate?voucherId=" + voucherEdit.getVoucherId();
		case 3:
			error = "Giảm giá phải lớn hơn 0 !";
			return "redirect:/voucherUpdate?voucherId=" + voucherEdit.getVoucherId();
		case 4:
			error = "Phần trăm giảm giá phải từ 0 - 100!" ;
			return "redirect:/voucherUpdate?voucherId=" + voucherEdit.getVoucherId();
		case 5:
			error = "Ngày tạo phải nhỏ hơn ngày hết hạn!" ;
			return "redirect:/voucherUpdate?voucherId=" + voucherEdit.getVoucherId();
		default:		
			voucherRepository.save(voucherEdit);
			return "redirect:/voucherManager";
		}
		
	}

	@GetMapping("/deleteVoucher")
	public String deleteStaff(@RequestParam("voucherId") Integer voucherId) {
		Voucher voucherDel = voucherRepository.findById(voucherId).orElseThrow();
		voucherDel.setActive(false);
		voucherRepository.save(voucherDel);
		return "redirect:/voucherManager";
	}

	private int validateVoucher(Voucher voucher) {
		if (voucher.getName().length() == 0 || voucher.getDescription().length() == 0) {
			return 0;
		}
		if (voucher.getName().length() > 250) {
			return 1;
		}
		if (voucher.getDescription().length() > 1000) {
			return 2;
		}
		if (voucher.getDiscount() < 0) {
			return 3;
		}
		if (voucher.getPercentage() < 0 || voucher.getPercentage() > 100) {
			return 4;
		}
		if (voucher.getCreateDate().after(voucher.getExpiresAt())) {
			return 5;
		}
		return -1;
	}

}
