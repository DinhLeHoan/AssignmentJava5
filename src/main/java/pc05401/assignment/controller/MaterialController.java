package pc05401.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.transaction.Transactional;
import pc05401.assignment.entity.Ingredient;
import pc05401.assignment.entity.Staff;
import pc05401.assignment.model.StaffModel;
import pc05401.assignment.repository.IngredientRepository;
import pc05401.assignment.service.SessionService;

@Controller
public class MaterialController {

	String regexName = "^[\\p{L} ]+$";
	String error = "" ;

	@Autowired
	SessionService session;

	@Autowired
	private IngredientRepository ingredientRepository;

	@GetMapping("checkMaterials")
	public String checkMaterials(Model model) {
		List<Ingredient> listItem = ingredientRepository.findAll();
		System.out.println(listItem);
		model.addAttribute("listItem", listItem);

		return checkAdmin("checkMaterials");
	}

	@GetMapping("checkMaterials/add/{ingredientId}")
	public String addOneMaterials(@PathVariable int ingredientId, Model model) {
		ingredientRepository.addOneToAmount(ingredientId);

		List<Ingredient> listItem = ingredientRepository.findAll();
		model.addAttribute("listItem", listItem);

		return checkAdmin("checkMaterials");
	}

	@GetMapping("checkMaterials/minus/{ingredientId}")
	public String minusOneMaterials(@PathVariable int ingredientId, Model model) {
		ingredientRepository.subtractOneFromAmount(ingredientId);

		List<Ingredient> listItem = ingredientRepository.findAll();
		model.addAttribute("listItem", listItem);

		return checkAdmin("checkMaterials");
	}

	@GetMapping("materialManager")
	public String materialManager(Model model) {
		List<Ingredient> listItem = ingredientRepository.findAll();
		model.addAttribute("listItem", listItem);
		return checkAdmin("materialManager");
	}

	@GetMapping("materialAdd")
	public String showMaterialAdd() {

		return checkAdmin("materialAdd");
	}

	@GetMapping("deleteMaterial/{ingredientId}")
	public String deleteMaterial(@PathVariable int ingredientId) {
		ingredientRepository.deleteById(ingredientId);
		return checkAdmin("redirect:/materialManager");
	}

	@PostMapping("materialAdd")
	public String addMaterial(@ModelAttribute Ingredient ingredient, Model model) {
		switch (validateIngredient(ingredient)) {
		case 0:
			model.addAttribute("error", "Vui lòng nhập đầy đủ thông tin !");
			return "materialAdd";
		case 1:
			model.addAttribute("error", "Tên chưa hợp lệ !");
			return "materialAdd";
		case 2:
			model.addAttribute("error", "Tên phải ít hơn 250 ký tự !");
			return "materialAdd";
		case 3:
			model.addAttribute("error", "Ghi chú phải ít hơn 250 ký tự !");
			return "materialAdd";
		case 4:
			model.addAttribute("error", "Số lượng từ 0 - 500!");
			return "materialAdd";
		case 5:
			model.addAttribute("error", "Số lượng tối thiểu từ 0 - 20 !");
			return "materialAdd";
		case 6:
			model.addAttribute("error", "Số lượng phải lớn hơn số lượng tối thiểu !");
			return "materialAdd";
		default:
			addIngredient(ingredient);
		}
		return checkAdmin("materialAdd");
	}

	@GetMapping("materialUpdate")
	public String showMaterialUpdate(@RequestParam("ingredientId") Integer ingredientId, Model model) {
		model.addAttribute("ingredientEdit", ingredientRepository.findById(ingredientId).orElseThrow());
		model.addAttribute("error", error) ;
		error = "" ;
		return checkAdmin("materialUpdate");
	}

	@PostMapping("materialUpdate")
	public String updtMaterial(@RequestParam int ingredientId, @RequestParam String name, @RequestParam String unit,
			@RequestParam int amount, @RequestParam int minAmount, @RequestParam String note, Model model) {
		Ingredient ingredientEdit = ingredientRepository.findById(ingredientId).orElseThrow();

		ingredientEdit.setName(name);
		ingredientEdit.setUnit(unit);
		ingredientEdit.setAmount(amount);
		ingredientEdit.setMinAmount(minAmount);
		ingredientEdit.setNote(note);

		switch (validateIngredient(ingredientEdit)) {
		case 0:
			error = "Vui lòng nhập đầy đủ thông tin !" ;
			return "redirect:/materialUpdate?ingredientId=" + ingredientEdit.getIngredientId();
		case 1:
			error = "Tên chưa hợp lệ !" ;
			return "redirect:/materialUpdate?ingredientId=" + ingredientEdit.getIngredientId();
		case 2:
			error = "Tên phải ít hơn 250 ký tự !" ;
			return "redirect:/materialUpdate?ingredientId=" + ingredientEdit.getIngredientId();
		case 3:
			error = "Ghi chú phải ít hơn 250 ký tự !" ;
			return "redirect:/materialUpdate?ingredientId=" + ingredientEdit.getIngredientId();
		case 4:
			error = "Số lượng từ 0 - 500!" ;
			return "redirect:/materialUpdate?ingredientId=" + ingredientEdit.getIngredientId();
		case 5:
			error = "Số lượng tối thiểu từ 0 - 20 !" ;
			return "redirect:/materialUpdate?ingredientId=" + ingredientEdit.getIngredientId();
		case 6:
			error = "Số lượng phải lớn hơn số lượng tối thiểu !" ;
			return "redirect:/materialUpdate?ingredientId=" + ingredientEdit.getIngredientId();
		default:
			ingredientRepository.save(ingredientEdit);
			return checkAdmin("materialUpdate");
		}

	}

	public String checkAdmin(String path) {
		StaffModel account = session.get("staff");
		if (account == null) {
			return "redirect:/login";
		}
		if (account.getRole().equals("USER")) {
			return "redirect:/home";
		}
		return path;
	}

	@Transactional
	public void addIngredient(Ingredient ingredient) {
		ingredientRepository.save(ingredient);
	}

	private int validateIngredient(Ingredient ingredient) {
		if (ingredient.getName().length() == 0 || ingredient.getNote().length() == 0
				|| ingredient.getUnit().length() == 0) {
			return 0;
		}
		if (!ingredient.getName().matches(regexName)) {
			return 1;
		}
		if (ingredient.getName().length() > 250) {
			return 2;
		}
		if (ingredient.getNote().length() > 250) {
			return 3;
		}
		if (ingredient.getAmount() <= 0 || ingredient.getAmount() > 500) {
			return 4;
		}
		if (ingredient.getMinAmount() <= 0 || ingredient.getMinAmount() > 20) {
			return 5;
		}
		if (ingredient.getAmount() <= ingredient.getMinAmount()) {
			return 6;
		}
		return -1;
	}
}
